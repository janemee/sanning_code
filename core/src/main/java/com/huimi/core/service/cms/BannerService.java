package com.huimi.core.service.cms;

import com.huimi.core.po.cms.Banner;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * banner管理业务相关Service接口<br>
 *
 * @ClassName: BannerService
 * @author fengting
 * @date   2018-04-23 09:43:28
 */
public interface BannerService extends GenericService<Integer, Banner> {

    /**
     * 获取首页bannerList,
     * @param size 数量
     * @return
     */
    List<Banner> getIndexList(Integer size);
}
