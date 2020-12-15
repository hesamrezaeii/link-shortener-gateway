package com.example.firstproject.mongo.repo.extra;

import com.example.firstproject.mongo.document.Url;
import com.example.firstproject.mongo.repo.UrlRepo;

import java.util.List;

public interface UrlRepoExtra {
    List<Url> findLimitedUrls(int limit);
}
