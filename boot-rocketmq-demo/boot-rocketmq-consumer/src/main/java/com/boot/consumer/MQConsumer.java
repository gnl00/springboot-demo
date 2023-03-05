package com.boot.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ConsumerMainApp
 *
 * @author lgn
 * @since 2022/2/22 11:37
 */

@SpringBootApplication
public class MQConsumer {
    public static void main(String[] args) {
        SpringApplication.run(MQConsumer.class, args);
    }
}
