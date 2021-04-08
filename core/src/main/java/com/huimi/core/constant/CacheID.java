package com.huimi.core.constant;

/**
 * 系统缓存目录配置常量
 *
 * @author liweidong
 * @date 2017年03月10日 13:43
 */
public interface CacheID {

    // 存放用户抽奖的次数
    String USER_LOTTERY_NUM = "USER_LOTTERY_NUM:";

    /**
     * 支付结果缓存目录前缀
     */
    String PAY_RESULT = "PAY_RESULT:";
    /**
     * 交易结果缓存目录前缀
     */
    String TRADE_RESULT = "TRADE_RESULT:";
    /**
     * 参数配置缓存目录前缀
     */
    String CONFIG_PREFIX = "CONFIG:";
    /**
     * 消息模板缓存目录前缀
     */
    String TEMPLATE_MESSAGE_PREFIX = "TEMPLATE:MESSAGE:";
    /**
     * 系统消息队列缓存目录
     */
    String QUEUE_MESSAGE = "QUEUE:MESSAGE";
    /**
     * 挂单处理列缓存目录
     */
    String QUEUE_TRADE = "QUEUE:TRADE";
    /**
     * 充值回调处理队列缓存目录
     */
    String QUEUE_RECHARGE = "QUEUE:RECHARGE";
    /**
     * 提现回调处理队列缓存目录
     */
    String QUEUE_CASH = "QUEUE:CASH";
    /**
     * 提现发起付款处理队列缓存目录
     */
    String QUEUE_CASHPAY = "QUEUE:CASHPAY";
    /**
     * 委托回报处理队列缓存目录
     */
    String QUEUE_ENTRUST_PUSH = "QUEUE:ENTRUST_PUSH";
    /**
     * 成交回报处理队列缓存目录
     */
    String QUEUE_DEAL_PUSH = "QUEUE:DEAL_PUSH";

    /**
     * 图文验证码key前缀
     */
    String GRAPHIC_CODE_PREFIX = "GRAPHIC_CODE:";
    /**
     * 找回密码验证码授权key前缀
     */
    String BACK_PWD_KEY_PREFIX = "BACK_PWD_AUTH_KEY:";
    /**
     * 登录校验认证key前缀
     */
    String LOGIN_KEY_PREFIX = "LOGIN_KEY:";
    /**
     * 修改登录密码验证码授权key前缀
     */
    String MODIFY_LOGIN_PWD_KEY_PREFIX = "MODIFY_LOGIN_PWD_AUTH_KEY:";
    /**
     * 绑定谷歌验证器验证码授权key前缀
     */
    String BIND_GOOGLE_KEY_AUTH_KEY = "BIND_GOOGLE_KEY_AUTH_KEY:";

    /**
     * 修改谷歌验证器验证码授权key前缀
     */
    String MODIFY_GOOGLE_KEY_AUTH_KEY = "MODIFY_GOOGLE_KEY_AUTH_KEY:";

    /**
     * 关闭、开启谷歌验证器验证码授权key前缀
     */
    String CLOSE_OR_OPEN_GOOGLE_KEY_AUTH_KEY = "CLOSE_OR_OPEN_GOOGLE_KEY_AUTH_KEY:";

    /**
     * 邮件/短信验证码前缀
     */
    // 忘记密码-重置
    String BACK_MOBILE_VERIFY_CODE_PREFIX = "BACK:MOBILE:VERIFY_CODE:";
    String BACK_EMAIL_VERIFY_CODE_PREFIX = "BACK:EMAIL:VERIFY_CODE:";
    // 注册
    String REGIST_MOBILE_VERIFY_CODE_PREFIX = "REGIST:MOBILE:VERIFY_CODE:";
    String REGIST_EMAIL_VERIFY_CODE_PREFIX = "REGIST:EMAIL:VERIFY_CODE:";
    // 绑定
    String BIND_MOBILE_VERIFY_CODE_PREFIX = "BIND:MOBILE:VERIFY_CODE:";
    String BIND_EMAIL_VERIFY_CODE_PREFIX = "BIND:EMAIL:VERIFY_CODE:";
    // 修改绑定
    String MODIFY_PWD_MOBILE_VERIFY_CODE_PREFIX = "MODIFY_PWD:MOBILE:VERIFY_CODE:";
    String MODIFY_PWD_EMAIL_VERIFY_CODE_PREFIX = "MODIFY_PWD:EMAIL:VERIFY_CODE:";
    // 修改支付密码
    String RESET_PAY_PWD_MOBILE_VERIFY_CODE_PREFIX = "RESET_PAY_PWD:MOBILE:VERIFY_CODE:";

    String BACK_MENTION_PWD_MOBILE_VERIFY_CODE_PREFIX = "BACK_MENTION_PWD:MOBILE:VERIFY_CODE:";
    String BACK_MENTION_PWD_EMAIL_VERIFY_CODE_PREFIX = "BACK_MENTION_PWD:EMAIL:VERIFY_CODE:";
    String BIND_GOOGLE_KEY_MOBILE_VERIFY_CODE_PREFIX = "BIND_GOOGLE_KEY:MOBILE:VERIFY_CODE:";
    String BIND_GOOGLE_KEY_EMAIL_VERIFY_CODE_PREFIX = "BIND_GOOGLE_KEY:EMAIL:VERIFY_CODE:";
    String MODIFY_GOOGLE_KEY_MOBILE_VERIFY_CODE_PREFIX = "MODIFY_GOOGLE_KEY:MOBILE:VERIFY_CODE:";
    String MODIFY_GOOGLE_KEY_EMAIL_VERIFY_CODE_PREFIX = "MODIFY_GOOGLE_KEY:EMAIL:VERIFY_CODE:";
    String CLOSE_OR_OPEN_GOOGLE_AUTH_MOBILE_VERIFY_CODE_PREFIX = "CLOSE_OR_OPEN_GOOGLE_AUTH:MOBILE:VERIFY_CODE:";
    String CLOSE_OR_OPEN_GOOGLE_AUTH_EMAIL_VERIFY_CODE_PREFIX = "CLOSE_OR_OPEN_GOOGLE_AUTH:EMAIL:VERIFY_CODE:";
    String MENTION_COIN_MOBILE_VERIFY_CODE_PREFIX = "MENTION_COIN:MOBILE:VERIFY_CODE:";
    String MENTION_COIN_EMAIL_VERIFY_CODE_PREFIX = "MENTION_COIN:EMAIL:VERIFY_CODE:";

    //校验发送信息间隔时间的前缀
    String CHECK_SEND_TIME_PREFIX = "CHECK:SEND_TIME:PREFIX:";

    //委托中主订单缓存前缀
    String CACHE_CURR_MAIN_DELEGATES = "DELEGATES:CURR:MAIN:";
    //委托中子订单缓存前缀
    String CACHE_CURR_SUB_DELEGATES = "DELEGATES:CURR:SUB:";

    /**
     * 最优母账号缓存前缀
     */
    String PREFIX_ACCOUNT = "MOTHER_ACCOUNT:";

    String EXCHANGE = "EXCHANGE:";


    //手续费模板（默认）
    String SERVICE_FEE_TPL = "biz:config:serviceFeeTpl:";
    String ETH_SERVICE_FEE_TPL = "ETH_SERVICE_FEE_TPL";
    //净值风控模板（默认）
    String NET_WORTH_CONTROL_TPL = "NET_WORTH_CONTROL_TPL";

    //绑定谷歌验证器-googleKey
    String BIND_GOOGLE_KEY = "BIND_GOOGLE_KEY:";


    /**
     * 提币成功-邮箱
     */
    String MENTION_COIN_SUCCESS_EMAIL = "mention_coin_success_email";
    /**
     * 提币成功-短信
     */
    String MENTION_COIN_SUCCESS_SMS = "mention_coin_success_sms";
    /**
     * 提币成功-站内信
     */
    String MENTION_COIN_SUCCESS_LETTER = "mention_coin_success_letter";

    /**
     * 充币成功-邮箱
     */
    String RECHARGE_COIN_SUCCESS_EMAIL = "recharge_coin_success_email";
    /**
     * 充币成功-短信
     */
    String RECHARGE_COIN_SUCCESS_SMS = "recharge_coin_success_sms";
    /**
     * 充币成功-站内信
     */
    String RECHARGE_COIN_SUCCESS_LETTER = "recharge_coin_success_letter";

    /**
     * 交易所信息缓存前缀
     */
    String EXCHANGE_INFO = "biz:config:exchange:";

    /**
     * 交易对配置信息缓存前缀
     */
    String COMMODITY_CONFIG_INFO = "biz:config:commodity:";

    /**
     * 待处理的子委托
     */
    String PROCESSING_SUB_DELEGATE = "biz:processing:subDelegate:";
    /**
     * 待处理的主委托
     */
    String PROCESSING_MAIN_DELEGATE = "biz:processing:mainDelegate:";

    /**
     * 中英文类型
     */
    String LANGUAGE_TYPE = "LANGUAGE_TYPE:";

    /**
     * 母账号主流币种资产信息缓存前缀  前缀 + [code] + : + [交易所编号]
     */
    String MOTHER_ACCOUNT_MAIN_CODE__PREFIX = "biz:mother:account:code:";

    String MENU_RIGHTS_CACHE = "menu:cache:";

    /**
     * 修改绑定手机 - 旧手机验证码
     */
    String BIND_NEW_MOBILE_OLD_CHECK_SUCCESS_PREFIX = "bind:new:mobile:old:check:success:";

    /**
     * 修改支付密码 - 忘记支付密码,手机短信验证通过
     */
    String BACK_PAY_PWD_MOBILE_CHECK_SUCCESS_PREFIX = "back:pay:pwd:mobile:check:success:";

    /**
     * 修改支付密码
     */
    String MODIFY_PAYPWD_CHECK_OLD_SUCCESS_PREFIX = "modify:paypwd:check:old:success:prefix:";

    /*全部分类数据的缓存标识*/
    String GOODS_TYPE_LIST = "goods:menu";

    /*首页分类数据的缓存标识*/
    String HOME_GOODS_TYPE_LIST = "home:goods:menu";

    /**
     * 下单队列任务订单编号前缀
     */
    String SUBMIT_ORDER_PREFIX = "submit:order:prefix:";

    /**
     * 订单支付超时前缀
     */
    String ORDER_PAY_TIME_OUT_PREFIX = "order:pay:time:out:prefix:";

    /**
     * 订单待支付前缀
     */
    String ORDER_WAIT_PAY_ORDERNO_PREFIX = "order:wait:pay:orderno:prefix:";

    String ORDER_WAIT_PAY_ORDERID_PREFIX = "order:wait:pay:orderid:prefix:";

    /**
     * 甬易支付通道列表缓存
     */
    String YYPAY_CHANNEL = "YYPAY_CHANNEL";
}
