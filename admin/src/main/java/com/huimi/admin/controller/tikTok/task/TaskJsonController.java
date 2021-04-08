package com.huimi.admin.controller.tikTok.task;

import cn.hutool.core.collection.CollectionUtil;
import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.DateUtils;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.task.TaskService;
import com.huimi.core.task.TaskAdminPramsModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.huimi.common.entity.ResultEntity.fail;

/**
 * create by lja on 2020/7/30 16:45
 */
@RestController
@RequestMapping(BaseController.BASE_URI)
public class TaskJsonController extends BaseController {
    @Resource
    private TaskService taskService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private AdminService adminService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskJsonController.class);

    @ResponseBody
    @RequestMapping("task/json/list")
    public DtGrid listJson(HttpServletRequest request, int rows, int page) throws Exception {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String nid = request.getParameter("search_val");
        if (StringUtils.isNotBlank(nid)) {
            whereSql.append(" and task_name  like '%").append(nid).append("%'");
        }
        whereSql.append(" and task_type  !=   '").append(EnumConstants.TaskType.LIVE_HOT.code).append("' ");
        whereSql.append(" and platform  =   '").append(EnumConstants.PLAT_FROM_TYPE.TIKTOK.value).append("' ");
        Integer adminId = AdminSessionHelper.getAdminId();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        //判断是否为代理商用户
        if (StringUtils.isNotBlank(admin.getParentId())) {
            //  找到用户的激活码
            String code = admin.getCode();
            //找到所有主任务的id
            whereSql.append("and sys_admin_id = ").append(adminId).append(" ");
            List<Integer> taskList = taskService.FindByAgent(code);
            whereSql.append("and id in(");
            if (taskList.size() != 0) {
                for (int i = 0; i < taskList.size(); i++) {
                    if (i == taskList.size() - 1) {
                        whereSql.append(taskList.get(i)).append(")");
                    } else {
                        whereSql.append(taskList.get(i)).append(",");
                    }
                }
            } else {
                return null;
            }

        }
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        return taskService.getDtGridList(dtGrid);
    }

    @ResponseBody
    @RequestMapping("task/json/superHotList")
    public DtGrid superHotList(HttpServletRequest request, int rows, int page) throws Exception {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String nid = request.getParameter("search_val");
        if (StringUtils.isNotBlank(nid)) {
            whereSql.append(" and task_name  like '%").append(nid).append("%'");
        }
        whereSql.append(" and task_type = '").append(EnumConstants.TaskType.LIVE_HOT.code).append("'");
        whereSql.append(" and platform  =   '").append(EnumConstants.PLAT_FROM_TYPE.TIKTOK.value).append("' ");
        Integer adminId = AdminSessionHelper.getAdminId();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        //判断是否为代理商用户
        if (StringUtils.isNotBlank(admin.getParentId())) {
            //  找到用户的激活码
            String code = admin.getCode();
            //找到所有主任务的id
            List<Integer> taskList = taskService.FindByAgent(code);
            whereSql.append("and sys_admin_id = ").append(adminId).append(" ");
            whereSql.append("and id in(");
            if (taskList.size() != 0) {
                for (int i = 0; i < taskList.size(); i++) {
                    if (i == taskList.size() - 1) {
                        whereSql.append(taskList.get(i)).append(")");
                    } else {
                        whereSql.append(taskList.get(i)).append(",");
                    }
                }
            } else {
                return null;
            }

        }
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        return taskService.getDtGridList(dtGrid);
    }

    @ResponseBody
    @RequestMapping("task/json/list/detail")
    public DtGrid listJsonDetail(HttpServletRequest request, String ids, int rows, int page) {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String nid = request.getParameter("nid");
        whereSql.append("task.id = ").append(ids);
        if (StringUtils.isNotBlank(nid)) {
            whereSql.append(" and nid like '%").append(nid).append("%'");
        }
        String name = request.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            whereSql.append(" and name like '%").append(name).append("%'");
        }
        String type = request.getParameter("type");
        if (StringUtils.isNotBlank(type) && !"99".equals(type)) {
            whereSql.append(" and type like '%").append(type).append("%'");
        }
        String state = request.getParameter("state");
        if (StringUtils.isNotBlank(state) && !"99".equals(state)) {
            whereSql.append(" and state like '%").append(state).append("%'");
        }

        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        return taskService.findOneDetailed(dtGrid);
    }

    /**
     * 添加任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @RequestMapping(value = "/task/json/add")
    public ResultEntity addTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            checkedPrams(taskAdminPramsModel);
            taskAdminPramsModel.setAdmin(AdminSessionHelper.getCurrAdmin());
            //获取设备列表
            ArrayList<Equipment> allEquipments = equipmentService.getEquipmentList(taskAdminPramsModel);
            if (CollectionUtil.isEmpty(allEquipments)) {
                return fail("未选择设备或选择的设备不存在，请选择设备");
            }
            ArrayList<Equipment> finalAllEquipments = allEquipments;
            new Thread(() -> taskService.addTask(finalAllEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel, AdminSessionHelper.getAdminId())).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    /**
     * 删除执行完的任务
     */
    @RequestMapping(value = "/task/json/delete")
    @RequiresPermissions("s:task:json:delete")
    public ResultEntity deleteTask() {
        taskService.findCloseTask();
        return ResultEntity.success("删除成功");
    }

    /**
     * 查找分组下的设备详情
     */
    @ResponseBody
    @RequestMapping(value = "task/json/getEquipmentsByGroups")
    public DtGrid getByTaskId(HttpServletRequest request, String ids, int rows, int page) {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String nid = request.getParameter("search_val");
        whereSql.append(" and t.state = 1 ");
        if (StringUtils.isNotBlank(ids)) {
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                if (i == 0) {
                    whereSql.append(" and t.group_id = ").append(split[i]);
                } else {
                    whereSql.append(" or t.group_id = ").append(split[i]);
                }
            }
        }
        if (StringUtils.isNotBlank(nid)) {
            whereSql.append(" and t.name like '%").append(nid).append("%'").append(" or t.device_code like '%").append(nid).append("%'");
        }
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        return equipmentService.findSysAdminGroup(dtGrid);

    }


    /**
     * 添加任务参数校验
     */
    public void checkedPrams(TaskAdminPramsModel taskAdminPramsModel) {
        EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
        if(taskType == null){
            throw new  BussinessException("任务类型有误，请检查后重试");
        }
        taskAdminPramsModel.setETaskType(taskType);
        //是否属于超级热度下--选择设备或分组
        if (taskType.taskType == 1) {
            if (StringUtil.isBlank(taskAdminPramsModel.getDevicesOrGroupsId())) {
                throw new BussinessException("请选择设备或分组后重试");
            }
        } else {
            if (taskAdminPramsModel.getEquipments() == null && taskAdminPramsModel.getEquipmentGroups() == null) {
                throw new BussinessException("请选择设备或分组后重试");
            }
        }

        //设备升级
        if (taskType.equals(EnumConstants.TaskType.EQUIPMENT_UPGRADE)) {
            if (StringUtil.isBlank(EnumConstants.EquipmentUpgradeType.getCode(taskAdminPramsModel.getApkUpgrade()))) {
                throw new BussinessException("请选择更新设备的方式");
            }
        }

        //定时开始时间不能小于当前时间
        if (StringUtil.isNotBlank(taskAdminPramsModel.getTaskStartTime())) {
            Date date = DateUtils.getDateByFullDateStr(taskAdminPramsModel.getTaskStartTime());
            assert date != null;
            if (date.before(new Date())) {
                throw new BussinessException("定时日期开始不能小于当前时间");
            }
            taskAdminPramsModel.setTaskStartDateTime(date);
        }

    }


}

