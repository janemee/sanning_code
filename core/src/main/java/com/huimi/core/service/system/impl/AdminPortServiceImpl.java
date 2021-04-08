package com.huimi.core.service.system.impl;


import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.system.AdminPortMapper;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.AdminPort;
import com.huimi.core.service.system.AdminPortService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * create by lja on 2020/8/27 17:45
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class AdminPortServiceImpl implements AdminPortService {

    @Resource
    private AdminPortMapper adminPortMapper;

    @Override
    public GenericMapper<AdminPort, Integer> _getMapper() {
        return adminPortMapper;
    }

}
