package com.huimi.core.service.liveHotSubTask;

import com.huimi.core.entity.LiveHotSubTask;
import com.huimi.core.model.LiveHotSubTask.LiveHotSubTaskModel;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 超级热度子任务服务类
 */
public interface LiveHotSubTaskService extends GenericService<Integer, LiveHotSubTask> {


    /**
     * 根据任务类型查询子任务
     *
     * @param code        任务类型
     * @param liveHotUuid 父任务uuid
     * @return
     */
    LiveHotSubTaskModel findByLiveHotTaskUuid(String code, String liveHotUuid,String platform);


    /**
     * 根据uuid获取任务信息
     *
     * @param uuid
     * @return
     */
    LiveHotSubTask findByUuid(String uuid,String deviceUid);

    /**
     * 查找是否有超级热度下的任务
     */
    List<LiveHotSubTask> findToTaskDetailsId(Integer taskDetailId, String state, String taskType);

    /**
     * 找到是否有结束任务主任务
     */
    List<LiveHotSubTask> findToDistantOver(String taskType, Integer taskDetailId, String state);

    /**
     * 结束超级热度下的所有子任务
     */
    Integer updateByTaskDetailsId(Integer taskDetailId, String state, String remarks, String taskType);

    /**
     * 更新子任务任务状态 （结束任务回调）
     *
     * @return
     */
    Integer updateByLiveHotTaskState(String deviceUid,String uuid, String state,String remarks);

    /**
     * 更新子任务发送相关信息
     *
     * @param uuid  uuid
     * @param l     发送耗时
     * @param state 状态
     * @return 是否更新成功
     */
    Integer updateByUuid(String uuid, long l, int state);

    int updateTaskOverByDeviceUid(String deviceUid,String remarks);
}
