package com.huimi.core.service.system;

import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.core.po.system.NoticeLog;
import com.huimi.core.service.base.GenericService;

import java.util.Map;

/**
 * 消息相关Service接口<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
public interface NoticeLogService extends GenericService<Integer, NoticeLog> {

    DtGrid getNotice(Integer userId, Integer rows, Integer page);
}
