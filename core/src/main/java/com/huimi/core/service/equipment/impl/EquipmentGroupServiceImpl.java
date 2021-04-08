package com.huimi.core.service.equipment.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.JsonUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.mapper.equipment.EquipmentGroupMapper;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.service.equipment.EquipmentGroupService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * create by lja on 2020/7/29 14:32
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class EquipmentGroupServiceImpl implements EquipmentGroupService  {
    @Resource
    private EquipmentGroupMapper equipmentGroupMapper;


    @Override
    public DtGrid findtaskGroup(Long id) {
        List<EquipmentGroup> equipmentGroups = equipmentGroupMapper.findtaskGroup(id);
        DtGrid dtGrid = new DtGrid();


        dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(equipmentGroups), new TypeReference<List<Object>>() {
        }));
        dtGrid.setExportDatas(JsonUtils.toGenericObject(JsonUtils.toJson(equipmentGroups), new TypeReference<List<Map<String, Object>>>() {
        }));
        return dtGrid;
    }

    @Override
    public List<EquipmentGroup> findAgentGroup(List<Equipment> equipmentList) {
        if (equipmentList.size()==0){
            return new ArrayList<EquipmentGroup>();
        }
        ArrayList<Integer> equipmentIds = new ArrayList<>();
        for (Equipment equipment : equipmentList) {
                equipmentIds.add(equipment.getId());
        }
        return  equipmentGroupMapper.findByAgent(equipmentIds);
    }

    @Override
    public EquipmentGroup findByAdminId(Integer adminId) {
        return equipmentGroupMapper.findByAdminId(adminId, EnumConstants.HistoryState.YES.value);
    }

    @Override
    public List<EquipmentGroup> findAgentGroup(Integer state, Integer sysAdminId) {
        return equipmentGroupMapper.findAgentGroup(state,sysAdminId);
    }

    @Override
    public List<EquipmentGroup> findAdminGroup(List<Integer> sysAdminId) {
        return equipmentGroupMapper.findAdminGroup(sysAdminId);
    }

    @Override
    public DtGrid findAll() {
        List<EquipmentGroup> retList = equipmentGroupMapper.selectIndexList();

        DtGrid dtGrid = new DtGrid();


        dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(retList), new TypeReference<List<Object>>() {
        }));
        dtGrid.setExportDatas(JsonUtils.toGenericObject(JsonUtils.toJson(retList), new TypeReference<List<Map<String, Object>>>() {
        }));
        return dtGrid;
    }

    @Override
    public GenericMapper<EquipmentGroup, Integer> _getMapper() {
        return equipmentGroupMapper;
    }

}
