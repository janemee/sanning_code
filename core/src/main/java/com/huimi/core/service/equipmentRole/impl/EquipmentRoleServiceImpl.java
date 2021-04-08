package com.huimi.core.service.equipmentRole.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.mapper.equipmentRole.EquipmentRoleMapper;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.po.equipmentRole.EquipmentRole;
import com.huimi.core.req.EquipmentReq;
import com.huimi.core.req.EquipmentRoleModelReq;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.equipmentRole.EquipmentRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class EquipmentRoleServiceImpl implements EquipmentRoleService {
    @Resource
    private EquipmentRoleMapper equipmentRoleMapper;
    @Resource
    private EquipmentGroupService equipmentGroupService;
    @Resource
    private EquipmentService equipmentService;


    @Override
    public GenericMapper<EquipmentRole, Integer> _getMapper() {
        return equipmentRoleMapper;
    }

    @Override
    public List<EquipmentRole> findByPage(EquipmentReq basePageReq) {
        return equipmentRoleMapper.findByPage(basePageReq);
    }

    @Override
    public long findByPageCount(EquipmentReq basePageReq) {
        return equipmentRoleMapper.findByPageCount(basePageReq);
    }

    @Override
    public void add(Integer equipmentId, Integer personnelId) {
        EquipmentRole equipmentRole = new EquipmentRole();
        equipmentRole.setEquipmentId(equipmentId);
        equipmentRole.setPersonnelId(personnelId);
        equipmentRole.setCreateTime(new Date());
        if (equipmentRoleMapper.insert(equipmentRole) == 0) {
            throw new BussinessException("授权失败");
        }
    }

    @Override
    public void del(Integer equipmentId, Integer personnelId) {
        equipmentRoleMapper.delRole(equipmentId, personnelId);
    }

    @Override
    public void addByGroup(Integer groupId, Integer personnelId) {
        EquipmentGroup equipmentGroup = equipmentGroupService.selectByPrimaryKey(groupId);
        if (equipmentGroup == null) {
            throw new BussinessException("分组不存在或已被删除，请检查后重试");
        }
        //获取分组
        List<Equipment> list = equipmentService.findByGroupList(groupId);
        if (CollectionUtils.isEmpty(list)) {
            throw new BussinessException("分组：" + equipmentGroup.getName() + "暂无设备，请先添加设备");
        }
        //循环添加设备
        for (Equipment equipment : list) {
            this.add(equipment.getId(), personnelId);
        }
    }

    @Override
    public void add(EquipmentRoleModelReq equipmentRoleModelReq) {
        if ("equipment".equals(equipmentRoleModelReq.getDeviceStyle())) {
            //选择授权设备
            String[] ids = equipmentRoleModelReq.getEquipmentIds().split(",");
            for (String id : ids) {
                this.add(Integer.parseInt(id), equipmentRoleModelReq.getPersonnelId());
            }
        } else {
            String[] ids = equipmentRoleModelReq.getEquipmentGroupIds().split(",");
            for (String id : ids) {
                this.addByGroup(Integer.parseInt(id), equipmentRoleModelReq.getPersonnelId());
            }
        }
    }
    @Override
    public int delAllByEquipmentId(Integer equipmentId) {
        return equipmentRoleMapper.delAllByEquipmentId(equipmentId);
    }
}
