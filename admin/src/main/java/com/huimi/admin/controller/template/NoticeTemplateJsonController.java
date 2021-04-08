package com.huimi.admin.controller.template;

import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.admin.controller.BaseController;
import com.huimi.admin.controller.GenericController;
import com.huimi.core.po.system.NoticeTemplate;
import com.huimi.core.service.system.NoticeTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(BaseController.BASE_URI)
public class NoticeTemplateJsonController extends GenericController <Integer, NoticeTemplate> {


    @Resource
    private NoticeTemplateService noticeTemplateService;


    /**
     * 通知记录列表json数据
     */
    @ResponseBody
    @RequestMapping("/noticeTemplate/json/list")
//    @RequiresPermissions("sys:noticeLog:list")
    public DtGrid listJson(HttpServletRequest request) throws Exception {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 1 : Integer.valueOf(request.getParameter("rows"));
        Integer pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1 : Integer.valueOf(request.getParameter("page"));
        String search_val = request.getParameter("search_val");
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        if (!StringUtils.isBlank(search_val)) {
            whereSql.append(" and (title like '%").append(search_val).append("%' or ").append("nid like '%").append(search_val).append("%')");
        }
        dtGrid.setWhereSql(whereSql.toString());
        return noticeTemplateService.getDtGridList(dtGrid);
    }

    /**
     * 添加参数配置
     *
     * @param noticeTemplate
     * @return
     */
    @ResponseBody
    @RequestMapping("/noticeTemplate/json/saveOrUpdata")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(NoticeTemplate noticeTemplate) throws Exception {
//        conf.setUuid(RandomUtils.randomCustomUUID());
        int insert = 0;
        if (noticeTemplate.getId() != null) {
            insert = noticeTemplateService.updateByPrimaryKeySelective(noticeTemplate);
        } else {
            insert = noticeTemplateService.insert(noticeTemplate);
        }
        return insert > 0 ? ok() : fail();
    }

    /**
     * 删除参数配置
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/noticeTemplate/json/delete")
//    @RequiresPermissions("sys:config:del")
    public ResultEntity delJson(String ids) throws Exception {
        if (StringUtils.isBlank(ids)) {
            return fail();
        }
        String[] idArr = ids.split(",");
        int insert = 0;
        for (String id : idArr) {
            insert += noticeTemplateService.deleteByPrimaryKey(Integer.valueOf(id));
        }
//        int insert = confService.removeById(new Conf(c -> c.setId(id)));
        return insert == idArr.length ? ok() : fail();
    }

}
