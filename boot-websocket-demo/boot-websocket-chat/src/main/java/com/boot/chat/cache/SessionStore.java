package com.boot.chat.cache;

import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * SessionStore
 *
 * @author lgn
 * @since 2022/3/22 10:13
 */

@Component
public class SessionStore {

    private Map<String, Session> localCache;

    public SessionStore() {
        localCache = new ConcurrentHashMap<>();
    }

    public void add(String key, Session value) {
        localCache.put(key, value);
    }

    public void remove(String key) {
        localCache.remove(key);
    }

    public Session get(String key) {
        return localCache.get(key);
    }

    public List<String> list() {
        List<String> list = localCache.keySet().stream().collect(Collectors.toList());
        return list;
    }

}
