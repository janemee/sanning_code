package com.huimi.core.service.task;

import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.service.base.GenericService;
import com.huimi.core.task.Task;
import com.huimi.core.task.TaskDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lja on 2020/7/31 15:35
 */
public interface TaskDetailsService extends GenericService<Integer, TaskDetails> {
    DtGrid findAll();

    /**
     * 查询总任务下的所有手机设备device_code
     */
    DtGrid findByAllDevCode(Long id);

    /**
     * 根据设备code 任务id 任务类型查询任务详情信息
     *
     * @param deviceUid 设备code
     * @param taskId    任务id
     * @param taskType  任务类型
     */
    TaskDetails findByTaskDetail(String deviceUid, String taskId, String taskType);

    /**
     * 根据当前时间判断是否有任务需要开启
     */
    List<TaskDetails> findByDelay();

    /**
     * 更新任务执行状态
     *
     * @param taskId 任务唯一标识 uuid
     * @param status 任务状态
     */
    boolean updateTaskDetailState(String taskId, String status);

    /**
     * 结束任务
     */
    void updateByEquipmentUid(String deviceUid, String value, String remarks);

    /**
     * 根据uuid获取任务信息
     */
    TaskDetails findByUuid(String taskUuid,String deviceUid);

    /**
     * 根据taskID查询任务详情
     */
    TaskDetails findByTaskId(Long taskId, String deviceUid);

    /**
     * 查询任务时间已到任务状态未更改的任务
     */
    List<TaskDetails> findByOutTimeTaskList();

    List<Integer> findByAgent(ArrayList ids);

    void updateByUuid(String task_id, long l, int state);

    /**
     * 根据设备uid 结束所有任务
     *
     * @param deviceUid
     * @return
     */
    int updateTaskOverByDeviceUid(String deviceUid,String remarks);
}
