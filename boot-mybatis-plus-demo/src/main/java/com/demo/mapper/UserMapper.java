package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/5/1
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
