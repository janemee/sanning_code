package com.huimi.core.service.task.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.DateUtil;
import com.huimi.common.utils.JsonUtils;
import com.huimi.core.mapper.liveHotSubTask.LiveHotSubTaskMapper;
import com.huimi.core.mapper.task.TaskDetailsMapper;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.service.task.TaskDetailsService;
import com.huimi.core.task.TaskDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * create by lja on 2020/7/31 15:36
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class TaskDetailsServiceImpl implements TaskDetailsService {
    @Autowired
    private TaskDetailsMapper taskDetailsMapper;
    @Autowired
    private LiveHotSubTaskMapper liveHotSubTaskMapper;


    @Override
    public DtGrid findAll() {
        List<TaskDetails> retList = taskDetailsMapper.findAll();
        DtGrid dtGrid = new DtGrid();
        dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(retList), new TypeReference<List<Object>>() {
        }));
        dtGrid.setExportDatas(JsonUtils.toGenericObject(JsonUtils.toJson(retList), new TypeReference<List<Map<String, Object>>>() {
        }));
        return dtGrid;
    }

    @Override
    public DtGrid findByAllDevCode(Long id) {
        DtGrid dtGrid = new DtGrid();
        List<Equipment> retList = taskDetailsMapper.findByEquipment(id);
        dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(retList), new TypeReference<List<Object>>() {
        }));
        dtGrid.setExportDatas(JsonUtils.toGenericObject(JsonUtils.toJson(retList), new TypeReference<List<Map<String, Object>>>() {
        }));
        return dtGrid;
    }

    @Override
    public TaskDetails findByTaskDetail(String deviceUid, String taskId, String taskType) {

        return taskDetailsMapper.findByTaskDetail(deviceUid, taskId, taskType);
    }

    @Override
    public List<TaskDetails> findByDelay() {
        return taskDetailsMapper.findByDelay();
    }

    @Override
    public boolean updateTaskDetailState(String taskId, String status) {
        return taskDetailsMapper.updateTaskDetailState(taskId, status) > 0;
    }

    @Override
    public void updateByEquipmentUid(String deviceUid, String state, String remarks) {
        taskDetailsMapper.updateByEquipmentUid(deviceUid, state, remarks);
        liveHotSubTaskMapper.updateByEquipmentUid(deviceUid, state, remarks);
    }

    @Override
    public TaskDetails findByUuid(String taskUuid,String deviceUid) {
        return taskDetailsMapper.findByUuid(taskUuid,deviceUid);
    }

    @Override
    public TaskDetails findByTaskId(Long taskId, String deviceUid) {
        return taskDetailsMapper.findByTaskId(taskId, deviceUid);
    }


    @Override
    public List<TaskDetails> findByOutTimeTaskList() {
        return taskDetailsMapper.findByOutTimeTaskList();
    }

    @Override
    public List<Integer> findByAgent(ArrayList ids) {
        return taskDetailsMapper.findByAgent(ids);
    }


    @Override
    public GenericMapper<TaskDetails, Integer> _getMapper() {
        return taskDetailsMapper;
    }

    @Override
    public void updateByUuid(String task_id, long millis, int state) {
        taskDetailsMapper.updateByUuid(task_id, millis, DateUtil.dateStrMillis(new Date()), state);
    }

    @Override
    public int updateTaskOverByDeviceUid(String deviceUid,String remarks) {
        return taskDetailsMapper.updateTaskOverByDeviceUid(deviceUid,remarks);
    }
}
