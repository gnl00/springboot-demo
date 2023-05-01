package com.demo;

import com.demo.entity.emp.Employee;
import com.demo.mapper.emp.EmployeeMapper;
import com.demo.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/5/1
 */
@SpringBootTest
public class EmployeeTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeServiceImpl employeeService;

//
//    @Autowired
//    @Qualifier("empSqlSessionTemplate")
//    private SqlSessionTemplate empSqlSessionTemplate;
//
//    @Test
//    public void test1() {
//        Employee emp = employeeMapper.selectOne(10084L);
//        System.out.println(emp);
//        List<Employee> employees = employeeService.list();
//        employees.stream().forEach(System.out::println);
//    }

    @Test
    public void test2() {
        List<Employee> employees = employeeService.list();
        System.out.println(employees.size());
    }

    @Test
    public void test3() {
        Employee employee = employeeMapper.selectByEmpNo(10084L);
        System.out.println(employee);
    }

    @Test
    public void test4() {
        List<Employee> emps = employeeMapper.selectEmps();
        System.out.println(emps.size());
    }

}
