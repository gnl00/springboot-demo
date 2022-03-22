package com.boot.chat.controller;

import com.boot.chat.bean.User;
import com.boot.chat.cache.SessionStore;
import com.boot.chat.websocket.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * WebSocketController
 *
 * @author lgn
 * @since 2022/3/22 10:10
 */

@Slf4j
@RestController
@RequestMapping("/chat/")
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    @PostMapping("/login")
    public Boolean login(@RequestBody User user) {
        log.info(user.toString());
        if (null != user) {
            // 账号密码正确才进行 websocket 链接
            log.info("account: {}, passwd: {}", user.getAccount(), user.getPassword());
            return true;
        }
        return false;
    }

    @GetMapping("/close/{sessionId}")
    public String closeConnect(@PathVariable("sessionId") String sessionId) {
        webSocketService.onClose(SessionStore.get(sessionId));
        return null;
    }

}
