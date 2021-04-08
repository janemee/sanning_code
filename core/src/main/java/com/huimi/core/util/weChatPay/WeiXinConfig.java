package com.huimi.core.util.weChatPay;

public class WeiXinConfig {
    /**
     * 微信支付缓存地址
     */
    public static final String REDIS_URL = "pay:weChatPay:order:";
    /**
     * 微信树木支付缓存地址
     */
    public static final String REDIS_URL_PRODUCT = "pay:weChatPay:productOrder:";
    /**
     *
     */
    public static String REDIS_URL_RECHARGE = "pay:weChatPay:recharge:";
    /**
     * 微信支付申请成功后的秘钥
     */
    public static String key = "bcfu4gFvdl1zH9JMn6D3FEooXHDV5rYk";
    /**
     * 微信支付登录密码（秘钥）
     */
    public static String pwd = "aa881030..";
    /**
     * 网关
     */
    public static String gatewayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 公众号appID
     */
    public static String appid = "wxf86e0a15c9b03736";
    //公众账号ID
    public static String mch_id = "1558389191";
    //子商户Id
    public static String sub_mch_id = "10000111";

    //签名类型
    public static String sign_type_md5 = "MD5";
    //签名类型
    public static String sign_type_HMACSHA256_ = "HMAC-SHA256";

    //币种
    public static String fee_type = "CNY";
    //交易金额（为分）例如12.53 应该（12.53*100） 放入当前值
    public static int total_fee = 0;


    /**
     * 微信回调接口（重要）
     */
    public static String notify_url = "api/web/weChat/weChatPayNotify";

    public static String web_url = "http://m.cchyg.com";
    /**
     * 支付交易类型
     * //支付场景 APP 微信app支付 JSAPI 公众号支付  NATIVE 扫码支付  MWEB h5支付
     */
    public static String trade_type = "MWEB";

    public static final int DEFAULT_CONNECT_TIMEOUT_MS = 6 * 1000;
    public static final int DEFAULT_READ_TIMEOUT_MS = 8 * 1000;

    /**
     * 执行成功过 返回状态码
     */
    public static String SSUCCESS = "SUCCESS";

    /**
     * 执行成功过 返回状态码
     */
    public static String FILL = "FILL";
    /**
     * 执行成功过 返回信息
     */
    public static String OK = "OK";
    /**
     * 执行成功过 返回信息
     */
    public static String NO = "OK";


    public static String h5_info = "{\"h5_info\": {\"type\":\"h5\",\"app_name\": \"王者荣耀\",\"package_name\": \"com.tencent.tmgp.sgame\"}}";

    /**
     * 查询订单地址
     */
    public static String orderQueryUrl = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 申请退款地址
     */
    public static String refund_url = "/secapi/pay/refund";
    /**
     * 查询订单地址
     */
    public static boolean autoReport = false;

    /**
     * 支付成功后跳转到商户系统页面
     * 拼接到支付页面跳转链接
     */
    public static String redirect_url = "&redirect_url=http://testm.cchyg.com";
//    https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx131409158974828b6119ddf51433031100&package=180131429&redirect_url=https://www.xxx.com

}
