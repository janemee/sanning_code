package com.huimi.core.task;

import lombok.Data;

@Data
public class TaskDataModel {

    private String task_type;

    private String task_run_code;

    private String task_start_time;
    private String task_end_time;

    private String task_expect_running;

    private String task_content;

    private String number;

    private String letter_type;

    private String live_in_type;

    private String live_in_content;

    private String comment_interval;

    private CommentTemplateModel comment_template;

    /**
     * 设备名称
     */
    private String device_code;

    /**
     * 关键字 自动养号时才有
     */
    private String key_word;

    /**
     * 任务结束时间 单位分钟 如果任务时间为空 则默认 10分钟
     * 丢弃任务由客户端处理
     */
    private String task_over_date;

}
