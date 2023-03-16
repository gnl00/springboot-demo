package com.boot.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;

/**
 * ConsumerMainApp
 *
 * @author lgn
 * @since 2022/2/22 11:37
 */

@SpringBootApplication
public class MQConsumer {
    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(MQConsumer.class, args);
        ac.getEnvironment();
    }
}
