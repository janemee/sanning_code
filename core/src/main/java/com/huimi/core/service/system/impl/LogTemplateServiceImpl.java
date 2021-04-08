package com.huimi.core.service.system.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.system.LogTemplateMapper;
import com.huimi.core.po.system.LogTemplate;
import com.huimi.core.service.system.LogTemplateService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 日志模板表业务相关Service接口实现<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class LogTemplateServiceImpl implements LogTemplateService {

    @Resource
    private LogTemplateMapper logTemplateMapper;

    @Override
    public GenericMapper<LogTemplate, Integer> _getMapper() {
        return logTemplateMapper;
    }

}
