package com.huimi.core.service.personnel.impl;

import cn.hutool.db.Page;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.personnel.PersonnelMapper;
import com.huimi.core.po.personnel.Personnel;
import com.huimi.core.req.EquipmentRoleModelReq;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.personnel.PersonnelService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class PersonnelServiceImpl implements PersonnelService {

    @Resource
    private RedisService redisService;
    @Resource
    private PersonnelMapper personnelMapper;


    @Override
    public GenericMapper<Personnel, Integer> _getMapper() {
        return personnelMapper;
    }


    @Override
    public Personnel findByPhone(String phone) {

        return personnelMapper.findByPhone(phone);
    }

    @Override
    public Page findUsersByPage(EquipmentRoleModelReq equipmentModelReq) {
        return personnelMapper.findUsersByPage(equipmentModelReq);
    }
}
