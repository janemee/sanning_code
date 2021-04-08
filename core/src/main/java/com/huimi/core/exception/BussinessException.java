package com.huimi.core.exception;

import lombok.AllArgsConstructor;

/**
 * 业务异常
 *
 * @author liweidong
 * @date 2017年03月05日 12:35
 */
public class BussinessException extends RuntimeException {
    /**
     * 错误描述信息
     */
    private String message;
    private Object data;
    private int code;

    /**
     * 错误码
     */
    @AllArgsConstructor
    public enum Code {
        /**
         * 通用错误
         */
        E400(400, "参数错误"),
        /**
         * 登陆过期
         */
        E401(401, "请先登录"),
        /**
         * 接口调用错误,一般作代码异常捕获判断
         */
        E402(402, "接口调用错误"),
        /**
         * 接口不存在
         */
        E404(404, "接口不存在"),
        /**
         * 验证码错误
         */
        E405(405, "验证码错误"),
        /**
         * 实名未认证
         */
        E202(202,"实名未认证"),
        /**
         * 实名认证中
         */
        E203(203,"实名认证中"),
        ;
        public int code;
        public String msg;
    }

    public BussinessException() {
        super();
        this.message = "系统异常";
    }

    public BussinessException(String message) {
        super();
        this.message = message;
    }

    public BussinessException(Code c) {
        super();
        this.code = c.code;
        this.message = c.msg;
    }

    public BussinessException(String message, Object data) {
        super();
        this.message = message;
        this.data = data;
    }

    public BussinessException(String message, Object data, int code) {
        super();
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public BussinessException(int code) {
        super();
        this.code = code;
    }

    public BussinessException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMsg) {
        this.message = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}