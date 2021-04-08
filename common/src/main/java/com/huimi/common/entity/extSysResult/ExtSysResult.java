package com.huimi.common.entity.extSysResult;

import lombok.Data;

import java.util.Map;

/**
 * 外部系统请求参数返回
 */
@Data
public class ExtSysResult {
    public Integer status;

    public String message;

    public Map<String,Object> data;
}
