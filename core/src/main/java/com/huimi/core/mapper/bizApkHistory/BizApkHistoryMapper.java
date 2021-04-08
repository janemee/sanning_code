package com.huimi.core.mapper.bizApkHistory;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.bizApkHistory.BizApkHistory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * create by lja on 2020/7/28 17:36
 */
@Repository
public interface BizApkHistoryMapper extends GenericMapper<BizApkHistory, Integer> {

    @Select(" select * from  biz_apk_history order by id desc ")
    List<BizApkHistory> findByAllList();


    @Select("select * from  biz_apk_history where state  = #{state} ")
    List<BizApkHistory> findByStateList(@Param("state") int state);


    @Select("select * from  biz_apk_history where name  = #{name} ")
    List<BizApkHistory> findByNameList(@Param("name") String name);

    @Select("select * from  biz_apk_history where state  = #{state} order by id desc  limit 1")
    BizApkHistory findByStateOne(@Param("state") int value);
}
