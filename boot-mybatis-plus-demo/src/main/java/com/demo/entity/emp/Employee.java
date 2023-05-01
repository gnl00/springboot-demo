package com.demo.entity.emp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/5/1
 */
@Data
@TableName("employees")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1792259529494486897L;

    @TableId("emp_no")
    private Long empNo;

    @TableField("first_name")
    private String firstName;

    @TableField("last_name")
    private String lastName;

    @TableField("gender")
    private String gender;

    @TableField("birth_date")
    private Date birthDate;

    @TableField("hire_date")
    private Date hireDate;
}
