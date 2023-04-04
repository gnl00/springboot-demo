package com.demo.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/4/4
 */
public class CallbackProducer {
    public static void main(String[] args) {
        // 生产者配置列表 https://kafka.apache.org/documentation.html#producerconfigs
        Properties props = new Properties();
        // kafka 地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // ack 级别 all
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        // 重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, 1);
        // 等待时间
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // 将需要发送的消息序列化
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            producer.send(new ProducerRecord<>("topic-test", "test-" + i,
                    "test-msg " + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (null == exception) {
                        System.out.println("send success " + finalI);
                    }
                }
            });
            System.out.println("[send] ==> " + i);
        }
        producer.close();
    }
}
