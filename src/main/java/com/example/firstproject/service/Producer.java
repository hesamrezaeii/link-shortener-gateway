package com.example.firstproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    private static final String TOPIC = "urlstats";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String url){
        kafkaTemplate.send(TOPIC,url);
    }

}
