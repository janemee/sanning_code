package com.huimi.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {

    /**
     * 获取指定日期的23：59：59 999
     *
     * @param date  指定日期
     * @param year  增加year年
     * @param month 增加 month月
     * @param day   增加day天
     * @return yyyy/mm/dd 23:59:59 999
     */
    public static Date getEndDateForDate(Date date, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        if (month != 0) {
            calendar.add(Calendar.YEAR, month);
        }
        if (year != 0) {
            calendar.add(Calendar.MONTH, year);
        }
        if (day != 0) {
            calendar.add(Calendar.DATE, day);
        }
        return calendar.getTime();
    }

    /**
     * 获取指定日期的00：00：00 000
     *
     * @param date  指定日期
     * @param year  增加year年
     * @param month 增加 month月
     * @param day   增加day天
     * @return yyyy/mm/dd 00:00:00 000
     */
    public static Date getStartDateForDate(Date date, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (month != 0) {
            calendar.add(Calendar.MONTH, month);
        }
        if (year != 0) {
            calendar.add(Calendar.MONTH, year);
        }
        if (day != 0) {
            calendar.add(Calendar.MONTH, day);
        }
        return calendar.getTime();
    }

    /**
     * 系统当前时间
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 前/后?天
     */
    public static Date rollDay(Date d, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 前/后?月
     */
    public static Date rollMon(Date d, int mon) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MONTH, mon);
        return cal.getTime();
    }

    /**
     * 日期转换为字符串 格式自定义
     */
    public static String dateStr(Date date, String f) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(f);
        return format.format(date);
    }

    /**
     * 日期转换为字符串 MM月dd日 hh:mm
     */
    public static String dateStr(Date date) {
        return dateStr(date, "MM月dd日 hh:mm");
    }

    /**
     * 日期转换为字符串 yyyy-MM-dd
     */
    public static String dateStr2(Date date) {
        return dateStr(date, "yyyy-MM-dd");
    }

    /**
     * yyyy年MM月dd日HH时mm分ss秒
     */
    public static String dateStr5(Date date) {
        return dateStr(date, "yyyy年MM月dd日 HH时mm分ss秒");
    }

    /**
     * yyyyMMddHHmmss
     */
    public static String dateStr3(Date date) {
        return dateStr(date, "yyyyMMddHHmmss");
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String dateStr4(Date date) {
        return dateStr(date, "yyyy-MM-dd HH:mm:ss");

    }

    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String dateStrMillis(Date date) {
        return dateStr(date, "yyyy-MM-dd HH:mm:ss.SSS");

    }

    /**
     * yyyy年MM月dd日
     */
    public static String dateStr6(Date date) {
        return dateStr(date, "yyyy年MM月dd日");
    }

    /**
     * yyyyMMdd
     */
    public static String dateStr7(Date date) {
        return dateStr(date, "yyyyMMdd");
    }

    /**
     * MM-dd
     */
    public static String dateStr8(Date date) {
        return dateStr(date, "MM-dd");
    }


    /**
     * HH:mm
     */
    public static String dateStr9(Date date) {
        return dateStr(date, "HH:mm");
    }

    /**
     * 将时间戳转换为Date
     */
    public static Date getDate(String times) {
        long time = Long.parseLong(times);
        return new Date(time * 1000);
    }

    public static String dateStr(String times) {
        return dateStr(getDate(times));
    }

    public static String dateStr2(String times) {
        return dateStr2(getDate(times));
    }

    public static String dateStr3(String times) {
        return dateStr3(getDate(times));
    }

    public static String dateStr4(String times) {
        return dateStr4(getDate(times));
    }

    public static String dateStr4(long times) {
        return dateStr4(new Date(times));
    }

    public static String dateStr5(String times) {
        return dateStr5(getDate(times));
    }

    public static String dateStr6(String times) {
        return dateStr6(getDate(times));
    }

    public static String dateStr7(String times) {
        return dateStr7(getDate(times));
    }

    /**
     * 根据时间戳，截取到分钟的字符串
     * 2016-09-23 10:13:19.439 >> 201609231013
     */
    public static String minuStr(String stamp) {
        if (StringUtils.isBlank(stamp) || stamp.length() < 16) {
            return "";
        }
        return stamp.substring(0, 16).replace("-", "").replace(":", "").replace(" ", "");
    }

    /**
     * 根据时间戳，截取年份字符
     * 2016-09-23 10:13:19.439 >> 2016
     */
    public static String yearOfTimeStamp(String stamp) {
        if (StringUtils.isBlank(stamp) || stamp.length() < 4) {
            return "";
        }
        return stamp.substring(0, 4);

    }

    /**
     * 根据时间戳，截取月字符
     * 2016-09-23 10:13:19.439 >> 09
     */
    public static String monthOfTimeStamp(String stamp) {
        if (StringUtils.isBlank(stamp) || stamp.length() < 7) {
            return "";
        }
        return stamp.substring(5, 7);

    }

    /**
     * 根据时间戳，截取日字符
     * 2016-09-23 10:13:19.439 >> 23
     */
    public static String dayOfTimeStamp(String stamp) {
        if (StringUtils.isBlank(stamp) || stamp.length() < 10) {
            return "";
        }
        return stamp.substring(8, 10);

    }

    /**
     * 根据时间戳，截取小时字符
     * 2016-09-23 10:13:19.439 >> 10
     */
    public static String hourOfTimeStamp(String stamp) {
        if (StringUtils.isBlank(stamp) || stamp.length() < 13) {
            return "";
        }
        return stamp.substring(11, 13);

    }

    /**
     * 根据时间戳，截取分钟字符
     * 2016-09-23 10:13:19.439 >> 13
     */
    public static String minuteOfTimeStamp(String stamp) {
        if (StringUtils.isBlank(stamp) || stamp.length() < 16) {
            return "";
        }
        return stamp.substring(14, 16);

    }

    /**
     * 获得当前日期
     */
    public static Date getNow() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    /**
     * 时间转中文时间
     *
     * @param time 以秒为单位的时间
     * @return 以名称为单位的时间
     */
    public static String timeTransfer(String time) {
        int val = Integer.parseInt(time);
        int day = val / (60 * 60 * 24);
        int hour = (val / (60 * 60)) - (day * 24);
        int minute = (val / 60) - (day * 24 * 60) - (hour * 60);
        int second = (val) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
        String dayName = "";
        String hourName = "";
        String minuteName = "";
        String secondName = "";
        if (day > 0)
            dayName = day + "天";
        if (hour > 0)
            hourName = hour + "小时";
        if (minute > 0)
            minuteName = minute + "分钟";
        if (second > 0)
            secondName = second + "秒";
        return dayName + hourName + minuteName + secondName;
    }

    public static Date todayEndTime(Date now) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd").format(now) + "23:59:59");
        } catch (ParseException e) {
            return null;
        }

    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
    }

    /**
     * 判断当前时间是否在当天的某个时间区间内
     *
     * @param startTime 开始时间 9:30
     * @param endTime   结束时间 18:00
     */
    public static boolean isTimeQuantum(String startTime, String endTime) {
        try {
            LocalTime now = LocalTime.now();
            String[] startTimes = startTime.split(":");
            String[] endTimes = endTime.split(":");
            boolean before = now.isBefore(LocalTime.of(Integer.parseInt(startTimes[0]), Integer.parseInt(startTimes[1])));
            boolean after = now.isAfter(LocalTime.of(Integer.parseInt(endTimes[0]), Integer.parseInt(endTimes[1])));
            return before || after;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 当天 + N天后的零点
     */
    public static Date severalDaysLater(int days) {
        Date date = new Date();
        while (days != 0) {
            date = cn.hutool.core.date.DateUtil.offsetDay(date, 1);
            if (cn.hutool.core.date.DateUtil.dayOfWeek(date) == 1 || cn.hutool.core.date.DateUtil.dayOfWeek(date) == 7) {
                // 周六周日跳过
                continue;
            }
            days--;
        }
        return date;
    }

    /**
     * 当天 + N天后的零点
     */
    public static Date severalDaysLater(Date date, int days) {
        while (days != 0) {
            date = cn.hutool.core.date.DateUtil.offsetDay(date, 1);
            if (cn.hutool.core.date.DateUtil.dayOfWeek(date) == 1 || cn.hutool.core.date.DateUtil.dayOfWeek(date) == 7) {
                // 周六周日跳过
                continue;
            }
            days--;
        }
        return date;
    }
}
