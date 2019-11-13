package com.easy.controller;

import com.easy.model.Person;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired private Gson gson;
    @Autowired private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${topic.name}") private String topicName;

    @PostMapping("/publish")
    public void publish(@RequestBody Person person) {
        String json = this.gson.toJson(person);
        this.kafkaTemplate.send(this.topicName, json);
    }
}
