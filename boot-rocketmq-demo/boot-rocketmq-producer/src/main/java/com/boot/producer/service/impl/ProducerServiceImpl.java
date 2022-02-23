package com.boot.producer.service.impl;

import com.boot.producer.service.ProducerService;
import com.boot.rocketmq.canstant.RMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

/**
 * ProducerServiceImpl
 *
 * @author lgn
 * @since 2022/2/22 13:51
 */

@Slf4j
@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public Boolean send(String msg) {
        try {
            log.info(System.currentTimeMillis() + " 开始发送");
            rocketMQTemplate.convertAndSend(RMQConstant.TP_BOOT, msg);
            log.info(System.currentTimeMillis() + " 发送成功: " + msg);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
