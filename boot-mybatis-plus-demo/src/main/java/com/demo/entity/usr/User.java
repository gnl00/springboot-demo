package com.demo.entity.usr;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/5/1
 */
@Data
@TableName("tb_test")
public class User implements Serializable {

    private static final long serialVersionUID = 7223245362061138198L;

    private Long id;
    private String name;
    private Integer age;

}
