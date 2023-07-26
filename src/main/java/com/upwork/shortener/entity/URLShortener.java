package com.upwork.shortener.entity;
 
import org.joda.time.DateTime;

import com.upwork.shortener.utils.DomainConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


 
@Entity
@Table(name="urls")
public class URLShortener implements DomainConstants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
     
    @Column(name = "created_on", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private DateTime createdOn = new DateTime();
     
    @Column(name = "original_url")
    private String originalURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }
}

