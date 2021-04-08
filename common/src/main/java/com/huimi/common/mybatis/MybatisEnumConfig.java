package com.huimi.common.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Donfy
 * 2018/10/23
 */
@Configuration
//@ConfigurationProperties(prefix = "mybatis")
public class MybatisEnumConfig {

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration globalConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, org.apache.ibatis.session.Configuration config) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setConfiguration(config);
        SqlSessionFactory sqlSessionFactory = factory.getObject();


        // 取得类型转换注册器
        TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        // 注册默认枚举转换器
        typeHandlerRegistry.setDefaultEnumTypeHandler(AutoEnumTypeHandler.class);

        return sqlSessionFactory;
    }
}
