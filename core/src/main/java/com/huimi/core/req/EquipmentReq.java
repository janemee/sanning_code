package com.huimi.core.req;

import lombok.Data;

/**
 * 后台设备相关查询 请求参数
 */
@Data
public class EquipmentReq extends BasePageReq {

    /**
     * 人员id
     */
    private String personnelId;
}
