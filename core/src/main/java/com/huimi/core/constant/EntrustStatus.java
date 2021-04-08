/*
 * Copyright (c) 2015-2018, HuiMi Tec co.,LTD. 枫亭子 (646496765@qq.com).
 */
package com.huimi.core.constant;

/**
 * @author 枫亭
 * @date 2018-07-13 16:16.
 */
public interface EntrustStatus {

    String NOT_REPORT = "0";
    String WAIT_REPORT = "1";
    String REPORTED = "2";
    String WAIT_REVOKE = "3";
    String PART_OF_DEAL_AND_WAIT_REVOKE = "4";
    String PART_OF_REVOKE = "5";
    String REVOKED = "6";
    String PART_OF_DEAL = "7";
    String ALL_DEAL = "8";
    String FAILED = "9";
}
