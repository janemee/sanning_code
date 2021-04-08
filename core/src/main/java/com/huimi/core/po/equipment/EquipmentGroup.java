package com.huimi.core.po.equipment;

import com.huimi.common.baseMapper.GenericPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;


@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "设备分组表")
@Table(name = "equipment_group")
public class EquipmentGroup  extends GenericPo<Integer> {
    public static final String TABLE_NAME = "equipment_group";

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private Integer state;

    /**
     * 用户id
     */
    @Column(name = "sys_admin_id")
    private Integer sysAdminId;


    @Column(name = "users_id")
    private Integer usersId;


    @Column(name = "address")
    private String address;

    /**
     * 用户名
     */
    @Transient
    private String userName = "--";
}
