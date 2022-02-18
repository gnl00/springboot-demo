package com.boot.rocketmq.simple.producer;

import com.boot.rocketmq.simple.listener.TransactionListenerImpl;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 事务消息生产者
 *
 * @author lgn
 * @since 2022/2/17 16:47
 */

public class TransactionProducer {
    public static void main(String[] args) {
        TransactionListenerImpl listener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("gp_default");
        producer.setNamesrvAddr("localhost:9876");
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        producer.setExecutorService(executor);
        producer.setTransactionListener(listener);
        try {
            producer.start();

            for (int i = 0; i < 10; i++) {
                Message msg = new Message("tp_default", "tg_trans", "KEY" + i,
                        ("hi, this is transaction msg from TransactionProducer " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

                TransactionSendResult result = producer.sendMessageInTransaction(msg, null);
                System.out.println("********************");
                System.out.println("发送第 " + i +" 条消息 " + result);

                Thread.sleep(1000);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }

    }
}
