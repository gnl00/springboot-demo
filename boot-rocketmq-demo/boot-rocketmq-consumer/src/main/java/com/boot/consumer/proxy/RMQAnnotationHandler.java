package com.boot.consumer.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * RMQAnnotationHandler 代理类，为每个标注 RMQListener 的方法生成代理对象
 *
 * @author lgn
 * @since 2022/2/24 17:23
 */

@Slf4j
public class RMQAnnotationHandler implements InvocationHandler {

    /**
     * 被代理类
     */
    private Object obj;

    /**
     * 构造方法注入被代理类
     */
    public RMQAnnotationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.info("代理对象: {}", proxy.getClass().toString());

        log.info("代理方法执行前 ===> ");

        // 获取方法执行返回值
        Object retVal = method.invoke(obj, args);

        log.info("代理方法执行后 ===> ");

        return retVal;
    }

}
