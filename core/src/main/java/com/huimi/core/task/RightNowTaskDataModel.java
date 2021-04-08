package com.huimi.core.task;

import lombok.Data;

@Data
public class RightNowTaskDataModel {

    private String task_type;

    private RightNowTaskModel comment_template;

    private String device_code;

}
