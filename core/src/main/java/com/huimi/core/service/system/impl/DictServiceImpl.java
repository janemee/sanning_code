package com.huimi.core.service.system.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.system.DictMapper;
import com.huimi.core.po.system.Dict;
import com.huimi.core.service.system.DictService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 数据字典表业务相关Service接口实现<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class DictServiceImpl implements DictService {

    @Resource
    private DictMapper dictMapper;

    @Override
    public GenericMapper<Dict, Integer> _getMapper() {
        return dictMapper;
    }

}
