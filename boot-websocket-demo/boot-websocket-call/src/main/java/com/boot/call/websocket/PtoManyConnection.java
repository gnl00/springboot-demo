package com.boot.call.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * PtoManyConnection
 *
 * @author lgn
 * @since 2022/4/7 10:00
 */

@Slf4j
@Component
@ServerEndpoint("/ptoMany/{roomId}/{id}")
public class PtoManyConnection {

    /**
     * key = uid
     * value = session
     */
    private static Map<String, Session> users = new ConcurrentHashMap<>();

    /**
     * key = roomId
     * value = uid list
     */
    private static Map<String, List<String>> rooms = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String uid) {
        users.put(uid, session);
        log.info("open session : {}, uid: {}", session.getId(), uid);
    }

    @OnClose
    public void onClose(Session session, @PathParam("id") String uid) {
        users.remove(uid);
        rooms.remove(uid);
        log.info("close session: {}, uid: {}", session.getId(), uid);
    }

    @OnError
    public void onError(Session session, Throwable e, @PathParam("id") String uid) {
        users.remove(uid);
        rooms.remove(uid);
        log.error("error in session: {}, error message: {}", session.getId(), e.getCause().getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("id") String uid, @PathParam("roomId") String roomId) {
        log.info("receive from uid: {}, roomId: {}, on session: {}, message: {}", uid, roomId, session.getId(), message);

        // get the right room and send to every member
        if (rooms.containsKey(roomId)) {
            List<String> members = rooms.get(roomId);
            for (String member : members) {
                sendMessage(message, users.get(member));
            }
        }
    }

    private void sendMessage(String message, Session to) {
        try {
            to.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void joinRoom(String roomId, String uid) {
        if (rooms.containsKey(roomId)) {
            List<String> members = rooms.get(roomId);
            members.add(uid);

        } else {
            List<String> members = new ArrayList<>();
            members.add(uid);
            rooms.put(roomId, members);
        }
    }

    public List<String> getRoomMembers(String roomId, String uid) {
        return rooms.containsKey(roomId) ? rooms.get(roomId).stream().filter(member -> !member.equals(uid)).collect(Collectors.toList()) : Collections.emptyList();
    }

}
