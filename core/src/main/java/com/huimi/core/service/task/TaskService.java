package com.huimi.core.service.task;

import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.entity.LiveHotSubTask;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.service.base.GenericService;
import com.huimi.core.task.Task;
import com.huimi.core.task.TaskAdminPramsModel;
import com.huimi.core.task.TaskAllModel;
import com.huimi.core.task.TaskModel;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by lja on 2020/7/30 16:39
 */
public interface TaskService extends GenericService<Integer, Task> {
    DtGrid findAll(Integer isLiveHot);

    /**
     * 查询超级热度的任务列表
     */
    DtGrid findLiveHotTask();

    /**
     * 查询当前时间段有哪些超级热度
     */
    List<Task> findLiveHeart(Integer adminId, String platform);

    /**
     * 查询当前时间段有哪些超级热度
     * 异常情况没有停止
     */
    List<Task> findLiveTrobleHeart(Integer adminId);

    /**
     * 每条任务下的具体详情
     *
     * @param dtGrid 任务id
     */
    DtGrid findOneDetailed(DtGrid dtGrid);


    /**
     * 获取当前设备的任务内容
     *
     * @param deviceUid 设备标识
     * @param taskType  任务类别
     */
    List<TaskModel> findCodeTask(String deviceUid, EnumConstants.TaskType taskType,String platform);


    /**
     * 获取当前设备的任务内容
     *
     * @param deviceUid 设备标识
     * @param taskType  任务类别
     */
    List<Object> findTikTokTask(String deviceUid, EnumConstants.TaskType taskType,String platform);

    TaskModel getTaskModel(TaskAllModel taskAllModel);

    /**
     * 任务执行回调处理
     *
     * @param deviceUid  id
     * @param taskType   任务类型
     * @param taskId     任务id
     * @param taskStatus 任务状态
     */
    void taskNotify(String deviceUid, EnumConstants.TaskType taskType, String taskId, String taskStatus, String comment) throws Exception;

    /**
     * 添加超级热度任务
     *
     * @param task               任务model
     * @param equipmentArrayList 设备集合
     * @param millis             时间戳
     */
    void addSuperHeatTask(Task task, ArrayList<Equipment> equipmentArrayList, String millis);

    /**
     * 添加实时互动任务
     *
     * @param task          任务model
     * @param allEquipments 设备集合
     */
    void addInteraction(Task task, ArrayList<Equipment> allEquipments);

    /**
     * 添加加入粉丝团任务
     */
    void addFans(Task task, ArrayList<Equipment> allEquipments, String light);

    /**
     * 添加任务
     *
     * @param allEquipments 设备列表
     * @param taskType      任务类型
     */
    Task addTask(ArrayList<Equipment> allEquipments, EnumConstants.TaskType taskType, TaskAdminPramsModel taskAdminPramsModel, Integer adminId);

    /**
     * 添加实时任务
     *
     * @param allEquipments 设备列表
     * @param taskType      任务类型
     */
    List<LiveHotSubTask> addImmediatelyTask(ArrayList<Equipment> allEquipments, EnumConstants.TaskType taskType, TaskAdminPramsModel taskAdminPramsModel);

    /**
     * 关注打榜
     *
     * @param makeListNum 关注打榜数量
     */
    void addMakeList(Task task, ArrayList<Equipment> allEquipments, Integer makeListNum);

    /**
     * 抢红包
     *
     * @param redEnvelopeTime 抢红包时间
     */
    void addRedEnvelope(Task task, ArrayList<Equipment> allEquipments, Integer redEnvelopeTime);

    /**
     * 查询超级热度子任务
     *
     * @param type        任务类型
     * @param liveHotUuid 超级热度任务uuid
     */
    List<Object> findLiveHotSubTask(EnumConstants.TaskType type, String liveHotUuid,String platform);

    /**
     * 超级热度子任务回调处理
     */
    void hotSubTaskNotify(String deviceUid, EnumConstants.TaskType task_type, String taskId, String taskStatus);

    /**
     * 根据代理商的设备列表查询任务列表
     */
    List<Integer> FindByAgent(String code);

    /**
     * 删除结束的主任务和子任务
     */
    void findCloseTask();

    /**
     * 停止设备的所有任务
     *
     * @param deviceUid
     */
    void activeOverTask(String deviceUid);

    /**
     * 后台分页列表 (小视频任务列表)
     * @param pramMap
     * @param page
     * @param rows
     * @return
     */
    DtGrid findByTaskPage( DtGrid dtGrid ,Map<String, Object> pramMap, int page, int rows);
}
