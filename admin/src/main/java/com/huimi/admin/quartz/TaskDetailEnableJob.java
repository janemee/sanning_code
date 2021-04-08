package com.huimi.admin.quartz;

import com.alibaba.fastjson.JSON;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.tools.DateUtil;
import com.huimi.common.tools.StringUtil;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.constant.Constants;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.comment.CommentTemplate;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.comment.CommentTemplateService;
import com.huimi.core.service.liveHotSubTask.LiveHotSubTaskService;
import com.huimi.core.service.task.TaskDetailsService;
import com.huimi.core.service.task.TaskService;
import com.huimi.core.task.Task;
import com.huimi.core.task.TaskAllModel;
import com.huimi.core.task.TaskDetails;
import com.huimi.core.task.TaskModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 超级热度任务-过期的自动已完成
 */

@Slf4j
@Component
public class TaskDetailEnableJob {

    @Resource
    private TaskDetailsService taskDetailsService;
    @Resource
    private TaskService taskService;
    @Resource
    private CommentTemplateService commentTemplateService;
    @Resource
    private RedisService redisService;
    @Resource
    private LiveHotSubTaskService liveHotSubTaskService;


    public static String remarks = "定时器扫描，任务时间已过，自动关闭，并关闭相关的子任务";

    /**
     * 每秒钟去判断是否有定时业务需要开启
     */
    @Scheduled(cron = "0/3 * * * * ?")
    public void executeInternal() throws Exception {
        if (EnumConstants.HistoryState.YES.value == redisService.getInt(ConfigNID.QUARTZ_TASK_FLAG)) {
            List<TaskDetails> taskDetails = taskDetailsService.findByDelay();
            if (CollectionUtils.isNotEmpty(taskDetails)) {
                for (TaskDetails taskDetail : taskDetails) {
                    taskDetail.setState(Integer.parseInt(EnumConstants.taskStatus.RUN.value));
                    taskDetail.setUpdateTime(new Date());
                    taskDetail.setRemarks("定时器自动开启任务");
                    taskDetailsService.updateByPrimaryKeySelective(taskDetail);
                    log.info("定时任务已开启：" + taskDetail.getTaskType() + "_" + taskDetail.getId());
                    //发送任务
                    List<TaskDetails> finalTaskDetails = taskDetails;
                    new Thread(() -> handlerTask(finalTaskDetails)).start();
                }
            }
            try {
                String flag = redisService.get(ConfigNID.AUTO_OUT_TASK_FLAG);
                if (StringUtil.isBlank(flag) || !"1".equals(flag)) {
                    return;
                }
                 taskDetails = taskDetailsService.findByOutTimeTaskList();
                for (TaskDetails taskDetail : taskDetails) {
                    //结束主任务
                    taskDetail.setState(Integer.parseInt(EnumConstants.taskStatus.DONE.value));
                    taskDetail.setRemarks(remarks);
                    taskDetail.setUpdateTime(DateUtil.getNow());
                    taskDetailsService.updateByPrimaryKeySelective(taskDetail);
                    // TODO: 2020/9/11  动添加结束任务
//                TaskDetails overTaskDetails = new TaskDetails();
//                overTaskDetails.setUuid(System.currentTimeMillis()+"");

                    //结束相关的子任务
                    liveHotSubTaskService.updateByTaskDetailsId(taskDetail.getId(), EnumConstants.taskStatus.DONE.value, remarks, EnumConstants.TaskType.OVER.value);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }


    private void handlerTask(List<TaskDetails> taskDetails) {
        Task task = null ;
        CommentTemplate commentTemplate = null ;
        for (TaskDetails taskDetail : taskDetails) {
            if(null == task) {
                task = taskService.selectByPrimaryKey(taskDetail.getTaskId());
            }
            String taskId = taskDetail.getTaskDetailUuid();
            TaskAllModel taskAllModel = new TaskAllModel();
            taskAllModel.setTaskDetailUuid(taskId);
            taskAllModel.setTaskType(taskDetail.getTaskType());
            taskAllModel.setCommentInterval(task.getCommentInterval());
            taskAllModel.setTaskContent(task.getTaskContent());
            taskAllModel.setTaskExpectRunning(task.getTaskExpectRunning());
            taskAllModel.setTaskRunCode(task.getTaskRunCode());
            taskAllModel.setNumber(taskDetail.getNumber());
            taskAllModel.setLetterType(taskDetail.getLetterType());
            taskAllModel.setTaskStartTime(taskDetail.getTaskStartTime());
            taskAllModel.setTaskEndTime(task.getTaskEndTime());
            taskAllModel.setAnalysisContent(task.getAnalysisContent());
            taskAllModel.setLiveInContent(task.getAnalysisContent());
            taskAllModel.setLiveInType(task.getLiveInType());

            Long commentTemplateId = task.getCommentTemplateId();
            if(null == commentTemplate) {
                commentTemplate = commentTemplateService.findById(commentTemplateId);
            }
            taskAllModel.setName(commentTemplate.getName());
            taskAllModel.setComment((commentTemplate.getComment()));
            taskAllModel.setLive(commentTemplate.getLive());
            taskAllModel.setTurns(commentTemplate.getTurns());
            taskAllModel.setLetter(commentTemplate.getLetter());
            TaskModel taskModel = taskService.getTaskModel(taskAllModel);
            List<TaskModel> list = new ArrayList<>();
            list.add(taskModel);
            ResultEntity<List<TaskModel>> resultEntity = new ResultEntity<>();
            resultEntity.setCode(ResultEntity.SUCCESS);
            resultEntity.setData(list);
            resultEntity.setMsg("success");
            resultEntity.setTotal(list.size());
            String channelId = redisService.get(Constants.DEVICE_CHANNEL + taskDetail.getDeviceUid());
            resultEntity.setUuid(channelId);
            //执行发送
            redisService.publish(Constants.TIKTOK_LIVE_HOT, JSON.toJSONString(resultEntity));
        }
    }
}
