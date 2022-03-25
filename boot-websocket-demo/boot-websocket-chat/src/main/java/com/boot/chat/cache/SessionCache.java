package com.boot.chat.cache;

import com.boot.chat.bean.CacheDo;
import org.springframework.stereotype.Component;

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
public class SessionCache {

    private Map<String, CacheDo> localCache;

    public SessionCache() {
        localCache = new ConcurrentHashMap<>();
    }

    public void add(String key, CacheDo value) {
        localCache.put(key, value);
    }

    public void delete(String key) {
        localCache.remove(key);
    }

    public CacheDo get(String key) {
        return localCache.get(key);
    }

    public List<String> list() {
        List<String> list = localCache.keySet().stream().collect(Collectors.toList());
        return list;
    }

}
