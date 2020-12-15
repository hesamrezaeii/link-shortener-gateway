package com.example.firstproject.controller;

import com.example.firstproject.kafka.Producer;
import com.example.firstproject.kafka.UrlClickMessage;
import com.example.firstproject.manager.UrlManager;
import com.example.firstproject.mongo.document.Url;
import com.example.firstproject.mongo.repo.UrlRepo;
import com.example.firstproject.request.MakeShortLinkRequest;
import com.example.firstproject.response.MakeShortUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UrlRepo urlRepo;
    @Autowired
    private Producer producer;
    @Autowired
    private UrlManager urlManager;

    @GetMapping("/all")
    public List<Url> all() {
        return urlRepo.findAll();
    }

    @GetMapping("/{shortUrl}")
    public Url originallyUrl(@PathVariable String shortUrl) {
        Url url = urlRepo.findByShortUrl(shortUrl); //
        return url;
    }

    @PostMapping("/make")
    public MakeShortUrlResponse shortUrl(@RequestBody MakeShortLinkRequest request) {
        Url url = urlManager.getOrMakeNewUrl(request);
        MakeShortUrlResponse response = new MakeShortUrlResponse();
        response.setShortUrl(url.getShortUrl());
        return response;
    }

    @GetMapping("/click/{url}")
    public void click(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @PathVariable String url) {
        Url exist = urlRepo.findByShortUrl(url);
        if (exist == null) {
            // return 404
            return;
        }
        UrlClickMessage message = new UrlClickMessage();
        message.setDate(Calendar.getInstance().getTime());
        message.setIp(httpRequest.getRemoteAddr());
        message.setOriginalUrl(exist.getOriginalUrl());
        message.setShortUrl(exist.getShortUrl());
        message.setUserAgent(httpRequest.getHeader("User-Agent"));
        producer.sendMessage(message);

        try {
            httpResponse.sendRedirect(exist.getOriginalUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/all")
    public String deleteAll() {
        urlRepo.deleteAll();
        return "cleared";
    }
}
