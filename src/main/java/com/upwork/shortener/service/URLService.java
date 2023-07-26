package com.upwork.shortener.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.upwork.shortener.dto.URLShortenerDTO;
import com.upwork.shortener.entity.URLShortener;
import com.upwork.shortener.repository.URLRepository;
import com.upwork.shortener.utils.Base62;

@Service
public class URLService  {
     
    @Autowired
    private URLRepository urlRepository;
     
    private final String domain;
     
    @Autowired
    public URLService(@Value("${domain.shortener}") String domain) {
        this.domain = domain;
    }

    /**
     * Reverse the original URL from the shortened URL
     */
    public URLShortenerDTO getURL(String shortenURL) {
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
                dto.setId(url.getId().toString());
                dto.setShortenedURL(shortenURL);
                dto.setOriginalURL(url.getOriginalURL());
                dto.setCreatedOn(url.getCreatedOn().toString());
            } 
        }
        return dto;
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
                // Otherwise, save a new original URL
                url.setId(urlRepository.getIdWithNextUniqueId());
                url.setOriginalURL(originalURL);
                url = urlRepository.save(url);
            }
        }
        //TODO Should handle the url didn't save successfully and return null.
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
        dto.setCreatedOn(url.getCreatedOn().toString());
         
        // Generate shortenedURL via base62 encode.
        String shortenedURL = this.domain +"/" + Base62.toBase62(url.getId().intValue());
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