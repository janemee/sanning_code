package com.huimi.core.req;

import lombok.Data;

/**
 * 添加设备请求参数
 */
@Data
public class PersonnelModelReq extends BasePageReq {


    /**
     * 人员id集合字符串  以逗号隔开1,2,3
     */
    private String ids;

    /**
     * 设备id
     */
    private Integer equipmentId;

    //--------------用户信息
    /**
     * 登录用户uuid
     */
    private String userUuid;

    /**
     * 用户id
     */
    private Integer usersId;
    /**
     * 分组id
     */
    private Integer groupId;


    //---------添加人员
    /**
     * 人员名称
     */
    private String personnelName;

    /**
     * 手机号
     */
    private String personnelPhone;
    /**
     * 韦根编号
     */
    private String weigenCode;

    /**
     * 人员头像
     */
    private String picUrl;

}
