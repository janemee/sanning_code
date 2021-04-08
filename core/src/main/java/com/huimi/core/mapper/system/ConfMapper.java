package com.huimi.core.mapper.system;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.system.Conf;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 系统参数配置表相关Dao接口<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Repository
public interface ConfMapper extends GenericMapper<Conf, Integer> {

    @Update("update sys_conf set value = #{value} where nid = #{nid}")
    Integer updateConfByNid(@Param("nid") String nid, @Param("value") String value);
}
