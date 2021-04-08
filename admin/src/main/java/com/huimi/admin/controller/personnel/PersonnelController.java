package com.huimi.admin.controller.personnel;


import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.po.personnel.Personnel;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.personnel.PersonnelService;
import com.huimi.core.service.system.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 人员信息管理
 */
@Controller
@RequestMapping(BaseController.BASE_URI + "/personnel")
public class PersonnelController extends BaseController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentGroupService equipmentGroupService;

    @Resource
    private AdminService adminService;
    @Resource
    private PersonnelService personnelService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personnel/list");
        return modelAndView;
    }


    /**
     * 添加用户
     */
    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView();
        Personnel personnel = new Personnel();
        //默认图片
        personnel.setPicUrl("/ui/img/timg.jpeg");
        modelAndView.addObject("personnel", personnel);
        modelAndView.setViewName("personnel/add");
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
        modelAndView.addObject("personnel", personnelService.selectByPrimaryKey(ids));
        modelAndView.setViewName("personnel/update");
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
        modelAndView.setViewName("personnel/batch");
        return modelAndView;
    }

    /**
     * 设备授权
     */
    @RequestMapping("/role")
    public ModelAndView role(Integer ids) {
        //获取ids 传入到下一个页面
        ModelAndView modelAndView = new ModelAndView();
        //获取分组
        modelAndView.addObject("equipmentGroupList", equipmentGroupService.selectAll());
        //获取所有设备
        modelAndView.addObject("equipmentList", equipmentService.selectAll());
        //获取人员信息
        modelAndView.addObject("personnel", personnelService.selectByPrimaryKey(ids));
        modelAndView.setViewName("personnel/role");
        return modelAndView;
    }
}
