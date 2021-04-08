package com.huimi.common.entity.dtgrid;

import lombok.Data;

/**
 * 接收dtGrid排序参数
 * Created by shopnc on 2015/11/4.
 */
@Data
public class Sort {

    public static final String ASC = "1";
    public static final String DESC = "2";
    /**
     * 要排序的字段
     */
    private String field;

    /**
     * 排序逻辑 1升序，2降序
     */
    private String logic;

}
