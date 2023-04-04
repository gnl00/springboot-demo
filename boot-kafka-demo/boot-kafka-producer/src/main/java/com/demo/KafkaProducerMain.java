package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KafkaProducerMain {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerMain.class, args);
    }

    @Autowired
    private KafkaTemplate<String, Object> kafka;

    @GetMapping("/send/{data}")
    public String send(@PathVariable("data") String data) {
        kafka.send("topic-test", data);
        return "send " + data + " success";
    }
}