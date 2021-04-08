package com.huimi.common.tools;

import cn.hutool.http.HtmlUtil;
import com.huimi.common.utils.DateUtils;
import com.huimi.common.utils.FreemarkerUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 工具类
 *
 * @author Donfy
 * 2015年12月1日
 */
public class StringUtil extends StringUtils {

    /**
     * 校验字符为空返回true
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return StringUtil.isNull(str).equals("");
    }

    /**
     * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
     *
     * @param str
     * @return
     */
    public static String isNull(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    /**
     * 将对象转为字符串
     *
     * @param o
     * @return
     */
    public static String isNull(Object o) {
        if (o == null) {
            return "";
        }
        String str = "";
        if (o instanceof String) {
            str = (String) o;
        } else {
            str = o.toString();
        }
        return str.trim();
    }

    /**
     * 大写字母转成“_”+小写
     *
     * @param str
     * @return
     */
    public static String toUnderline(String str) {
        char[] charArr = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        sb.append(charArr[0]);
        for (int i = 1; i < charArr.length; i++) {
            if (charArr[i] >= 'A' && charArr[i] <= 'Z') {
                sb.append('_').append(charArr[i]);
            } else {
                sb.append(charArr[i]);
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 首字母大写
     *
     * @param s
     * @return
     */
    public static String firstCharUpperCase(String s) {
        StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
        sb.append(s.substring(1, s.length()));
        return sb.toString();
    }


    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 0; i < hex.length; i++) {
            try {
                // 汉字范围 \u4e00-\u9fa5 (中文)
                if (hex[i].length() >= 4) {//取前四个，判断是否是汉字
                    String chinese = hex[i].substring(0, 4);
                    try {
                        int chr = Integer.parseInt(chinese, 16);
                        boolean isChinese = isChinese((char) chr);
                        //转化成功，判断是否在  汉字范围内
                        if (isChinese) {//在汉字范围内
                            // 追加成string
                            string.append((char) chr);
                            //并且追加  后面的字符
                            String behindString = hex[i].substring(4);
                            string.append(behindString);
                        } else {
                            string.append(hex[i]);
                        }
                    } catch (NumberFormatException e1) {
                        string.append(hex[i]);
                    }

                } else {
                    string.append(hex[i]);
                }
            } catch (NumberFormatException e) {
                string.append(hex[i]);
            }
        }

        return string.toString();
    }

    public static String string2Unicode(String string) {
        String result = "";
        if (StringUtils.isBlank(string)) {
            return null;
        }

        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char chr1 = string.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
                // 转换为unicode
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += string.charAt(i);
            }
        }
        return result;
    }

    /**
     * 判断是否为中文字符
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * String to Long
     *
     * @param str
     * @return
     */
    public static long toLong(String str) {
        if (StringUtil.isBlank(str))
            return 0L;
        long ret = 0;
        try {
            ret = Long.parseLong(str);
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }

    /**
     * 检验手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        phone = isNull(phone);
        Pattern regex = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher matcher = regex.matcher(phone);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 密码格式校验
     *
     * @param pwd
     * @return
     */
    public static boolean isPwd(String pwd) {
        if (pwd.length() < 8 || pwd.length() > 16) {
            return false;
        }
        boolean b1 = Pattern.compile("[0-9]").matcher(pwd).find();
        boolean b2 = Pattern.compile("(?i)[a-zA-Z]").matcher(pwd).find();
        if (b1 && b2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (isBlank(str)) {
            return false;
        }
        Pattern regex = Pattern.compile("(-)?\\d*(.\\d*)?");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    public static void main(String[] age) {
        System.out.println(isPwd("121323qq"));
    }


    public static String fillTemplet(String template, Map<String, Object> sendData) {
        // 模板中的'是非法字符，会导致无法提交，所以页面上用`代替
        template = template.replace('`', '\'');
        try {
            return FreemarkerUtil.renderTemplate(template, sendData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取文本中的http/https链接地址
     *
     * @param content
     * @return
     */
    public static String subTextToWebUrlStr(String content) {
        String url = "";
        try {
            //获取最后一次出现http的位置
            int count = content.lastIndexOf("http");
            //获取链接结尾位置
            int end = content.lastIndexOf("/");
            if (count >= 0 && end > 0) {
                url = content.substring(count, end + 1);
            }
            return url;
        } catch (Exception e) {
            return url;
        }

    }

    /**
     * 获取文本中的http/https链接地址
     *
     * @param content
     * @return
     */
    public static String getU(String content, String startStr, String endStr) {
        String url = "";
        try {
            //获取最后一次出现http的位置
            int count = content.indexOf(startStr);
            //获取链接结尾位置
            int end = content.indexOf(endStr);
            if (count > 1 && end > 1) {
                url = content.substring(count, end + 1);
            }
            return url;
        } catch (Exception e) {
            return url;
        }

    }


}
