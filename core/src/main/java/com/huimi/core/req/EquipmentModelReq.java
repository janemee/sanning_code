package com.huimi.core.req;

import lombok.Data;

/**
 * 添加设备请求参数
 */
@Data
public class EquipmentModelReq extends BasePageReq {

    /**
     * 设备唯一标识
     */
    private Integer id;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 激活码
     */
    private String activationCode;

    /**
     * 网络类型 0 有线  1 无线
     */
    private Integer networkType;

    //------高级设置
    /**
     * 识别阀值
     */
    private Integer distinguishThreshold;

    /**
     * 活体阀值
     */
    private Integer liveThreshold;

    /**
     * 续电器极性 0 关闭  1 开启
     */
    private Integer relayPolarity;

    /**
     * 续电器延时
     */
    private Integer relayDelayed;
    /**
     * 补光灯高亮
     */
    private Integer fillInLightHigh;
    /**
     * 补光灯低亮
     */
    private Integer fillInLightLow;

    /**
     * 正脸判断  0 关闭 1 开启
     */
    private Integer faceChecked;

    /**
     * wifi名称
     */
    private String wifiName;

    /**
     * wifi安全类型
     */
    private String clearType;

    /**
     * wifi密码
     */
    private String wifiPwd;

    /**
     * 设备类型
     */
    private Integer type;

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





}
