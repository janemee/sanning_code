package com.huimi.core.service.cms;

import com.huimi.core.po.cms.Announcement;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 系统公告业务相关Service接口<br>
 *
 * @ClassName: AnnouncementService
 * @author fengting
 * @date   2018-04-23 09:43:28
 */
public interface AnnouncementService extends GenericService<Integer, Announcement> {

    Announcement getNewAnnouncement();

    long announcementListCount(Integer state);

    List<Announcement> getAnnouncementList(Integer state,Integer pageSize,Integer pageNumber);

    List<Announcement> getAnnouncementThree(Integer size);
}
