package com.huimi.core.service.tpl.impl;


import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.tpl.ProtocolMapper;
import com.huimi.core.po.tpl.Protocol;
import com.huimi.core.service.tpl.ProtocolService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class ProtocolServiceImpl implements ProtocolService {

    @Resource
    private ProtocolMapper protocolMapper;

    @Override
    public GenericMapper<Protocol, Integer> _getMapper() {
        return protocolMapper;
    }
}
