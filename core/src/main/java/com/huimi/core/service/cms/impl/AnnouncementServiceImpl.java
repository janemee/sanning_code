package com.huimi.core.service.cms.impl;

import com.huimi.core.po.cms.Announcement;
import com.huimi.core.mapper.cms.AnnouncementMapper;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.service.cms.AnnouncementService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统公告业务相关Service接口实现<br>
 *
 * @ClassName: AnnouncementServiceImpl
 * @author fengting
 * @date   2018-04-23 09:43:28
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class AnnouncementServiceImpl implements AnnouncementService {

	@Resource
    private AnnouncementMapper announcementMapper;

    @Override
    public GenericMapper<Announcement,Integer> _getMapper() {
        return announcementMapper;
    }

    @Override
    public Announcement getNewAnnouncement() {
        Announcement announcement = null;
        List<Announcement> announcements = announcementMapper.getNewAnnouncement(Announcement.STATE.PUBLISHED.code);
        if (null != announcements && announcements.size()>0) {
            announcement = announcements.get(0);
        }
        return announcement;
    }

    @Override
    public long announcementListCount(Integer state) {
        Map<String, Object> params = new HashMap<>();
        params.put("whereSql", " AND state = " + state + " AND del_flag = 0 ");
        params.put("sortSql", " ORDER BY create_time DESC");
        return announcementMapper.announcementListCount(params);
    }

    @Override
    public List<Announcement> getAnnouncementList(Integer state,Integer pageSize,Integer pageNumber) {
        Map<String, Object> params = new HashMap<>();
        int startPos = (pageNumber - 1) * pageSize;
        params.put("whereSql", " AND state = " + state + " AND del_flag = 0 ");
        params.put("sortSql", " ORDER BY create_time DESC");
        params.put("nowPage", startPos);
        params.put("pageSize", pageSize);
        return announcementMapper.getAnnouncementList(params);
    }

    @Override
    public List<Announcement> getAnnouncementThree(Integer size) {
        return announcementMapper.getAnnouncementThree(Announcement.STATE.PUBLISHED.code,size);
    }
}
