package com.boot.test;

import jodd.time.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Time;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

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
    public void testAtomicLong() {
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
    public void testAtomicDouble() {
        // 原子浮点 AtomicDouble 默认值是0
        RAtomicDouble atDB = redissonClient.getAtomicDouble("atDB");
        atDB.set(1.5);
        System.out.println(atDB.get());
        // 获取到 1.5 再加上1.23，返回它们的和
        atDB.getAndAdd(1.23);
        System.out.println(atDB.get());
    }

    @Test
    public void testLongAdder() {

        // LongAdder 的功能类似于 AtomicLong，但是高并发性能会更好，适合高并发时的计数
        RLongAdder lAdder = redissonClient.getLongAdder("lAdder");
        lAdder.add(100l);
        lAdder.decrement();
        lAdder.decrement();
        // sum() 即为 get()
        System.out.println(lAdder.sum());

    }

    @Test
    public void testRateLimiter() {
        // 限流器 不保证公平性
        RRateLimiter rateLimiter = redissonClient.getRateLimiter("rateLimiter");
        // RateType.OVERALL 限制所有 Redisson 实例的操作
        // RateType.PER_CLIENT 限制当前 Redisson 实例的操作
        rateLimiter.trySetRate(RateType.OVERALL, 3, 5, RateIntervalUnit.SECONDS);
        rateLimiter.acquire();
    }


    @Test
    public void testGetLock() {
        // getLock() 获取非公平锁
        RLock myRLock = redissonClient.getLock("myRLock");
        // 不带过期时间的 lock 需要手动 unlock
        myRLock.lock();
        try {
            log.info("testGetLock 获取锁成功，进行业务操作...");
            Thread.sleep(45 * 1000);
            log.info("testGetLock 业务操作完成，进行解锁操作...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 解锁
            myRLock.unlock();
        }
    }

    /**
     * tryLock()
     * 尝试获取锁，无参数，会尝试获取锁一次，成功返回 true，失败返回 false
     */
    @Test
    public void testTryLock() {

        RLock myRLock = redissonClient.getLock("myRLock");

        boolean tryL = myRLock.tryLock();
        log.info("testTryLock 尝试获取锁...{}", tryL);

        if (tryL) {
            myRLock.lock();
            try {
                log.info("testTryLock 尝试获取锁成功，进行业务操作。。。");
                TimeUnit.SECONDS.sleep(5);
                log.info("testTryLock 业务操作完成，释放锁。。。");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myRLock.unlock();
            }
        }
    }


    /**
     * 在 lock 还未释放的情况下进行 lock 操作
     * 会进入阻塞状态一直等待获取锁，直到获取成功，然后 lock，最后 unlock
     */
    @Test
    public void testLockWhenLock() {

        RLock myRLock = redissonClient.getLock("myRLock");

        log.info("testLockLock lock开始。。。{}", System.currentTimeMillis());
        myRLock.lock();
        log.info("testLockLock lock完成。。。{}", System.currentTimeMillis());

        try {
            log.info("testLockLock 进行业务操作。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myRLock.lock();
        }

    }

    @Test
    public void testLockWithExpire() {
        RLock myRLock = redissonClient.getLock("myRLock");
        try {
            // 带过期时间的lock
            myRLock.lock(10, TimeUnit.SECONDS);
            log.info("locked...");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myRLock.unlock();
        }
    }

    @Test
    public void testGetDiffLock() {
        RLock lock = redissonClient.getLock("lock");
        // 获取公平锁
        RLock fairLock = redissonClient.getFairLock("fairLock");
        // 获取多重锁
        RLock multiLock = redissonClient.getMultiLock(lock, fairLock);
        // 获取 redLock
        RLock redLock = redissonClient.getRedLock(lock, fairLock);
    }

}
