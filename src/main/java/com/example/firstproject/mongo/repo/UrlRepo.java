package com.example.firstproject.mongo.repo;

import com.example.firstproject.mongo.document.Url;
import com.example.firstproject.mongo.repo.extra.UrlRepoExtra;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepo extends MongoRepository<Url, String>, UrlRepoExtra {
    Url findByShortUrl(String shortUrl);

    Url findByOriginalUrl(String originalUrl);
}

