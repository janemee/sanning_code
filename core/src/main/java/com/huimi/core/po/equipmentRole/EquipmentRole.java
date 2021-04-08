package com.huimi.core.po.equipmentRole;

import com.huimi.common.baseMapper.GenericPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;


@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "人员设备授权")
@Table(name = "equipment_role")
public class EquipmentRole extends GenericPo<Integer> {
    public static final String TABLE_NAME = "equipment_role";
    /**
     * 设备id
     */
    @Column(name = "equipment_id")
    private Integer equipmentId;

    /**
     * 使用人员id
     */
    @Column(name = "personnel_id")
    private Integer personnelId;

    /**
     * 人员名称
     */
    @Transient
    private String personnelName;

    /**
     * 设备名称
     */
    @Transient
    private String equipmentName;

    /**
     * 地址
     */
    @Transient
    private String address;


}
