package com.boot.rocketmq.listener;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事务消息监听器
 *
 * @author lgn
 * @since 2022/2/17 16:36
 */

public class TransactionListenerImpl implements TransactionListener {

    // 全局事务标识
    private AtomicInteger transIndex = new AtomicInteger(0);
    // 本地事务表
    private ConcurrentHashMap<String, Integer> localTransMap = new ConcurrentHashMap<>();

    /**
     * 执行本地事务
     * @param msg
     * @param arg
     * @return LocalTransactionState
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        int index = transIndex.getAndIncrement();
        int status = index % 3;
        String transactionId = msg.getTransactionId();
        System.out.println("transactionId " + transactionId);
        localTransMap.put(transactionId, status);
        return LocalTransactionState.UNKNOW;
    }

    /**
     * 检查本地事务状态，并回复消息队列（Message Queue）的事务状态检查请求
     * @param msg
     * @return LocalTransactionState
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        Integer status = localTransMap.get(msg.getTransactionId());
        System.out.println("回查本地事务");
        if (null != status) {
            switch (status) {
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
