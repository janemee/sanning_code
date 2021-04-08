package com.huimi.admin.controller.equipment;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * create by lja on 2020/7/30 11:28
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class EquipmentGroupController extends BaseController {
    @Autowired
    private EquipmentGroupService equipmentGroupService;
    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping("equipmentGroup/list")
    public String list() {
        return "equipmentGroup/list";
    }


    /**
     * 设备添加
     */
    @RequestMapping("equipmentGroup/add")
    public String add() {
        return "equipmentGroup/add";
    }


    /**
     * 编辑
     *
     * @param ids
     * @return
     */
    @RequestMapping("equipmentGroup/edit")
    public ModelAndView update(Integer ids) {
        EquipmentGroup equipmentGroup = equipmentGroupService.selectByPrimaryKey(ids);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("equipmentGroup", equipmentGroup);
        modelAndView.setViewName("equipmentGroup/update");
        return modelAndView;
    }

    /**
     * 查看分组下的设备
     *
     * @param ids
     * @return
     */
    @RequestMapping("equipmentGroup/equipmentList")
    public ModelAndView equipmentList(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("groupId", ids);
//        HashMap hashMap = equipmentService.findWorkNumberByGroup(ids);
//        modelAndView.addObject("busy", hashMap.get("busy"));
//        modelAndView.addObject("free", hashMap.get("free"));
//        modelAndView.addObject("count", hashMap.get("count"));
        modelAndView.setViewName("equipmentGroup/equipmentList");
        return modelAndView;
    }
}
