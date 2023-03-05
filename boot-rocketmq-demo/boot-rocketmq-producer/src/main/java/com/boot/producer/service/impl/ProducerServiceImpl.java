package com.boot.producer.service.impl;

import com.boot.producer.service.ProducerService;
import com.boot.rocketmq.canstant.RMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ProducerServiceImpl
 *
 * @author lgn
 * @since 2022/2/22 13:51
 */

@Slf4j
@Service
public class ProducerServiceImpl implements ProducerService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendSync(String msg) {
        try {
            log.info(System.currentTimeMillis() + " sendSync start");
            rocketMQTemplate.convertAndSend(RMQConstant.topic + RMQConstant.separator + RMQConstant.tag_sync, msg);
            log.info(System.currentTimeMillis() + " sendSync done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAsync(String msg) {

        rocketMQTemplate.asyncSend(RMQConstant.topic + RMQConstant.separator + RMQConstant.tag_async, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info(sendResult.getSendStatus().toString());
            }

            @Override
            public void onException(Throwable e) {
                e.printStackTrace();
            }
        });
    }
}
