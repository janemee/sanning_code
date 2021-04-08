package com.huimi.core.service.system.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.system.AdminLoginLogMapper;
import com.huimi.core.po.system.AdminLoginLog;
import com.huimi.core.service.system.AdminLoginLogService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 系统管理员登陆日志业务相关Service接口实现<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class AdminLoginLogServiceImpl implements AdminLoginLogService {

    @Resource
    private AdminLoginLogMapper adminLoginLogMapper;

    @Override
    public GenericMapper<AdminLoginLog, Integer> _getMapper() {
        return adminLoginLogMapper;
    }

}
