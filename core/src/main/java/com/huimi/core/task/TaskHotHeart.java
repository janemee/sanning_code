package com.huimi.core.task;

import lombok.Data;

/**
 * create by lja on 2020/8/4 18:00
 */
@Data
public class TaskHotHeart extends  TaskVo {
    private String live_in_type;
    private String live_in_content;
    private Integer comment_interval;
}
