package com.rmq.sample.producer;

import com.rmq.sample.contant.SimpleMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * SimpleProducer
 * <p>生产之前先检查 broker.conf 配置文件中的 brokerIP1 是否为本机 IP</p>
 *
 * @author lgn
 * @since 2022/2/17 14:06
 */

@Slf4j
public class SimpleProducer {
    public static void main(String[] args) throws MQBrokerException {
        // 1、创建生产者，并指定生产者组
        DefaultMQProducer producer = new DefaultMQProducer(SimpleMQConstant.PRODUCER_GROUP);
        // 2、指定 nameserver 地址
        producer.setNamesrvAddr(SimpleMQConstant.NAME_SERVER);

        try {
            // 3、启动生产者
            producer.start();
            log.info("SimpleProducer started");

            // 4、创建消息并发送

            // 5、发送同步消息
            // sendSync(producer);

            // 6、发送消息
            sendSync(producer);

            // 7、发送单向消息
            // 主要用于生产者不太关心发送结果的情况下，比如日志发送
            // sendOneWay(producer);

        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 6、关闭生产者
            producer.shutdown();
        }

    }

    private static void sendSync(DefaultMQProducer producer) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        for (int i = 1; i < 5; i++) {
            Message message = new Message(SimpleMQConstant.TOPIC_DEFAULT, "tag_sync", ("hi, this is a SYNC msg from SimpleProducer " + i).getBytes(StandardCharsets.UTF_8));

            producer.send(message, 10000);
            log.info("sendSync " + new String(message.getBody()));

            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void sendAsync(DefaultMQProducer producer) throws RemotingException, InterruptedException, MQClientException {
        for (int i = 1; i < 5; i++) {
            Message message = new Message(SimpleMQConstant.TOPIC_DEFAULT, "tag_async", ("hi, this is an ASYNC msg from SimpleProducer " + i).getBytes(StandardCharsets.UTF_8));
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("sendAsync " + new String(message.getBody()));
                }

                @Override
                public void onException(Throwable e) {
                    e.printStackTrace();
                }
            });

            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void sendOneWay(DefaultMQProducer producer) throws RemotingException, InterruptedException, MQClientException {
        for (int i = 1; i < 5; i++) {
            Message message = new Message(SimpleMQConstant.TOPIC_DEFAULT, "tag_oneway", ("hi, this is a ONEWAY msg from SimpleProducer " + i).getBytes(StandardCharsets.UTF_8));
            producer.sendOneway(message);
            log.info("sendOneWay " + new String(message.getBody()));

            TimeUnit.SECONDS.sleep(1);
        }
    }
}
