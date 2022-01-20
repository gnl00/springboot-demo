package com.boot.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedissonMainAppTest
 *
 * @author lgn
 * @since 2022/1/19 18:24
 */

@Slf4j
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
            redisTemplate.opsForValue().set("k333", "333");
            log.info("redis SET: {}", "success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void test3() {
        // 原子整型 AtomicLong

        // 设置key名
        String key = "myAtomicLong";
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        // 设置对应的值
        atomicLong.set(1);
        atomicLong.getAndIncrement();
        System.out.println(atomicLong.get());
        atomicLong.compareAndSet(2, 5);
        System.out.println(atomicLong.get());
    }

    @Test
    public void test4() {
        // 原子浮点 AtomicDouble 默认值是0
        RAtomicDouble atDB = redissonClient.getAtomicDouble("atDB");
        atDB.set(1.5);
        System.out.println(atDB.get());
        // 获取到 1.5 再加上1.23，返回它们的和
        atDB.getAndAdd(1.23);
        System.out.println(atDB.get());
    }

    @Test
    public void test5() {

        // LongAdder 的功能类似于 AtomicLong，但是高并发性能会更好，适合高并发时的计数

        RLongAdder lAdder = redissonClient.getLongAdder("lAdder");
        lAdder.add(100l);
        lAdder.decrement();
        lAdder.decrement();
        // sum() 即为 get()
        System.out.println(lAdder.sum());

    }

    @Test
    public void test6() {

        // 限流器 不保证公平性

        RRateLimiter rateLimiter = redissonClient.getRateLimiter("rateLimiter");
        // RateType.OVERALL 限制所有 Redisson 实例的操作
        // RateType.PER_CLIENT 限制当前 Redisson 实例的操作
        rateLimiter.trySetRate(RateType.OVERALL, 3, 5, RateIntervalUnit.SECONDS);
        rateLimiter.acquire();
    }

}
