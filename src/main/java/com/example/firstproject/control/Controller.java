package com.example.firstproject.control;

import com.example.firstproject.db.UrlRepository;
import com.example.firstproject.service.ClickHourly;
import com.example.firstproject.service.Producer;
import com.example.firstproject.service.Url;
import com.example.firstproject.service.UrlClick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {

    private Producer producer;
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping("/all")
    List<Url> all() {

        List<Url> urls = urlRepository.findAll().stream()
                .collect(Collectors.toList());
        return urls;
    }

    @GetMapping("/ten-short-urls")
    List<String> shortUrl() {

        List<String> shortUrls = urlRepository.findAll()
                .stream().map(Url::getShortUrl).
                        limit(10).collect(Collectors.toList());


        return shortUrls;
    }

    @GetMapping("/{shortUrl}")
    Url originallyUrl(@PathVariable String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl); //
        return url;
    }

    @PostMapping("/")
    Url shortUrl(HttpServletRequest request, @RequestBody Url url) {
        Url exist = urlRepository.findByOriginalUrl(url.getOriginalUrl());
        if (exist != null) {
            return null;
        }
        int random = (int) ((Math.random() * 89999) + (10000));
        String shortUrl = ("") + random;
        String remoteAddr = "";

        //not working good
        String ipAddress = request.getRemoteAddr();

//        String shortUrl = ("") + url.hashCode();
        url.setShortUrl(shortUrl);
        url.setIp(ipAddress);
        this.urlRepository.insert(url);
        return url;

    }

    @PostMapping("/click/{url}")
    void click(@PathVariable String url) {
        Url exist = urlRepository.findByShortUrl(url);
        if (exist != null) {
            kafkaTemplate.send("urlstats", url);
        }
    }

    @DeleteMapping("/all")
    String deleteAll() {
        urlRepository.deleteAll();
        return "cleared";
    }
}
