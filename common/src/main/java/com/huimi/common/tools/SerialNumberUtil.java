/*
 * Copyright (c) 2015-2017, HuiMi Tec co.,LTD. 枫亭子 (646496765@qq.com).
 */
package com.huimi.common.tools;

import java.util.Random;

/**
 * @author 枫亭
 * @description 随机不重复邀请码
 * @date 2018/1/4 15:52.
 */
public class SerialNumberUtil {
    /**
     * 自定义进制(0,1没有加入,容易与o,l混淆)
     */
    private static char[] r = new char[]{'Q', 'w', 'E', '8', 'a', 'S', '2', 'd', 'Z', 'x', '9', 'c', '7', 'p', 'O', '5', 'i', 'K', '3', 'm', 'j', 'U', 'f', 'r', '4', 'V', 'y', 'L', 't', 'N', '6', 'b', 'g', 'H'};
    /**
     * 自动补全组(不能与自定义进制有重复)
     */
    private static char[] b = new char[]{'q', 'W', 'e', 'A', 's', 'D', 'z', 'X', 'C', 'P', 'o', 'I', 'k', 'M', 'J', 'u', 'F', 'R', 'v', 'Y', 'T', 'n', 'B', 'G', 'h'};
    /**
     * 进制长度
     */
    private static int l = r.length;
    /**
     * 序列最小长度
     */
    private static int s = 6;

    public static String toSerialNumber(long num) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((num / l) > 0) {
            buf[--charPos] = r[(int) (num % l)];
            num /= l;
        }
        buf[--charPos] = r[(int) (num % l)];
        String str = new String(buf, charPos, (32 - charPos));
        //不够长度的自动随机补全
        if (str.length() < s) {
            StringBuilder sb = new StringBuilder();
            Random rnd = new Random();
            for (int i = 0; i < s - str.length(); i++) {
                sb.append(b[rnd.nextInt(24)]);
            }
            str += sb.toString();
        }
        return str;
    }

    /**
     *
     * 随机生成验证码（数字+字母）
     *
     * @param len 邀请码长度
     * @return
     *
     * @author ailo555
     * @date 2016年10月23日 上午9:27:09
     */
    public static String generateInviteCode(int len) {
        //字符源，可以根据需要删减
        String generateSource = "23456789ABCDEFGHGKLMNPQRSTUVWXYZ";//去掉1和i ，0和o
        String rtnStr = "";
        for (int i = 0; i < len; i++) {
            //循环随机获得当次字符，并移走选出的字符
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            rtnStr += nowStr;
            generateSource = generateSource.replaceAll(nowStr, "");
        }
        return rtnStr;
    }

    public static void main(String[] args) {
        int i=100;
        while (i>0){
            i--;
            System.out.println(generateInviteCode(6));
        }
    }
}
