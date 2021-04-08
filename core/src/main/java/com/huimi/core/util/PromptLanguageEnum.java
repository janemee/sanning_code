package com.huimi.core.util;

import cn.hutool.core.collection.CollectionUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.Language;
import com.huimi.core.exception.BussinessException;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * 中英文提示枚举
 */
@AllArgsConstructor
public enum PromptLanguageEnum {

    /**
     * [买入价]不能为空
     */
    PRO1("pro1", "[买入价]不能为空", "The purchase price cannot be empty"),
    /**
     * [买入量]不能为空
     */
    PRO2("pro2", "[买入量]不能为空", "Buy volume cannot be empty"),
    /**
     * [交易对]不能为空
     */
    PRO3("pro3", "[交易对]不能为空", "Trading pairs cannot be empty"),
    /**
     * [交易对]不合法
     */
    PRO4("pro4", "[交易对]不合法", "Trading is illegal"),
    /**
     * 非主流币种
     */
    PRO5("pro5", "非主流币种", "Non-mainstream currency"),
    /**
     *
     */
    PRO6("pro6", "无可用净资产或净资产计算出错", "No error in calculating net assets or net assets"),
    /**
     * [卖出价]不能为空
     */
    PRO7("pro7", "[卖出价]不能为空", "The selling price cannot be empty"),
    /**
     * [卖出量]不能为空
     */
    PRO8("pro8", "[卖出量]不能为空", "You can't sell anything short"),
    /*
    PRO9("pro9","[交易对]不能为空", "Failed to get available assets"),*/
    /**
     * [交易所编号]不能为空
     */
    PRO10("pro10", "[交易所编号]不能为空", "Exchange Numbers cannot be empty"),

    PRO11("pro11", "[交易对]不合法", "Trading is illegal"),

    PRO12("pro12", "[交易额]不能为空", "Trading volumes cannot be empty"),
    /*
    PRO13("pro13","[交易对]不能为空", "Failed to get available assets"),*/
    /**
     * [交易所信息]不能为空
     */
    PRO14("pro14", "[交易所信息]不能为空", "Exchange information cannot be empty"),
    /*
    PRO15("pro15","[交易对]不合法", "Failed to get available assets"),*/
    /**
     * [委托编号]不能为空
     */
    PRO16("pro16", "[委托编号]不能为空", "The delegate number cannot be empty"),
    /**
     * 抱歉,您选择的交易所暂停了该币种的交易
     */
    PRO17("pro17", "抱歉,您选择的交易所暂停了该币种的交易", "Sorry, the exchange you choose suspended trading of this currency"),
    /**
     * 下单精度不符合要求,精度为:{$minAmount}
     */
    PRO18("pro18", "下单精度不符合要求,精度为:{$minAmount}", "The order precision is not in accordance with the requirements, and the precision is:{$minAmount}"),

    PRO19("pro19", "[{$currencyPair}]限价下单量需在[{$minAmount},{$maxAmount}]之间", "[{$currencyPair}]Price limit order quantity must be in[{$minAmount},{$maxAmount}]among"),

    PRO20_1("pro20_1", "[{$currencyPair}]市价买入额需在[{$minBuyAmount},{$maxBuyAmount}]之间", "[{$currencyPair}]Buy at market price[{$minBuyAmount},{$maxBuyAmount}]among"),

    PRO20_2("pro20_2", "Okex交易所[{$currencyPair}]市价买入量需大于[{$minBuyAmount}]", "okex [{$currencyPair}]Buy at market price must greater than [{$minBuyAmount}] "),

    PRO20("pro20", "[{$currencyPair}]市价买入量需在[{$minBuyAmount},{$maxBuyAmount}]之间", "[{$currencyPair}]Buy at market price[{$minBuyAmount},{$maxBuyAmount}]among"),

    PRO21("pro21", "[{$currencyPair}]市价卖出量需在[{$minSellAmount},{$maxSellAmount}]之间", "[{$currencyPair}]Sell at market price[{$minSellAmount},{$maxSellAmount}]among"),

    PRO22("pro22", "未查询到币种配置", "No currency configuration was queried"),
    /**
     * 平台升级中,当前禁止交易。如有疑问,请联系客服!
     */
    PRO23("pro23", "平台升级中,当前禁止交易。如有疑问,请联系客服!", "Trading is currently prohibited during the platform upgrade.If you have any questions, please contact customer service!"),
    /**
     * 抱歉,当前交易所已暂停交易
     */
    PRO24("pro24", "抱歉,当前交易所已暂停交易", "Sorry, the current exchange has suspended trading"),
    /**
     * 获取可用资产失败
     */
    PRO25("pro25", "获取资产失败", "Failed to get the available assets"),
    /**
     * 用户信息有误
     */
    PRO26("pro26", "用户信息有误", "User information error"),
    /**
     * 平台升级中，当前禁止提币
     */
    PRO27("pro27", "平台升级中，当前禁止提币", "Currency withdrawals are currently prohibited during platform upgrades"),
    /**
     * 参数不能为空
     */
    PRO28("pro28", "参数不能为空", "Parameters cannot be empty"),
    /**
     * 该用户不存在
     */
    PRO29("pro29", "该用户不存在", "This user does not exist"),
    /**
     * 用户id参数错误
     */
    PRO30("pro30", "用户id参数错误", "User id parameter error"),
    /**
     * 没有数据
     */
    PRO31("pro31", "没有数据", "no data"),
    /**
     * 提币功能已经冻结，请联系客服
     */
    PRO32("pro32", "提币功能已经冻结，请联系客服", "The function of withdrawing money has been frozen. Please contact customer service"),
    /**
     * 未实名认证，请先实名认证
     */
    PRO33("pro33", "未实名认证，请先实名认证", "Without real-name authentication, please first real-name authentication"),
    /**
     * 您的实名认证已提交，请等待审核
     */
    PRO34("pro34", "您的实名认证已提交，请等待审核", "Your real-name authentication has been submitted, please wait for approval"),
    /**
     * 请先设置提币密码
     */
    PRO35("pro35", "请先设置提币密码", "Please set the withdrawal password first"),
    /**
     * 请先绑定谷歌验证器
     */
    PRO36("pro36", "请先绑定谷歌验证器", "Bind the Google validator first"),
    /**
     * 提币密码参数错误
     */
    PRO37("pro37", "提币密码参数错误", "The password parameter of withdrawal is wrong"),
    /**
     * 用户认证信息有误
     */
    PRO38("pro38", "用户认证信息有误", "User authentication information is wrong"),
    /**
     * 提币密码不正确
     */
    PRO39("pro39", "提币密码不正确", "The withdrawal code is incorrect"),
    /**
     * 参数错误
     */
    PRO40("pro40", "参数错误", "parameter error"),
    /**
     * [language]类型错误
     */
    PRO41("pro41", "[language]类型错误", "[language] type error"),
    /**
     * 获取验证码过于频繁，稍后再试
     */
    PRO42("pro42", "获取验证码过于频繁，稍后再试", "Get the verification code too frequently and try again later"),
    /**
     * 谷歌验证码为空
     */
    PRO43("pro43", "谷歌验证码为空", "Google verification code is empty"),
    /**
     * 邮箱验证码错误
     */
    PRO44("pro44", "邮箱验证码错误", "Email authentication code error"),
    /**
     * 短信验证码错误
     */
    PRO45("pro45", "短信验证码错误", "Message verification code error"),
    /**
     * [提币币种]不能为空
     */
    PRO46("pro46", "[提币币种]不能为空", "[withdrawal currency] cannot be empty"),
    /**
     * [提币币种]参数错误
     */
    PRO47("pro47", "[提币币种]参数错误", "[withdrawal currency] parameter error"),
    /**
     * [提币地址]不能为空
     */
    PRO48("pro48", "[提币地址]不能为空", "[withdrawal address] cannot be empty"),
    /**
     * [提币数量]不能为空
     */
    PRO49("pro49", "[提币数量]不能为空", "[amount of withdrawal] cannot be empty"),
    /**
     * [提币数量]不能为零
     */
    PRO51("pro51", "[提币数量]不能为零", "[amount of withdrawal] cannot be zero"),
    /**
     * [手续费]不能为空
     */
    PRO52("pro52", "[手续费]不能为空", "[service charge] cannot be empty"),
    /**
     * [提币密码]不能为空
     */
    PRO53("pro53", "[提币密码]不能为空", "[withdrawal code] cannot be empty"),
    /**
     * 平台升级中，当前禁止提币
     */
    PRO54("pro54", "平台升级中，当前禁止提币", "Currency withdrawals are currently prohibited during platform upgrades"),
    /**
     * [userId]不能为空
     */
    PRO55("pro55", "[userId]不能为空", "[userId] cannot be empty"),
    /**
     * [encryptMentionPwd]不能为空
     */
    PRO56("pro56", "[encryptMentionPwd]不能为空", "[encryptMentionPwd] cannot be empty"),
    /**
     * [netWorth]不能为空
     */
    PRO57("pro57", "[netWorth]不能为空", "[netWorth] can't be empty"),

    /**
     * 您还没有实名认证,请先通过实名认证
     */
    PRO58("pro58", "您还没有实名认证,请先通过实名认证", "You haven't got the real-name authentication, please pass the real-name authentication first"),
    /**
     * 手续费不正确
     */
    PRO59("pro59", "手续费不正确", "Incorrect service charge"),
    /**
     * 提币地址错误
     */
    PRO60("pro60", "提币地址错误", "The withdrawal address is wrong"),
    /**
     * 　提币数量大于可用数量
     */
    PRO61("pro61", "提币数量大于可用数量", "The amount of withdrawal is greater than the amount available"),

    /**
     * 小于单次提币最小限额
     */
    PRO62("pro62", "小于单次提币最小限额", "Less than the minimum amount of single withdrawal"),
    /**
     * [提币数量]不能为零
     */
    PRO63("pro63", "超出今日累计提币最大限额", "Exceeding the maximum withdrawal limit for today"),
    /**
     * 用户手续费模板错误
     */
    PRO64("pro64", "用户手续费模板错误", "User fee template error"),
    /**
     * 必填参数不能为空
     */
    PRO65("pro65", "必填参数不能为空", "Mandatory parameters cannot be empty"),
    /**
     * 资金记录保存失败
     */
    PRO66("pro66", "资金记录保存失败", "Failed to save funds records"),
    /**
     * [pageSize]不能为空
     */
    PRO67("pro67", "[pageSize]不能为空", "[pageSize] cannot be empty"),
    /**
     * [pageNumber]不能为空
     */
    PRO68("pro68", "[pageNumber]不能为空", "[pageNumber] cannot be empty"),
    /**
     * 币种不正确
     */
    PRO69("pro69", "币种不正确", "Incorrect currency"),
    /**
     * [code]不能为空
     */
    PRO70("pro70", "[code]不能为空", "[code] cannot be empty"),
    /**
     * [{$currencyCode}]交易对不合法
     */
    PRO71("pro71", "[{$currencyCode}]交易对不合法", "[{$currencyCode}] transaction pairs are not legal"),
    /**
     * 用户id不能为空
     */
    PRO72("pro72", "用户id不能为空", "The user id cannot be empty"),
    /**
     * 手机号码格式错误
     */
    PRO73("pro73", "手机号码格式错误", "Wrong phone number format"),
    /**
     * 验证码错误
     */
    PRO74("pro74", "验证码错误", "Verification code error"),
    /**
     * 绑定失败
     */
    PRO75("pro75", "绑定失败", "Binding failure"),
    /**
     * 邮箱格式错误
     */
    PRO76("pro76", "邮箱格式错误", "Email format error"),
    /**
     * 参数格式错误
     */
    PRO77("pro77", "参数格式错误", "Parameter format error"),
    /**
     * 绑定的邮箱已存在
     */
    PRO78("pro78", "绑定的邮箱已存在", "The bound mailbox already exists"),
    /**
     * 绑定的手机号码已存在
     */
    PRO79("pro79", "绑定的手机号码已存在", "The bound phone number already exists"),
    /**
     * 验证类型不正确
     */
    PRO80("pro80", "验证类型不正确", "The validation type is incorrect"),
    /**
     * 两次输入的密码不一致
     */
    PRO81("pro81", "两次输入的密码不一致", "The passwords entered are inconsistent"),
    /**
     * 非法操作或验证码已过期
     */
    PRO82("pro82", "非法操作或验证码已过期", "Illegal operation or verification code expired"),
    /**
     * 两次密码不一致
     */
    PRO83("pro83", "两次密码不一致", "The passwords do not match"),
    /**
     * 已设置提币密码
     */
    PRO85("pro85", "已设置提币密码", "The withdrawal password has been set"),
    /**
     * 设置提币密码失败
     */
    PRO86("pro86", "设置提币密码失败", "Failed to set the withdrawal password"),
    /**
     * 修改提币密码失败
     */
    PRO87("pro87", "修改提币密码失败", "Failed to modify the withdrawal password"),
    /**
     * 保存数据到缓存出错
     */
    PRO88("pro88", "保存数据到缓存出错", "Error saving data to cache"),
    /**
     * 注册手机号码不正确
     */
    PRO89("pro89", "注册手机号码不正确", "The registered mobile phone number is incorrect"),
    /**
     * 手机号码未绑定
     */
    PRO90("pro90", "手机号码未绑定", "The phone number is unbound"),
    /**
     * 图文验证码错误
     */
    PRO91("pro91", "图文验证码错误", "Schema validation code error"),
    /**
     * 已绑定谷歌验证器
     */
    PRO92("pro92", "已绑定谷歌验证器", "Google validator is bound"),
    /**
     * 获取谷歌验证信息失败
     */
    PRO93("pro93", "获取谷歌验证信息失败", "Failed to get Google validation information"),
    /**
     * uuid不能为空
     */
    PRO94("pro94", "uuid不能为空", "Uuid cannot be empty"),
    /**
     * type不能为空
     */
    PRO95("pro95", "type不能为空", "Type cannot be empty"),
    /**
     * code不能为空
     */
    PRO96("pro96", "code不能为空", "The code can't be empty"),
    /**
     * 短信验证码校验失败
     */
    PRO97("pro97", "短信验证码校验失败", "Message verification code failed"),
    /**
     * 邮箱验证码校验失败
     */
    PRO98("pro98", "邮箱验证码校验失败", "Mailbox verification code failed"),
    /**
     * 类型参数错误
     */
    PRO99("pro99", "类型参数错误", "Type parameter error"),
    /**
     * googleCode不能为空
     */
    PRO100("pro100", "googleCode不能为空", "A googleCode cannot be empty"),
    /**
     * google私钥不存在或已超时
     */
    PRO101("pro101", "google私钥不存在或已超时", "The Google private key does not exist or has timed out"),
    /**
     * Google授权码校验失败
     */
    PRO102("pro102", "Google授权码校验失败", "Google authentication failed"),
    /**
     * 未绑定谷歌验证器
     */
    PRO103("pro103", "未绑定谷歌验证器", "Google validators are not bound"),
    /**
     * 生成Google私钥失败
     */
    PRO104("pro104", "生成Google私钥失败", "Failed to generate the Google private key"),
    /**
     * 原谷歌授权码校验失败
     */
    PRO105("pro105", "原谷歌授权码校验失败", "The original Google authorization code failed to verify"),
    /**
     * 谷歌验证器已开启
     */
    PRO106("pro106", "谷歌验证器已开启", "The Google validator is on"),
    /**
     * 谷歌验证器已关闭
     */
    PRO107("pro107", "谷歌验证器已关闭", "Failed to get available assets"),
    /**
     * 用户认证异常
     */
    PRO108("pro108", "用户认证异常", "User authentication exception"),
    /**
     * googleState不能为空
     */
    PRO109("pro109", "googleState不能为空", "GoogleState cannot be empty"),
    /**
     * authKey不能为空
     */
    PRO110("pro110", "authKey不能为空", "The authKey cannot be empty"),
    /**
     * 验证校验失败
     */
    PRO111("pro111", "验证校验失败", "Validation failed"),
    /**
     * Google验证码有误，请重新输入
     */
    PRO112("pro112", "Google验证码有误，请重新输入", "The Google verification code is wrong, please re-enter it"),
    /**
     * 分页条数参数错误
     */
    PRO113("pro113", "分页条数参数错误", "Page number parameter error"),
    /**
     * 分页页码参数错误
     */
    PRO114("pro114", "分页页码参数错误", "Page number parameter error"),
    /**
     * 提币地址不能超过50个字符
     */
    PRO115("pro115", "提币地址不能超过50个字符", "The withdrawal address shall not exceed 50 characters"),
    /**
     * 删除提币地址失败
     */
    PRO116("pro116", "删除提币地址失败", "Deletion of the withdrawal address failed"),
    /**
     * 删除失败
     */
    PRO117("pro117", "删除失败", "Delete failed"),
    /**
     * [真实姓名]不能为空
     */
    PRO119("pro119", "[真实姓名]不能为空", "[real name] cannot be empty"),
    /**
     * [身份证号码]不能为空
     */
    PRO120("pro120", "[身份证号码]不能为空", "[id card number] cannot be empty"),
    /**
     * 身份证号码不能重复
     */
    PRO121("pro121", "身份证号码不能重复", "The id number cannot be repeated"),
    /**
     * [身份证正面图片]不能为空
     */
    PRO122("pro122", "[身份证正面图片]不能为空", "[photo of id card front] cannot be empty"),
    /**
     * [身份证反面图片]不能为空
     */
    PRO123("pro123", "[身份证反面图片]不能为空", "[photo on opposite side of id card] cannot be empty"),
    /**
     * 姓名格式不正确
     */
    PRO124("pro124", "姓名格式不正确", "Incorrect name format"),
    /**
     * 身份证号格式不正确
     */
    PRO125("pro125", "身份证号格式不正确", "The id number format is incorrect"),
    /**
     * 图片不能超过2M
     */
    PRO126("pro126", "图片不能超过2M", "The picture should not exceed 2M"),
    /**
     * 认证失败
     */
    PRO127("pro127", "认证失败", "authentication failure"),
    /**
     * [realname]不能为空
     */
    PRO128("pro128", "[realname]不能为空", "[realname] cannot be empty"),
    /**
     * [idCardNo]不能为空
     */
    PRO129("pro129", "[idCardNo]不能为空", "[idCardNo] cannot be empty"),
    /**
     * [idCardFrontPic]不能为空
     */
    PRO130("pro130", "[idCardFrontPic]不能为空", "[idCardFrontPic] cannot be empty"),
    /**
     * [idCardReversePic]不能为空
     */
    PRO131("pro131", "[idCardReversePic]不能为空", "[idCardReversePic] cannot be empty"),
    /**
     * 已实名认证成功
     */
    PRO132("pro132", "已实名认证成功", "The real-name authentication has been successful"),
    /**
     * 已提交认证，请等待审核
     */
    PRO133("pro133", "已提交认证，请等待审核", "Certification has been submitted, please wait for approval"),
    /**
     * 实名认证失败
     */
    PRO134("pro134", "实名认证失败", "The authentication has been submitted, please wait for the verification real-name authentication to fail"),
    /**
     * [护照号码]不能为空
     */
    PRO135("pro135", "[护照号码]不能为空", "[passport number] cannot be empty"),
    /**
     * [护照图片]不能为空
     */
    PRO136("pro136", "[护照图片]不能为空", "[photo of passport] cannot be empty"),
    /**
     * 提交认证失败
     */
    PRO137("pro137", "提交认证失败", "Failed to submit authentication"),
    /**
     * 分页参数错误
     */
    PRO138("pro138", "分页参数错误", "Page parameter error"),
    /**
     * 获取失败
     */
    PRO139("pro139", "获取失败", "fail to get"),
    /**
     * 用户名密码不能为空
     */
    PRO140("pro140", "用户名密码不能为空", "The username password cannot be empty"),
    /**
     * 平台升级中，当前禁止登陆
     */
    PRO141("pro141", "平台升级中，当前禁止登陆", "Login is currently prohibited in the platform upgrade"),
    /**
     * 您还没有注册,请先注册!
     */
    PRO142("pro142", "您还没有注册,请先注册!", "You have not registered, please register first!"),
    /**
     * 您的账号已注销，请联系客服
     */
    PRO143("pro143", "您的账号已注销，请联系客服", "Your account has been cancelled. Please contact customer service"),
    /**
     * 用户名或密码错误
     */
    PRO144("pro144", "用户名或密码错误", "User name or password error"),
    /**
     * 密码错误次数超限
     */
    PRO144_1("pro144_1", "密码错误次数超限", "password error count out of range"),
    /**
     * 无效的google验证码
     */
    PRO145("pro145", "无效的google验证码", "Invalid Google verification code"),
    /**
     * 登录失败
     */
    PRO146("pro146", "登录失败", "login failure"),
    /**
     * 用户已开启Google验证,请输入Google验证码
     */
    PRO147("pro147", "用户已开启Google验证,请输入Google验证码", "User has started Google validation, please enter Google verification code"),
    /**
     * 用户信息有误或未登录
     */
    PRO148("pro148", "用户信息有误或未登录", "User information is incorrect or not logged in"),
    /**
     * 请勾选同意《注册协议》
     */
    PRO149("pro149", "请勾选同意《注册协议》", "Please click to agree to the registration agreement."),
    /**
     * 平台升级中，当前禁止注册
     */
    PRO150("pro150", "平台升级中，当前禁止注册", "Registration is currently prohibited during platform upgrades"),
    /**
     * 用户已存在
     */
    PRO151("pro151", "用户已存在", "User already exist"),
    /**
     * 类型错误
     */
    PRO152("pro152", "类型错误", "Type error"),
    /**
     * 手机验证码错误
     */
    PRO153("pro153", "手机验证码错误", "Mobile phone verification code error"),
    /**
     * 该手机号码未注册
     */
    PRO154("pro154", "该手机号码未注册", "The phone number is not registered"),
    /**
     * 您的账号已冻结，请联系客服
     */
    PRO155("pro155", "您的账号已冻结，请联系客服", "Your account has been frozen, please contact customer service"),
    /**
     * 手机号码不存在
     */
    PRO156("pro156", "手机号码不存在", "Cell phone Numbers don't exist"),
    /**
     * 未查询到具体协议
     */
    PRO157("pro157", "未查询到具体协议", "No specific protocol was queried"),
    /**
     * pageSize不能为空
     */
    PRO158("pro158", "pageSize不能为空", "PageSize cannot be empty"),
    /**
     * pageNumber不能为空
     */
    PRO159("pro159", "pageNumber不能为空", "PageNumber cannot be empty"),
    /**
     * 清空我的消息失败
     */
    PRO160("pro160", "清空我的消息失败", "Clearing my message failed"),
    /**
     * 选中参数有误
     */
    PRO161("pro161", "选中参数有误", "Incorrect selection parameter"),
    /**
     * 登录成功
     */
    PRO162("pro162", "登录成功", "login successfully"),
    /**
     * [用户名]不能为空
     */
    PRO163("pro163", "[用户名]不能为空", "The username cannot be empty"),
    /**
     * [用户名]格式错误
     */
    PRO164("pro164", "[用户名]格式错误", "User name format error"),
    /**
     * 注册失败
     */
    PRO165("pro165", "注册失败", "fail to register"),
    /**
     * 该邮箱未注册
     */
    PRO166("pro166", "该邮箱未注册", "The mailbox is not registered"),
    /**
     * 邮箱不存在
     */
    PRO167("pro167", "邮箱不存在", "Email doesn't exist"),
    /**
     * 登录超时，请重新登录
     */
    PRO168("pro168", "登录超时，请重新登录", "Login timeout, please login again"),
    /**
     * userId不能为空
     */
    PRO169("pro169", "userId不能为空", "UserId cannot be empty"),
    /**
     * 模板错误
     */
    PRO170("pro170", "模板错误", "Template error"),
    /**
     * 参数类型错误
     */
    PRO171("pro171", "参数类型错误", "Parameter type error"),
    /**
     * 新密码和旧密码不能一致
     */
    PRO172("pro172", "新密码和旧密码不能一致", "The new password does not match the old one"),
    /**
     * 验证失败
     */
    PRO173("pro173", "验证失败", "authentication failed"),
    /**
     * state不能为空
     */
    PRO174("pro174", "state不能为空", "State cannot be empty"),
    /**
     * 币种错误
     */
    PRO175("pro175", "币种错误", "Currency error"),
    /**
     * 是否默认参数错误
     */
    PRO176("pro176", "是否默认参数错误", "Default parameter error"),
    /**
     * 提币失败
     */
    PRO177("pro177", "提币失败", "Mention money failed"),
    /**
     * 充币功能已经冻结，请联系客服
     */
    PRO178("pro178", "充币功能已经冻结，请联系客服", "The function of charging money has been frozen, please contact customer service"),
    /**
     * 该用户暂未配置充币地址
     */
    PRO179("pro179", "该用户暂未配置充币地址", "The user has not configured a prepaid address"),
    /**
     * [currencyPair]格式不合法
     */
    PRO180("pro180", "[currencyPair]格式不合法", "[currencyPair] is not valid"),
    /**
     * 未查询到交易手续费模板
     */
    PRO181("pro181", "未查询到交易手续费模板", "The transaction fee template was not queried"),
    /**
     * 限价买入委托成功
     */
    PRO182("pro182", "限价买入委托成功", "Limit the price to buy the commission successful"),
    /**
     * 限价卖出委托成功
     */
    PRO183("pro183", "限价卖出委托成功", "Limit the sale of the commission successful"),
    /**
     * [currencyPair]不合法
     */
    PRO184("pro184", "[currencyPair]不合法", "[currencyPair] is illegal"),
    /**
     * [price]不能为空
     */
    PRO185("pro185", "[price]不能为空", "[price] cannot be empty"),
    /**
     * [price]不能小于等于0
     */
    PRO186("pro186", "[price]不能小于等于0", "The price cannot be less than or equal to 0"),
    /**
     * 市价买入委托成功
     */
    PRO187("pro187", "市价买入委托成功", "The purchase order was successful"),
    /**
     * 市价卖出委托成功
     */
    PRO188("pro188", "市价卖出委托成功", "The commission was successfully sold at market price"),
    /**
     * 未查询到可用资产
     */
    PRO189("pro189", "未查询到可用资产", "The available assets were not queried"),
    /**
     * [delegateNo]不能为空
     */
    PRO190("pro190", "[delegateNo]不能为空", "[delegateNo] can't be empty"),
    /**
     * [symbol]不能为空
     */
    PRO191("pro191", "[symbol]不能为空", "[symbol] cannot be empty"),
    /**
     * 未找到对应子委托,撤回主委托
     */
    PRO193("pro193", "未找到对应子委托,撤回主委托", "The corresponding sub-delegate was not found and the principal delegate was withdrawn"),
    /**
     * [uuid]不能为空
     */
    PRO194("pro194", "[uuid]不能为空", "[uuid] cannot be empty"),
    /**
     * 主委托记录不存在
     */
    PRO195("pro195", "主委托记录不存在", "The primary delegate record does not exist"),
    /**
     * 未查询到子委托记录
     */
    PRO196("pro196", "未查询到子委托记录", "Subdelegate records were not queried"),
    /**
     * 全部撤回请求成功
     */
    PRO197("pro197", "撤回请求成功", "The complete withdrawal request was successful"),
    /**
     * 部分撤回请求成功
     */
    PRO198("pro198", "部分撤回请求成功", "The partial withdrawal request succeeded"),
    /**
     * 无可撤单委托
     */
    PRO199("pro199", "无可撤单委托", "No cancellation of the order"),
    /**
     * [委托价格]不能为空
     */
    PRO200("pro200", "[委托价格]不能为空", "[entrustment price] cannot be empty"),
    /**
     * [price]不合法
     */
    PRO201("pro201", "[price]不合法", "[price] is not legal"),
    /**
     * [buyFeeScale]不能为空
     */
    PRO202("pro202", "[buyFeeScale]不能为空", "BuyFeeScale cannot be empty"),
    /**
     * [amount]不能为空
     */
    PRO203("pro203", "[amount]不能为空", "There can be no empty amount"),
    /**
     * [currencyPair]不能为空
     */
    PRO204("pro204", "[currencyPair]不能为空", "[currencyPair] cannot be empty"),
    /**
     * 交易对格式不合法
     */
    PRO205("pro205", "交易对格式不合法", "The format of the transaction is illegal"),
    /**
     * [terminal]不能为空
     */
    PRO206("pro206", "[terminal]不能为空", "Terminal can't be empty"),
    /**
     * 生成主委托记录失败
     */
    PRO207("pro207", "生成主委托记录失败", "Failed to generate the primary delegate record"),
    /**
     * 查询失败
     */
    PRO208("pro208", "查询失败", "The query fails"),
    /**
     * 请选择上传图片
     */
    PRO209("pro209", "请选择上传图片", "Please choose to upload pictures"),
    /**
     * 图片不能超过2M
     */
    PRO210("pro210", "图片不能超过2M", "The picture should not exceed 2M"),
    /**
     * [exchangeNo]不能为空
     */
    PRO211("pro211", "[exchangeNo]不能为空", "[exchange eno] cannot be empty"),
    /**
     * 未找到合适的母账号
     */
    PRO212("pro212", "未找到合适的母账号", "No suitable parent account was found"),
    /**
     * [accountName]不能为空
     */
    PRO213("pro213", "[accountName]不能为空", "[accountName] cannot be empty"),
    /**
     * 委托类型错误
     */
    PRO214("pro214", "委托类型错误", "Incorrect delegate type"),
    /**
     * 子委托记录保存失败
     */
    PRO215("pro215", "子委托记录保存失败", "The sub-delegate record failed to be saved"),
    /**
     * 您的可用资产不足
     */
    PRO216("pro216", "您的可用资产不足", "Your available assets are insufficient"),
    /**
     * 主委托记录保存失败
     */
    PRO217("pro217", "主委托记录保存失败", "The master delegate record failed to be saved"),
    /**
     * 删除成功
     */
    PRO218("pro218", "删除成功", "deleted successfully"),
    /**
     * 密码错误次数超过系统设定的次数
     */
    PRO219("pro219", "密码错误次数超过系统设定的次数", "The number of password errors exceeds the number set by the system"),
    /**
     * BIAN卖出未配置最小下单额
     */
    PRO220("pro220", "BIAN卖出未配置最小下单额", "Bian sell unconfigured minimum amount"),
    /**
     * BIAN卖出最小交易额为:[{$bianOrderMinAmount}],当前交易额为:[{$gmv}]
     */
    PRO221("pro221", "BIAN卖出最小交易额为:[{$bianOrderMinAmount}],当前交易额为:[{$gmv}]", "The minimum sales volume of BIAN is as follows:[{$bianOrderMinAmount}],The current transaction amount is as follows:[{$gmv}]"),

    PRO222("pro222", "BIAN买入最小交易额为:[{$bianOrderMinAmount}],当前交易额为:[{$gmv}]", "The minimum buy volume of BIAN is as follows:[{$bianOrderMinAmount}],The current transaction amount is as follows:[{$gmv}]"),
    /**
     * 已设置提币密码
     */
    PRO223("pro223", "已设置提币密码", "A coin cipher has been set up"),

    /**
     * 注册未完成
     */
    PRO224("pro224", "注册未完成", "Registration is not completed "),

    /**
     * 用户账户已激活
     */
    PRO225("pro225", "用户账户已激活", "User account has been activated"),

    /**
     * 激活失败
     */
    PRO226("PRO226", "激活失败", "Activation failure"),

    /**
     * 激活码已被使用
     */
    PRO227("PRO227", "激活码已被使用", "The activation code has been used"),

    /**
     * 激活码不存在
     */
    PRO228("PRO228", "激活码不存在", "Activation code does not exist"),

    /**
     * 激活码与用户等级不匹配
     */
    PRO229("PRO229", "激活码与用户等级不匹配", "Activation code does not match user level"),

    /**
     * 推荐人不存在
     */
    PRO230("PRO230", "推荐人不存在", "The referee does not exist"),

    /**
     * 请选择具体交易所进行交易
     */
    PRO231("PRO231", "请选择具体交易所进行交易", "Please select specific exchanges for trading"),
    /**
     * 充币数量不能为空
     */
    PRO232("PRO232", "充币数量不能为空", "The amount of charge can not be empty"),
    /**
     * 充币流水单号不能为空
     */
    PRO233("PRO233", "充币流水单号不能为空", "The odd number can not be empty"),
    /**
     * 我的钱包地址不能为空
     */
    PRO234("PRO234", "我的钱包地址不能为空", "My wallet address can not be empty"),
    /**
     * 确认密码不能为空
     */
    PRO235("PRO235", "确认密码不能为空", "Confirm password is empty"),
    /**
     * 重置密码失败
     */
    PRO236("PRO236", "重置密码失败", "Failed to reset password"),
    /**
     * 请不要重复操作
     */
    PRO237("PRO237", "请不要重复操作", "Repeat operations"),
    /**
     * 修改昵称失败
     */
    PRO238("PRO238", "修改昵称失败", "Failed to modify nickname"),
    /**
     * 修改昵称成功
     */
    PRO239("PRO239", "修改昵称成功", "Modify nickname successfully"),
    /**
     * 无法操作正在审核中的申请
     */
    PRO240("PRO240", "无法操作正在审核中的申请", "Identity application is under reviewing"),
    /**
     * 无法操作审核通过的申请
     */
    PRO241("PRO241", "无法操作审核通过的申请", "Identity application has already been verified"),
    /**
     * 修改绑定手机失败
     */
    PRO242("PRO242", "修改绑定手机失败", "Failed to modify phone"),
    /**
     * 修改绑定手机成功
     */
    PRO243("PRO243", "修改绑定手机成功", "Modify phone successfully"),
    /**
     * 密码错误
     */
    PRO244("PRO244", "密码错误", "Wrong password"),
    /**
     * 修改密码失败
     */
    PRO245("PRO245", "修改密码失败", "Failed to modify login password"),
    /**
     * 修改密码成功
     */
    PRO246("PRO246", "修改密码成功", "Modify login password successfully"),
    /**
     * 操作失败,已设置过支付密码
     */
    PRO247("PRO247", "操作失败,已设置过支付密码", "A pay password has already been set up"),
    /**
     * 设置支付密码失败
     */
    PRO248("PRO248", "设置支付密码失败", "Failed to set pay-password"),
    /**
     * 设置支付密码成功
     */
    PRO249("PRO249", "设置支付密码成功", "Set pay-password successfully"),
    /**
     * 姓名与实名信息不一致
     */
    PRO250("PRO250", "姓名与实名信息不一致", "Name error"),
    /**
     * 新增银行卡失败
     */
    PRO251("PRO251", "新增银行卡失败", "Failed to add a new bank card"),
    /**
     * 新增银行卡成功
     */
    PRO252("PRO252", "新增银行卡成功", "Add a new bank successfully"),
    /**
     * 商品不存在
     */
    PRO253("PRO253", "商品不存在", "This goods does not exist"),
    /**
     * 商品收藏成功
     */
    PRO254("PRO254", "商品收藏成功", "Collect successfully"),
    /**
     * 商品收藏失败
     */
    PRO255("PRO255", "商品收藏失败", "Failed to collect"),
    /**
     * 银行卡不存在
     */
    PRO256("PRO256", "银行卡不存在", "This card doesn't exist"),
    /**
     * 删除银行卡成功
     */
    PRO257("PRO257", "删除银行卡成功", "Remove card successfully"),
    /**
     * 删除银行卡失败
     */
    PRO258("PRO258", "删除银行卡失败", "Failed to remove card"),
    /**
     * 未找到商品信息
     */
    PRO259("PRO258", "未找到商品信息", "No goods information found"),
    /**
     * 加入购物车失败
     */
    PRO260("PRO260", "加入购物车失败", "Add to cart fail"),
    /**
     * 请填写收货人姓名
     */
    PRO261("PRO261", "请填写收货人姓名", "Please enter consignee's name"),
    /**
     * 请输入手机号码
     */
    PRO262("PRO262", "请输入手机号码", "Please enter phone number"),
    /**
     * 请输入详细地址
     */
    PRO263("PRO263", "请输入详细地址", "Please enter a detailed address"),
    /**
     * 请输入完整行政区划
     */
    PRO264("PRO264", "请输入完整行政区划", "Please enter in the complete administrative divisions"),
    /**
     * 收货地址不正确
     */
    PRO265("PRO265", "收货地址不正确", "Delivery address error"),
    /**
     * 收货地址添加失败
     */
    PRO266("PRO266", "收货地址添加失败", "Failed to add address"),
    /**
     * 收货地址不存在
     */
    PRO267("PRO267", "收货地址不存在", "Address does not exist"),
    /**
     * 修改地址失败
     */
    PRO268("PRO268", "修改地址失败", "Failed to modify address"),
    /**
     * 用户账户不存在
     */
    PRO269("PRO269", "用户账户不存在", "Account does not exist"),
    /**
     * 账户余额不足
     */
    PRO270("PRO270", "账户余额不足", "Insufficient account balance"),
    /**
     * 购买失败
     */
    PRO271("PRO271", "购买失败", "Insufficient account balance"),
    /**
     * 订单不存在
     */
    PRO272("PRO272", "订单不存在", "Order does not exist"),
    /**
     * 订单状态发生改变
     */
    PRO273("PRO273", "订单状态发生改变", "Change in order status"),
    /**
     * 订单取消失败
     */
    PRO274("PRO274", "订单取消失败", "Order Cancellation Failed"),
    /**
     * 设置支付密码成功
     */
    PRO275("PRO275", "用户未设置支付密码", "User didn't have a pay-password yet"),
    /**
     * 修改支付密码成功
     */
    PRO276("PRO276", "修改支付密码成功", "Modify pay-password successfully"),
    /**
     * 修改支付密码失败
     */
    PRO277("PRO277", "修改支付密码失败", "Failed to modify pay-password"),
    /**
     * 请输入省份
     */
    PRO278("PRO278", "请输入所在地", "Please enter a province"),
    /**
     * 请输入城市
     */
    PRO279("PRO279", "请输入城市", "Please enter a city"),
    /**
     * 请输入地区
     */
    PRO280("PRO280", "请输入地区", "Please enter a area"),
    /**
     * 修改头像成功
     */
    PRO281("PRO281", "修改头像成功", "Update avatar successfully"),
    /**
     * 修改头像失败
     */
    PRO282("PRO282", "修改头像失败", "failed to update avatar"),
    /**
     * 已实名认证通过,无需上传
     */
    PRO283("PRO283", "已实名认证通过,无需上传", "You have been identified"),
    /**
     * 实名认证正在审核中,无需上传
     */
    PRO284("PRO284", "实名认证正在审核中,无需上传", "You are bing identified"),
    /**
     * 上传成功
     */
    PRO285("PRO285", "上传成功", "Upload successfully"),
    /**
     * 订单还未确认收货
     */
    PRO286("PRO286", "订单还未确认收货", "The order needs to confirm receipt"),
    /**
     * 厂家不存在
     */
    PRO287("PRO287", "厂家不存在", "The shop doesn't exist"),
    /**
     * 订单结算失败
     */
    PRO288("PRO288", "订单结算失败", "Failed to settle order"),
    /**
     * 商品收藏成功
     */
    PRO289("PRO289", "已收藏过该商品", "Repeat collect goods"),
    /**
     * 已经绑定过这张银行卡
     */
    PRO290("PRO290", "已经绑定过这张银行卡", "You have already bound this card"),
    /**
     * 创建聊天失败
     */
    PRO291("PRO291", "创建聊天失败", "Failed to create chat"),
    /**
     * 客服不存在
     */
    PRO292("PRO292", "客服不存在", "This customer service doesn't exist"),
    /**
     * 读取消息列表失败
     */
    PRO293("PRO293", "读取消息列表失败", "Failed to load message records"),
    /**
     * 图片
     */
    PRO294("PRO294", "[图片]", "[picture]"),
    /**
     * 每笔订单只能延长一次
     */
    PRO295("PRO295", "每笔订单只能延长一次", "Each order can only be extended once"),
    /**
     * 评价失败
     */
    PRO296("PRO296", "评价失败", "Failed to publish comment"),
    /**
     * 评价内容不能为空
     */
    PRO297("PRO297", "评价内容不能为空", "Evaluation content should not be empty"),
    /**
     * 查看评价失败
     */
    PRO298("PRO298", "查看评价失败", "Failed to look comment"),
    /**
     * 确认收货失败
     */
    PRO299("PRO299", "确认收货失败", "Confirmation of failure of receipt"),
    /**
     * 已评价
     */
    PRO300("PRO296", "已评价", "Already comment"),
    /**
     * 该订单无法支付
     */
    PRO301("PRO301", "该订单无法支付", "Can't pay for this order"),
    /**
     * 创单失败
     */
    PRO302("PRO302","创单失败","Failed to create pay order"),
    /**
     * 小数点后最长两位
     */
    PRO303("PRO303","小数点后最多两位数字","No more than two digits after decimal point"),
    /**
     * 充值失败
     */
    PRO304("PRO304","充值失败","Failed to recharge"),
    /**
     * 物流信息不存在
     */
    PRO305("PRO305","物流信息不存在","Logistics information does not exist"),
    /**
     * 查询物流信息失败
     */
    PRO306("PRO306","查询物流信息失败","Failure to query logistics information"),
    /**
     * 申请提现失败
     */
    PRO307("PRO307","申请提现失败","Failed to apply cash"),
    /**
     * 支付密码错误
     */
    PRO308("PRO308","支付密码错误","Wrong pay-password"),
    /**
     * 请先设置支付密码
     */
    PRO309("PRO309","请先设置支付密码","Please set a pay-password first"),
    /**
     * 订单支付超时
     */
    PRO310("PRO310","订单支付超时","Order payment timeout"),
    /**
     * 订单支付失败
     */
    PRO311("PRO311","订单支付失败","Failed to pay for order"),
    /**
     * 无法重复支付此订单
     */
    PRO312("PRO312","无法重复支付此订单","Failed to repeatedly pay this order"),
    /**
     * 请先添加一张银行卡
     */
    PRO313("PRO313","请先添加一张银行卡","Please add a bank card"),
    /**
     * 订单正在处理中，请稍后重试
     */
    PRO314("PRO314","订单正在处理中，请稍后重试...","The order is being processed. Please try again later"),
    /**
     * 库存不足
     */
    PRO315("PRO315","库存不足","Stock shortage"),
    /**
     * 用户还未通过实名认证
     */
    PRO316("PRO316","用户还未通过实名认证","User didn't pass the realname identity"),

    /**
     * 更新购物车失败
     */
    PRO317("PRO317", "更新购物车失败", "Failed to update cart"),
    /**
     * 您还没有设置收货地址,请点击这里设置
     */
    PRO318("PRO318", "您还没有设置收货地址,请点击这里设置", "You should add a delivery address first"),
    /**
     * 该地址不存在
     */
    PRO319("PRO319", "该地址不存在", "The address doesn't exist"),
    /**
     * 地址无效
     */
    PRO320("PRO320", "地址无效", "Invalid address"),
    /**
     * 价格计算错误
     */
    PRO321("PRO321", "价格计算错误", "Invalid price"),
    /**
     * 订单创建失败
     */
    PRO322("PRO322", "订单创建失败", "Failed to create order"),
    /**
     * 提交订单失败[购物车记录不存]
     */
    PRO323("PRO323", "提交订单失败[购物车记录不存]", "Failed to create order[Cart record doesn't exist]"),
    /**
     * 无效的支付请求
     */
    PRO324("PRO324", "无效的支付请求", "Invalid pay request"),

    /**
     * 实名认证中,需要审核通过
     */
    PRO325("PRO325", "实名认证中,需要审核通过", "Real-name authentication is under review"),

    /**
     * 邀请码不能为空
     */
    PRO326("PRO326", "邀请码不能为空", "Invite code cannot be empty"),

    /**
     * 已取消收藏
     */
    PRO327("PRO327", "已取消收藏", "Cancel collection"),

    /**
     * 取消收藏操作失败
     */
    PRO328("PRO328", "取消收藏操作失败", "Cancel collection failed"),

    PRO329("PRO329", "发送内容不能为空", "Send content cannot be empty"),

    PRO330("PRO330", "请输入正确的原密码", "Please enter the correct original password"),

    PRO331("PRO331", "运费规则错误", "Post fee rule error"),

    PRO332("PRO332", "不是代理商", "Isn't agent"),

    PRO333("PRO333", "原支付密码输入有误", "Wrong password"),

    PRO334("PRO334", "商品已失效", "Goods invalid"),

    PRO335("PRO335", "支付金额有误", "pay money error"),

    /**
     * 邀请码无效
     */
    PRO336("PRO336", "无效邀请码", "Invite code is invalid"),
    ;

    public String code;
    public String zh;
    public String en;

    public String getCode() {
        return code;
    }

    public String getZh(Map<String, Object> param) {
        if (!CollectionUtil.isEmpty(param)) {
            return StringUtils.replace(zh, param);
        } else {
            return zh;
        }
    }

    public String getEn(Map<String, Object> param) {
        if (!CollectionUtil.isEmpty(param)) {
            return StringUtils.replace(en, param);
        } else {
            return en;
        }
    }

    public String getZh() {
        return zh;
    }


    public String getE() {
        return en;
    }

    public static PromptLanguageEnum getPromptLanguageEnum(String code) {
        for (PromptLanguageEnum k : PromptLanguageEnum.values()) {
            if (k.code.equals(code)) {
                return k;
            }
        }
        throw new BussinessException("提示语言类型错误");
    }

    public String getLanguageMsg(String code) {
        if (null == code) {
            return zh;
        }
        switch (code) {
            case Language.ZH:
                return zh;
            case Language.EN:
                return en;
            default:
                throw new BussinessException("code错误");
        }
    }
}
