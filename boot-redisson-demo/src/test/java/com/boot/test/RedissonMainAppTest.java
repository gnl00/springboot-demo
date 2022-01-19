package com.boot.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedissonMainAppTest
 *
 * @author lgn
 * @since 2022/1/19 18:24
 */

@SpringBootTest
public class RedissonMainAppTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test() {
        System.out.println(redisTemplate);
    }

    @Test
    public void test2() {
        try {
            redisTemplate.opsForValue().set("k111", "v1111");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(redisTemplate);
    }

}
