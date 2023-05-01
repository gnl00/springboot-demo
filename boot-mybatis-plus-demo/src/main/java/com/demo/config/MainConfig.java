package com.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/5/1
 */
// @Configuration
public class MainConfig {

    @Bean("usrDataSource")
    @ConfigurationProperties("spring.datasource.usr")
    public DataSource usrDataSource() {
        return new HikariDataSource();
    }

    @Bean("empDataSource")
    @ConfigurationProperties("spring.datasource.emp")
    public DataSource empDataSource() {
        return new HikariDataSource();
    }

    @Bean("usrSqlSessionFactory")
    public SqlSessionFactory usrSqlSessionFactory(@Qualifier("usrDataSource") DataSource usrDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(usrDataSource);
        factoryBean.setTypeAliasesPackage("com.demo.entity");
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/usr/*.xml")
        );

        return factoryBean.getObject();
    }

    @Bean("empSqlSessionFactory")
    public SqlSessionFactory empSqlSessionFactory(@Qualifier("empDataSource") DataSource empDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(empDataSource);
        factoryBean.setTypeAliasesPackage("com.demo.entity");
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/emp/*.xml")
        );

        return factoryBean.getObject();
    }

    @Bean("usrSqlSessionTemplate")
    public SqlSessionTemplate usrSqlSessionTemplate(@Qualifier("usrSqlSessionFactory") SqlSessionFactory factory) {
        return new SqlSessionTemplate(factory);
    }

    @Bean("empSqlSessionTemplate")
    public SqlSessionTemplate empSqlSessionTemplate(@Qualifier("empSqlSessionFactory") SqlSessionFactory factory){
        return new SqlSessionTemplate(factory);
    }

}
