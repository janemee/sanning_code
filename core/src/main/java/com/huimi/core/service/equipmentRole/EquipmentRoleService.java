package com.huimi.core.service.equipmentRole;

import com.huimi.core.po.equipmentRole.EquipmentRole;
import com.huimi.core.req.EquipmentReq;
import com.huimi.core.req.EquipmentRoleModelReq;
import com.huimi.core.service.base.GenericService;

import java.util.List;

public interface EquipmentRoleService extends GenericService<Integer, EquipmentRole> {


    List<EquipmentRole> findByPage(EquipmentReq basePageReq);

    long findByPageCount(EquipmentReq basePageReq);

    /**
     * 授权 单设备
     *
     * @param equipmentId 设备id
     * @param personnelId 人员id
     */
    void add(Integer equipmentId, Integer personnelId);

    /**
     * 解除授权
     *
     * @param equipmentId 设备id
     * @param personnelId 人员id
     */
    void del(Integer equipmentId, Integer personnelId);


    /**
     * 按分组授权
     */
    void addByGroup(Integer groupId, Integer personnelId);

    /**
     * 授权
     *
     * @param equipmentRoleModelReq
     */
    void add(EquipmentRoleModelReq equipmentRoleModelReq);

    /**
     * 清楚用户设备授权
     *
     * @param equipmentId
     * @return
     */
    int delAllByEquipmentId(Integer equipmentId);
}
