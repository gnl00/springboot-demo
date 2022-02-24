package com.boot.consumer.service.receive2;

import com.boot.consumer.annotation.RMQConsumerGroup;
import com.boot.consumer.annotation.RMQListener;
import com.boot.consumer.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ConsumerServiceImpl2
 *
 * @author lgn
 * @since 2022/2/22 16:00
 */
@Slf4j
@Service
@RMQConsumerGroup("gp_default")
public class ConsumerServiceImpl2 implements BaseService {

    @RMQListener(topic = "myTopic", tag = "myTag", consumerGroup = "myG", clazz = String.class)
    public void onMyMessage() {

    }

    @RMQListener(topic = "myTopic", tag = "myTag", consumerGroup = "myG", clazz = String.class)
    public void onMessage1(Object message) {

    }

    @RMQListener(topic = "myTopic", tag = "myTag", consumerGroup = "myG", clazz = String.class)
    public void onMessage2(Object message) {

    }
}
