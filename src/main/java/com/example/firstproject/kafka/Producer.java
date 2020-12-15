package com.example.firstproject.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    private static final String TOPIC = "urlClick";
    @Autowired
    private KafkaTemplate<String, UrlClickMessage> kafkaTemplate;

    public void sendMessage(UrlClickMessage message) {
        kafkaTemplate.send(TOPIC, message);
    }

}
