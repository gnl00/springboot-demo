package com.boot.consumer.service.impl;

import com.boot.consumer.constant.RMQTopic;
import com.boot.consumer.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * ConsumerServiceImpl
 *
 * @author lgn
 * @since 2022/2/22 14:06
 */

@Slf4j
@Service
@RocketMQMessageListener(topic = RMQTopic.TP_BOOT, consumerGroup = "${spring.application.name}")
public class ConsumerServiceImpl implements ConsumerService, RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info(System.currentTimeMillis() + " 收到消息");
        log.info(message);
    }



}
