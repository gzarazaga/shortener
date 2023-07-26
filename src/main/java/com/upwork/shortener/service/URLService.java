package com.upwork.shortener.service;

import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.upwork.shortener.dto.URLShortenerDTO;
import com.upwork.shortener.entity.URLShortener;
import com.upwork.shortener.exception.ExpiredUrlException;
import com.upwork.shortener.exception.URLNotFoundException;
import com.upwork.shortener.repository.URLRepository;
import com.upwork.shortener.utils.Base62;

@Service
public class URLService {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLService.class);

    @Autowired
    private URLRepository urlRepository;
     
    private final String domain;
    private final int retentionPeriodinDays;
     
    @Autowired
    public URLService(@Value("${domain.shortener}") String domain, @Value("${domain.retentionperiod.days}") int retentionPeriodinDays) {
        this.domain = domain;
        this.retentionPeriodinDays = retentionPeriodinDays;
    }

    /**
     * Reverse the original URL from the shortened URL
     */
    public URLShortenerDTO getURL(String shortenURL) throws URLNotFoundException, ExpiredUrlException {
        URLShortenerDTO dto = new URLShortenerDTO();
        if (validateURL(shortenURL)) {
            //Remove domain to shortened URL if possible.
            String str = shortenURL.replace(this.domain +"/", "");
             
            // Resolve a shortened URL to the initial ID.
            long id = Base62.toBase10(str);
            // Now find your database-record with the ID found
            Optional<URLShortener> urlShortener = urlRepository.findById(id);
             
            if(urlShortener.isPresent()) {
                // Mapped domain to DTO
                URLShortener url = urlShortener.get();
                if (validateExpiration(url)) {
                    dto.setId(url.getId().toString());
                    dto.setShortenedURL(shortenURL);
                    dto.setOriginalURL(url.getOriginalURL());
                    dto.setCreatedOn(url.getCreatedOn().toString());
                } else {
                    throw new ExpiredUrlException("Shorten URL has expired.");
                }
            } else {
                throw new URLNotFoundException("Shorten URL was not found.");
            }
        }
        return dto;
    }
     
    private boolean validateExpiration(URLShortener urlShortener) {
        boolean result = false;
        Period diff = Period.between(urlShortener.getCreatedOn().toLocalDate(),ZonedDateTime.now().toLocalDate());
        result = diff.getDays() <= retentionPeriodinDays;
        return result;
    }

    /**
     * Save an original URL to database and then
     * generate a shortened URL.
     */
    public URLShortenerDTO saveUrl(String originalURL) {
        URLShortener url = new URLShortener();
        if (validateURL(originalURL)) {
            originalURL = sanitizeURL(originalURL);
            // Quickly check the originalURL is already entered in our system.
            Optional<URLShortener> exitURL = urlRepository.findByOriginalURL(originalURL);
         
            if(exitURL.isPresent()) {
                // Retrieved from the system.
                url = exitURL.get();
            } else {
                try {
                    // Otherwise, save a new original URL
                    url.setId(urlRepository.getIdWithNextUniqueId());
                    url.setOriginalURL(originalURL);
                    url.setCreatedOn(ZonedDateTime.now());
                    url = urlRepository.save(url);
                } catch (Exception e) {
                    LOGGER.error("Error saving url {}", e);
                }
            }
        }
        return generateURLShorterner(url);
    }
    /**
     * Generate a shortened URL.
     */
    private URLShortenerDTO generateURLShorterner(URLShortener url) {
        // Mapped domain to DTO
        URLShortenerDTO dto = new URLShortenerDTO();
        dto.setId(url.getId().toString());
        dto.setOriginalURL(url.getOriginalURL());
        dto.setCreatedOn(Optional.ofNullable(url.getCreatedOn()).orElse(ZonedDateTime.now()).toString());
         
        // Generate shortenedURL via base62 encode.
        String shortenedURL = this.domain +"/" + Base62.toBase62(url.getId());
        dto.setShortenedURL(shortenedURL);
        return dto;
    }
     
    /**
     * Validate URL not implemented, but should be implemented to 
     * check whether the given URL is valid or not
     */
    private boolean validateURL(String url) {
        return true;
    }
     
    /** 
     * This method should take into account various issues with a valid url
     * e.g. www.google.com,www.google.com/, http://www.google.com,
     * http://www.google.com/
     * all the above URL should point to same shortened URL
     * There could be several other cases like these.
     */
    private String sanitizeURL(String url) {
        if (url.substring(0, 7).equals("http://"))
            url = url.substring(7);
 
        if (url.substring(0, 8).equals("https://"))
            url = url.substring(8);
 
        if (url.charAt(url.length() - 1) == '/')
            url = url.substring(0, url.length() - 1);
        return url;
    }
 
}