package com.huimi.core.req;

import lombok.Data;

/**
 * 用户登录注册 请求参数
 */
@Data
public class UserLoginModelReq {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 短信验证码
     */
    private String smsCode;

    /**
     * 请求ip
     */
    private String ipAddress;

    /**
     * 用户uuid
     */
    private String uuid;
}
