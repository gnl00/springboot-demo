package com.boot.chat;

import com.boot.chat.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author lgn
 * @since 2022/3/23 13:59
 */

public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        Map<String, Object> src = new HashMap<>();
        User user = new User("111", "111");
        src.put("user", user);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);

        src.put("list", list);

        System.out.println(src);

        Map<String, Object> target = new HashMap<>();
        target.putAll(src);

        list.add(2);

        System.out.println(src);
        System.out.println(target);

        List<Integer> list1 = (List<Integer>) target.get("list");
        list1.add(3);

        System.out.println(src);
        System.out.println(target);

    }

}
