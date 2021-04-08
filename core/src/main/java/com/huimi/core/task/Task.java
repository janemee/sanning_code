package com.huimi.core.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.comment.CommentTemplate;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * create by lja on 2020/7/30 16:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "任务表")
@Table(name = "task")
public class Task extends GenericPo<Integer> {
    public static final String TABLE_NAME = "task";

    @Column(name = "task_type")
    private String taskType;

    @Column(name = "task_run_code")
    private String taskRunCode;

    @Column(name = "task_content")
    private String taskContent;

    @Column(name = "task_expect_running")
    private Integer taskExpectRunning;

    @Column(name = "live_in_content")
    private String liveInContent;

    @Column(name = "analysis_content")
    private String analysisContent;

    @Column(name = "live_in_Type")
    private String liveInType;

    @Column(name = "sys_admin_id")
    private Integer sysAdminId;

    @Column(name = "comment_interval")
    private Integer commentInterval;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "comment_template_id")
    private Long CommentTemplateId;

    @Column(name = "is_live_hot")
    private Integer isLiveHot;

    @Column(name = "task_end_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskEndTime;

    /**
     * 平台名称 1.tiktok 2.快手  默认为抖音
     */
    @Column(name = "platform")
    private String platform;


    @Transient
    private String name;

    @Transient
    private CommentTemplate commentTemplates;

    @Transient
    private Integer comments;


    @Column(name = "task_start_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskStartTime;


    @Transient
    private String taskTypeHtml;


    @Transient
    private String taskRunCodeHtml;

    /**
     * 任务名称  规则 任务类型 + 输入名称
     */

    public String getTaskTypeHtml() {
        EnumConstants.TaskType enumConstants = EnumConstants.TaskType.getTaskType(this.taskType);
        if (enumConstants == null) {
            return "";
        }
        return enumConstants.htmlStr;
    }

    public String getTaskRunCodeHtml() {
        return EnumConstants.TaskRunCode.getHtmlStr(this.taskRunCode);
    }


}
