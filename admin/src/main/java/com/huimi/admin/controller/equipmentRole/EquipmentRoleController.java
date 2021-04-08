package com.huimi.admin.controller.equipmentRole;


import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.user.Users;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.equipmentLog.EquipmentLogService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping(BaseController.BASE_URI)
public class EquipmentRoleController extends BaseController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentGroupService equipmentGroupService;
    @Autowired
    private UsersService usersService;

    @Resource
    private AdminService adminService;

    @Resource
    private EquipmentLogService equipmentLogService;

    @RequestMapping("equipmentRole/list")
    public ModelAndView list(String ids) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("personnelId", ids);
        modelAndView.setViewName("equipmentRole/list");
        return modelAndView;
    }


    /**
     * 设备添加
     */
    @RequestMapping("equipmentRole/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView();
        DtGrid all = equipmentGroupService.findAll();
        List<Object> exhibitDatas = all.getExhibitDatas();
        //获取所有用户
        List<Users> usersList = usersService.selectAll();
        Equipment equipment = new Equipment();
        equipment.setGroupId(1);
        modelAndView.addObject("equipment", equipment);
        modelAndView.addObject("exhibitDatas", exhibitDatas);
        modelAndView.addObject("usersList", usersList);
        modelAndView.setViewName("equipmentRole/add");
        return modelAndView;
    }

    /**
     * 编辑
     *
     * @param ids
     * @return
     */
    @RequestMapping("equipmentRole/edit")
    public ModelAndView update(Integer ids) {
        Equipment equipment = equipmentService.selectByPrimaryKey(ids);
        //获取所有用户
        List<Users> usersList = usersService.selectAll();
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
        modelAndView.addObject("usersList", usersList);
        modelAndView.addObject("equipment", equipment);
        modelAndView.setViewName("equipmentRole/update");
        return modelAndView;
    }

    /**
     * 批量分组
     */
    @RequestMapping("equipmentRole/batch")
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
        modelAndView.setViewName("equipmentRole/batch");
        return modelAndView;
    }

    /**
     * 设备下具体在线任务
     */
    @RequestMapping("equipmentRole/eqTask")
    public ModelAndView task(String ids) {
        //获取ids 传入到下一个页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ids", ids);
        modelAndView.setViewName("equipmentRole/eqTask");
        return modelAndView;
    }
}
