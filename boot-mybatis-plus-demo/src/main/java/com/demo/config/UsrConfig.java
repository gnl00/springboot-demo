package com.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
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
@Configuration
// 配置多数据源时，需要分开配置类，并分别配置 sqlSessionTemplateRef 才能正确创建 XXXMapper 代理类
@MapperScan(value = {"com.demo.mapper.usr"}, sqlSessionFactoryRef = "usrSqlSessionFactory", sqlSessionTemplateRef = "usrSqlSessionTemplate")
public class UsrConfig {

    @Bean("usrDataSource")
    @ConfigurationProperties("spring.datasource.usr")
    public DataSource usrDataSource() {
        return new HikariDataSource();
    }

    @Bean("usrSqlSessionFactory")
    public SqlSessionFactory usrSqlSessionFactory(@Qualifier("usrDataSource") DataSource usrDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(usrDataSource);
        factoryBean.setTypeAliasesPackage("com.demo.entity.usr");
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/usr/*.xml")
        );

        return factoryBean.getObject();
    }

    @Bean("usrSqlSessionTemplate")
    public SqlSessionTemplate usrSqlSessionTemplate(@Qualifier("usrSqlSessionFactory") SqlSessionFactory factory) {
        return new SqlSessionTemplate(factory);
    }

}
