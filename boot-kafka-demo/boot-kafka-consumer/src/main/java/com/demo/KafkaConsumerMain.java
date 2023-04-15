package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class KafkaConsumerMain {
    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerMain.class, args);
    }


    @KafkaListener(topics = {"topic-test"}, groupId = "group-test")
    public void receive(String message) {
        System.out.println("[received] " + message);
    }
}