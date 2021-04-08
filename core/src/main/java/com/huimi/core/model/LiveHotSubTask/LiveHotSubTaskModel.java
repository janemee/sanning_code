package com.huimi.core.model.LiveHotSubTask;

import lombok.Data;

/**
 * 超级热度子任务返回实体
 */
@Data
public class LiveHotSubTaskModel {

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 任务的uuid唯一标识
     */
    private String uuid;

    /**
     * 设备uid
     */
    private String deviceUid;

    /**
     * 礼物页数
     */
    private Integer giftPage;
    /**
     * 礼物数量
     */
    private Integer giftNumber;

    /**
     * 礼物格子数
     */
    private String giftBox;

    /**
     * 0表示不赠送1表示赠送
     */

    private Integer giveLight;

    /**
     * 抢红包时间
     */
    private Integer redEnvelopeTime;

    /**
     * 打榜关注数量
     */
    private Integer makeListNumber;

    /**
     * 点击次数
     */
    private Integer clickNumber;

    /**
     * 实时互动文本内容
     */
    private String content;

    /**
     * 任务运行时间
     */
    private Integer taskExpectRunning;

    /**
     * 任务运行间隔时间
     */
    private Integer commentInterval;

    /**
     * 主任务id
     */
    private String taskDetailUuid;

}
