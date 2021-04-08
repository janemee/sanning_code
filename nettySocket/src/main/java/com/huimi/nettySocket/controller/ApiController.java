package com.huimi.nettySocket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.tools.StringUtil;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.task.TaskService;
import com.huimi.core.task.TaskModel;
import com.huimi.nettySocket.config.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("All")
@RestController
@RequestMapping("/netty")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Resource
    private RedisService redisService;

    /**
     * 获取任务(超级热度、自动养号)，子任务(实时互动等)
     */
    @PostMapping(value = "/socket/task")
    public String task(String body) {
        String resJson = "";
        if (StringUtil.isBlank(body)) {
            resJson = JSON.toJSONString(ResultEntity.fail("request body can not be blank."));
        }
        JSONObject object = JSON.parseObject(body);
        String option = object.getString("option");
        String deviceId = object.getString("device_id");
        String data = object.getString("data");
        String platform = object.getString("platform");
        //区分平台 默认抖音
        if (StringUtil.isNotBlank(platform) && null != (EnumConstants.PLAT_FROM_TYPE.getEnumCodeOrValue(platform))) {
            platform = EnumConstants.PLAT_FROM_TYPE.getEnumCodeOrValue(platform).value;
        } else {
            platform = EnumConstants.PLAT_FROM_TYPE.TIKTOK.value;
        }
        JSONObject deviceData = JSON.parseObject(data);
        try {
            if (option.equalsIgnoreCase("online")) {
                String deviceCode = deviceData.getString("device_code");
                String invitationCode = deviceData.getString("invitation_code");
                String app_secret = deviceData.getString("app_secret");

                EquipmentService equipmentService = SpringContext.getBean(EquipmentService.class);
                try {
                    equipmentService.selectByUid(deviceId, deviceCode, invitationCode, "");
                    resJson = JSON.toJSONString(ResultEntity.success("Device ID:" + deviceId + ", Connect Success"));
                } catch (Exception e) {
                    throw new BussinessException("Register Failed");
                }
                //获取未完成的任务 暂时只查询超级热度
                TaskService taskService = SpringContext.getBean(TaskService.class);
                List<TaskModel> list = taskService.findCodeTask(deviceId, EnumConstants.TaskType.LIVE_HOT, platform);
                if (null == list || list.size() == 0) {
                    //如果没有获取到超级热度则获取自动养号任务
                    list = taskService.findCodeTask(deviceId, EnumConstants.TaskType.AUTO, platform);
                }
                if (null != list && list.size() != 0) {
                    TaskModel taskModel = list.get(0);
                    Map<String, Object> map = new HashMap<>();
                    map.put("option", "task");
                    map.put("task_type", taskModel.getTask_type());
                    map.put("task_data", taskModel.getTask_data());
                    map.put("task_id", taskModel.getTask_id());
                    map.put("msg", "This device had a unfinished task,please keep executing!");
                    resJson = JSON.toJSONString(map);
                }
            } else if (option.equalsIgnoreCase("taskNotify")) {
                String taskType = deviceData.getString("task_type");
                String taskId = deviceData.getString("task_id");
                String taskStatus = deviceData.getString("task_status");
                EnumConstants.TaskType type = EnumConstants.TaskType.getTaskType(taskType);
                if (type == null) {
                    resJson = JSON.toJSONString(ResultEntity.fail("Device id:" + deviceId + ". task type error, please confirm and retry."));
                } else {
                    if (EnumConstants.taskStatus.DONE.code.equals(taskStatus)) {
                        String key = taskType + ":" + deviceId;
                        redisService.del(key);
                    }
                    TaskService taskService = SpringContext.getBean(TaskService.class);
                    taskService.taskNotify(deviceId, type, taskId, taskStatus, "");
                }
            } else if (option.equalsIgnoreCase("hotSubTaskNotify")) {
                String taskType = deviceData.getString("task_type");
                String taskId = deviceData.getString("task_id");
                String taskStatus = deviceData.getString("task_status");
                EnumConstants.TaskType type = EnumConstants.TaskType.getTaskType(taskType);
                if (type == null) {
                    resJson = JSON.toJSONString(ResultEntity.fail("Device id:" + deviceId + ". task type error, please confirm and retry."));
                } else {
                    if (EnumConstants.taskStatus.DONE.code.equals(taskStatus)) {
                        String key = taskType + ":" + deviceId;
                        redisService.del(key);
                    }
                    TaskService taskService = SpringContext.getBean(TaskService.class);
                    taskService.hotSubTaskNotify(deviceId, type, taskId, taskStatus);
                }
                //获取超级热度子任务
            } else if (option.equalsIgnoreCase("getSubTask")) {
                String taskType = deviceData.getString("task_type");
                String taskId = deviceData.getString("task_id");
                String taskStatus = deviceData.getString("task_status");
                String liveHotUuid = deviceData.getString("live_hot_uuid");
                EnumConstants.TaskType type = EnumConstants.TaskType.getTaskType(taskType);
                if (type == null) {
                    resJson = JSON.toJSONString(ResultEntity.fail("Device id:" + deviceId + ". task type error, please confirm and retry."));
                } else {
                    if (EnumConstants.taskStatus.DONE.code.equals(taskStatus)) {
                        String key = taskType + ":" + deviceId;
                        redisService.del(key);
                    }
                    TaskService taskService = SpringContext.getBean(TaskService.class);
                    List<Object> list = taskService.findLiveHotSubTask(type, liveHotUuid,platform);
                    if (null != list && list.size() != 0) {
                        Object o = list.get(0);
                        Map<String, Object> map = new HashMap<>();
                        map.put("option", "task");
                        map.put("task_type", taskType);
                        map.put("task_data", o);
                        map.put("task_id", taskId);
                        resJson = JSON.toJSONString(map);
                    }
                }
                //获取超级热度等同级任务
            } else if (option.equalsIgnoreCase("getTask")) {
                String taskType = deviceData.getString("task_type");
                String taskId = deviceData.getString("task_id");
                String taskStatus = deviceData.getString("task_status");
                EnumConstants.TaskType type = EnumConstants.TaskType.getTaskType(taskType);
                if (type == null) {
                    resJson = JSON.toJSONString(ResultEntity.fail("Device id:" + deviceId + ". task type error, please confirm and retry."));
                } else {
                    if (EnumConstants.taskStatus.DONE.code.equals(taskStatus)) {
                        String key = taskType + ":" + deviceId;
                        redisService.del(key);
                    }
                    TaskService taskService = SpringContext.getBean(TaskService.class);
                    EnumConstants.TaskType type1 = EnumConstants.TaskType.getTaskType(taskType);
                    List<TaskModel> list = taskService.findCodeTask(deviceId, type1, platform);
                    if (null != list && list.size() != 0) {
                        TaskModel taskModel = list.get(0);
                        Map<String, Object> map = new HashMap<>();
                        map.put("option", "task");
                        map.put("task_type", taskModel.getTask_type());
                        map.put("task_data", taskModel.getTask_data());
                        map.put("task_id", taskModel.getTask_id());
                        map.put("msg", "This device had a unfinished task,please keep executing!");
                        resJson = JSON.toJSONString(map);
                    }
                }
            }
        } catch (Exception e) {
            if (e instanceof JSONException) {
                resJson = JSON.toJSONString(ResultEntity.fail("Device ID:" + deviceId + ", notify Failed, Cause: data not json."));
            } else if (e instanceof BussinessException) {
                resJson = JSON.toJSONString(ResultEntity.fail("Device ID:" + deviceId + ", notify Failed, Cause:" + e.getMessage()));
            } else {
                resJson = JSON.toJSONString(ResultEntity.fail("Device ID:" + deviceId + ", notify Failed, Cause:" + e.getCause()));
            }
        }
        return resJson;
    }


}
