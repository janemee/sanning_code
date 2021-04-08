package com.huimi.core.mapper.tpl;

import com.huimi.core.po.tpl.Protocol;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.model.tpl.ProtocolModel;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 各种协议模板相关Dao接口<br>
 *
 * @author fengting
 * @date   2018-04-23 09:50:40
 */
@Repository
public interface ProtocolMapper extends GenericMapper<Protocol,Integer> {

    @Select("SELECT t.* FROM tpl_protocol t WHERE 1=1 ${whereSql} ${sortSql} limit ${nowPage}, ${pageSize}")
    List<ProtocolModel> findListBySqlJoin(Map<String, Object> params);

    @Select("SELECT count(1) FROM tpl_protocol t WHERE 1=1 ${whereSql}")
    long countBySqlJoin(Map<String, Object> params);
}
