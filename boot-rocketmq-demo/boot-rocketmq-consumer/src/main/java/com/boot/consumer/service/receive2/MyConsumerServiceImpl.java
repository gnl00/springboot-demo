package com.boot.consumer.service.receive2;

import com.boot.consumer.annotation.RMQConsumerGroup;
import com.boot.consumer.annotation.RMQListener;
import com.boot.consumer.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * MyConsumerServiceImpl
 *
 * @author lgn
 * @since 2022/2/24 16:35
 */

@Slf4j
@Service
@RMQConsumerGroup("gp_default")
public class MyConsumerServiceImpl implements BaseService {

    @RMQListener(topic = "myTopic", tag = "myTag", consumerGroup = "myG", clazz = String.class)
    public void onMessage(Object message) {

    }

}
