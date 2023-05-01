package com.demo;

import com.demo.entity.User;
import com.demo.mapper.UserMapper;
import com.demo.service.UserService;
import com.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/5/1
 */
@SpringBootTest
public class MainTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void test() {
        List<User> users = userMapper.selectList(null);
        users.stream().forEach(System.out::println);
        System.out.println("================================================");
        List<User> list = userService.list();
        list.stream().forEach(System.out::println);
    }

    @Value("${spring.application.name}")
    private String appName;

    @Test
    public void test2() {
        System.out.println(appName);
    }
}
