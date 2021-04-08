package com.huimi.admin.controller.permission;

import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.admin.controller.BaseController;
import com.huimi.admin.controller.GenericController;
import com.huimi.core.po.system.Role;
import com.huimi.core.service.system.RoleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理员组
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class RoleJsonController extends GenericController <Integer, Role> {


    @Autowired
    RoleService roleService;

    /**
     * 管理员组列表JSON
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/json/list")
//    @RequiresPermissions("sys:role:list")
    public DtGrid listJson(HttpServletRequest request) throws Exception {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 1 : Integer.valueOf(request.getParameter("rows"));
        Integer pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1 : Integer.valueOf(request.getParameter("page"));
        String search_val = request.getParameter("search_val");
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        if (!StringUtils.isBlank(search_val)) {
            whereSql.append(" and (name like '%").append(search_val).append("%')");
        }
        dtGrid.setWhereSql(whereSql.toString());
        return roleService.getDtGridList(dtGrid);
    }

    /**
     * 角色信息保存或更新
     *
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/json/saveOrUpdate")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(Role role) throws Exception {
        int insert = 0;
        if (role.getId() != null) {
            role.setUpdator(SecurityUtils.getSubject().getPrincipals().toString());
            insert = roleService.updateByPrimaryKeySelective(role);
        } else {
            role.setCreator(SecurityUtils.getSubject().getPrincipals().toString());
            insert = roleService.insert(role);
        }
        return insert > 0 ? ok() : fail();
    }

    /**
     * 管理员权限组删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/json/delete")
//    @RequiresPermissions("sys:config:del")
    public ResultEntity delJson(String ids) throws Exception {
        if (StringUtils.isBlank(ids)) {
            return fail();
        }
        String[] idArr = ids.split(",");
        int insert = 0;
        for (String id : idArr) {
            insert += roleService.deleteByPrimaryKey(Integer.valueOf(id));
        }
//        int insert = confService.removeById(new Conf(c -> c.setId(id)));
        return insert == idArr.length ? ok() : fail();
    }

    /**
     * 管理员权限组删除
     *
     * @param id
     * @return
     *//*
    @ResponseBody
    @RequestMapping("/role/json/del")
    @RequiresPermissions("sys:role:del")
    public ResultEntity delJson(@RequestParam int id) throws Exception {
        return roleService.deleteRoleById(id);
    }*/

    /**
     * 获取角色
     *
     * @param oldRoleids
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/json/getZtreeData")
    public Object getZtreeData(String oldRoleids) {
        return roleService.getZtreeData(oldRoleids);
    }

    /**
     * 角色授权
     */
    @ResponseBody
    @RequestMapping("/role/json/doAuthorize")
    public ResultEntity doAuthorize(Integer ids, String menuids) {
        int insert = 0;
        insert = roleService.doAuthorize(menuids, ids.toString());
        return insert > 0 ? ok() : fail();
    }

    /**
     * 刷新权限
     */
    @ResponseBody
    @RequestMapping("/role/json/refreshRights")
    public ResultEntity refreshRights(String roleId) {

        roleService.refreshRights(roleId);
        return ok();
//        renderBooleanJson(true);

    }

}