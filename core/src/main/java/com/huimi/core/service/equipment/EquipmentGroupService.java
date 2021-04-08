package com.huimi.core.service.equipment;

import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * create by lja on 2020/7/29 14:26
 */
public interface EquipmentGroupService extends GenericService<Integer, EquipmentGroup> {
    DtGrid findAll();

    DtGrid findtaskGroup(Long id);

    List<EquipmentGroup> findAgentGroup(List<Equipment> equipmentList);

    /**
     * 获取当前人的默认第一个分组
     *
     * @param id
     */
    EquipmentGroup findByAdminId(Integer id);

    List<EquipmentGroup> findAgentGroup(Integer state,Integer sysAdminId);

    List<EquipmentGroup> findAdminGroup(List<Integer> sysAdminId);
}
