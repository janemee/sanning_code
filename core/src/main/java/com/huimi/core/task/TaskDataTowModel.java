package com.huimi.core.task;

import lombok.Data;

import java.util.Map;


/**
 * 无话术任务返回数据结构
 */
@Data
public class TaskDataTowModel {

    /**
     * 任务id
     */
    private String task_id;

    /**
     * 任务类型
     */
    private String task_type;


    /**
     * 返回内容
     */
    Map<String, Object> task_data;


    public TaskDataTowModel() {
    }

    public TaskDataTowModel(String task_id, Map<String, Object> map) {
        this.task_id = task_id;
        this.task_data = map;
    }

    public TaskDataTowModel(String task_id, String task_type, Map<String, Object> data) {
        this.task_id = task_id;
        this.task_type = task_type;
        this.task_data = data;
    }
}
