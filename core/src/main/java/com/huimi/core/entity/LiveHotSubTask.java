package com.huimi.core.entity;

import com.huimi.common.baseMapper.GenericPo;
import com.huimi.core.constant.EnumConstants;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 超级热度子任务实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "直播子任务表")
@Table(name = "live_hot_sub_task")
public class LiveHotSubTask extends GenericPo<Integer> {
    public static final String TABLE_NAME = "live_hot_sub_task";


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
     * 子任务唯一标识uuid
     */
    @Column(name = "uuid")
    private String uuid;

    /**
     * 直播任务id
     */
    @Column(name = "task_detail_id")
    private Integer taskDetailId;

    /**
     * 设备的唯一识别UID
     */
    @Column(name = "device_uid")
    private String deviceUid;

    /**
     * 任务类型
     */
    @Column(name = "task_type")
    private String taskType;

    /**
     * 任务状态
     */
    @Column(name = "state")
    private Integer state;
    /**
     * 礼物页数
     */
    @Column(name = "gift_page")
    private Integer giftPage;

    /**
     * 礼物数量
     */
    @Column(name = "gift_number")
    private Integer giftNumber;

    /**
     * 礼物格子数 ( no1 ......)
     */
    @Column(name = "gift_box")
    private String giftBox;
    /**
     * 抢红包时间
     */
    @Column(name = "red_envelope_time")
    private Integer redEnvelopeTime;
    /**
     * 0表示不赠送1表示赠送
     */
    @Column(name = "give_light")
    private Integer giveLight;
    /**
     * 打榜关注数量
     */
    @Column(name = "make_list_number")
    private Integer makeListNumber;
    /**
     * 点击次数
     */
    @Column(name = "click_number")
    private Integer clickNumber;

    /**
     * 任务运行时间
     */
    @Column(name = "task_expect_running")
    private Integer taskExpectRunning;

    /**
     * 任务运行间隔时间
     */
    @Column(name = "comment_interval")
    private Integer commentInterval;
    /**
     * 话术
     */
    @Column(name = "content")
    private String liveInContent;

    /**
     * 平台名称 1.tiktok 2.快手  默认为抖音
     */
    @Column(name = "platform")
    private String platform;

    @Transient
    private String taskTypeHtml;


    @Transient
    private String taskRunCodeHtml;

    /**
     * 任务状态
     */
    @Transient
    private String stateHtml;

    /**
     * 任务名称  规则 任务类型 + id
     */
    @Transient
    private String taskName;

    public String getTaskTypeHtml() {
        EnumConstants.TaskType enumConstants = EnumConstants.TaskType.getTaskType(this.taskType);
        if (enumConstants == null) {
            return "";
        }
        return enumConstants.htmlStr;
    }


    public String getTaskName() {
        EnumConstants.TaskType enumConstants = EnumConstants.TaskType.getTaskType(this.taskType);
        if (enumConstants == null) {
            return "";
        }
        return enumConstants.desc + "-" + this.id;
    }

    public String getStateHtml() {
        return EnumConstants.taskStatus.getDesc(this.state + "");
    }
}
