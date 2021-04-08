package com.huimi.core.service.system.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.system.NoticeTemplateMapper;
import com.huimi.core.po.system.NoticeTemplate;
import com.huimi.core.service.system.NoticeTemplateService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 系统通知模板表业务相关Service接口实现<br>
 * @author fengting
 * @date   2018-09-29 04:30:19
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class NoticeTemplateServiceImpl implements NoticeTemplateService {

	@Resource
    private NoticeTemplateMapper noticeTemplateMapper;

    @Override
    public GenericMapper<NoticeTemplate,Integer> _getMapper() {
        return noticeTemplateMapper;
    }

}
