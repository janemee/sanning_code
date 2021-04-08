package com.huimi.core.service.liveHotSubTask.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.common.utils.DateUtil;
import com.huimi.core.entity.LiveHotSubTask;
import com.huimi.core.mapper.liveHotSubTask.LiveHotSubTaskMapper;
import com.huimi.core.model.LiveHotSubTask.LiveHotSubTaskModel;
import com.huimi.core.service.liveHotSubTask.LiveHotSubTaskService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 超级热度子任务impl类
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class LiveHotSubTaskServiceImpl implements LiveHotSubTaskService {

    @Resource
    private LiveHotSubTaskMapper liveHotSubTaskMapper;


    @Override
    public GenericMapper<LiveHotSubTask, Integer> _getMapper() {
        return liveHotSubTaskMapper;
    }

    @Override
    public LiveHotSubTaskModel findByLiveHotTaskUuid(String code, String liveHotUuid,String platform) {

        return liveHotSubTaskMapper.findByLiveHotTaskUuid(code, liveHotUuid,platform);
    }


    @Override
    public LiveHotSubTask findByUuid(String uuid, String deviceUid) {
        return liveHotSubTaskMapper.findByUuid(uuid, deviceUid);
    }

    @Override
    public List<LiveHotSubTask> findToTaskDetailsId(Integer taskDetailId, String state, String taskType) {
        return liveHotSubTaskMapper.findToTaskDetailsId(taskDetailId, state, taskType);
    }


    @Override
    public List<LiveHotSubTask> findToDistantOver(String taskType, Integer taskDetailId, String state) {
        return liveHotSubTaskMapper.findToDistantOver(taskType, taskDetailId, state);
    }

    @Override
    public Integer updateByTaskDetailsId(Integer taskDetailId, String state, String remarks, String taskType) {
        return liveHotSubTaskMapper.updateByTaskDetailsId(taskDetailId, state, remarks, taskType);
    }

    @Override
    public Integer updateByLiveHotTaskState(String deviceUid, String uuid, String state, String remarks) {
        return liveHotSubTaskMapper.updateByLiveHotTaskState(deviceUid, uuid, state, remarks);
    }

    @Override
    public Integer updateByUuid(String uuid, long l, int state) {
        return liveHotSubTaskMapper.updateByUuid(uuid, l, DateUtil.dateStrMillis(new Date()), state);
    }

    @Override
    public int updateTaskOverByDeviceUid(String deviceUid, String remarks) {
        return liveHotSubTaskMapper.updateTaskOverByDeviceUid(deviceUid, remarks);
    }
}
