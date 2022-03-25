package com.boot.chat;

import com.boot.chat.bean.OnlineDo;
import com.boot.chat.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @org.junit.jupiter.api.Test
    public void test2() {
        Map<String, List> map = new HashMap<>();

        List<String> list = new ArrayList<>();
        list.add("1");
        map.put("list", list);

        System.out.println(map);

        list.add("2");

        System.out.println(map);
    }

    @org.junit.jupiter.api.Test
    public void test3() {

        List<OnlineDo> list = new ArrayList<>();
        list.add(OnlineDo.builder().uid("a").sessionId("1").build());
        list.add(OnlineDo.builder().uid("b").sessionId("2").build());
        list.add(OnlineDo.builder().uid("c").sessionId("3").build());

        Map<String, OnlineDo> map = new HashMap<>();
        map.put("a", OnlineDo.builder().uid("a").sessionId("1").build());
        map.put("b", OnlineDo.builder().uid("b").sessionId("2").build());
        map.put("c", OnlineDo.builder().uid("c").sessionId("3").build());

        System.out.println(list);

    }

}
