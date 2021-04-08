//package com.huimi.apis.controller.task;
//
//import com.huimi.apis.controller.WebGenericController;
//import com.huimi.common.entity.ResultEntity;
//import com.huimi.core.constant.EnumConstants;
//import com.huimi.core.exception.BussinessException;
//import com.huimi.core.po.equipment.Equipment;
//import com.huimi.core.service.cache.RedisService;
//import com.huimi.core.service.equipment.EquipmentService;
//import com.huimi.core.service.task.TaskService;
//import com.huimi.core.task.Task;
//import com.huimi.core.task.TaskModel;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.collections.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//
//@RestController
//
//@RequestMapping("/api/v1/hs")
//@Api(tags = "任务需求")
//public class HuoShanTaskController extends WebGenericController<Integer, Task> {
//    private static final Logger log = LoggerFactory.getLogger(DouYinTaskController.class);
//    @Resource
//    private EquipmentService equipmentService;
//    @Resource
//    private TaskService taskService;
//    @Resource
//    private RedisService redisService;
//
//
//
//    /**
//     * 获取直播任务内容 (直播任务)
//     *
//     * @param taskType  任务类型
//     * @param deviceUid 设备唯一识别码
//     */
//    @ApiOperation(value = " 获取任务接口")
//    @RequestMapping("/task")
//    public ResultEntity<List<TaskModel>> task(@RequestParam(value = "task_type", required = false) String taskType,
//                                              @RequestParam(value = "device_id", required = false) String deviceUid) {
//        ResultEntity<List<TaskModel>> resultEntity = new ResultEntity<>();
//        //判断code是否真实
//        Equipment equipment = new Equipment();
//        equipment.setDeviceUid(deviceUid);
//        try {
//            if (equipmentService.select(equipment).size() == 0) {
//                return fail("设备ID有误，请确认后重试");
//            }
//            EnumConstants.TaskType type = checkedPrams(taskType);
//            List<TaskModel> list = taskService.findCodeTask(deviceUid, type,"");
//            resultEntity.setCode(ResultEntity.SUCCESS);
//            resultEntity.setData(list);
//            resultEntity.setMsg("success");
//            resultEntity.setTotal(list.size());
//            return resultEntity;
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return fail(e.getMessage());
//        }
//    }
//
//
//    /**
//     * 获取结束任务 (结束全部)
//     *
//     * @param deviceUid 设备唯一识别码
//     */
//    @ApiOperation(value = " 获取任务接口")
//    @RequestMapping("/overAll")
//    public ResultEntity<List<Object>> task(@RequestParam(value = "device_id", required = false) String deviceUid) {
//        ResultEntity<List<Object>> resultEntity = ok();
//        try {
//            List<Object> list = taskService.findTikTokTask(deviceUid, EnumConstants.TaskType.OVER);
//            resultEntity.setData(list);
//            resultEntity.setMsg("success");
//            resultEntity.setTotal(list.size());
//            return resultEntity;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return fail(e.getMessage());
//        }
//    }
//
//
//    /**
//     * 获取超级热度子任务
//     *
//     * @param liveHotUuid 超级热度任务uuid
//     * @param taskType    任务类型
//     */
//    @ApiOperation(value = "获取超级热度子任务")
//    @RequestMapping("/liveHotSubTask")
//    public ResultEntity<List<Object>> liveHotSubTask(@RequestParam(value = "task_type", required = false) String taskType,
//                                                     @RequestParam(value = "live_hot_uuid", required = false) String liveHotUuid) {
//        ResultEntity<List<Object>> resultEntity = new ResultEntity<>();
//        try {
//            EnumConstants.TaskType type = checkedPrams(taskType);
//            if (type == null) {
//                return fail("任务类型有误，请确认后重试");
//            }
//            List<Object> list = taskService.findLiveHotSubTask(type, liveHotUuid);
//            resultEntity.setCode(ResultEntity.SUCCESS);
//            resultEntity.setData(list);
//            resultEntity.setMsg("success");
//            resultEntity.setTotal(list.size());
//            return resultEntity;
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return fail(e.getMessage());
//        }
//    }
//
//
//    /**
//     * 获取抖音任务内容 (抖音任务)
//     *
//     * @param taskType  任务类型
//     * @param deviceUid 设置唯一标识 不可更改
//     */
//    @ApiOperation(value = " 获取抖音任务接口")
//    @RequestMapping("/tikTokTask")
//    public ResultEntity<List<Object>> tikTokTask(@RequestParam(value = "task_type", required = false) String taskType,
//                                                 @RequestParam(value = "device_id", required = false) String deviceUid) {
//        ResultEntity<List<Object>> resultEntity = new ResultEntity<>();
//        //判断code是否真实
//        Equipment equipment = new Equipment();
//        equipment.setDeviceUid(deviceUid);
//        try {
//            if (CollectionUtils.isEmpty(equipmentService.select(equipment))) {
//                return fail("设备ID有误，请确认后重试");
//            }
//            List<Object> list = taskService.findTikTokTask(deviceUid, checkedPrams(taskType));
//            resultEntity.setCode(ResultEntity.SUCCESS);
//            resultEntity.setData(list);
//            resultEntity.setMsg("success");
//            resultEntity.setTotal(list.size());
//            return resultEntity;
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return fail(e.getMessage());
//        }
//    }
//
//
//    /**
//     * 任务执行回调接口
//     *
//     * @param task_type  任务类型
//     * @param taskId     任务ID
//     * @param taskStatus 任务执行状态
//     * @param deviceUid  设置唯一标识 不可更改
//     */
//    @ApiOperation(value = "任务回调接口")
//    @RequestMapping("/taskNotify")
//    public ResultEntity<List<Object>> taskNotify(
//            @RequestParam(value = "task_type", required = false) String task_type,
//            @RequestParam(value = "task_id", required = false) String taskId,
//            @RequestParam(value = "task_status", required = false) String taskStatus,
//            @RequestParam(value = "device_id", required = false) String deviceUid,
//
//            @RequestParam(value = "comment", required = false) String comment) {
//        try {
//            //判断code是否真实
//            EnumConstants.TaskType type = checkedPrams(task_type);
//            //todo 实时互动 需要删除redis缓存  //任务完成时 删除任务缓存
//            if (EnumConstants.taskStatus.DONE.code.equals(taskStatus)) {
//                String key = task_type + ":" + deviceUid;
//                redisService.del(key);
//            }
//            taskService.taskNotify(deviceUid, type, taskId, taskStatus, comment);
//            return ok(null, "success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            return fail(e.getMessage());
//        }
//    }
//
//
//    /**
//     * 超级热度子任务回调接口
//     *
//     * @param task_type  任务类型
//     * @param taskId     任务ID
//     * @param taskStatus 任务执行状态
//     * @param deviceUid  设置唯一标识 不可更改
//     */
//    @ApiOperation(value = "超级热度子任务回调接口")
//    @RequestMapping("/hotSubTaskNotify")
//    public ResultEntity<Object> hotSubTaskNotify(
//            @RequestParam(value = "task_type", required = false) String task_type,
//            @RequestParam(value = "task_id", required = false) String taskId,
//            @RequestParam(value = "task_status", required = false) String taskStatus,
//            @RequestParam(value = "device_id", required = false) String deviceUid) {
//        try {
//            //判断code是否真实
//            EnumConstants.TaskType type = checkedPrams(task_type);
//            taskService.hotSubTaskNotify(deviceUid, type, taskId, taskStatus);
//            return ok(null, "success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            return fail(e.getMessage());
//        }
//    }
//
//    /**
//     * 设备主动结束任务（客户端主动调用）
//     *
//     * @param deviceUid 设备uid
//     */
//    @ApiOperation(value = "设备主动结束任务")
//    @RequestMapping("/activeOverTask")
//    public ResultEntity<Object> activeOverTask(@RequestParam(value = "device_id", required = false) String deviceUid) {
//        try {
//            taskService.activeOverTask(deviceUid);
//            return ok(null, "success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            return fail(e.getMessage());
//        }
//    }
//
//    /**
//     * 请求参数校验
//     */
//    public EnumConstants.TaskType checkedPrams(String taskType) { //判断code是否真实
//        EnumConstants.TaskType type = EnumConstants.TaskType.getTaskType(taskType);
//        if (type == null) {
//            throw new BussinessException("任务类型有误，请确认后重试");
//        }
//        return type;
//    }
//}
