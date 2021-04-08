package com.huimi.core.constant;

import com.huimi.core.exception.BussinessException;
import lombok.AllArgsConstructor;

/**
 * 短信类型枚举
 */
@AllArgsConstructor
public enum PhoneCodeKey {

    /**
     * 注册
     */
    REGISTER("register", MsgTempNID.VALIDATE_CODE, SessionId.MEMBER_REG_MSG, false),
    /**
     * 忘记密码
     */
    RESET_PWD("reset_pwd", MsgTempNID.VALIDATE_CODE, SessionId.RESET_PWD, false),
    /**
     * 修改支付密码
     */
    RESET_PAY_PWD("reset_pay_pwd", MsgTempNID.VALIDATE_CODE, SessionId.RESET_PAY_PWD, false),
    /**
     * 修改手机号
     */
    RESET_PHONE("reset_phone", MsgTempNID.VALIDATE_CODE, SessionId.RESET_PHONE, true),
    /**
     * 修改手机号 新手机号发送短信
     */
    RESET_PHONE_NEW("reset_phone_new", MsgTempNID.VALIDATE_CODE, SessionId.RESET_PHONE, true),
    /**
     * 短信登录
     */
    MSG_LOGIN("msg_login", MsgTempNID.VALIDATE_CODE, SessionId.MSG_LOGIN, false),;

    public String code;
    public String nid;
    public String CACHE_ID;
    public boolean isLogin;

    public static PhoneCodeKey getPhoneCodeKey(String code) {
        for (PhoneCodeKey k : PhoneCodeKey.values()) {
            if (k.code.equals(code))
                return k;
        }
        throw new BussinessException("短信类型错误");
    }
}
