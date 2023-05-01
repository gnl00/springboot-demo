package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.entity.usr.User;
import com.demo.mapper.usr.UserMapper;
import com.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/5/1
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
