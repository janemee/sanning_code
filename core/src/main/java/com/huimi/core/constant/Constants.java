package com.huimi.core.constant;

/**
 * 系统常量
 *
 * @author Vector
 * @create 2017-06-12 15:06
 */
public interface Constants {

    String PLATFORM_INVITE_CODE = "P80001";

    /**
     * 常量手机类型
     */
    int MOBILE_TYPE = 1;

    /**
     * 常量邮箱类型
     */
    int EMAIL_TYPE = 2;

    /**
     * 常量0
     */
    Integer CONS_ZERO = 0;

    /**
     * 常量0
     */
    Integer CONS_ONE = 1;

    /**
     * 表单token标识
     */
    String FORM_TOKEN = "form_token";

    /**
     * 后台权限查询标识-代理商
     */
    String AG = "ag";

    /**
     * 后台权限查询标识-用户
     */
    String USER = "user";

    /**
     * 找回密码 - 邮箱验证码
     */
    String BACK_PWD_EMAIL = "back-pwd-code-email";
    /**
     * 找回密码 -手机验证码
     */
    String BACK_PWD_MOBILE = "back-pwd-code-mobile";
    /**
     * 找回支付密码 -手机验证码
     */
    String BACK_PAY_PWD_MOBILE = "back-pay-pwd-code-mobile";
    /**
     * 注册 - 手机验证码
     */
    String REGIST_CODE_MOBILE = "regist-pwd-mobile";
    /**
     * 注册 - 邮箱验证码
     */
    String REGIST_CODE_EMAIL = "regist-pwd-email";
    /**
     * 提币 - 邮箱验证码
     */
    String MENTION_COIN_CODE_EMAIL = "mention_coin-code-email";
    /**
     * 提币 - 手机验证码
     */
    String MENTION_COIN_CODE_MOBILE = "mention_coin-code-mobile";

    /**
     * 绑定手机 -手机验证码
     */
    String BIND_MOBILE = "bind-code-mobile";

    /**
     * 修改绑定手机 - 旧手机验证码
     */
    String OLD_BIND_NEW_MOBILE = "old-bind-new-mobile-code";

    /**
     * 修改绑定手机 - 新手机验证码
     */
    String NEW_BIND_NEW_MOBILE = "new-bind-new-mobile-code";


    /**
     * 绑定邮箱 -邮箱验证码
     */
    String BIND_EMAIL = "bind-code-email";

    /**
     * 修改登录密码 -手机验证码
     */
    String MODIFY_PWD_MOBILE = "modify-pwd-code-mobile";

    /**
     * 修改登录密码 -邮箱验证码
     */
    String MODIFY_PWD_EMAIL = "modify-pwd-code-email";


    /**
     * 找回提币密码 -手机验证码
     */
    String BACK_MENTION_PWD_MOBILE = "back-mention-pwd-code-mobile";

    /**
     * 找回提币密码 -邮箱验证码
     */
    String BACK_MENTION_PWD_EMAIL = "back-mention-pwd-code-email";

    /**
     * 绑定谷歌验证码 -手机验证码
     */
    String BIND_GOOGLE_KEY_MOBILE = "bind-google-key-code-mobile";

    /**
     * 绑定谷歌验证码 -邮箱验证码
     */
    String BIND_GOOGLE_KEY_EMAIl = "bind-google-key-code-email";

    /**
     * 修改谷歌验证码 -手机验证码
     */
    String MODIFY_GOOGLE_KEY_MOBILE = "modify-google-key-code-mobile";

    /**
     * 修改谷歌验证码 -邮箱验证码
     */
    String MODIFY_GOOGLE_KEY_EMAIL = "modify-google-key-code-email";

    /**
     * 开启/关闭谷歌验证码 -手机验证码
     */
    String CLOSE_OR_OPEN_GOOGLE_AUTH_MOBILE = "close-or-open-google-auth-code-mobile";

    /**
     * 开启/关闭谷歌验证码 -邮箱验证码
     */
    String CLOSE_OR_OPEN_GOOGLE_AUTH_EMAIL = "close-or-open-google-auth-code-email";

    /**
     * 添加新银行卡 -预留手机验证码
     */
    String ADD_NEW_BANK_CARD = "add_new_bank_card";


    String DEVICE_CHANNEL = "device:channel:";
    String CHANNEL_PREFIX = "channel:device:";
    String TIKTOK_TASK = "tiktok_task";
    String TIKTOK_LIVE_HOT= "tiktok_live_hot";
    String TIKTOK_LIVE_CHAT = "tiktok_live_chat";

}
