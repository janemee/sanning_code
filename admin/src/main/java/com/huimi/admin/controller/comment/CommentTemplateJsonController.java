package com.huimi.admin.controller.comment;

import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.DateUtils;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.po.comment.CommentTemplate;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.comment.CommentTemplateService;
import com.huimi.core.service.system.AdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.huimi.common.entity.ResultEntity.fail;

/**
 * create by lja on 2020/7/30 15:24
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class CommentTemplateJsonController extends BaseController {
    @Resource
    private CommentTemplateService commentTemplateService;
    @Resource
    private AdminService adminService;

    @ResponseBody
    @RequestMapping("commentTemplate/json/list")
    @RequiresPermissions(":s:commentTemplate:list")
    public DtGrid listJson(HttpServletRequest request) throws Exception {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 1 : Integer.parseInt(request.getParameter("rows"));
        Integer pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String nid = request.getParameter("nid");
        Integer adminId = AdminSessionHelper.getAdminId();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isNotBlank(admin.getParentId())) {
            whereSql.append(" and ").append(" sys_admin_id = ").append(adminId);
        }
        if (StringUtils.isNotBlank(nid)) {
            whereSql.append(" and nid like '%").append(nid).append("%'");
        }
        String name = request.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            whereSql.append(" and name like '%").append(name).append("%'");
        }
        String type = request.getParameter("type");
        if (StringUtils.isNotBlank(type) && !"99".equals(type)) {
            whereSql.append(" and type like '%").append(type).append("%'");
        }
        String state = request.getParameter("state");
        if (StringUtils.isNotBlank(state) && !"99".equals(state)) {
            whereSql.append(" and state like '%").append(state).append("%'");
        }

        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        return commentTemplateService.getDtGridList(dtGrid);
    }


    /**
     * 添加/编辑话术模板
     */
    @ResponseBody
    @RequestMapping("/commentTemplate/json/saveOrUpdate")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(CommentTemplate commentTemplate) {
        int insert;
        Integer adminId = AdminSessionHelper.getAdminId();
        if (commentTemplate.getId() != null) {
            commentTemplate.setUpdate_time(DateUtils.getNow());
            insert = commentTemplateService.updateByPrimaryKeySelective(commentTemplate);
        } else {
            commentTemplate.setCreateTime(DateUtils.getNow());
            commentTemplate.setSysAdminId(adminId);
            commentTemplate.setDelFlag(GenericPo.DELFLAG.NO.code);
            insert = commentTemplateService.insert(commentTemplate);
        }
        return insert > 0 ? ResultEntity.success() : fail();
    }


    /**
     * 删除话术模板
     */
    @ResponseBody
    @RequestMapping("/commentTemplate/json/del")
//    @RequiresPermissions("sys:config:del")
    public ResultEntity delJson(String ids) throws Exception {
        if (StringUtils.isBlank(ids)) {
            return fail();
        }
        String[] idArr = ids.split(",");
        int insert = 0;
        for (String id : idArr) {
            insert += commentTemplateService.deleteByPrimaryKey(Long.valueOf(id));
        }
        return insert == idArr.length ? ResultEntity.success("删除成功") : fail();
    }
}
