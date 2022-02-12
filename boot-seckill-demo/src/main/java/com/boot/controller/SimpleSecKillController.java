package com.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SimpleSecKillController
 *
 * @author lgn
 * @since 2022/2/12 11:11
 */

@Slf4j
@RestController
@RequestMapping("/seckill")
public class SimpleSecKillController {

    @Value("${goods.stock}")
    private Integer goodsStock;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,
            Runtime.getRuntime().availableProcessors() * 2,
            5L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(8),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
            );

    @GetMapping("/getStock")
    public Integer getGoodsStock() {
        return goodsStock;
    }

    @GetMapping("/setStock")
    public void setGoodsStock() {
        this.goodsStock = 100;
    }

    /**
     * 单体应用，单线程
     */
    @GetMapping("/sk")
    public String sk() {
        if (goodsStock > 0) {
            synchronized (this) {
                if (goodsStock > 0) {
                    goodsStock--;
                    log.info("库存扣减[成功]，剩余商品: {}", goodsStock);
                } else {
                    log.info("库存扣减[失败]，剩余商品: {}", goodsStock);
                }
            }
        } else {
            log.info("库存扣减[失败]，剩余商品: {}", goodsStock);
        }
        return "操作成功";
    }

    /**
     * 单体应用，多线程 synchronized
     */
    @GetMapping("/skSync")
    public String skThread() {
        executor.execute(() -> {
            log.info("当前线程名：{}", Thread.currentThread().getName());
            if (goodsStock > 0) {
                synchronized (this) {
                    if (goodsStock > 0) {
                        goodsStock--;
                        log.info("线程 {} 执行库存扣减[成功]，剩余商品: {}", Thread.currentThread().getName(), goodsStock);
                    } else {
                        log.info("线程 {} 执行库存扣减[失败]，剩余商品: {}", Thread.currentThread().getName(), goodsStock);
                    }
                }
            } else {
                log.info("线程 {} 执行库存扣减[失败]，剩余商品: {}", Thread.currentThread().getName(), goodsStock);
            }
        });
        return "操作成功";
    }

    private Lock lock = new ReentrantLock();

    /**
     * 单体应用，多线程 lock
     */
    @GetMapping("/skLock")
    public String skThreadLock() {
        executor.execute(() -> {
            if (goodsStock > 0) {
                lock.lock();
                try {
                    log.info("线程 {} 获取到锁，开始减库存...", Thread.currentThread().getName());
                    if (goodsStock > 0) {
                        goodsStock--;
                        log.info("线程 {} 执行库存扣减[成功]，剩余商品: {}", Thread.currentThread().getName(), goodsStock);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    log.info("线程 {} 释放锁...", Thread.currentThread().getName());
                }
            } else {
                log.info("线程 {} 执行库存扣减[失败]，剩余商品: {}", Thread.currentThread().getName(), goodsStock);
            }

        });

        return "操作成功";
    }

}
