package com.huimi.common.entity;

import lombok.Data;

/**
 * 通用返回结果对象
 */
@Data
public class Result<T> {
    private Integer error;
    private String url;
    private String message;


}
