package com.huimi.common.entity;

import lombok.Data;

/**
 * 通用返回结果对象
 */
@Data
public class ResultEntity<T> {
    public static final int SUCCESS = 200;
    public static final int LOADING = 300;
    public static final int FAIL = 400;
    //设备中有任务执行的设备是
    public static final int NO_REGISTER = 2001;
    //手机未认证
    public static final int NO_MOBILE_AUTH = 201;

    //实名未认证
    public static final int NO_REALNAME_AUTH = 202;
    //实名认证中
    public static final int REALNAME_AUTHING = 203;
    //支付密码未设置
    public static final int PAY_PASSWOR_NOT_SET = 204;
    //没有绑定任何银行卡
    public static final int NO_BANK_CARD_AUTH = 205;
    //业务错误报错
    public static final int BUSINESS_ERROR = 2002;

    private int code;
    private String msg = "";
    private String url = "";
    private T data;
    private String uuid;
    private Integer total;



    public Integer getTotal() {
        if("".equals(total)||total==null){
            return 0;
        }
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public static ResultEntity success() {
        ResultEntity restResult = new ResultEntity();
        restResult.code = SUCCESS;
        restResult.msg = "操作成功";
        return restResult;
    }

    public static ResultEntity success(String msg) {
        ResultEntity restResult = new ResultEntity();
        restResult.code = ResultEntity.SUCCESS;
        restResult.msg = msg;
        return restResult;
    }

    public static ResultEntity success(String msg,Object data) {
        ResultEntity restResult = new ResultEntity();
        restResult.code = ResultEntity.SUCCESS;
        restResult.msg = msg;
        restResult.data = data;
        return restResult;
    }

    public static ResultEntity fail() {
        ResultEntity restResult = new ResultEntity();
        restResult.code = FAIL;
        restResult.msg = "操作失败";
        return restResult;
    }

    public static ResultEntity fail(String msg) {
        ResultEntity restResult = new ResultEntity();
        restResult.code = FAIL;
        restResult.msg = msg;
        return restResult;
    }


}
