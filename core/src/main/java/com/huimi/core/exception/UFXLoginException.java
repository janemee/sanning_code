package com.huimi.core.exception;

/**
 * 易盛登录异常
 *
 * @author liweidong
 * @date 2017年03月05日 12:35
 */
public class UFXLoginException extends Exception {
    /**
     * 错误描述信息
     */
    private String message;
    private String code;


    public UFXLoginException() {
        super();
        this.message = "系统异常";
    }

    public UFXLoginException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMsg) {
        this.message = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}