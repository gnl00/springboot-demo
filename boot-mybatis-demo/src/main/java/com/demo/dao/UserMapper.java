package com.demo.dao;

import com.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/3/15
 */
@Mapper
public interface UserMapper {
    List<User> queryAllUser();

    User queryUser(Integer id);

    Integer countUsers();
}
