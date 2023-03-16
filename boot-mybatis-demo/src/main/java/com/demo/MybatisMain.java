package com.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
@MapperScan(basePackages = {"com.demo.dao"})
public class MybatisMain {
    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(MybatisMain.class, args);
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(System.out::println);

    }
}