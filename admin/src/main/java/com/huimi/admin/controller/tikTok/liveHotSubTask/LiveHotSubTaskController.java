package com.huimi.admin.controller.tikTok.liveHotSubTask;

import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.comment.CommentTemplate;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.service.comment.CommentTemplateService;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.task.TaskService;
import com.huimi.core.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 超级热度子任务跳转页面控制类
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class LiveHotSubTaskController extends BaseController {
    @Resource
    private TaskService taskService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private EquipmentGroupService equipmentGroupService;
    @Resource
    private CommentTemplateService commentTemplateService;

    //平台类型
    private String  platform = EnumConstants.PLAT_FROM_TYPE.TIKTOK.value;
    /**
     * 超级热度任务列表
     */
    @RequestMapping("task/subHotTaskList")
    public ModelAndView list(String ids) {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<CommentTemplate> commentTemplate = commentTemplateService.findByOpen();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.addObject("commentTemplate", commentTemplate);
        modelAndView.addObject("taskId", ids);
        modelAndView.setViewName("task/subHotTaskList");
        return modelAndView;
    }

    /**
     * 超级热度任务添加页面
     */
    @RequestMapping("task/superHeat")
    public ModelAndView superHeat() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        List<EquipmentGroup> equipmentGroups;
        equipmentGroups = equipmentGroupService.findAgentGroup(equipmentList);
        equipmentGroups.removeIf(Objects::isNull);
        List<CommentTemplate> commentTemplate = commentTemplateService.findByOpen();
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.addObject("commentTemplate", commentTemplate);
        modelAndView.setViewName("task/superHeat");
        return modelAndView;
    }

    @RequestMapping(value = "task/interaction")
    public ModelAndView interaction(String[] taskContent) {
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        if (taskContent != null) {
            for (String s : taskContent) {
                Equipment equipment = equipmentService.selectByPrimaryKey(Integer.parseInt(s));
                equipmentList.add(equipment);
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(),platform);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.setViewName("task/interaction");
        return modelAndView;
    }

    @RequestMapping("task/fans")
    public ModelAndView fans() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(),platform);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/fans");
        return modelAndView;
    }

    /**
     * 关注打榜
     */
    @RequestMapping("task/makeList")
    public ModelAndView makerList() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(),platform);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/makeList");
        return modelAndView;
    }

    @RequestMapping("task/redEnvelope")
    public ModelAndView redEnvelope() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(),platform);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/redEnvelope");
        return modelAndView;
    }

    @RequestMapping("task/lookShopping")
    public ModelAndView lookShopping() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(),platform);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/lookShopping");
        return modelAndView;
    }

    @RequestMapping("task/giveGift")
    public ModelAndView giveGift() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(),platform);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/giveGift");
        return modelAndView;
    }

    @RequestMapping("task/fansGroup")
    public ModelAndView fansGroup() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(),platform);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/fansGroup");
        return modelAndView;
    }

    @RequestMapping("task/followPk")
    public ModelAndView followPk() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(),platform);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/followPk");
        return modelAndView;
    }


    @RequestMapping("task/insaneClick")
    public ModelAndView insaneClick() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(),platform);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/insaneClick");
        return modelAndView;
    }

    @RequestMapping("task/follow")
    public ModelAndView follow() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(),platform);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/follow");
        return modelAndView;
    }

    @RequestMapping("task/grabFuBag")
    public ModelAndView grabFuBag() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        List<Task> liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(),platform);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/grabFuBag");
        return modelAndView;
    }

    /**
     * 停止任务(超级热度下的子任务)
     */
    @RequestMapping("task/overSubTask")
    public ModelAndView overSubTask() {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        DtGrid dt = equipmentGroupService.findAll();
        List<Object> equipmentGroups = dt.getExhibitDatas();
        List<Task> liveHot = taskService.findLiveTrobleHeart(AdminSessionHelper.getAdminId());
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.setViewName("task/overSubTask");
        return modelAndView;
    }

    /**
     * 跳转到超级热度下的设备和分组页面
     */
    @RequestMapping("task/equipmentSome")
    public ModelAndView equipmentSome(String taskId) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("taskId", taskId);
        modelAndView.setViewName("task/equipmentSome");
        return modelAndView;
    }


    /**
     * 跳转到超级热度下的设备和分组页面
     */
    @RequestMapping("task/equipmentGroupSome")
    public ModelAndView equipmentGroupSome(String taskId) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("taskId", taskId);
        modelAndView.setViewName("task/equipmentGroupSome");
        return modelAndView;
    }
}
