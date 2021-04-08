package com.huimi.core.service.apkHistory;

import com.huimi.core.po.bizApkHistory.BizApkHistory;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * create by lja on 2020/7/28 17:31
 */
public interface BizApkHistoryService extends GenericService<Integer, BizApkHistory> {


    /**
     * 获取数据列表（后台列表数据显示）
     *
     * @return
     */
    List<BizApkHistory> findByAll();


    /**
     * 根据状态查询启用的文件列表
     *
     * @param state
     * @return
     */
    List<BizApkHistory> findByStateList(int state);

    /**
     * 根据文件名称查询文件列表
     *
     * @param name
     * @return
     */
    List<BizApkHistory> findByNameList(String name);

    BizApkHistory findByStateOne(int value);
}
