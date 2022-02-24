package com.boot.consumer.annotation;

import java.lang.annotation.*;

/**
 * RMQConsumerGroup 标注在类上，表示该类下每一个方法均为一个同一个消费者组的消费者
 *
 * @author lgn
 * @since 2022/2/24 16:30
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RMQConsumerGroup {

    // 消费者组名
    String value() default "";

}
