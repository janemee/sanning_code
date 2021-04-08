package com.huimi.core.constant;

/**
 * 系统参数常量  类型1,系统底层配置:bottom;2,费率信息配置:fees;3,邮件/短信配置:msgTemplate;4,三方接口配置:third;5,附加功能配置:other
 *
 * @author Mr.
 * @date 2017/5/22
 */
public interface ConfigNID {

    /******************************** 1 系统底层配置 ***********************************/
    /**
     * 网站名称
     */
    String WEB_NAME = CacheID.CONFIG_PREFIX + "web_name";
    /**
     * 网站copyRight
     */
    String COPY_RIGHT = CacheID.CONFIG_PREFIX + "copy_right";
    /**
     * 网站ICP备案
     */
    String WEB_ICP = CacheID.CONFIG_PREFIX + "web_icp";
    /**
     * 前台API服务地址
     */
    String API_SERVER_URL = CacheID.CONFIG_PREFIX + "api_server_url";
    /**
     * 前台WEB服务地址
     */
    String WEB_SERVER_URL = CacheID.CONFIG_PREFIX + "web_server_url";
    /**
     * 文件服务器地址
     */
    String IMAGE_SEVER_URL = CacheID.CONFIG_PREFIX + "image_server_url";
    /**
     * 后台服务器地址
     */
    String ADMIN_SEVER_URL = CacheID.CONFIG_PREFIX + "admin_server_url";
    /**
     * 静态资源地址
     */
    String STATIC_SERVER_URL = CacheID.CONFIG_PREFIX + "static_server_url";
    /**
     * 是否开通万能验证码 9999
     */
    String SUPER_VALIDATE_OPEN = CacheID.CONFIG_PREFIX + "super_validate_open";

    /**
     * 万能验证码
     */
    String SUPER_VALIDATE_OPEN_CODE = CacheID.CONFIG_PREFIX + "super_validate_open_code";
    /**
     * 短信通道
     */
    String SMS_CHANNEL = CacheID.CONFIG_PREFIX + "sms_channel";
    /**
     * 大家都在发
     */
    String PLATFORM_INVITE_CODE = CacheID.CONFIG_PREFIX + "platform_invite_code";

    //普通用户
    String GENERAL = CacheID.CONFIG_PREFIX + "general";
    //商户
    String BUSINESS = CacheID.CONFIG_PREFIX + "business";

    //支付超时时间 分钟
    String TIME_OUT_PAY = CacheID.CONFIG_PREFIX + "time_out_pay";
    //自动确认收货时间 天
    String TIME_AUTO_OVER = CacheID.CONFIG_PREFIX + "time_auto_over";
    //延长收货时间
    String TIME_EXTEND = CacheID.CONFIG_PREFIX + "time_extend";

    // 注册是否必填邀请码
    String INVITE_CODE_NECESSARY = CacheID.CONFIG_PREFIX + "enabled_invite_mechanism";
    /**
     * 测试环境是否开启
     */
    String TEST_ENVIRONMENT = CacheID.CONFIG_PREFIX + "test_environment";

    /**
     * 自动扫描时间过期任务开关
     */
    String AUTO_OUT_TASK_FLAG = CacheID.CONFIG_PREFIX + "auto_out_task_flag";

    String HistoryStateApkRedisUrl = "apk:history:apkUrl";

    String HistoryStateQcCodeRedisUrl = "apk:history:qrCode";

    /**
     * 设备注册码是否必填
     */
    String REGISTER_CODE_FLAG = CacheID.CONFIG_PREFIX + "register_code_flag";

    /**
     * 回调测试开关
     */
    String NOTIFY_TEST_FLAG = CacheID.CONFIG_PREFIX + "notify_test_flag";

    /**
     * 登录有效期 单位：分钟
     */
    String login_USER_COOKIE_TIME = CacheID.CONFIG_PREFIX + "login_user_cookie_time";

    /**
     * 系统log图片地址  默认抖管家
     */
    String WEB_LOG_IMG_URL = CacheID.CONFIG_PREFIX + "web_log_img_url";

    /**
     * 网站头部信息
     */
    String SYSTEM_TITLE = CacheID.CONFIG_PREFIX + "system_title";
    /**
     * 公司名称
     */
    String COMPANY_NAME = CacheID.CONFIG_PREFIX + "company_name";

    /**
     * 公司主页地址
     */
    String COMPANY_INDEX_URL = CacheID.CONFIG_PREFIX + "company_index_url";

    /**
     * 网站头部ico图标地址
     */
    String FAV_ICO_URL = CacheID.CONFIG_PREFIX + "fav_ico_url";

    /**
     * 定时器开启开关
     */
    String QUARTZ_TASK_FLAG = CacheID.CONFIG_PREFIX + "quartz_task_flag";


    /**
     * 优化备用发送任务开关  1 开启  其他  关闭
     */
    String SEND_TASK_FLAG = CacheID.CONFIG_PREFIX + "send_task_flag";

    /******************************** 2 费率信息配置 ***********************************/
    /**
     * 提现手续费收取方式 ratio 按比例, count 固定(按笔) ，禁用则不收取
     */
    String CASH_FEE_TYPE = CacheID.CONFIG_PREFIX + "cash_fee_type";
    /**
     * 提现收付费按比例收取， 比例值
     **/
    String CASH_FEE_RATIO = CacheID.CONFIG_PREFIX + "cash_fee_ratio";
    /**
     * 提现收付费固定收取(按提现笔数)，值
     **/
    String CASH_FEE_COUNT = CacheID.CONFIG_PREFIX + "cash_fee_count";
    /**
     * 提现手续费最低收取值
     */
    String CASH_FEE_LOWEST = CacheID.CONFIG_PREFIX + "cash_fee_lowest";
    /**
     * 单笔提现上限
     */
    String CASH_LIMIT_HIGH = CacheID.CONFIG_PREFIX + "cash_limit_high";
    /**
     * 单笔提现下限
     */
    String CASH_LIMIT_LOW = CacheID.CONFIG_PREFIX + "cash_limit_low";

    /**
     * 充值手续费收取方式 ratio 按比例， count 固定,禁用则不收取
     */
    String RECHARGE_FEE_TYPE = CacheID.CONFIG_PREFIX + "recharge_fee_type";
    /**
     * 充值手续费收取比例
     */
    String RECHARGE_FEE_RATIO = CacheID.CONFIG_PREFIX + "recharge_fee_ratio";
    /**
     * 充值手续费固定值
     */
    String RECHARGE_FEE_COUNT = CacheID.CONFIG_PREFIX + "recharge_fee_count";
    /**
     * 充值手续费最低收取值
     */
    String RECHARGE_FEE_LOWEST = CacheID.CONFIG_PREFIX + "recharge_fee_lowest";
    /**
     * 单笔充值上限
     */
    String RECHARGE_LIMIT_HIGH = CacheID.CONFIG_PREFIX + "recharge_limit_high";
    /**
     * 单笔充值下限
     */
    String RECHARGE_LIMIT_LOW = CacheID.CONFIG_PREFIX + "recharge_limit_low";

    /**
     * 平台分佣比例
     */
    String FLAT_COMMISSION_RATE = CacheID.CONFIG_PREFIX + "flat_commission_rate";

    /**
     * 交易数量限制
     */
    String TRADE_NUMBER_LIMIT = CacheID.CONFIG_PREFIX + "trade_number_limit";
    /**
     * 最小提货数量
     */
    String MIN_DELIVERY_NUMBER = CacheID.CONFIG_PREFIX + "min_delivery_number";

    /******************************** 3 邮件/短信配置 ***********************************/
    /**
     * 是否开通短信功能
     */
    String SMS_ENABLED = CacheID.CONFIG_PREFIX + "sms_enabled";
    /**
     * 验证码发送间隔
     */
    String SMS_YZM_TIME_LIMIT = CacheID.CONFIG_PREFIX + "sms_yzm_time_limit";
    /**
     * 短信验证码有效期（单位：分钟）
     */
    String SMS_YZM_TIME_OUT = CacheID.CONFIG_PREFIX + "sms_yzm_time_out";
    /**
     * 邮箱smtp地址
     */
    String EMAIL_SMTP_ADDRESS = CacheID.CONFIG_PREFIX + "email_smtp_address";
    /**
     * 邮箱发件人邮箱
     */
    String EMAIL_ACCOUNT = CacheID.CONFIG_PREFIX + "email_account";
    /**
     * 邮箱发件人邮箱密码
     */
    String EMAIL_ACCOUNT_PWD = CacheID.CONFIG_PREFIX + "email_account_pwd";
    /**
     * 邮箱发件人邮箱名称
     */
    String EMAIL_ACCOUNT_NAME = CacheID.CONFIG_PREFIX + "email_account_name";

    /**
     * 短信 验证码 用户名
     */
    String SMS_YZM_USERNAME = CacheID.CONFIG_PREFIX + "sms_yzm_username";
    /**
     * 短信  验证码 密码
     */
    String SMS_YZM_PASSWORD = CacheID.CONFIG_PREFIX + "sms_yzm_password";
    /**
     * 云片网短信apikey
     */
    String SMS_YP_APIKEY = CacheID.CONFIG_PREFIX + "sms_yp_apikey";

    /**
     * 短信 签名
     */
    String SMS_SIGNATURE = CacheID.CONFIG_PREFIX + "sms_signature";

    /**
     * 客服二维码
     */
    String CUSTOMER_QRCODE_IMG = CacheID.CONFIG_PREFIX + "customer_qrcode_img";


    /******************************** 4 三方接口配置 ***********************************/


    /******************************** 5 附加功能配置 ***********************************/
    /**
     * 未登录状态是否显示商品 FULL 全部显示， EMPTY 不显示
     */
    String SHOW_GOODS_UNLOGIN = CacheID.CONFIG_PREFIX + "show_goods_unlogin";
    /**
     * 下线关闭所有任务开关
     */
    String ActiveOverFlag = CacheID.CONFIG_PREFIX + "active_over_flag";
}
