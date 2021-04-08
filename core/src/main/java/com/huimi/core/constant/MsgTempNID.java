package com.huimi.core.constant;

/**
 * 消息模板标识常量
 *
 * @author Vector
 * @date 2017/5/22
 */
public interface MsgTempNID {
    /**
     * 统一短信验证码
     */
    String VALIDATE_CODE = "validate_code";

//=================短信============================
    /**
     * 注册验证码
     */
    String SMS_CODE_REGISTER_PHONE_CODE = "register_phone_code";
    /**
     * 绑定手机号码验证码
     */
    String SMS_CODE_BIND_MOBILE_CODE = "bind_mobile_code";
    /**
     * 忘记登录密码验证码
     */
    String SMS_CODE_FORGOT_PASSWORD_CODE = "forgot_password_code";
    /**
     * 重置支付密码验证码
     */
    String SMS_CODE_RESET_PAY_PWD_CODE = "reset_pay_pwd_code";

    /**
     * 修改支付密码短信通知
     */
    String SMS_MODIFY_PAY_PWD = "modify_pay_pwd";

//===========================================================

//======================站内信================================

    /**
     * 提现提醒
     */
    String LETTER_CASH_APPLY = "cash_apply";

    /**
     * 提现成功提醒
     */
    String LETTER_CASH_SUCCESS = "cash_success";

    /**
     * 提现失败提醒
     */
    String LETTER_CASH_FAILED = "cash_failed";

    /**
     * 提现退汇提醒
     */
    String LETTER_CASH_TUIHUI = "cash_tuihui";

    /**
     * 实名认证提醒
     */
    String LETTER_IDENTIFY_APPLY = "identify_apply";

    /**
     * 实名认证成功提醒
     */
    String LETTER_IDENTIFY_SUCCESS = "identify_success";

    /**
     * 实名认证失败提醒
     */
    String LETTER_IDENTIFY_FAILED = "identify_failed";

    /**
     * 待付款提醒
     */
    String LETTER_ORDER_WAIT_PAY = "order_wait_pay";

    /**
     * 取消付款提醒
     */
    String LETTER_ORDER_CANCEL = "order_cancel";

    /**
     * 超时关闭提醒
     */
    String LETTER_ORDER_TIMEOUT = "order_timeout";

    /**
     * 待发货提醒
     */
    String LETTER_ORDER_WAIT_SEND = "order_wait_send";

    /**
     * 已发货提醒
     */
    String LETTER_ORDER_SENT = "order_sent";

    /**
     * 确认收货提醒
     */
    String LETTER_ORDER_RECEIVE = "order_receive";

    /**
     * 评价回复提醒
     */
    String LETTER_COMMENT_REPLY = "comment_reply";

    /**
     * 退款申请失败提醒
     */
    String LETTER_REFUND_MONEY_FAILED = "refund_money_failed";

    /**
     * 退款成功提醒
     */
    String LETTER_REFUND_MONEY_SUCCESS = "refund_money_success";

    /**
     * 退货退款申请失败提醒
     */
    String LETTER_REFUND_ALL_FAILED = "refund_all_failed";

    /**
     * 退货退款申请成功提醒
     */
    String LETTER_REFUND_ALL_APPLY_SUCCESS = "refund_all_apply_success";

    /**
     * 退款成功提醒
     */
    String LETTER_REFUND_ALL_SUCCESS = "refund_all_success";

    /**
     * 发货提醒 - 商家
     */
    String LETTER_SHOP_ORDER_WAIT_SEND = "shop_order_wait_send";

    /**
     * 退货提醒 - 商家
     */
    String LETTER_SHOP_REFUND_GOODS_POSTAGE = "shop_refund_goods_post";

    /**
     * 买家延迟收货提醒 - 商家
     */
    String LETTER_SHOP_ORDER_DELAY = "shop_order_delay";

    /**
     * 退款成功提醒 - 商家
     */
    String LETTER_SHOP_REFUND_MONEY_SUCCESS = "shop_refund_money_success";

    /**
     * 结算到账 - 商家
     */
    String LETTER_SHOP_STATEMENT_ARRIVE = "shop_statement_arrive";

    /**
     * 提现提醒 - 商家
     */
    String LETTER_SHOP_CASH_APPLY = "shop_cash_apply";

    /**
     * 提现成功提醒 - 商家
     */
    String LETTER_SHOP_CASH_SUCCESS = "shop_cash_success";

    /**
     * 提现失败提醒 - 商家
     */
    String LETTER_SHOP_CASH_FAILED = "shop_cash_failed";

//============================================================

}
