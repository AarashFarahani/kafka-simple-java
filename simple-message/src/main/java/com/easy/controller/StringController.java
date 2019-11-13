package com.easy.controller;

import com.easy.model.Person;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/string")
public class StringController {
    @Autowired private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${topic.name}") private String topicName;
    @Autowired private KafkaConsumer<String, String> kafkaConsumer;
    @Autowired private Gson gson;

    @PostMapping("/publish")
    public void publish(@RequestParam("message") String message) {
        this.kafkaTemplate.send(this.topicName, message);
    }

    @GetMapping("/consume")
    public List consume() {
        this.kafkaConsumer.subscribe(Arrays.asList(this.topicName));
        ConsumerRecords<String, String> records = this.kafkaConsumer.poll(Duration.ofSeconds(10));
        List list = new ArrayList<>();
        Iterator<ConsumerRecord<String, String>> iterator = records.iterator();

        while (iterator.hasNext()) {
            list.add(this.toPerson(iterator.next().value()));
        }

        return list;
    }

    private Object toPerson(String message) {
        try {
            return gson.fromJson(message, Person.class);
        } catch (Exception ex) {
            return message;
        }
    }
}
