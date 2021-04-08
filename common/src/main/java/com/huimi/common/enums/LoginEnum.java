package com.huimi.common.enums;

/**
 * 描述: 登录枚举
 * 作者: 陌上人
 * 时间: 2016/7/23 23:47
 */
public enum LoginEnum {

    SUCCESS(1,"登录成功"),
    NOLOGIN(-1,"未登录"),
    USERNAME_OR_PASSWD_ERROR(-2,"用户名或密码错误"),
    DISABLED(-3,"登录功能被冻结，请联系客服"),
    PASSWD_OUT_TIMES(-4,"密码错误次数超过系统设定的次数"),
    EXCEPTION(500,"系统异常");

    private int state;

    private String stateInfo;

    LoginEnum(int state , String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState(){
        return state;
    }

    public String getStateInfo(){
        return stateInfo;
    }

    public static LoginEnum stateOf(int index){
        for (LoginEnum state : values()) {
            if(state.getState() == index){
                return state;
            }
        }
        return null;
    }
}
