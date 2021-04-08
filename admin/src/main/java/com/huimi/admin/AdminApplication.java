/*
 * Copyright (c) 2015-2017, HuiMi Tec co.,LTD. 枫亭子 (646496765@qq.com).
 */
package com.huimi.admin;

import com.huimi.admin.config.AdminInterceptor;
import com.huimi.admin.config.StringToEnumConverterFactory;
import com.huimi.common.interceptor.DefaultInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author     枫亭
 * @implSpec  管理控制台对外服务
 * @since     2018-04-05 21:07.
 */
@Slf4j
@SpringBootApplication(exclude = RabbitAutoConfiguration.class)
@MapperScan(basePackages = {"com.huimi.core.mapper", "com.huimi.core.business.*.mapper",})
@ComponentScan(basePackages = {
        "com.huimi.common.mybatis",
        "com.huimi.common.config",
        "com.huimi.common.utils",
        "com.huimi.admin",
        "com.huimi.core",
})
public class AdminApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("拦截器加载..");
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new DefaultInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/**");
    }
}
