package com.easy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/string")
public class StringController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${topic.name}")
    private String topicName;

    @PostMapping("/publish")
    public void publish(@RequestParam("message") String message) {
        this.kafkaTemplate.send(this.topicName, message);
    }
}
