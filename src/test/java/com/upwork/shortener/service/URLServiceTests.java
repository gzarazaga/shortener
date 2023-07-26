package com.upwork.shortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.boot.test.context.SpringBootTest;

import com.upwork.shortener.dto.URLShortenerDTO;
import com.upwork.shortener.entity.URLShortener;
import com.upwork.shortener.exception.ExpiredUrlException;
import com.upwork.shortener.exception.URLNotFoundException;
import com.upwork.shortener.repository.URLRepository;

@SpringBootTest
public class URLServiceTests {

    @InjectMocks
    private URLService urlService;

    @Mock
    private URLRepository urlRepository;

    @Test
    void getURLTest() throws URLNotFoundException, ExpiredUrlException, MockitoException {
        URLShortener urlShortener = new URLShortener();
        urlShortener.setId(1l);
        urlShortener.setOriginalURL("www.test.com");
        urlShortener.setCreatedOn(ZonedDateTime.now());

        when(urlRepository.findById(anyLong())).thenReturn(Optional.of(urlShortener));
        URLShortenerDTO dto = urlService.getURL("c");

        assertEquals(dto.getId(), urlShortener.getId().toString());
        assertNotNull(dto.getCreatedOn());
        assertEquals(dto.getOriginalURL(), urlShortener.getOriginalURL());
        assertNotNull(dto.getShortenedURL());
    }

    @Test
    void getURLExpiredURLTest() {
        assertThrows(ExpiredUrlException.class,
            ()->{
                URLShortener urlShortener = new URLShortener();
                urlShortener.setId(1l);
                urlShortener.setOriginalURL("www.test.com");
                urlShortener.setCreatedOn(ZonedDateTime.now().minusDays(4));
        
                when(urlRepository.findById(anyLong())).thenReturn(Optional.of(urlShortener));
                URLShortenerDTO dto = urlService.getURL("c");
            });
    }

    @Test
    void getURLNotFoundTest() {
        assertThrows(URLNotFoundException.class,
            ()->{
                URLShortenerDTO dto = urlService.getURL("c");
            });
    }

}
