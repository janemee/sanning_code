//package com.huimi.core.util.aliPay;
//
//import lombok.Data;
//
//@Data
//public class AliPayWayGateInfo {
//
//    /**
//     * 支付宝AppId
//     */
//    private String app_id;
//
//    /**
//     * 接口名称 例如：alipay.trade.wap.pay
//     */
//    private String method;
//    /**
//     * 仅支持JSON
//     */
//    private String format;
//
//    /**
//     * 返回路径 https://m.alipay.com/Gk8NF23
//     */
//    private String return_url;
//    /**
//     * 编码格式 utf-8
//     */
//    private String charset;
//    /**
//     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
//     */
//    private String sign_type;
//    /**
//     * 加密签名串
//     */
//    private String sign;
//    /**
//     * 请求时间 格式：2014-07-24 03:07:50
//     */
//    private String timestamp;
//    /**
//     * 版本 固定1.0
//     */
//    private String version;
//
//    /**
//     * 支付宝服务器主动通知商户服务器里指定的页面http/https路径。
//     */
//    private String notify_url;
//
//    /**
//     * 请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
//     */
//    private String biz_content;
//
//}
