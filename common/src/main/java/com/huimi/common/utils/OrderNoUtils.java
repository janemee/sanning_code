package com.huimi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * JAVA版本的自动生成有规则的订单号(或编号) 生成的格式是: 200908010001 前面几位为当前的日期,后面五位为系统自增长类型的编号 原理:
 * 1.获取当前日期格式化值; 2.读取文件,上次编号的值+1最为当前此次编号的值 (新的一天会重新从1开始编号)
 *
 * @author syx
 */
public class OrderNoUtils {
    /**
     * 时间格式化
     */
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");


    /**
     * 获取范围内int值
     *
     * @param
     * @return
     */
    public static int getRandomRange(int max, int min) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * 随机位数编号
     *
     * @return
     */
    public static String generateCode(int length) {
        String[] codePool = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        //获取一个0~9的随机整数
        StringBuilder generateCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomNum = getRandomRange(0, 10);
            generateCode.append(codePool[randomNum]);
        }
        return generateCode.toString();
    }

    /**
     * 生成账号
     *
     * @return
     */
    public static String getExNo() {
        return SDF.format(new Date()).substring(2, 8) + generateCode(5);
    }

    /**
     * 产生唯一 的序列号。
     *
     * @return 序列号
     */
    public static String getSerialNumber() {
        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        synchronized (OrderNoUtils.class) {
            return SDF.format(new Date()).substring(2, 6) + String.format("%010d", hashCode);
        }
    }
}
