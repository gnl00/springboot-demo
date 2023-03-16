package com.demo.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void queryAllUser() {
        mapper.queryAllUser().forEach(System.out::println);
    }

    @Test
    void queryUser() {
        log.info("user whose id = 1 : {}", mapper.queryUser(1));
    }

    @Test
    void countUser() {
        log.info("user count: {}", mapper.countUsers());
    }
}