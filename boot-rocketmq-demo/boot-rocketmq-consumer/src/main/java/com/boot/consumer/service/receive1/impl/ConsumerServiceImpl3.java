package com.boot.consumer.service.receive1.impl;

import com.boot.consumer.service.receive1.ConsumerService;
import com.boot.rocketmq.canstant.RMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * ConsumerServiceImpl2
 *
 * @author lgn
 * @since 2022/2/24 17:12
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = RMQConstant.TP_BOOT, consumerGroup = "${spring.application.name}")
public class ConsumerServiceImpl3 implements ConsumerService, RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("ConsumerServiceImpl3 收到消息: {}", message);
    }
}
