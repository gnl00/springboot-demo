package com.boot.chat.cache;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SessionStore
 *
 * @author lgn
 * @since 2022/3/22 10:13
 */

public class SessionStore {

    private static Map<String, Session> localCache = new ConcurrentHashMap<>();

    public static void add(String key, Session value) {
        localCache.put(key, value);
    }

    public static void remove(String key) {
        localCache.remove(key);
    }

    public static Session get(String key) {
        return localCache.get(key);
    }

}
