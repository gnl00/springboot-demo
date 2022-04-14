package com.boot.call.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Connection
 *
 * @author lgn
 * @since 2022/4/7 10:00
 */

@Slf4j
@Component
@ServerEndpoint("/call/{id}")
public class Connection {

    /**
     * key = uid
     * value = session
     */
    private static Map<String, Session> users = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String uid) {
        log.info("open session : {}, uid: {}", session.getId(), uid);
        users.put(uid, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("id") String uid) {
        log.info("close session: {}, uid: {}", session.getId(), uid);
        users.remove(uid);
    }

    @OnError
    public void onError(Session session, Throwable e) {
        log.error("error in session: {}, error message: {}", session.getId(), e.getCause().getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("id") String uid) {
        log.info("receive from: {}, on session: {}, message: {}", uid, session.getId(), message);

        List<String> keys = users.keySet().stream().filter(uKey -> !uKey.equals(uid)).collect(Collectors.toList());
        log.info(keys.toString());
        for (String key : keys) {
            // send message
            sendMessage(message, users.get(key));
        }
    }

    private void sendMessage(String message, Session to) {
        try {
            to.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
