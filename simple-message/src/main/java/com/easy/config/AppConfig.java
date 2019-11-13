package com.easy.config;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class AppConfig {
    @Value("${spring.kafka.consumer.group-id}") private String zookeeperGroupId;
    @Value("${spring.kafka.consumer.bootstrap-servers}") private String kafkaBootstrapServers;
    @Value("${spring.kafka.consumer.key-deserializer}") private String keyDeserializer;
    @Value("${spring.kafka.consumer.value-deserializer}") private String valueDeserializer;

    @Bean
    public Gson producerConfigs() {
        return new Gson();
    }

    @Bean
    public KafkaConsumer<String, String> kafkaConsumer() {
        Properties consumerProperties = new Properties();
        consumerProperties.put("bootstrap.servers", kafkaBootstrapServers);
        consumerProperties.put("group.id", zookeeperGroupId);
        consumerProperties.put("zookeeper.session.timeout.ms", "1000");
        consumerProperties.put("zookeeper.sync.time.ms","2000");
        consumerProperties.put("auto.commit.enable", "false");
        consumerProperties.put("auto.commit.interval.ms", "1000");
        consumerProperties.put("consumer.timeout.ms", "-1");
        consumerProperties.put("max.poll.records", "10");
        consumerProperties.put("value.deserializer", valueDeserializer);
        consumerProperties.put("key.deserializer", keyDeserializer);

        return new KafkaConsumer<String, String>(consumerProperties);
    }
}
