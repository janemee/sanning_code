package com.huimi.core.util.weChatPay;

import lombok.Data;


/**
 * 退款参数实体类
 */
@Data
public class WeiXinOutPayInfo {

    /**
     * 用户uuid
     */
    private String userUuid;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 树木订单id
     */
    private Integer skuId;
    /**
     * 微信订单号 (商户订单号 为空时必填)
     * 微信生成的订单号，在支付通知中有返回
     */
    private String transactionId;
    /**
     * 商户订单号 (微信订单号 为空时必填)
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    private String outTradeNo;

    /**
     * 订单金额
     * 订单总金额，单位为分，只能为整数，详见支付金额
     */
    private String totalFee;

    /**
     * 退款金额
     * 退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
     */
    private String refundFee;


    //--------------------------------------非必填参数------------------------------------------
    /**
     * 退款货币种类
     * 退款货币类型，需与支付一致，或者不填。符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    private String refundFeeType;

    /**
     * 退款原因 String(80)
     * 若商户传入，会在下发给用户的退款消息中体现退款原因
     */
    private String refundDesc;


    /**
     * 退款资金来源 String(30)
     * 仅针对老资金流商户使用
     * <p>
     * REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）
     * <p>
     * REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
     */
    private String refundAccount;

    /**
     * 退款结果通知url  异步通知回调接口地址
     * 异步接收微信支付退款结果通知的回调地址，通知URL必须为外网可访问的url，不允许带参数
     * <p>
     * 如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效。
     */
    private String notifyUrl;

}
