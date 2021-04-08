package com.huimi.apis;/*
 * Copyright (c) 2015-2017, HuiMi Tec co.,LTD. 枫亭子 (646496765@qq.com).
 */

import com.huimi.apis.config.WebInterceptor;
import com.huimi.common.interceptor.DefaultInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 枫亭
 * @date 2018-04-05 21:07.
 */
@Slf4j
@SpringBootApplication(exclude = RabbitAutoConfiguration.class)
@MapperScan(basePackages = {"com.huimi.core.mapper","com.huimi.core.business.mapper", "com.huimi.core.business.*.mapper",})
@ComponentScan(basePackages = {
        "com.huimi.common.mybatis",
        "com.huimi.common.config",
        "com.huimi.common.utils",
        "com.huimi.apis",
        "com.huimi.core",})
@EnableSwagger2
public class ApisApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(ApisApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("拦截器加载..");
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new DefaultInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/api/member/**");
    }
}
