//package com.huimi.core.util.aliPay;
//
//import lombok.Data;
//
//@Data
//public class AliPayWayGateContentInfo {
//
//    //_____________________________________必填参数
//
//    /**
//     * 商品的标题/交易标题/订单标题/订单关键字等。
//     */
//    private String subject;
//    /**
//     * 商户网站唯一订单号
//     */
//    private String out_trade_no;
//
//    /**
//     * 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
//     */
//    private String total_amount;
//    /**
//     * 用户付款中途退出返回商户网站的地址
//     */
//    private String quit_url;
//    /**
//     * 销售产品码，商家和支付宝签约的产品码  固定 ：QUICK_WAP_WAY
//     */
//    private String product_code;
//
//    //_____________________________________可选填参数
//    /**
//     * 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
//     */
//    private String body;
//
//}
