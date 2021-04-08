package com.huimi.common.tools;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * 描述: 随机字符串类
 * 作者: 陌上人
 * 时间: 2016/6/2 17:33
 */
public class ToolRandoms {
    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(ToolRandoms.class);

    private static final Random random = new Random();

    // 定义验证码字符.去除了O、I、l、、等容易混淆的字母
    public static final char authCode[] = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'G', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'a', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            '3', '4', '5', '7', '8' };

    public static final int length = authCode.length;

    /**
     * 生成验证码
     * @return 验证码字符串
     */
    public static char getAuthCodeChar() {
        return authCode[number(0, length)];
    }

    /**
     * 生成验证码
     * @return 验证码
     */
    public static String getAuthCode(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(authCode[number(0, length)]);
        }
        return sb.toString();
    }

    /**
     * 获取UUID by jdk
     * @return uuid
     */
    public static String getUuid(boolean is32bit){
        String uuid = UUID.randomUUID().toString();
        if(is32bit){
            return uuid.replace("-", "");
        }
        return uuid;
    }

    /**
     * 产生两个数之间的随机数
     * @param min 小数
     * @param max 比min大的数
     * @return int 随机数字
     */
    public static int number(int min, int max) {
        return min + random.nextInt(max - min);
    }

    /**
     * 产生0--number的随机数,不包括num
     * @param number   数字
     * @return int 随机数字
     */
    public static int number(int number) {
        return random.nextInt(number);
    }

    /**
     * 生成RGB随机数
     * @return RGB随机数
     */
    public static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    /**
     * 生成6位随机数验证码
     */
    public static String code() {
        Set<Integer> set = GetRandomNumber();
        Iterator<Integer> iterator = set.iterator();
        String temp = "";
        while (iterator.hasNext()) {
            temp += iterator.next();
        }
        return temp;
    }

    public static Set<Integer> GetRandomNumber() {
        Set<Integer> set = new HashSet<>();
        Random random = new Random();
        while (set.size() < 6) {
            set.add(random.nextInt(10));
        }
        return set;
    }
}
