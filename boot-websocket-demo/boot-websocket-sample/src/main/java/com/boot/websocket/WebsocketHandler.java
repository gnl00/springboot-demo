package com.boot.websocket;

import com.boot.controller.WebsocketController;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.UUID;

/**
 * WebsocketController
 *
 * @author lgn
 * @since 2021/12/22 18:44
 */

@ServerEndpoint("/test/ws")
@Component
public class WebsocketHandler {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("有新的连接加入: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("有链接关闭: " + session.getId());
    }

    // 从客户端接收到消息
    @OnMessage
    public void onMessage(String msg, Session session) {
        System.out.println("接收到客户端消息: " + session.getId() + " ===> " + msg);
        // sendMsg(sendMsg, session);
        sendMsg(session);
    }

    // 发送消息给客户端
    public void sendMsg(Session toSession) {
        String myMsg = UUID.randomUUID().toString();
        System.out.println("发送消息到客户端: " + myMsg);
        try {
            toSession.getBasicRemote().sendText(myMsg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
