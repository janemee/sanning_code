/*
 * Copyright (c) 2015-2017, HuiMi Tec co.,LTD. 枫亭子 (646496765@qq.com).
 */
package com.huimi.core.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 枫亭
 * @description TODO
 * @date 2018-05-22 19:38.
 */
public class ThreadPoolUtils {

    private static ExecutorService executorService ;

    static {
        executorService = Executors.newCachedThreadPool();
    }

    public static ExecutorService getExecutor() {
        return executorService;
    }
}
