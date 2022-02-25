package com.boot.consumer.service.receive2;

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
public class ConsumerServiceImpl2 implements BaseService {

    @RMQListener(topic = "myTopic", tag = "myTag", consumerGroup = "myG", type = String.class)
    public void onMyMessage() {

    }

    @Override
    public void onBaseMessage(Object message) {

    }
}
