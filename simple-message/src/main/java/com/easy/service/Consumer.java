package com.easy.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    @KafkaListener(topics = "${topic.name}", groupId = "testTopicOtherGroup")
    public void consume(String message){
        System.out.println("**********************************************************************************");
        System.out.println(message);
    }
}
