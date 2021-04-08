package com.huimi.core.po.equipmentLog;

import com.huimi.common.baseMapper.GenericPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;


@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "设备使用记录表")
@Table(name = "equipment_log")
public class EquipmentLog extends GenericPo<Integer> {
    public static final String TABLE_NAME = "equipment_log";
    /**
     * 设备id
     */
    @Column(name = "equipment_id")
    private String equipmentId;
    /**
     * 设备名称
     */
    @Column(name = "equipment_name")
    private String equipmentName;
    /**
     * 使用人员id
     */
    @Column(name = "personnel_id")
    private String personnelId;
    /**
     * 识别状态 0 成功 1 失败
     */
    @Column(name = "state")
    private String state;
    /**
     * 记录状态 1 正常 2 重要 3 报警
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 人员名称
     */
    @Transient
    private String personnelName;

    /**
     * 地址
     */
    @Transient
    private String address;


}
