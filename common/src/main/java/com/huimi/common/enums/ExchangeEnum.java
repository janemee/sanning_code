package com.huimi.common.enums;

/**
 * 描述: 交易所枚举
 * 作者: 陌上人
 * 时间: 2016/7/23 23:47
 */
public enum ExchangeEnum {

    HUOBI("huobi","火币"),
    BIAN("bian","币安"),
    OKEX("okex","OKex"),
    BITFINEX("bitfinex","BitFinex"),
    ALL("all","全部");

    private String code;
    private String info;

    ExchangeEnum(String state , String stateInfo){
        this.code = state;
        this.info = stateInfo;
    }

    public String getCode(){
        return code;
    }

    public String getInfo(){
        return info;
    }

    public static ExchangeEnum codeOf(String index){
        for (ExchangeEnum state : values()) {
            if(state.getCode().equals(index)){
                return state;
            }
        }
        return null;
    }
}
