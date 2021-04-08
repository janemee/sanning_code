package com.huimi.common.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ToolValidate {
    /**
     * @param userName 用户名
     * @return 校验结果 true:通过,false: 不通过
     * @throws Exception
     * @title 校验用户名规则：数字与字母组合，字母，汉字，4-16位(?![a-zA-Z]+$)
     * @author eproo
     * @date 2015年12月7日 下午10:56:27
     * @description
     */
    public static boolean isUserName(String userName) {
        if (userName.length() < 4 || userName.length() > 16) {
            throw new RuntimeException("用户名长度必须是4-16位！");
        }
        Pattern p = Pattern.compile("^(?![0-9]+$)[0-9A-Za-z\u0391-\uFFE5]{4,16}$");
        Matcher m = p.matcher(userName);
        return m.matches();
    }

    /**
     * @param password
     * @return
     * @title 密码格式校验
     * @author eproo
     * @date 2015年12月7日 下午11:06:10
     * @description
     */
    public static String isPwd(String password) {
        String result = "";
        if (password.length() < 8 || password.length() > 16) {
            result = "密码长度必须是8-16位！";
            return result;
        }
        boolean p1 = Pattern.compile("[0-9]").matcher(password).find();
        boolean p2 = Pattern.compile("[a-zA-Z]").matcher(password).find();
        if (p1 && p2) {
            result += "";
        } else {
            result = "密码必须是数字加字母组成";
        }
        return result;
    }

    /**
     * @param str
     * @return
     * @title 校验是否为中文
     * @author eproo
     * @date 2015年12月7日 下午11:13:59
     * @description
     */
    public static boolean isChinese(String str) {
        Pattern regex = Pattern.compile("[\\u4e00-\\u9fa5]{2,25}");
        Matcher matcher = regex.matcher(str.trim());
        return matcher.matches();
    }

    /**
     * @param str
     * @return
     * @title 校验是否为中文姓名
     * @author eproo
     * @date 2015年12月7日 下午11:13:59
     * @description
     */
    public static boolean isChineseName(String str) {
        Pattern regex = Pattern.compile("([\\u4e00-\\u9fa5]{2,25})|([\\u4e00-\\u9fa5]{2,25}·[\\u4e00-\\u9fa5]{2,25})");
        Matcher matcher = regex.matcher(str.trim());
        return matcher.matches();
    }

    /**
     * @param email
     * @return
     * @title 校验Email格式
     * @author eproo
     * @date 2015年12月7日 下午11:15:45
     * @description
     */
    public static boolean isEmail(String email) {
        Pattern regex = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = regex.matcher(email.trim());
        return matcher.matches();
    }

    /**
     * 校验手机号格式
     *
     * @param mobile 手机号码
     * @return true or false
     */
    public static boolean isMobile(String mobile) {
        Pattern regex = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|145|147|(17[0-9]))\\d{8}$");
        Matcher matcher = regex.matcher(mobile.trim());
        return matcher.matches();
    }

    /**
     * 校验身份证号格式
     *
     * @param idCard 身份证号码
     * @return true or false
     */
    public static boolean isIDCard(String idCard) {
        Pattern regex = Pattern.compile("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
        Matcher matcher = regex.matcher(idCard.trim());
        return matcher.matches();
    }

}
