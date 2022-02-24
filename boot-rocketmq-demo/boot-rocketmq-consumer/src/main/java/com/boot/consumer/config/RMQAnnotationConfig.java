package com.boot.consumer.config;

import com.boot.consumer.annotation.RMQConsumerGroup;
import com.boot.consumer.annotation.RMQListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * RMQAnnotationConfig
 *
 * @author lgn
 * @since 2022/2/24 16:05
 */

@Slf4j
@Configuration
public class RMQAnnotationConfig {

    @Autowired
    private ApplicationContext ac;

    @PostConstruct
    public void init() throws ClassNotFoundException {
        log.info("***** 初始化 RMQAnnotationConfig *****");
        Map<String, Object> beanMap = ac.getBeansWithAnnotation(RMQConsumerGroup.class);
        for (String beanName : beanMap.keySet()) {
            String wholeName = beanMap.get(beanName).toString();
            int endIndex = wholeName.indexOf("@");
            String className = wholeName.substring(0, endIndex);
            log.info(className);

            Class<?> clazz = Class.forName(className);

            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(RMQListener.class)) {
                    log.info("为方法: {} 创建代理类。。。", method.getName());
                }
            }
        }
    }

}
