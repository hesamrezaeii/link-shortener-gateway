package com.example.firstproject.db;

import com.example.firstproject.service.UrlClick;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UrlClickRepository extends MongoRepository<UrlClick,String> {
    UrlClick findByShortUrl(String shortUrl);
    UrlClick findByOriginalUrl(String originalUrl);
    List<UrlClick> findByCreatedDateBetween(@Param("from") Date from, @Param("to") Date to);

}
