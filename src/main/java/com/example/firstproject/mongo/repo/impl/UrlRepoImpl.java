package com.example.firstproject.mongo.repo.impl;

import com.example.firstproject.mongo.document.Url;
import com.example.firstproject.mongo.repo.extra.UrlRepoExtra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UrlRepoImpl implements UrlRepoExtra {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public List<Url> findLimitedUrls(int limit) {
        Query query = new Query().limit(limit).with(Sort.by(Sort.Order.desc(Url.CreationDate_Col)));
        return mongoOperations.find(query, Url.class);
    }
}
