package com.huimi.core.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.core.constant.EnumConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * create by lja on 2020/7/31 15:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "任务表详情表")
@Table(name = "task_details")
public class TaskDetails extends GenericPo<Integer> {
    public static final String TABLE_NAME = "task_details";

    /**
     * 任务发送状态 0未发送 1已发送
     */
    @Column(name = "send_state")
    private Integer sendState;

    /**
     * 任务发送時間
     */
    @Column(name = "send_time")
    private String sendTime;

    /**
     * 任发送耗时
     */
    @Column(name = "send_took_millis")
    private Long sendTookMillis;

    /**
     * 任务id
     */
    @Column(name = "task_id")
    private Integer taskId;

    /**
     * 设备uuid
     */
    @Column(name = "device_uid")
    private String deviceUid;

    /**
     * 任务类型
     */
    @Column(name = "task_type")
    private String taskType;
    /**
     * 任务详情uuid
     */
    @Column(name = "task_detail_uuid")
    private String  taskDetailUuid;

    /**
     * 任务状态
     */
    @Column(name = "state")
    private Integer state;
    /**
     * 播放次数
     */
    @Column(name = "playtimes")
    private Integer playtimes;
    /**
     * 点赞次数
     */
    @Column(name = "fabulous")
    private Integer fabulous;
    /**
     * 查看主页次数
     */
    @Column(name = "homepages")
    private Integer homepages;
    /**
     * 转发次数
     */
    @Column(name = "forwards")
    private Integer forwards;
    /**
     * 关注次数
     */
    @Column(name = "follows")
    private Integer follows;
    /**
     * 私信次数
     */
    @Column(name = "letters")
    private Integer letters;
    /**
     * 评论次数
     */
    @Column(name = "comments")
    private Integer comments;

    /**
     * 私信方式
     */
    @Column(name = "letter_type")
    private String letterType;

    /**
     * 私信的个数
     */
    @Column(name = "number")
    private Integer number;

    /**
     * 关键字
     */
    @Column(name = "key_word")
    private String keyWord;

    /**
     * 平台名称 1.tiktok 2.快手  默认为抖音
     */
    @Column(name = "platform")
    private String platform;

    @Transient
    private String taskContent;


    @Transient
    private String taskRunCode;

    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskStartTime;

    /**
     * 城市名称
     */
    @Column(name = "city")
    private String city;

    /**
     * 设备升级方式 1 版本更新 2 全量更新
     */
    @Column(name = "apk_upgrade")
    private Integer apkUpgrade;

    /**
     * 备注
     */
    @Column(name = "remarks")
    private String remarks;


    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskEndTime;

    @Transient
    private String equipmentName;

    @Transient
    private String taskRunCodeHtml;

    @Transient
    private String equipmentGroupName;

    @Transient
    private String stateHtml;

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

    public String getStateHtml() {
        return EnumConstants.taskStatus.getDesc(this.state + "");
    }
}
