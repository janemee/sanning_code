package com.huimi.admin.controller.tikTok.task;

import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.comment.CommentTemplate;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.comment.CommentTemplateService;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.task.TaskService;
import com.huimi.core.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * create by lja on 2020/7/30 16:42
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class TaskController extends BaseController {
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private EquipmentGroupService equipmentGroupService;
    @Resource
    private CommentTemplateService commentTemplateService;
    @Resource
    private AdminService adminService;
    @Resource
    private TaskService taskService;

    @RequestMapping("task/list")
    public String list() {
        return getAdminTemplate("task/list");
    }

    @RequestMapping("task/superHotList")
    public String superHotList() {
        return getAdminTemplate("task/taskSuperHotList");
    }

    /**
     * 任务的详细内容
     */
    @RequestMapping("task/taskDetails")
    public ModelAndView detailList(String ids) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ids", ids);
        modelAndView.setViewName("task/taskDetails");
        return modelAndView;
    }

    /**
     * 任务的详细内容(超级热度)
     */
    @RequestMapping("task/hotTaskDetails")
    public ModelAndView hotTaskDetails(String ids) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ids", ids);
        modelAndView.setViewName("task/hotTaskDetails");
        return modelAndView;
    }


    @RequestMapping("task/yangHao")
    public ModelAndView yangHao() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        List<EquipmentGroup> equipmentGroups;
        equipmentGroups = equipmentGroupService.findAgentGroup(equipmentList);
        equipmentGroups.removeIf(Objects::isNull);
        List<CommentTemplate> commentTemplate = commentTemplateService.findByOpen();
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.addObject("commentTemplate", commentTemplate);
        modelAndView.setViewName("task/yangHao");
        return modelAndView;
    }

    @RequestMapping("task/privateLetter")
    public ModelAndView privateLetter() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        List<EquipmentGroup> equipmentGroups;
        equipmentGroups = equipmentGroupService.findAgentGroup(equipmentList);
        equipmentGroups.removeIf(Objects::isNull);
        List<CommentTemplate> commentTemplate = commentTemplateService.findByOpen();
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.addObject("commentTemplate", commentTemplate);
        modelAndView.setViewName("task/privateLetter");
        return modelAndView;
    }

    @RequestMapping("task/oneKeyExplosivePowder")
    public ModelAndView oneKeyExplosivePowder() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        List<EquipmentGroup> equipmentGroups;
        Integer adminId = this.findAdmin();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isBlank(admin.getParentId())) {
            equipmentGroups = equipmentGroupService.selectAll();
        } else {
            equipmentGroups = equipmentGroupService.findAgentGroup(equipmentList);
        }
        List<CommentTemplate> commentTemplate = commentTemplateService.findByOpen();
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.addObject("commentTemplate", commentTemplate);
        modelAndView.setViewName("task/oneKeyExplosivePowder");
        return modelAndView;
    }

    /**
     * 跳转添加停止任务页面
     */
    @RequestMapping("task/over")
    public ModelAndView over() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        List<EquipmentGroup> equipmentGroups;
        Integer adminId = this.findAdmin();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isBlank(admin.getParentId())) {
            equipmentGroups = equipmentGroupService.selectAll();
        } else {
            equipmentGroups = equipmentGroupService.findAgentGroup(equipmentList);
            equipmentGroups.removeIf(Objects::isNull);
        }
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/over");
        return modelAndView;
    }

    /**
     * 跳转到对应的添加任务页面
     */
    @RequestMapping("task/taskType")
    public ModelAndView taskType(String taskType) {
        ModelAndView modelAndView = new ModelAndView();
        //话术模板
        List<CommentTemplate> commentTemplate = commentTemplateService.findByOpen();
        //判断是否是一级代理商
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        List<EquipmentGroup> equipmentGroups;
        Integer adminId = this.findAdmin();
        Admin admin = adminService.selectByPrimaryKey(adminId);

        equipmentGroups = equipmentGroupService.findAgentGroup(equipmentList);
        equipmentGroups.removeIf(Objects::isNull);

        //over 类型
        if (EnumConstants.TaskType.OVER.code.equals(taskType)) {
            if (StringUtils.isBlank(admin.getParentId())) {
                equipmentList = equipmentService.selectByState(null);
                equipmentGroups = equipmentGroupService.findAgentGroup(equipmentList);
                equipmentGroups.removeIf(Objects::isNull);

            } else {
                equipmentList = equipmentService.findByAll(admin.getCode());
                equipmentGroups = equipmentGroupService.findAgentGroup(equipmentList);
                equipmentGroups.removeIf(Objects::isNull);
            }
        }
        if("giveLightPlate".equals(taskType)){
            List<Equipment> equipmentList1 = equipmentService.selectByState(1);
            DtGrid dt = equipmentGroupService.findAll();
            List<Object> equipmentGroups1 = dt.getExhibitDatas();
            List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(), EnumConstants.PLAT_FROM_TYPE.TIKTOK.value);
            modelAndView.addObject("liveHot", liveHot);
            modelAndView.addObject("equipmentList", equipmentList1);
            modelAndView.addObject("equipmentGroups", equipmentGroups1);
        }else {
            modelAndView.addObject("equipmentList", equipmentList);
            modelAndView.addObject("equipmentGroups", equipmentGroups);
            modelAndView.addObject("commentTemplate", commentTemplate);
        }
        modelAndView.setViewName("task/" + taskType);
        return modelAndView;
    }

    /**
     * 根据设备分组查看对应的设备情况
     */
    @RequestMapping("task/equipmentToGroups")
    public ModelAndView equipmentSome(String equipmentGroups) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/equipmentToGroups");
        return modelAndView;
    }


    /**
     * 查询用户id
     */

    public Integer findAdmin() {
        Subject subject = SecurityUtils.getSubject();
        String principal = subject.getPrincipal().toString();
        Admin admin = new Admin();
        admin.setUsername(principal);
        Admin resultAdmin = adminService.selectOne(admin);
        return resultAdmin.getId();
    }
}
