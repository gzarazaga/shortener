package com.upwork.shortener.dto;

public class URLShortenerDTO {
    private String id;
    private String originalURL;
    private String createdOn;
    private String shortenedURL;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOriginalURL() {
        return originalURL;
    }
    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }
    public String getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
    public String getShortenedURL() {
        return shortenedURL;
    }
    public void setShortenedURL(String shortenedURL) {
        this.shortenedURL = shortenedURL;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((originalURL == null) ? 0 : originalURL.hashCode());
        result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
        result = prime * result + ((shortenedURL == null) ? 0 : shortenedURL.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        URLShortenerDTO other = (URLShortenerDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (originalURL == null) {
            if (other.originalURL != null)
                return false;
        } else if (!originalURL.equals(other.originalURL))
            return false;
        if (createdOn == null) {
            if (other.createdOn != null)
                return false;
        } else if (!createdOn.equals(other.createdOn))
            return false;
        if (shortenedURL == null) {
            if (other.shortenedURL != null)
                return false;
        } else if (!shortenedURL.equals(other.shortenedURL))
            return false;
        return true;
    }
    
}
