package com.huimi.nettySocket;
/*
 * Copyright (c) 2015-2017, HuiMi Tec co.,LTD. 枫亭子 (646496765@qq.com).
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 枫亭
 * @since  2020-04-05 21:07.
 */
@Slf4j
@SpringBootApplication(exclude = RabbitAutoConfiguration.class)
@ServletComponentScan("com.huimi.nettySocket")
@MapperScan(basePackages = {"com.huimi.core.mapper", "com.huimi.core.business.mapper", "com.huimi.core.business.*.mapper",})
@ComponentScan(basePackages = {
        "com.huimi.common.mybatis",
        "com.huimi.common.utils",
        "com.huimi.nettySocket",
        "com.huimi.core",})
public class NettySocketApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(NettySocketApplication.class, args);
    }

}
