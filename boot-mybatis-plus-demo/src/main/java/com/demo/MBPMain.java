package com.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// 多数据源
// @MapperScan(value = {"com.demo.mapper"}, sqlSessionTemplateRef = "usrSqlSessionTemplate")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
// 单数据源
//@MapperScan(value = {"com.demo.mapper"})
//@SpringBootApplication
public class MBPMain {
    public static void main(String[] args) {
        SpringApplication.run(MBPMain.class, args);
    }
}