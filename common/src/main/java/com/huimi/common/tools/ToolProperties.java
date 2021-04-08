package com.huimi.common.tools;

import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * 描述: 配置文件属性读取
 * 作者: 陌上人
 * 时间: 2016/8/7 9:33
 */
public class ToolProperties {
    static Logger logger = Logger.getLogger(ToolProperties.class);
    private static Properties properties;

    private ToolProperties() {
    }

    static {
        properties = new Properties();
        try {
            properties.load(ToolProperties.class.getClassLoader().getResourceAsStream("conf.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        String value;
        value = properties.getProperty(key);
        if (null == value || "".equals(value)) {
            logger.info("没有获取指定key的值，请确认资源文件中是否存在【" + key + "】");
        }
        return value;
    }

    public static Integer getInt(String key) {
        String value;
        value = properties.getProperty(key);
        if (null == value || "".equals(value)) {
            System.out.println("没有获取指定key的值，请确认资源文件中是否存在【" + key + "】");
            return 0;
        } else {
            return Integer.parseInt(value);
        }
    }
}
