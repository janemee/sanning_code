package com.huimi.core.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class TaskAllModel {


    @Column(name = "device_Uid")
    private String deviceUid;

    @Column(name = "task_detail_uuid")
    private String taskDetailUuid;

    @Column(name = "task_type")
    private String taskType;

    @Column(name = "task_run_code")
    private String taskRunCode;

    @Column(name = "task_content")
    private String taskContent;

    @Column(name = "task_expect_running")
    private Integer taskExpectRunning;

    @Column(name = "analysis_content")
    private String analysisContent;

    @Column(name = "live_in_content")
    private String liveInContent;

    @Column(name = "liveInType")
    private String liveInType;

    @Column(name = "number")
    private Integer number;

    @Column(name = "letter_type")
    private String letterType;

    @Column(name = "comment_interval")
    private Integer commentInterval;

    @Column(name = "comment_template_id")
    private Long CommentTemplateId;

    @Column(name = "task_start_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskEndTime;

    @Column(name = "name")
    private String name;

    @Column(name = "comment")
    private String comment;

    @Column(name = "letter")
    private String letter;

    @Column(name = "live")
    private String live;

    @Column(name = "turns")
    private String turns;

    @Column(name = "symbol")
    private Integer symbol;

    @Column(name = "open")
    private Integer open;

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
     * 礼物格子数
     */
    @Column(name = "gift_box")
    private String giftBox;

    /**
     * 0表示不赠送1表示赠送
     */
    @Column(name = "give_light")
    private Integer giveLight;

    /**
     * 抢红包时间
     */
    @Column(name = "red_envelope_time")
    private Integer redEnvelopeTime;

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
     * 更新方式
     */
    @Column(name = "apk_upgrade")
    private Integer apkUpgrade;


    /**
     * 关键字
     */
    @Column(name = "key_word")
    private String keyWord;

    /**
     * 设备uid列表 结构 deviceUid:taskId
     */
    private List deviceUidList;
}
