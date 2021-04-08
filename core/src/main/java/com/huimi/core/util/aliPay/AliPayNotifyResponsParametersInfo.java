//package com.huimi.core.util.aliPay;
//
//import lombok.Data;
//
//@Data
//public class AliPayNotifyResponsParametersInfo {
//    /**
//     * 状态码 10000为正常 其他异常
//     */
//    private String code;
//    /**
//     * 操作结果 Success为正常 其他异常
//     */
//    private String msg;
//    /**
//     * 业务返回码，参见具体的API接口文档 非必要
//     */
//    private String sub_code;
//    /**
//     * 业务返回码描述，参见具体的API接口文档
//     */
//    private String sub_msg;
//    /**
//     * 商户网站唯一订单号
//     */
//    private String out_trade_no;
//    /**
//     * 该交易在支付宝系统中的交易流水号。最长64位
//     */
//    private String trade_no;
//    /**
//     * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
//     */
//    private String total_amount;
//    /**
//     * 收款支付宝账号对应的支付宝唯一用户号。
//     * 以2088开头的纯16位数字
//     */
//    private String seller_id;
//    /**
//     * 商户原始订单号，最大长度限制32位
//     */
//    private String merchant_order_no;
//
//    private Integer userId;
//
//
//
//}
