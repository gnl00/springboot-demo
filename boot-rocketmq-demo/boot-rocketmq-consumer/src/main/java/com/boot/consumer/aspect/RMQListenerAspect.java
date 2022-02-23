package com.boot.consumer.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * RMQListenerAspect 利用切面类处理标注 @RMQListener 的方法
 * 可以利用切面类和自定义 pull 消息的方法实现，不需要等消息推送
 *
 * @author lgn
 * @since 2022/2/22 16:18
 */

@Slf4j
@Aspect
@Component
public class RMQListenerAspect {

    @Pointcut("@annotation(com.boot.consumer.annotation.RMQListener)")
    private void mqCutPont() {}

    @Around("mqCutPont()")
    public void aroundRMQListener(ProceedingJoinPoint jp) {
        log.info("环绕通知开始。。。");
        log.info("执行的方法名: {}", jp.getSignature().getName());
    }

}