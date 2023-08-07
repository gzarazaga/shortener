package com.upwork.shortener.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.upwork.shortener.ShortenerApplication;
import com.upwork.shortener.config.H2TestProfileJPAConfig;

@SpringBootTest(classes = {ShortenerApplication.class, H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
public class Base62Tests {

    @Autowired
    private Base62 base62;
    
    @Test
	void toBase62Test() {

        String result = base62.toBase62(2233734704544l);
        assertEquals(result, "NunTllI");

	}

    @Test
	void toBase10Test() {

        long result = base62.toBase10("https://www.mundodeportivo.com/uncomo/animales/articulo/como-cuidar-un-conejo-domestico-7287.html");
        assertEquals(result, 2233734704544l);
	}
}
