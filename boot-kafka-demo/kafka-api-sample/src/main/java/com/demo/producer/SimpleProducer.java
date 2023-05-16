package com.demo.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/4/4
 */
public class SimpleProducer {
    public static void main(String[] args) {
        // 生产者配置列表 https://kafka.apache.org/documentation.html#producerconfigs
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // kafka 地址
        props.put(ProducerConfig.ACKS_CONFIG, "all"); // ack 级别
        props.put(ProducerConfig.RETRIES_CONFIG, 1); // 重试次数
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1); // 等待时间
        // key-value 序列化器
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("topic-test", "test-" + i,
                    "test-" + i));
            System.out.println("[send] ==> " + i);
        }
        producer.close();
    }
}
