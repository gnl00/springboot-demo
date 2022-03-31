package com.boot.chat.service;

import javax.websocket.Session;

/**
 * WebSocketService
 *
 * @author lgn
 * @since 2022/3/16 15:37
 */

@Deprecated
public interface WebSocketService {

    /**
     * 发送消息
     * @param message
     * @param to
     */
    void sendMessage(String message, Session to);

}
