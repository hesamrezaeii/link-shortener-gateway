package com.example.firstproject.db;

import com.example.firstproject.service.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface UrlRepository extends MongoRepository<Url,String> {
    Url findByShortUrl(String shortUrl);
    Url findByOriginalUrl(String originalUrl);
}

