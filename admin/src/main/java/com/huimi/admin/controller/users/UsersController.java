package com.huimi.admin.controller.users;


import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping(BaseController.BASE_URI + "/users")
public class UsersController extends BaseController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentGroupService equipmentGroupService;
    @Autowired
    private UsersService usersService;

    @Resource
    private AdminService adminService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
        Integer adminId = AdminSessionHelper.getAdminId();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        int free = 0;
        int busy = 0;
        if (StringUtils.isNotBlank(admin.getParentId())) {
            free = equipmentService.findWorkNumber(1, adminId);
            busy = equipmentService.findWorkNumber(0, adminId);
        } else {
            free = equipmentService.findWorkNumber(1, null);
            busy = equipmentService.findWorkNumber(0, null);
        }
        modelAndView.addObject("busy", busy);
        modelAndView.addObject("free", free);
        modelAndView.setViewName("users/list");
        return modelAndView;
    }


    /**
     * 添加用户
     */
    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/add");
        return modelAndView;
    }

    /**
     * 编辑
     *
     * @param ids
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView update(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", usersService.selectByPrimaryKey(ids));
        modelAndView.setViewName("users/update");
        return modelAndView;
    }

    /**
     * 批量分组
     */
    @RequestMapping("/batch")
    public ModelAndView batch(String ids) {
        //获取ids 传入到下一个页面
        ModelAndView modelAndView = new ModelAndView();
        Integer adminId = AdminSessionHelper.getAdminId();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isNotBlank(admin.getParentId())) {
            List<EquipmentGroup> exhibitDatas = equipmentGroupService.findAgentGroup(EnumConstants.HistoryState.YES.value, adminId);
            modelAndView.addObject("exhibitDatas", exhibitDatas);
        } else {
            DtGrid dtGrid = equipmentGroupService.findAll();
            List<Object> exhibitDatas = dtGrid.getExhibitDatas();
            modelAndView.addObject("exhibitDatas", exhibitDatas);
        }
        modelAndView.addObject("ids", ids);
        modelAndView.setViewName("users/batch");
        return modelAndView;
    }

    /**
     * 设备下具体在线任务
     */
    @RequestMapping("/eqTask")
    public ModelAndView task(String ids) {
        //获取ids 传入到下一个页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ids", ids);
        modelAndView.setViewName("users/eqTask");
        return modelAndView;
    }
}
