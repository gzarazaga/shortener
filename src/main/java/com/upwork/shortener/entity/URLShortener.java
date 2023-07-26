package com.upwork.shortener.entity;
 
import java.time.ZonedDateTime;

import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import com.upwork.shortener.utils.DomainConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


 
@Entity
@Table(name="urls")
public class URLShortener implements DomainConstants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
     
    @Column(name = "created_on", updatable = false)
    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE_UTC)
    private ZonedDateTime createdOn;
     
    @Column(name = "original_url")
    private String originalURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }
}

