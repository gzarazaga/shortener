package com.upwork.shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upwork.shortener.entity.URLShortener;

import java.util.Optional;

@Repository
public interface URLRepository extends JpaRepository<URLShortener, Long>{
    @Query(value = "from URLShortener u where u.id = :id")
    Optional<URLShortener> findById(@Param(value = "id") Long id);
     
    @Query(value = "from URLShortener u where u.originalURL = :originalURL")
    Optional<URLShortener> findByOriginalURL(@Param(value = "originalURL") String originalURL);
     
    @Query(nativeQuery = true, value = "SELECT nextval('seq_unique_id')") 
    Long getIdWithNextUniqueId() ;
     
}