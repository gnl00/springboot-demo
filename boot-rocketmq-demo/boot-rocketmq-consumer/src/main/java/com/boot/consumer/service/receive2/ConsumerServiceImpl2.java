package com.boot.consumer.service.receive2;

import com.boot.consumer.annotation.RMQListener;
import com.boot.consumer.service.BaseService;
import com.boot.rocketmq.canstant.RMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * ConsumerServiceImpl2
 *
 * @author lgn
 * @since 2022/2/22 16:00
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = RMQConstant.TP_BOOT, consumerGroup = "${rocketmq.consumer.group}")
public class ConsumerServiceImpl2 implements BaseService, RocketMQListener {

    @RMQListener(topic = "myTopic", tag = "myTag", consumerGroup = "myG", clazz = String.class)
    public void onMyMessage() {

    }

    @RMQListener(topic = "myTopic", tag = "myTag", consumerGroup = "myG", clazz = String.class)
    @Override
    public void onMessage(Object message) {

    }
}
