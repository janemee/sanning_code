package com.huimi.core.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 任务返回信息类
 */

@Data
public class RightNowModel {

    /**
     * 任务id
     */
    String task_id;
    /**
     * 任务类型
     */
    String task_type;

    /**
     * 任务结束时间 单位分钟 如果任务时间为空 则默认 10分钟
     * 丢弃任务由客户端处理
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date task_over_date;

    /**
     * 话术内容
     */
    RightNowTaskDataModel task_data;

}
