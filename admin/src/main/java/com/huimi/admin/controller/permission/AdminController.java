package com.huimi.admin.controller.permission;

import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.Role;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台管理员
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class AdminController extends BaseController {

    @Autowired
    RoleService roleService;
    @Autowired
    AdminService adminService;
    @Resource
    private EquipmentService equipmentService;

    /**
     * 管理员列表
     */
    @RequestMapping("admin/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
        List<Role> list = roleService.selectAll();
        modelAndView.addObject("roleList", list);
        Admin admin = adminService.selectByPrimaryKey(AdminSessionHelper.getAdminId());
        //  管理员页面展示数据不一样
        if (StringUtils.isBlank(admin.getParentId())) {
            modelAndView.addObject("maxEquipment", equipmentService.selectAll().size());
            modelAndView.addObject("freeEquipment", "您是管理员");
        } else {
            modelAndView.addObject("maxEquipment", admin.getPorts());
            //查找一级代理商的空闲设备数
            Integer subEquipmentNum = adminService.freeEquipmentNum(admin);
            modelAndView.addObject("freeEquipment", subEquipmentNum);
        }
        modelAndView.setViewName("system/admin/list");
        return modelAndView;
    }

    /**
     * 编辑
     */

    @RequestMapping("admin/edit")
    public String edit(ModelMap modelMap, String ids) {
        Integer id = Integer.parseInt(ids);
        Admin admin = adminService.findById(id);
        modelMap.put("adminInfo", admin);
        return getAdminTemplate("system/admin/update");
    }

    /**
     * 用户添加
     */
    @RequestMapping("admin/add")
    public String add() {
        return getAdminTemplate("system/admin/add");
    }

    /**
     * 给用户分配角色
     * 2015年12月4日 下午5:43:27
     */
    @RequestMapping("admin/toZtreeDataPage")
    public String toZtreeDataPage(ModelMap map, Integer ids) {
        Admin administrator = adminService.findById(ids);
        map.put("admin", administrator);
        return getAdminTemplate("system/admin/zTreeRole");
    }


    /**
     * 修改端口
     */
    @RequestMapping("system/admin/assignEquipment")
    public String modifyPwd(ModelMap modelMap, String ids) {
        Integer id = Integer.parseInt(ids);
        Admin admin = adminService.findById(id);
        modelMap.put("adminInfo", admin);
        return getAdminTemplate("system/admin/assignEquipment");
    }

    /**
     * 修改密码
     */
    @RequestMapping("system/admin/modifyPwd")
    public String modifyPwd() {
        return getAdminTemplate("system/admin/modifyPwd");
    }
}