package com.huimi.core.constant;

public interface LockId {

    /**
     * 充值锁
     */
    String RECHARGE = "LOCK:RECHARGE:";

    /**
     * 提现锁
     */
    String CASH = "LOCK:CASH:";

    /**
     * 管理员操作实名认证
     */
    String REALNAME_REVIEW_BY_ADMIN = "LOCK:REALNAME_REVIEW_BY_ADMIN:";

    /**
     * 管理员操作银行卡认证
     */
    String ACCOUNTBANK_REVIEW_BY_ADMIN = "LOCK:ACCOUNTBANK_REVIEW_BY_ADMIN:";

    /**
     * 管理员操作提现审核
     */
    String ACCOUNTCASH_REVIEW_BY_ADMIN = "LOCK:ACCOUNT_CASH_REVIEW_BY_ADMIN:";

    /**
     * 用户修改昵称
     */
    String PROVICER_USER_CHANGE_NICKNAME="LOCK:PROVICER_USER_CHANGE_NICKNAME:";
    String USER_CHANGE_NICKNAME="LOCK:USER_CHANGE_NICKNAME:";
    /**
     * 用户修改头像
     */
    String USER_MODIFY_AVATAR="LOCK:USER_MODIFY_AVATAR:";

    /**
     * 新用户注册
     */
    String PROVIDER_USER_REGISTER="LOCK:PROVIDER_USER_REGISTER:";
    String CONSUMER_USER_REGISTER="LOCK:CONSUMER_USER_REGISTER:";
    /**
     * 用户实名认证提交
     */
    String CONSUMER_USER_REALNAME_IDENTIFY="LOCK:CONSUMER_USER_REALNAME_IDENTIFY:";
    /**
     * 用户支付订单
     */
    String USER_PAY_ORDER = "LOCK:USER_PAY_ORDER:";

    /**
     * 用户充值余额
     */
    String USER_RECHARGE = "LOCK:USER_RECHARGE:";

    /**
     * 商品锁
     */
    String GOODS = "LOCK:GOODS:";

    /**
     * 用户支付订单
     */
    String USER_PAY_PRODUCT = "LOCK:USER_PAY_PRODUCT:";
}
