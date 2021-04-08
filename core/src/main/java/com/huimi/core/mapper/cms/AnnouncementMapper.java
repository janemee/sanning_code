package com.huimi.core.mapper.cms;

import com.huimi.core.po.cms.Announcement;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.model.cms.AnnouncementModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 系统公告相关Dao接口<br>
 *
 * @author fengting
 * @date   2018-04-23 09:43:28
 */
@Repository
public interface AnnouncementMapper extends GenericMapper<Announcement,Integer> {

    @Select("SELECT t.* FROM cms_announcement t WHERE 1=1 ${whereSql} ${sortSql} limit ${nowPage}, ${pageSize}")
    List<AnnouncementModel> findListBySqlJoin(Map<String, Object> params);

    @Select("SELECT count(1) FROM cms_announcement t WHERE 1=1 ${whereSql}")
    long countBySqlJoin(Map<String, Object> params);

    @Select("SELECT t.* FROM cms_announcement t WHERE 1=1 AND t.state = #{state} AND t.del_flag = 0 ORDER BY t.publish_time DESC")
    List<Announcement> getNewAnnouncement(@Param("state") Integer state);

    @Select("SELECT t.* FROM cms_announcement t WHERE 1=1 AND t.state = #{state} AND t.del_flag = 0 ORDER BY t.publish_time DESC LIMIT 0,#{size}")
    List<Announcement> getAnnouncementThree(@Param("state") Integer state, @Param("size")Integer size);

    @Select("SELECT count(1) FROM cms_announcement WHERE 1=1 ${whereSql} ${sortSql}")
    long announcementListCount(Map<String, Object> params);

    @Select("SELECT * FROM cms_announcement WHERE 1=1 ${whereSql} ${sortSql} limit ${nowPage}, ${pageSize}")
    List<Announcement> getAnnouncementList(Map<String, Object> params);
}
