package com.rmq.sample.transaction;

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
     * 执行本地事务，并根据事务执行结果返回不同状态
     * @param msg
     * @param arg
     * @return LocalTransactionState
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        // 执行本地事务操作
        double r = Math.random() * 10;
        System.out.println("********************");
        System.out.println("开始执行本地事务");

        int index = transIndex.getAndIncrement();
        int status = index % 3;
        String transactionId = msg.getTransactionId();
        localTransMap.put(transactionId, status);

        if (r < 3) {
            System.out.println("本地事务执行完成，返回状态： UNKNOW");
            System.out.println("更新本地消息表，Id为 " + transactionId);
            localTransMap.put(transactionId, 0);
            return LocalTransactionState.UNKNOW;
        } else if( r > 3 && r < 6) {
            System.out.println("本地事务执行完成，返回状态： COMMIT_MESSAGE");
            System.out.println("更新本地消息表，Id为 " + transactionId);
            localTransMap.put(transactionId, 1);
            return LocalTransactionState.COMMIT_MESSAGE;
        } else {
            System.out.println("本地事务执行完成，返回状态： ROLLBACK_MESSAGE");
            System.out.println("更新本地消息表，Id为 " + transactionId);
            localTransMap.put(transactionId, 2);
            return LocalTransactionState.ROLLBACK_MESSAGE;

        }
    }

    /**
     * 检查本地事务状态，并回复消息队列（Message Queue）的事务状态检查请求
     * @param msg
     * @return LocalTransactionState
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        System.out.println("********************");
        String transactionId = msg.getTransactionId();
        System.out.println("Id为 " + transactionId + " 的事务消息状态不确定，回查本地事务表...");
        Integer status = localTransMap.get(msg.getTransactionId());
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
