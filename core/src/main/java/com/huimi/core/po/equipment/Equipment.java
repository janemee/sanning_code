package com.huimi.core.po.equipment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huimi.common.baseMapper.GenericPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "设备表")
@Table(name = "equipment")
public class Equipment extends GenericPo<Integer> {
    public static final String TABLE_NAME = "equipment";
    /**
     * 激活码
     */
    @Column(name = "device_uid")
    private String deviceUid;

    /**
     * 用户id
     */
    @Column(name = "sys_admin_id")
    private Integer sysAdminId;

    /**
     * 激活码
     */
    @Column(name = "device_code")
    private String deviceCode;

    /**
     * 设备名称
     */
    @Column(name = "device_name")
    private String deviceName;
    /**
     * 用户id
     */
    @Column(name = "users_id")
    private Integer usersId;

    /**
     * 代理商邀请码
     */
    @Column(name = "sys_admin_code")
    private String sysAdminCode;
    /**
     * 代理商名称
     */
    @Transient
    private String sysAdminName;
    /**
     * 设备分组的id
     */
    @Column(name = "group_id")
    private Integer groupId;
    /**
     * 状态 1 在线 0离线
     */
    @Column(name = "state")
    private Integer state;
    /**
     * 类型0全部 1R版本 2V版 3E粉
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 详细地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 网络类型 1 有线  2 无线
     */
    @Column(name = "network_type")
    private Integer networkType;

    //------高级设置
    /**
     * 识别阀值
     */
    @Column(name = "distinguish_threshold")
    private Integer distinguishThreshold;

    /**
     * 活体阀值
     */
    @Column(name = "live_threshold")
    private Integer liveThreshold;

    /**
     * 续电器极性 0 关闭  1 开启
     */
    @Column(name = "relay_polarity")
    private Integer relayPolarity;

    /**
     * 续电器延时
     */
    @Column(name = "relay_delayed")
    private Integer relayDelayed;
    /**
     * 补光灯高亮
     */
    @Column(name = "fill_in_light_high")
    private Integer fillInLightHigh;
    /**
     * 补光灯低亮
     */
    @Column(name = "fill_in_light_low")
    private Integer fillInLightLow;

    /**
     * 正脸判断  0 关闭 1 开启
     */
    @Column(name = "face_checked")
    private Integer faceChecked;

    /**
     * 最近在线时间
     */
    @Column(name = "`last_time`")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;


    /**
     * wifi名称
     */
    @Column(name = "wifi_name")
    private String wifiName;

    /**
     * wifi安全类型
     */
    @Column(name = "clear_type")
    private String clearType;

    /**
     * wifi密码
     */
    @Column(name = "wifi_pwd")
    private String wifiPwd;
    /**
     * 状态
     */
    @Transient
    private String status;

    /**
     * 用户名
     */
    @Transient
    private String username;

    /**
     * 任务数量
     */
    @Transient
    private Integer taskNumber;

    /**
     * 任务数量
     */
    @Transient
    private String remakes;
    /**
     * 任务数量
     */
    @Transient
    private String channelId;


    /**
     * 设备状态
     */
    public enum STATE {
        NO("离线", 0),
        YES("在线", 1);

        public final int code;
        public final String value;
        private static Map<Integer, String> map = new HashMap<>();

        STATE(String value, int code) {
            this.code = code;
            this.value = value;
        }

        public static String getValue(Integer code) {
            if (null == code) {
                return null;
            }
            for (STATE status : STATE.values()) {
                if (status.code == code) {
                    return status.value;
                }
            }
            return null;
        }

        public static Integer getCode(String value) {
            if (null == value || "".equals(value)) {
                return null;
            }
            for (STATE status : STATE.values()) {
                if (status.value.equals(value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static Map<Integer, String> getEnumMap() {
            if (map.size() == 0) {
                for (STATE status : STATE.values()) {
                    map.put(status.code, status.value);
                }
            }
            return map;
        }
    }

}
