package com.example.firstproject.controller;

import com.example.firstproject.mongo.document.Url;
import com.example.firstproject.mongo.repo.UrlRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private UrlRepo urlRepo;

    @GetMapping("/ten-short-urls")
    public List<String> shortUrl() {
        return urlRepo.findLimitedUrls(10).stream()
                .map(Url::getShortUrl)
                .collect(Collectors.toList());
    }
}
