package com.example.firstproject.control;

import com.example.firstproject.db.ClickHourlyRepository;
import com.example.firstproject.db.UrlClickRepository;
import com.example.firstproject.db.UrlRepository;
import com.example.firstproject.service.ClickHourly;
import com.example.firstproject.service.Producer;
import com.example.firstproject.service.Url;
import com.example.firstproject.service.UrlClick;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@RestController
public class Controller {

    private  Producer producer;
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UrlClickRepository urlClickRepository;
    @Autowired
    private ClickHourlyRepository clickHourlyRepository;
    @Autowired
    private KafkaTemplate kafkaTemplate ;

    @GetMapping("/all")
    List<Url> all() {

        List<Url> urls = urlRepository.findAll().stream()
                .collect(Collectors.toList());

        return urls;
    }
    @GetMapping("/clicksStat")
    List<ClickHourly> ClickHourly() {

        List<ClickHourly> stats = clickHourlyRepository.findAll().stream()
                .collect(Collectors.toList());

        return stats;
    }
    @GetMapping("/pastHourClick")
    List<UrlClick> allUrlClickFromHourAg() {

        Date now = new Date();

        Date hourAgo = new Date(System.currentTimeMillis() - (60 * 60 * 1000));

        List<UrlClick> urls = urlClickRepository.findByCreatedDateBetween(hourAgo,now);

        return urls;
    }
    @GetMapping("/{shortUrl}")
    Url originallyUrl(@PathVariable String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl); //
        return url;
    }

    @PostMapping("/")
    Url shortUrl(HttpServletRequest request , @RequestBody Url url) {
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
//        Url url1 = new Url(url.getOriginalUrl(),url.getShortUrl());
//        System.out.println(url1);
       kafkaTemplate.send("urlstats",url);
    }

    @DeleteMapping("/all")
    String deleteAll() {

        urlRepository.deleteAll();
        urlClickRepository.deleteAll();
        clickHourlyRepository.deleteAll();

        return " Cleared Collections";
    }
}
