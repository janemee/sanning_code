package com.huimi.core.service.system.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageInfo;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.JsonUtils;
import com.huimi.common.utils.StringUtils;

import com.huimi.core.exception.BussinessException;
import com.huimi.core.mapper.system.AdminMapper;
import com.huimi.core.mapper.system.NoticeLogMapper;
import com.huimi.core.model.system.NoticeLogModel1;
import com.huimi.core.model.system.NoticeLogModel2;
import com.huimi.core.model.system.ShopNoticeVO;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.NoticeLog;
import com.huimi.core.service.system.NoticeLogService;
import com.huimi.core.util.PromptLanguageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 交易手续费率模板业务相关Service接口实现<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class NoticeLogServiceImpl implements NoticeLogService {

    @Resource
    private NoticeLogMapper noticeLogMapper;


    @Autowired
    private AdminMapper adminMapper;



    @Override
    public GenericMapper<NoticeLog, Integer> _getMapper() {
        return noticeLogMapper;
    }



    @Override
    public DtGrid getNotice(Integer shopId, Integer rows, Integer page) {
        Integer pageSize = StringUtils.isBlank(rows) ? 1 : rows;
        Integer nowPage = StringUtils.isBlank(page) ? 1 : page;
        int startPos = (nowPage - 1) * pageSize;
        Map<String, Object> params = new HashMap<>(3);
        params.put("startPos", startPos);
        params.put("pageSize", pageSize);
        params.put("shopId", shopId);
        ArrayList<NoticeLog> noticeLogList = noticeLogMapper.queryAll(params);
        DtGrid dtGrid = new DtGrid();
        dtGrid.setNowPage(nowPage);
        dtGrid.setPageSize(pageSize);
        if (noticeLogList == null) {
            ArrayList<Object> list = new ArrayList<>();
            dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(list), new TypeReference<List<Object>>() {
            }));
            dtGrid.setRecordCount(0);
            dtGrid.setPageCount(0);
        } else {
            dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(noticeLogList), new TypeReference<List<Object>>() {
            }));
            int recordCount = noticeLogMapper.countQueryAll(params).intValue();
            dtGrid.setRecordCount(recordCount);
            int pageCount = ((int) (Math.ceil(recordCount * 1.0 / pageSize)));
            dtGrid.setPageCount(pageCount);
        }
        return dtGrid;
    }

}
