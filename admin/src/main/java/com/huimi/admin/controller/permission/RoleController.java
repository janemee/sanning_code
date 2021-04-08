package com.huimi.admin.controller.permission;

import com.alibaba.fastjson.JSON;
import com.huimi.common.entity.ZTreeNode;
import com.huimi.common.utils.StringUtils;
import com.huimi.admin.controller.BaseController;
import com.huimi.core.po.system.Role;
import com.huimi.core.service.system.MenuService;
import com.huimi.core.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 后台管理员组
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class RoleController extends BaseController {


    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    /**
     * 管理员列表
     */
    @RequestMapping("/role/list")
    public String list() {
        return getAdminTemplate("system/role/list");
    }

    /**
     * 管理员组添加
     */
    @RequestMapping("/role/add")
    public String add(ModelMap modelMap) {
//        List<Menu> list = menuService.selectAll();
//        modelMap.addAttribute("menuList", list);
        return getAdminTemplate("system/role/add");
    }

    /**
     * 管理员组编辑
     */
    @RequestMapping("/role/edit")
    public ModelAndView edit(Integer ids) {
        Role role = roleService.findById(ids.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(role);
        modelAndView.setViewName("system/role/update");
        return modelAndView;
    }

    /**
     * 授权页面
     */
    @RequestMapping("/role/authorize")
    public ModelAndView authorize(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        Role role = roleService.findById(ids.toString());
//        //查询该角色已经选中的菜单id集合
//        String [] menuIds = role.getMenuIds().split(",");

        String[] menuIds = new String[]{};
        if (!StringUtils.isBlank(role.getMenuIds())) {
            menuIds = role.getMenuIds().split(",");
        }
        List <ZTreeNode> list = roleService.getZTreeData(menuIds);
        modelAndView.addObject("role", role);
        modelAndView.addObject("zNodes", JSON.toJSON(list));
        modelAndView.setViewName("system/role/zTree_menu");
        return modelAndView;
    }

}