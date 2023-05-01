package com.demo.mapper.emp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.emp.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/5/1
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    Employee selectByEmpNo(Long empNo);

    List<Employee> selectEmps();
}
