package com.huimi.admin.controller.equipment;


import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.system.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


/**
 * create by lja on 2020/7/28 17:58
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class EquipmentController extends BaseController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentGroupService equipmentGroupService;

    @Resource
    private AdminService adminService;

    @RequestMapping("equipment/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
//        Integer adminId = AdminSessionHelper.getAdminId();
//        Admin admin = adminService.selectByPrimaryKey(adminId);
//        HashMap data ;
//        if (StringUtils.isNotBlank(admin.getParentId())) {
//            data = equipmentService.findWorkNumber(adminId);
//        } else {
//            data = equipmentService.findWorkNumber(null);
//        }
//        modelAndView.addObject("busy", data.get("busy"));
//        modelAndView.addObject("free", data.get("free"));
//        modelAndView.addObject("count", data.get("count"));
        modelAndView.setViewName("equipment/list");
        return modelAndView;
    }


    /**
     * 设备添加
     */
    @RequestMapping("equipment/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView();
        DtGrid all = equipmentGroupService.findAll();
        List<Object> exhibitDatas = all.getExhibitDatas();
        modelAndView.addObject("exhibitDatas", exhibitDatas);
        modelAndView.setViewName("equipment/add");
        return modelAndView;
    }

    /**
     * 编辑
     *
     * @param ids
     * @return
     */
    @RequestMapping("equipment/edit")
    public ModelAndView update(Integer ids) {
        Equipment equipment = equipmentService.selectByPrimaryKey(ids);
        ModelAndView modelAndView = new ModelAndView();
        Integer adminId = AdminSessionHelper.getAdminId();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isNotBlank(admin.getParentId())){
            List<EquipmentGroup> exhibitDatas = equipmentGroupService.findAgentGroup(EnumConstants.HistoryState.YES.value, adminId);
            modelAndView.addObject("exhibitDatas", exhibitDatas);
        }else {
            DtGrid dtGrid = equipmentGroupService.findAll();
            List<Object> exhibitDatas = dtGrid.getExhibitDatas();
            modelAndView.addObject("exhibitDatas", exhibitDatas);
        }
        modelAndView.addObject("equipment", equipment);
        modelAndView.setViewName("equipment/update");
        return modelAndView;
    }

    /**
     * 批量分组
     */
    @RequestMapping("equipment/batch")
    public ModelAndView batch(String ids) {
        //获取ids 传入到下一个页面
        ModelAndView modelAndView = new ModelAndView();
        Integer adminId = AdminSessionHelper.getAdminId();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isNotBlank(admin.getParentId())){
            List<EquipmentGroup> exhibitDatas = equipmentGroupService.findAgentGroup(EnumConstants.HistoryState.YES.value, adminId);
            modelAndView.addObject("exhibitDatas", exhibitDatas);
        }else {
            DtGrid dtGrid = equipmentGroupService.findAll();
            List<Object> exhibitDatas = dtGrid.getExhibitDatas();
            modelAndView.addObject("exhibitDatas", exhibitDatas);
        }
        modelAndView.addObject("ids", ids);
        modelAndView.setViewName("equipment/batch");
        return modelAndView;
    }

    /**
     * 设备下具体在线任务
     */
    @RequestMapping("equipment/eqTask")
    public ModelAndView task(String ids) {
        //获取ids 传入到下一个页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ids", ids);
        modelAndView.setViewName("equipment/eqTask");
        return modelAndView;
    }
}
