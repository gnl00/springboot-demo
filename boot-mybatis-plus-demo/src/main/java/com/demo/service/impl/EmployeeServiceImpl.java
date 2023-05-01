package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.entity.emp.Employee;
import com.demo.mapper.emp.EmployeeMapper;
import com.demo.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/5/1
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
