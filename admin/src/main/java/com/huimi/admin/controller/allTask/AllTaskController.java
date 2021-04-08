package com.huimi.admin.controller.allTask;

import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
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
 * 任务跳转页面控制器Ø
 */
@Controller
@RequestMapping(BaseController.BASE_URI + "/all")
public class AllTaskController extends BaseController {
    @Resource
    private TaskService taskService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private EquipmentGroupService equipmentGroupService;
    @Resource
    private CommentTemplateService commentTemplateService;

    /**
     * 小视频任务-任务列表
     *
     * @return
     */
    @RequestMapping("task/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("platformList", EnumConstants.PLAT_FROM_TYPE.getEnumList());
        modelAndView.addObject("taskTypeList", EnumConstants.TaskType.getEnumList(EnumConstants.TaskType.OVER.taskType));
        modelAndView.setViewName("allTask/list");
        return modelAndView;
    }

    /**
     * 超级热度任务列表
     *
     * @return
     */
    @RequestMapping("task/superHotList")
    public ModelAndView superHotList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("platformList", EnumConstants.PLAT_FROM_TYPE.getEnumList());
        modelAndView.addObject("taskTypeList", EnumConstants.TaskType.getEnumList(EnumConstants.TaskType.OVER.taskType));
        modelAndView.addObject("taskTypeList", EnumConstants.TaskRunCode.getEnumList());
        modelAndView.setViewName("allTask/taskSuperHotList");
        return modelAndView;
    }

    /**
     * 任务的详细内容
     */
    @RequestMapping("task/taskDetails")
    public ModelAndView detailList(String ids) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ids", ids);
        modelAndView.addObject("platformList", EnumConstants.PLAT_FROM_TYPE.getEnumList());
        modelAndView.setViewName("allTask/taskDetails");
        return modelAndView;
    }

    /**
     * 任务的详细内容(超级热度)
     */
    @RequestMapping("task/hotTaskDetails")
    public ModelAndView hotTaskDetails(String ids) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ids", ids);
        modelAndView.addObject("platformList", EnumConstants.PLAT_FROM_TYPE.getEnumList());
        modelAndView.setViewName("allTask/hotTaskDetails");
        return modelAndView;
    }

    /**
     * 超级热度子任务列表
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
        modelAndView.addObject("platformList", EnumConstants.PLAT_FROM_TYPE.getEnumList());
        modelAndView.addObject("taskId", ids);
        modelAndView.setViewName("allTask/subHotTaskList");
        return modelAndView;
    }

    /**
     * 跳转到超级热度下的设备页面
     */
    @RequestMapping("task/equipmentSome")
    public ModelAndView equipmentSome(String taskId) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("taskId", taskId);
        modelAndView.addObject("platformList", EnumConstants.PLAT_FROM_TYPE.getEnumList());
        modelAndView.setViewName("allTask/equipmentSome");
        return modelAndView;
    }


    /**
     * 跳转到超级热度下的分组页面
     */
    @RequestMapping("task/equipmentGroupSome")
    public ModelAndView equipmentGroupSome(String taskId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("platformList", EnumConstants.PLAT_FROM_TYPE.getEnumList());
        modelAndView.addObject("taskId", taskId);
        modelAndView.setViewName("allTask/equipmentGroupSome");
        return modelAndView;
    }


    @RequestMapping("task/yangHao")
    public ModelAndView yangHao(String platform) {
        ModelAndView modelAndView = new ModelAndView();
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        List<EquipmentGroup> equipmentGroups;
        equipmentGroups = equipmentGroupService.findAgentGroup(equipmentList);
        equipmentGroups.removeIf(Objects::isNull);
        List<CommentTemplate> commentTemplate = commentTemplateService.findByOpen();
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.addObject("commentTemplate", commentTemplate);
        modelAndView.addObject("platformList", EnumConstants.PLAT_FROM_TYPE.getEnumList());
        modelAndView.addObject("platform", platform);
        modelAndView.setViewName("allTask/yangHao");
        return modelAndView;
    }




    /**
     * 跳转到对应的添加任务页面
     */
    @RequestMapping("task/taskType")
    public ModelAndView taskType(String taskType, String platform) {
        return handlePram(taskType, platform);
    }

    /**
     * 页面跳转参数处理
     *
     * @param taskType 任务类型
     * @param platform 平台类型
     * @return
     */
    public ModelAndView handlePram(String taskType, String platform) {
        ModelAndView modelAndView = new ModelAndView();
        //接收任务类型
        EnumConstants.TaskType taskType1 = EnumConstants.TaskType.getTaskType(taskType);
        //解析平台类型
        EnumConstants.PLAT_FROM_TYPE platFromType = EnumConstants.PLAT_FROM_TYPE.getEnumCodeOrValue(platform);
        //话术模板
        List<CommentTemplate> commentTemplate = commentTemplateService.findByOpen();
        //用户拥有设备
        List<Equipment> equipmentList = equipmentService.selectByState(1);
        //设备分组
        List<EquipmentGroup> equipmentGroups = equipmentGroupService.findAgentGroup(equipmentList);
        //超级热度任务
        List<Task> liveHot = new ArrayList<>();

        if (StringUtils.isBlank(AdminSessionHelper.getCurrAdmin().getParentId())) {
            equipmentGroups = equipmentGroupService.selectAll();
        }

        //直播任务
        if (taskType1 != null && taskType1.taskType != EnumConstants.TaskType.OVER.taskType && !"superHeat".equals(taskType)) {
            //1.根据平台查询超级热度任务
            liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(), platform);

        }
        //直播任务 停止子任务
        if ("overSubTask".equals(taskType)) {
            //直播任务
            //1.根据平台查询超级热度任务
            liveHot = taskService.findLiveHeart(AdminSessionHelper.getAdminId(), platform);
        }

        String htmlUrl = "";
        if (EnumConstants.PLAT_FROM_TYPE.TIKTOK == platFromType) {
            htmlUrl = "allTask";
        }
        if (EnumConstants.PLAT_FROM_TYPE.KUAISHOU == platFromType) {
            htmlUrl = "kuaiShou/task";
        }
        //todo 其他平台
        htmlUrl += "/" + taskType;

        equipmentGroups.removeIf(Objects::isNull);
        modelAndView.addObject("liveHot", liveHot);
        modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("equipmentGroups", equipmentGroups);
        modelAndView.addObject("commentTemplate", commentTemplate);
        modelAndView.addObject("platformList", EnumConstants.PLAT_FROM_TYPE.getEnumList());
        modelAndView.addObject("platform", platform);
        //3.根据平台跳转对应的页面
        modelAndView.setViewName(htmlUrl);
        return modelAndView;
    }
}
