package com.boot.rocketmq.boot.config;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQDefaultProducerConfig
 *
 * @author lgn
 * @since 2022/2/18 16:55
 */

@Slf4j
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "rocketmq.producer")
public class MQDefaultProducerConfig {

    private String groupName;

    private String namesrvAddr;

    @Bean
    @ConditionalOnProperty(prefix = "rocketmq.producer", value = "enable", havingValue = "true")
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.start();
        log.info("rocketmq producer 正在启动...");
        return producer;
    }

}
