package com.rmq.sample.consumer;

import com.rmq.sample.contant.SimpleMQCanstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * SimpleConsumer
 *
 * @author lgn
 * @since 2022/2/17 14:07
 */

public class SimpleConsumer {
    public static void main(String[] args) {
        // 1、创建消费者，并指定消费者组
        // new DefaultMQPushConsumer()
        // new DefaultLitePullConsumer()
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(SimpleMQCanstant.CONSUMER_GROUP);
        // 2、指定 nameserver
        consumer.setNamesrvAddr(SimpleMQCanstant.NAME_SERVER);
        // 设置 consumer 第一次启动是从队列头部开始还是尾部开始消费，若非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {


            // 3、指定 topic、tags，多个 tag 使用“||”隔开，所有 tag 使用“*”
            consumer.subscribe(SimpleMQCanstant.TOPIC_DEFAULT, "*");

            // 4、设置回调函数，处理消息
            // new MessageListenerOrderly 顺序消费，有序地使用消息意味着消息的消费顺序与生产者为每个消息队列发送消息的顺序相同
            // new MessageListenerConcurrently 并行消费，在此模式下不再保证消息顺序，消费的最大并行数量受每个消费者客户端指定的线程池限制。
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt msg : msgs) {
                        System.out.println("接受到消息Id " + new String(msg.getBody()));
                    }
                    // 返回消费状态为成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            // 5、启动消费者
            consumer.start();

        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
