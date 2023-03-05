package com.boot.consumer.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * RMQListenerAspect 利用切面类处理标注 @RMQListener 的方法
 * <p>
 * 想法1，切面加 pull 模式感知到消息发送
 * 可以利用切面类和自定义 pull 消息的方法实现，不需要等消息推送
 *
 * <p>
 * ❌ 想法2，配置类加动态代理，使用代理生成带注解的类
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
    public Object aroundRMQListener(ProceedingJoinPoint jp) throws Throwable {
        log.info("=====> 环绕通知开始 <=====");
        log.info("执行方法名 ===> {}", jp.getSignature().getName());

        log.info("before proceed");

        Object result = jp.proceed();

        log.info("after proceed");

        return result;
    }

}
