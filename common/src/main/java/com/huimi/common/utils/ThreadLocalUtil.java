package com.huimi.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal线程工具类
 *
 * @author LeviCoder
 * @create 2019-02-15 14:04
 */
public class ThreadLocalUtil {
    // 在new 时，该方法将会执行,初始化我们的值
    private static ThreadLocal<Map<String,Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    public static Map<String,Object> getResource() {
        return threadLocal.get();
    }

    public static void setValue(String key,Object value) {
        if(key==null||value==null) {
            throw new RuntimeException("要存在线程里面的值不能为null");
        }
        Map<String, Object> res = getResource();
        res.put(key, value);
    }

    public static Object getValue(String key) {
        if(key==null) {
            throw new RuntimeException("获取线程里面的值时，key不能为null");
        }
        return getResource().get(key);
    }
}
