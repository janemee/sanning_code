//package com.huimi.core.util.aliPay;
//
//import com.alibaba.fastjson.JSON;
//import lombok.Data;
//
//import java.math.BigDecimal;
//
///**
// * 支付回调响应参数
// */
//@Data
//public class AliPayNotifyInfo {
//
//    /**
//     * 签名
//     */
//    private String sign;
//
//    private String alipay_trade_wap_pay_response;
//
//    /**
//     * 回调响应参数信息
//     */
//    private AliPayNotifyResponsParametersInfo aliPayNotifyResponsParametersInfo;
//
//    public AliPayNotifyResponsParametersInfo getAliPayNotifyResponsParametersInfo() {
//        try {
//            return JSON.parseObject(this.alipay_trade_wap_pay_response, AliPayNotifyResponsParametersInfo.class);
//        } catch (Exception e) {
//            System.out.println("数据格式错误");
//        }
//        return null;
//    }
//
//}
