package com.huimi.core.service.apkHistory.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.bizApkHistory.BizApkHistoryMapper;
import com.huimi.core.po.bizApkHistory.BizApkHistory;
import com.huimi.core.service.apkHistory.BizApkHistoryService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * create by lja on 2020/7/28 17:33
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class BizHistoryServiceImpl implements BizApkHistoryService {

    @Resource
    private BizApkHistoryMapper bizApkHistoryMapper;


    @Override
    public GenericMapper<BizApkHistory, Integer> _getMapper() {
        return bizApkHistoryMapper;
    }


    @Override
    public List<BizApkHistory> findByAll() {
        return bizApkHistoryMapper.findByAllList();
    }

    @Override
    public List<BizApkHistory> findByStateList(int state) {
        return bizApkHistoryMapper.findByStateList(state);
    }

    @Override
    public List<BizApkHistory> findByNameList(String name) {
        return bizApkHistoryMapper.findByNameList(name);
    }

    @Override
    public BizApkHistory findByStateOne(int value) {
        return bizApkHistoryMapper.findByStateOne(value);
    }
}
