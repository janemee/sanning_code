package com.huimi.core.util;

import cn.hutool.core.date.DateUtil;
import com.huimi.common.tools.ToolDateTime;
import com.huimi.common.utils.DateUtils;
import com.huimi.common.utils.SpringContextUtils;
import com.huimi.common.utils.StringUtils;

import com.huimi.core.exception.BussinessException;
import com.huimi.core.service.cache.RedisService;
import org.apache.commons.collections.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * String 工具类
 *
 * @author Donfy
 * 2015年12月1日
 */
public class TradeUtil {

    public static final Integer AGTYPE = 1;

    public static final Integer SSETYPE = 2;

    public static final Integer ETFTYPE = 2;

    private static RedisService redisService;


    public static RedisService getRedisService() {
        return redisService;
    }

    public static void setRedisService(RedisService redisService) {
        TradeUtil.redisService = redisService;
    }

    /**
     * 周六日的时候判断的代码
     *
     * @param tradeTimeInfo 交易信息
     * @return true or false
     * @throws BussinessException ex
     */
    private static boolean isTradeTime2(String tradeTimeInfo) throws BussinessException {
        String[] tradeTimeArr = tradeTimeInfo.split(",");
        if (tradeTimeArr.length < 1) {
            // throw new BussinessException("当前为非交易时间段");
        }
        try {
            //得到当前时间的小时数和分钟数
            SimpleDateFormat currDay = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat currMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String currDateStr = currDay.format(new Date()); //当前日期字符串
            Date currDateTme = currMinute.parse(currMinute.format(new Date())); //当前时间
            for (String t : tradeTimeArr) {
                String[] time = t.split("~");
                if (time.length < 1) {
                    continue;
                }
                if (time.length == 2) {
                    Date startTime;
                    Date endTime;
                    if (t.contains("次日")) {
                        startTime = currMinute.parse(currDateStr + " 00:00");
                        endTime = currMinute.parse(currDateStr + " " + time[1].replace("次日", ""));
                    } else {
                        startTime = currMinute.parse(currDateStr + time[0]);
                        endTime = currMinute.parse(currDateStr + " " + time[1]);
                    }
                    //当前时间在交易时间段
                    if (ToolDateTime.compare(currDateTme, startTime) >= 0 && ToolDateTime.compare(endTime, currDateTme) > 0) {
                        return true;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BussinessException("交易时间转换异常");
        }
        return false;
    }

    /**
     * 判断是否为交易时间
     */
    public static boolean isTradeTime(String tradeTime) throws BussinessException {
        if (DateUtil.dayOfWeek(new Date()) == 1 || DateUtil.dayOfWeek(new Date()) == 7) {
            // 周六周日不能交易
            return false;
        }
        if (StringUtils.isBlank(tradeTime)) {
            return false;
        }
        String[] tradeTimeArr = tradeTime.split(";");
        try {
            //得到当前时间的小时数和分钟数
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String currDateStr = sdf.format(new Date()); //当前日期字符串
            Date currDateTme = sdf1.parse(sdf1.format(new Date())); //当前时间
            for (String t : tradeTimeArr) {
                String[] time = t.split(",");
                if (time.length < 1) {
                    continue;
                }
                if (time.length == 2) {
                    Date startTime = sdf1.parse(currDateStr + " " + time[0]);
                    Date endTime;
                    if (time[1].contains("次日")) {
                        endTime = sdf1.parse(currDateStr + " " + time[1].replace("次日", ""));
                        Calendar c = Calendar.getInstance();
                        c.setTime(endTime);
                        //判断当前小时数是否过凌晨0点
                        Calendar currCal = Calendar.getInstance();
                        currCal.setTime(currDateTme);
                        int currHour = currCal.get(Calendar.HOUR_OF_DAY);
                        if (currHour >= 0 && currHour < c.get(Calendar.HOUR_OF_DAY)) {
                            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
                            startTime = sdf1.parse(currDateStr + " 00:00");
                            //当前时间在交易时间段
                            if (ToolDateTime.compare(currDateTme, startTime) >= 0 && ToolDateTime.compare(c.getTime(), currDateTme) > 0) {
                                return true;
                            }
                        } else {
                            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
                        }
                        endTime = c.getTime();

                    } else {
                        endTime = sdf1.parse(currDateStr + " " + time[1]);
                    }
                    //当前时间在交易时间段
                    if (ToolDateTime.compare(currDateTme, startTime) >= 0 && ToolDateTime.compare(endTime, currDateTme) > 0) {
                        return true;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 判断是否为交易日
     */
    public static boolean isTradeDate() {
        String date = redisService.get(ToolDateTime.format(new Date(), ToolDateTime.pattern_ymd));
        return StringUtils.isBlank(date) || !"1".equals(date);
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(DateUtil.dayOfWeek(sdf.parse("2019-11-19")));
        System.out.println(DateUtil.dayOfWeek(sdf.parse("2019-11-18")));
        System.out.println(DateUtil.dayOfWeek(sdf.parse("2019-11-17")));
        System.out.println(DateUtil.dayOfWeek(sdf.parse("2019-11-16")));
    }

}
