package com.boot.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Main
 *
 * @author lgn
 * @since 2022/2/12 14:04
 */

@Slf4j
public class Main {

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(2, Runtime.getRuntime().availableProcessors() * 2, 2L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        Runnable myRun = new MyRunLock();
        Thread thread1 = new Thread(myRun);
        Thread thread2 = new Thread(myRun);

        thread1.start();
        thread2.start();

    }
}

@Slf4j
class MyRun implements Runnable {

    private volatile int stock = 100;

    @Override
    public void run() {
        while (true) {
            if (stock > 0) {
                synchronized (MyRun.class) {
                    if (stock > 0) {
                        log.info("{} 获得第 {} 件商品", Thread.currentThread().getName(), stock);
                        stock--;
                    } else {
                        log.info("商品销售完");
                        break;
                    }
                }
            } else {
                log.info("商品销售完");
                break;
            }
        }
    }
}

@Slf4j
class MyRunLock implements Runnable {

    private Lock lock = new ReentrantLock();
    private volatile int stock = 100;

    @Override
    public void run() {
        while (true) {
            if (stock > 0) {
                lock.lock();
                try {
                    if (stock > 0) {
                        log.info("MyRunLock {} 获得第 {} 件商品", Thread.currentThread().getName(), stock);
                        stock--;
                    } else {
                        log.info("MyRunLock 商品销售完");
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } else {
                log.info("MyRunLock 商品销售完");
                break;
            }
        }
    }
}
