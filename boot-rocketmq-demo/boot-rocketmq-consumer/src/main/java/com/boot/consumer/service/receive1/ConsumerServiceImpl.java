package com.boot.consumer.service.receive1;

import com.boot.consumer.annotation.RMQListener;
import com.boot.rocketmq.canstant.RMQConstant;
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
@RocketMQMessageListener(topic = RMQConstant.topic, consumerGroup = "${spring.application.name}")
public class ConsumerServiceImpl implements RocketMQListener<String> {

    @RMQListener(consumerGroup = "${spring.application.name}", topic = RMQConstant.topic, tag = "*", type = String.class)
    @Override
    public void onMessage(String message) {
        log.info("Time ==> {}", System.currentTimeMillis());
        log.info("收到消息 ==> {}", message);
    }

}
