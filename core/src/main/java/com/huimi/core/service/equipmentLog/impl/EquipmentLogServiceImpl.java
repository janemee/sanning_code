package com.huimi.core.service.equipmentLog.impl;

import cn.hutool.db.Page;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.equipmentLog.EquipmentLogMapper;
import com.huimi.core.po.equipmentLog.EquipmentLog;
import com.huimi.core.req.BasePageReq;
import com.huimi.core.req.EquipmentModelReq;
import com.huimi.core.service.equipmentLog.EquipmentLogService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class EquipmentLogServiceImpl implements EquipmentLogService {
    @Resource
    private EquipmentLogMapper equipmentLogMapper;


    @Override
    public GenericMapper<EquipmentLog, Integer> _getMapper() {
        return equipmentLogMapper;
    }

    @Override
    public List<EquipmentLog> findByPage(BasePageReq basePageReq) {
        return equipmentLogMapper.findByPage(basePageReq);
    }

    @Override
    public long findByPageCount(BasePageReq basePageReq) {
        return equipmentLogMapper.findByPageCount(basePageReq);
    }

    @Override
    public Page findUsersByPage(EquipmentModelReq equipmentModelReq) {
        return equipmentLogMapper.findUsersByPage(equipmentModelReq);
    }
}
