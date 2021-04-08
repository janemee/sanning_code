package com.huimi.core.task;

import com.huimi.core.po.comment.CommentTemplate;
import lombok.Data;

import java.util.Date;

/**
 * create by lja on 2020/8/4 17:51
 */
@Data
public  class TaskVo {
    private String device_code;
    private String task_type;
    private String task_run_code;
    private Date task_start_time;
    private Integer task_expect_running;
    private String task_content;
    private CommentTemplate commentTemplate;
}
