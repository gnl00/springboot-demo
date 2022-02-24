package com.boot.consumer.proxy;

import java.lang.reflect.Proxy;

/**
 * RMQAnnotationProxy
 *
 * @author lgn
 * @since 2022/2/24 17:29
 */

public class RMQAnnotationProxy {

    public static Object getInstance(Object obj) {
        RMQAnnotationHandler handler = new RMQAnnotationHandler(obj);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
    }

}
