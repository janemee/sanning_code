package com.huimi.core.mapper.cms;

import com.huimi.core.po.cms.Banner;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.model.cms.BannerModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * banner管理相关Dao接口<br>
 *
 * @author fengting
 * @date   2018-04-23 09:43:28
 */
@Repository
public interface BannerMapper extends GenericMapper<Banner,Integer> {

    @Select("SELECT t.* FROM cms_banner t WHERE 1=1 ${whereSql} ${sortSql} limit ${nowPage}, ${pageSize}")
    List<BannerModel> findListBySqlJoin(Map<String, Object> params);

    @Select("SELECT count(1) FROM cms_banner t WHERE 1=1 ${whereSql}")
    long countBySqlJoin(Map<String, Object> params);

    @Select("SELECT t.title,t.pc_path,t.app_path FROM cms_banner t WHERE 1=1 AND t.state = #{state} AND t.del_flag = 0  ORDER BY t.create_time DESC LIMIT 0,#{size} " )
    List<Banner> selectIndexList(@Param("state") Integer state, @Param("size") Integer size);
}
