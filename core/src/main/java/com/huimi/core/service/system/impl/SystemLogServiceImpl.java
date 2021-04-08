package com.huimi.core.service.system.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.system.SystemLogMapper;
import com.huimi.core.po.system.SystemLog;
import com.huimi.core.service.system.SystemLogService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 系统访问日志业务相关Service接口实现<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class SystemLogServiceImpl implements SystemLogService {

    @Resource
    private SystemLogMapper systemLogMapper;

    @Override
    public GenericMapper<SystemLog, Integer> _getMapper() {
        return systemLogMapper;
    }

}
