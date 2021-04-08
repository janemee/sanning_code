package com.huimi.core.req;

import lombok.Data;

@Data
public class EquipmentRoleModelReq extends BasePageReq {
    /**
     * 设备ids
     */
    private String equipmentIds;

    /**
     * 分组id
     */
    private String equipmentGroupIds;

    /**
     * 授权类型  equipment  设备  grouping 分组
     */
    private String deviceStyle;

    /**
     * 被授权人员id
     */
    private Integer personnelId;
}
