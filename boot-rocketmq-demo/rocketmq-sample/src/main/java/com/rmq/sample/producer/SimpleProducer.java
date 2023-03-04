package com.rmq.sample.producer;

import com.rmq.sample.contant.SimpleMQConstant;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * SimpleProducer
 *
 * @author lgn
 * @since 2022/2/17 14:06
 */

public class SimpleProducer {
    public static void main(String[] args) {
        // 1、创建生产者，并指定生产者组
        DefaultMQProducer producer = new DefaultMQProducer(SimpleMQConstant.PRODUCER_GROUP);
        // 2、指定 nameserver 地址
        producer.setNamesrvAddr(SimpleMQConstant.NAME_SERVER);

        try {
            // 3、启动生产者
            producer.start();

            // 4、创建消息并发送

            // 5、发送同步消息
            for (int i = 1; i < 5; i++) {
                Message message = new Message(SimpleMQConstant.TOPIC_DEFAULT, "tag_sync", ("hi, this is a SYNC msg from SimpleProducer " + i).getBytes(StandardCharsets.UTF_8));

                producer.send(message, 10000);

                TimeUnit.SECONDS.sleep(1);
            }

            // 6、发送异步消息
//            for (int i = 1; i < 5; i++) {
//                Message message = new Message(SimpleMQCanstant.TOPIC_DEFAULT, "tag_async", ("hi, this is an ASYNC msg from SimpleProducer " + i).getBytes(StandardCharsets.UTF_8));
//                producer.send(message, new SendCallback() {
//                    @Override
//                    public void onSuccess(SendResult sendResult) {
//                        System.out.println(sendResult);
//                    }
//
//                    @Override
//                    public void onException(Throwable e) {
//                        System.out.println(e.getMessage());
//                    }
//                });
//
//                TimeUnit.SECONDS.sleep(1);
//            }

            // 7、发送单向消息
            // 主要用于生产者不太关心发送结果的情况下，比如日志发送
//            for (int i = 1; i < 5; i++) {
//                Message message = new Message(SimpleMQCanstant.TOPIC_DEFAULT, "tag_oneway", ("hi, this is a ONEWAY msg from SimpleProducer " + i).getBytes(StandardCharsets.UTF_8));
//                producer.sendOneway(message);
//
//                TimeUnit.SECONDS.sleep(1);
//            }

        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } finally {
            // 6、关闭生产者
            producer.shutdown();
        }

    }
}
