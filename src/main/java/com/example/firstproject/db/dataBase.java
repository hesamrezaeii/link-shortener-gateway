package com.example.firstproject.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class dataBase implements CommandLineRunner {
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UrlClickRepository urlClickRepository;
    @Override
    public void run(String... args) throws Exception {

    }
}