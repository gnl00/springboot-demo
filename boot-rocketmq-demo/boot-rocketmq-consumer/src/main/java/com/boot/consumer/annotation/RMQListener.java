package com.boot.consumer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

/**
 * RMQListener，RocketMQ 消息监听，标注在方法上
 *
 * @author lgn
 * @since 2022/2/22 15:47
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RMQListener {

    /**
     * 接收消息的消费者组
     */
    String consumerGroup() default "";

    /**
     * 接收的消息 topic
     */
    String topic() default "";

    /**
     * 接收的消息 tag
     */
    String tag() default "";

    /**
     * 消息类型.class
     */
    Class type() default Objects.class;
}
