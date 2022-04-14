package com.boot.call.controller;

import com.boot.call.websocket.Connection;
import com.boot.call.websocket.PtoManyConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RoomController
 *
 * @author lgn
 * @since 2022/4/14 14:19
 */

@Slf4j
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private PtoManyConnection conn;

    @PostMapping("/join")
    public Boolean joinRoom(@RequestParam("roomId") String roomId, @RequestParam("uid") String uid) {
        log.info("joinRoom {} , {}", uid, roomId);

        try {
            conn.joinRoom(roomId, uid);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @GetMapping("/members")
    public List<String> members(@RequestParam("roomId") String roomId, @RequestParam("uid") String uid) {
        log.info("get room members, roomId: {}, uid: {}", roomId, uid);
        return conn.getRoomMembers(roomId, uid);
    }

}
