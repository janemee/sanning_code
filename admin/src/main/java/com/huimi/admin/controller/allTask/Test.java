package com.huimi.admin.controller.allTask;

import com.huimi.common.tools.DateUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Test {

    public static ConcurrentHashMap<String, LoginModel> map = new ConcurrentHashMap();

    public synchronized boolean access(String ip) {
        LoginModel loginModel = map.get(ip);
        if (loginModel == null) {
            //未登录过的 默认次数1
            return addLoginModel(ip, "add");
        } else {
            return addLoginModel(ip, "login");
        }
    }

    public boolean addLoginModel(String ip, String type) {
        if (type.equals("add")) {
            LoginModel loginModel = new LoginModel();
            loginModel.setCount(1);
            loginModel.setFirstDate(new Date());
            loginModel.setIp(ip);
            map.put(ip, loginModel);
            toLog();
            return true;
        } else {
            LoginModel loginModel = map.get(ip);
            //是否在十分钟之内 登录次数为10 则不能登录
            if (minutesBetween(loginModel.getFirstDate(), new Date()) <= 10 && loginModel.getCount() >= 10) {
                //不能登录
                System.out.println("已经登录十次，操作过于频繁" + "ip=" + ip + "登录次数+" + loginModel.getCount());
                toLog();
                return false;
            }
            //十分钟之后 登录次数超过10 重置登录次数
            if (DateUtil.minutesBetween(loginModel.getFirstDate(), new Date()) >= 10 && loginModel.getCount() >= 10) {
                loginModel.setCount(1);
                loginModel.setIp(ip);
                loginModel.setFirstDate(new Date());
                map.put(ip, loginModel);
                toLog();
                return true;
            }

            loginModel.setCount(loginModel.getCount() + 1);
            loginModel.setIp(ip);
            loginModel.setLastDate(new Date());
            map.put(ip, loginModel);
            toLog();
            return true;
        }
    }

    public void toLog() {
        Collection<LoginModel> loginModels = map.values();
        for (LoginModel loginModel : loginModels) {
            System.out.println("登录IP" + loginModel.getIp() + "--次数：" + loginModel.getCount() + "+登录时间上次登录时间+" + DateUtil.dateStr(loginModel.getLastDate()));
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        String ip = "127.0.0.";
        for (int i = 0; i < 11; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 20; i++) {
                        test.access(ip + i);
                        System.out.println("线程" + i);
                    }
                }
            }, "线程" + i).start();
        }
        System.out.println(map);
    }

    class LoginModel {

        private String ip;

        private Date lastDate;

        private Date firstDate;

        private int count;

        public Date getLastDate() {
            return lastDate;
        }

        public void setLastDate(Date lastDate) {
            this.lastDate = lastDate;
        }

        public Date getFirstDate() {
            return firstDate;
        }

        public void setFirstDate(Date firstDate) {
            this.firstDate = firstDate;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }


    /**
     * 计算date2 - date1之间相差的分钟
     *
     */
    public static int minutesBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        // date1.setSeconds(0);
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        if (time2 - time1 <= 0) {
            return 0;
        } else {
            return Integer.parseInt(String.valueOf((time2 - time1) / 60000L)) + 1;
        }

    }
}
