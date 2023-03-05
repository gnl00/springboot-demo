package com.boot.producer.service;

/**
 * ProducerService
 *
 * @author lgn
 * @since 2022/2/22 13:51
 */
public interface ProducerService {

    /**
     * 发送消息到 MQ 服务器
     * @param msg
     * @return java.lang.Boolean
     */

    void sendSync(String msg);

    void sendAsync(String msg);

}
