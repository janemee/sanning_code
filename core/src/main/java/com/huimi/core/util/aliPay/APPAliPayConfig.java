//package com.huimi.core.util.aliPay;
//
///**
// * 支付宝支付 相关配置信息类
// */
//public class APPAliPayConfig {
//
//    /**
//     * 正式商户APP_ID
//     */
//    public static String app_id = "2019100968175572";
//    //    public static String app_id = "2016081900289953";
//
//    /**
//     * 商户PID
//     */
//    public static String PID = "2088631356089575";
//    /**
//     * 商户支付宝id 用于验证收款人信息
//     */
//    public static String seller_id = "2016081900289953";
////    /**
////     * 商户私钥 （沙箱调试）
////     */
////    public static String private_key_pkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCpFXlJvtnqqaKrFZheDUSobXuC2I2Zg2ivXyOm8bVCS4Bn8wbmUJRGTIcuTZGIVw1nG/+NP6T8TW5uGvVU+lqQyGOPklU6Q2UWbKZ1f1EjTRibeTYmQodrcXLYse9fXpZW5EcSrpepEwuQjEvkC8nYgQ9pLvxq3yyQ0yZEWKtQR5BjMjJAeVwc/YNnX8hH5DsQLza4ZfXaT8kyUu+IdB/wP1/U4lE6odUG2iyhc6UEHf/QBIy39V/K1Xxw/6cevvVJO0vduk5GULSbt77bp/0r0I7EzXahHHs/14sBu4KN55ltWZ8yKOraAbugMoqmc4B8xX8wCs2njvFIXpsk0YAvAgMBAAECggEAHjwXxycYgoy46MzgxRisSU3igyBEGcpUwt9JWcXo+qTTnkLn3fFUwklW9uGp/25JmEF6bgCOeQtDju7FJd/qp+8dVoP9N4Drp5ngHiyZDq3vk4WWODs3SfAscDFJM42PZpRFXULZHcOpErzzqSCuhEd3wQBE5LtplbAcLKaKY71N2vWf+Q+rTLBtJOtaK4wf0K6NRwQD80KhL/nMLX//UTNmiUd5ED4dQTQ+qQmB1rom42ElVZAR36YUUuNJHkGp79beivUg9wf1KGc1sYpL1HZsV7fxY841KvK40haK020GhZZnTKEVFbXTNMsFGC/bhT2EUIc9F3/+FYsacWwFaQKBgQDTUXMBCAdTQ6Fqi2MwPK86WFrGYZBEBKmvqA0Qr8v/bTaYTgGvrltWpguK2kfohZQwuYz3/YPnN0mz97SnSMEyiz4ld73mXyjA+uiIO8KgaG458GK59jLsunoL9UsvDJQS7D+ldF7hSC9bpK7u5s2D4HRu0WhY7Hah8oatoTc2OwKBgQDM1ec8Qk4oTD92TdpnnauiZWxIjBukhmeisNnugKlreqbrjlkkRhsTuXyFIht8hwOrAqGYS52LXnxiENbfNa0xT1O2faxH1MlFmqQiGIudlQutUfmZnLM6p7UvxxhhcWH8490eQ+iKAAf2U8Mt7hHc9IwZN0IQGOUdV7I5TeXanQKBgQDS0lSA9+iO+k8Ew7EeQ7yx4coWVumd4mUD2Q3H0wsbdnLcOh7WjYWUia3ERoctBG9WuRdZpfO9blw6jusNZblRWreSm8a6Gb/H38qzd6Nxcx5c8WUdj/E4WcY/PeZXNx5XTpkk0VeDPAf7Tu4za9InOnwOvx5ycN7eLN6OROOXVwKBgDFzmsXThCb6cX/QP+AwEpW8Qq4pFStdP3vpklOgQEawb8gCr8awHi1e5I8HsvcXaW1Jz1WDO9+/yeurARUWUl/LNPyjzonw9z6UnPeWUW6T4yPO3KGXUTXh8z47pMkxpGR3ZpS99FAMvd9cTYyzIyDi8UfNLWfRes7gsyhehBWJAoGBALl/AfVWd+vy5yU6zc2CX+62pidIu5tZYkhRiz1vUZ1tswjS2VDfKHzxj44UL8q4L8yLq2TSjDMa0L5aqRG+BT2Inu+0VsFNcm6DlWyzk1cd9p9oKsbE/lzJqySlj14vdqxzhA1+7L9LggcCv7TG8nxa/qSQatVkBG10guSprA4l";
//
//    /**
//     * 正式商户应用私钥
//     */
//    public static String private_key_pkcs8 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCjkf3xnC/BELTY6blxnz4qD/bYE/W3Ln8rYSM/elvsf+l2he1QoH4JZcRy8+wbOUnIi8lVv9AzsuxbcBRet2zT7lZ2Ej5/FhNeFJtPVxEf5z0npn050RzPf0YKxcTyxolct6RSTWSagpftEEZXfq1kZnx293w26w/VgRw2SMOO4GmdWTOlN2NnEO9zWZ9rBt+qPAylVhDv6sriBWv2o6cBHCTeQLYyBAOPC6B1J4dcy1o/g8Vv4zGIYdFzuqJMDLygvM/KNmCFnZwkSDw7axuFZj7cy2TdgQOf1kLp82dhtTVxCpl7pnWw5KXSJcx92VtyqhXpaMe+gbi7RHYF96qXAgMBAAECggEABz7VR9mKR/wMdrtLqpvKLiTM419m9/lISsyfuhUac0xrNAPUskiZrKLkY88oO0B7YzXF8lVvzIp1a8t4K4XmfTDGha5jNE/vBbDV/Fq3+HjOva3jSCdaJlrV6G5APhzLQt8jmT2yYiG8xxR8eS20UokzpDVsPCaHagaxEDqC5xY5jxBZHXKZOKJnNqzEnk5ONy9olx46Wq+UnGgh6sCPid2Nam2V/kvnj/164GnW9tQAAW0k27ZPnCiVjoGh/4HeVpvi31sY8+pnMcrHh3nSMkOwyjzqoiyTdkFw3jxvhxI0C6OtDXZvH1Ub8xdqyf2TOr5mMlq9bzJbj0dXZ4uJ0QKBgQDYgmpj6ZZUjLK8jhFePCT6TSxPZOpJBr4cMssq1gmzv+4N+20jwYUnjymEamqNeb37Wl0XykUzk9QNZirO3HNdX68kigSWHmjt+QKoEU+SrdRGdFHGKhxqVcCWRYTaMMnRouYgAqAdr1U2mdiXEWGTqeu7TBg+eLYMwGddElAMPwKBgQDBZ6lk9Zd5n8IgGv0B3CwDZCY/A1M5UunmgaiiDWxVR0G60rBC0a8b2sD2v85nQ+HSd7NlWSVqSvUksgJr5RKju0HrhBlsqWar2+htbc1ujjXmmeJlBR8kMe4+tF3ROZXXKiTEJyWCcYjLtToVSd4OIJuz4QvFVPtQH6U3Yq8rqQKBgQDRbgW/s9oo2xih78K5JvCV9kSgX/uIvC71U+TXAMfNyiBaAhVmevF0fLUqU03SoCq3koduVchuxdZfR66s/u3RKpdefutE0xGo8DAzptsUBXXK2QWo7F8kNOLf+UszS/JNCIgV+rQrQsjo0hSCvNtoXPub51WkSFvBJfRWQTqRPQKBgDWcvblN1xKQ9agesxnr5Yt/HJcFHfHbCRmrwYpCfFBbc14Nf2zWYVswVaNQ4i+AAr5sDh+/CX8gjjoA2mj8VCFtDEX0FXQotVxYlmKXOIY4B7sH11k3hT1sVeWt6//OnbnKZey2CVDSliAZ+aLjGhoCcgb4EGJAP3Yjf5IN2g8ZAoGAerVp3e1HhYksXtK+vV1sh2cF79WjUsdPmPKl5/vATC99nWZPPMjYwnoLDAHiC4OxTzvsle/lK3U+e13Gekp27CAJImuxSp3/DsWcGao29uYhmY519O1jv7aU2t+tkMu4xkcRujPNQQcMvjjcFAsUW9iTsWvxfps/5lqqu/46wAM=";
//
//    /**
//     * 正式商户应用公钥
//     */
//    public static String public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo5H98ZwvwRC02Om5cZ8+Kg/22BP1ty5/K2EjP3pb7H/pdoXtUKB+CWXEcvPsGzlJyIvJVb/QM7LsW3AUXrds0+5WdhI+fxYTXhSbT1cRH+c9J6Z9OdEcz39GCsXE8saJXLekUk1kmoKX7RBGV36tZGZ8dvd8NusP1YEcNkjDjuBpnVkzpTdjZxDvc1mfawbfqjwMpVYQ7+rK4gVr9qOnARwk3kC2MgQDjwugdSeHXMtaP4PFb+MxiGHRc7qiTAy8oLzPyjZghZ2cJEg8O2sbhWY+3Mtk3YEDn9ZC6fNnYbU1cQqZe6Z1sOSl0iXMfdlbcqoV6WjHvoG4u0R2BfeqlwIDAQAB";
//
////    /**
////     * 支付宝公钥,此处是rsa2的支付宝公钥 （沙箱调试）
////     */
////    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzDH3A4q3+8GZKQkHNLYqXm+OHnFtQaHfFPk7gzf+wIBGkceGqdS7UddF7eqzSrEU4k4D088m8kPm35EXPeLVtBm4fT/9iJxQIh6YQxlzEYvTyHTO1bkvWQZYls9+WxX64Tp1svCKFvy4CC/8/LdR/ApRVBx1G6IvVrlqv5aGjelFkeB7Mb9xEJf1VJdWEA/bxzMrtwjirT9VAerfMAPYzWsGmqj3Fxc35+Gyv2vgWutPRULfUEXf7+UXVZCA75P2/p+XoVbpHgmnYTnhdhqGvHNO63BJRDyta2EYb5XlNOqpKmTz99f79konfxB8fKkID2+dQan4GahkXpzDgH2RJQIDAQAB";
//
//    /**
//     * 正式支付宝公钥
//     */
//    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjZ9s5seQwgRsvtc41vyWIZfGpX7a+heZxixEmxsX6BTwsxf1CrZjT9k18NCjfcaASAQy9DsjFHd9mmo/iippOUDynFJZ2jraPHpza+xWfnlfkoq5QgI14B4mwcBZjkvqOqPOOUcg79FDFY9Gyih8iPj31dcaNpvWGQk2uJ87XczUOASXErpU7aWsHxSR/7cKGifWLUJ6d+m21+muvyf0eSSoe+XKhbBJsz0fxBN8LX92GInWZbjZM89OacEPS3MUNAFhTALRoBe+raPqV5kBapeQK3IVH8qm4wcgoYb2JVjHUw5ngBd4sAx6196wdpsUrBEe8ZEes1B/d3IkKN6siwIDAQAB";
//    /**
//     * 异步回调地址
//     */
//    public static String notify_url = "https://jane.utools.club/api/web/public/aliPay/aliPayNotify";
//    //    String notify_url = "http://api.qsc365.com/api/web/public/aliPay/notify";
//    /**
//     * 同步回调地址 支付成功或者失败后以及返回商户的url
//     */
//    public static String return_url = "https://jane.utools.club/api/web/public/aliPay/aliPayNotify";
//    //    String return_url = "http://api.qsc365.com/api/web/public/aliPay/callback";
//    /**
//     * 加密方式
//     */
//    public static String sign_type = "RSA2";
//    /**
//     * 字符集
//     */
//    public static String charset = "utf-8";
//    /**
//     * 支付宝网关, 注意dev是沙箱环境
//     */
////    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
//    /**
//     * 支付宝网关 正式环境请求地址
//     */
//    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
//
//    /**
//     * 数据格式
//     */
//    public static String format = "JSON";
//
//    /**
//     * 支付接口名称
//     */
//    public static String PAY_METHOD = "alipay.trade.app.pay";
//    /**
//     * 提现接口名称
//     */
//    public static String TRANSFER_METHOD = "alipay.fund.trans.toaccount.transfer";
//
//    /**
//     * 缓存地址
//     */
//    public static String REDIS_URL = "pay:aliPay:";
//}
