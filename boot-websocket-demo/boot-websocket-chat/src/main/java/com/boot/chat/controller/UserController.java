package com.boot.chat.controller;

import com.boot.chat.bean.OnlineDo;
import com.boot.chat.bean.User;
import com.boot.chat.cache.SessionCache;
import com.boot.chat.websocket.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * WebSocketController
 *
 * @author lgn
 * @since 2022/3/22 10:10
 */

@Slf4j
@RestController
@RequestMapping("/chat/user")
public class UserController {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private SessionCache store;

    @PostMapping("/login")
    public Boolean login(@RequestBody User user) {
        if (null != user) {
            // 账号密码正确才进行 websocket 链接
            log.info("account: {}, passwd: {}", user.getAccount(), user.getPassword());

            // 登录成功，分配 websocket 通道

            return true;
        }
        return false;
    }

    @GetMapping("/contacts")
    public List<OnlineDo> userList() {
        return webSocketService.getContacts();
    }

    @GetMapping("/count")
    public Integer userCount() {
        return userList().size();
    }

    @PostMapping("/group")
    public Boolean createGroup(@RequestBody Map<String, String> map) {
        try {
            webSocketService.createGroup(map.get("groupId"), map.get("uid"));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @PutMapping("/group")
    public void updateGroup(@RequestBody Map<String, String> map) {
        webSocketService.updateGroup(map.get("groupId"), map.get("uid"));
    }

    // @GetMapping("/close/{sessionId}")
    public String closeConnect(@PathVariable("sessionId") String sessionId) {
        webSocketService.onClose(store.get(sessionId).getSession());
        return "operation successful";
    }

}
