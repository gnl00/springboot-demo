package com.boot.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ProducerMainApp
 *
 * @author lgn
 * @since 2022/2/22 11:37
 */

@SpringBootApplication
public class MQProducer {
    public static void main(String[] args) {
        SpringApplication.run(MQProducer.class, args);
    }
}
