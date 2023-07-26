package com.upwork.shortener.controler;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.upwork.shortener.dto.URLShortenerDTO;
import com.upwork.shortener.exception.ExpiredUrlException;
import com.upwork.shortener.exception.URLNotFoundException;
import com.upwork.shortener.service.URLService;

@RestController
@RequestMapping("/api/url")
public class URLShortenerControler {
    @Autowired
    private URLService urlService;
    
    @RequestMapping(value = "/shorten",
            method = RequestMethod.POST,
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public URLShortenerDTO saveURL(@RequestParam(value = "originalURL") String originalURL) {
        return urlService.saveUrl(originalURL);
    }
        
    @RequestMapping(value = "/reverse", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public URLShortenerDTO getURL(@RequestParam(value = "shortenedURL") String shortenedURL) throws URLNotFoundException, ExpiredUrlException {
        return urlService.getURL(shortenedURL);
    }
}
