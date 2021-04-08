package com.huimi.common.annotation;

import java.lang.annotation.*;

/**
 * 用于是否生成admin操作日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SysLog {

    boolean value() default true;
}
