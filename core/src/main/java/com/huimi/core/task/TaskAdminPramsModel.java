package com.huimi.core.task;

import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.system.Admin;
import lombok.Data;

import java.util.Date;

/**
 * 后台添加任务参数模型类
 */
@Data
public class TaskAdminPramsModel {
    /**
     * 设备数组
     */
    private String[] equipments;
    /**
     * 分组数组
     */
    private String[] equipmentGroups;
    /**
     * 任务类型
     */
    private String taskType;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 超级热度的id
     */
    private Long heart;

    /**
     * 添加设备的方式(设备或者群组)
     */
    private String deviceStyle;

    /**
     * 添加超级热度设备的Id(可设备id可分组id 逗号隔开)
     */
    private String devicesOrGroupsId;

    /**
     * 礼物数量
     */
    private Integer giftNumber;

    /**
     * 礼物页数
     */
    private Integer giftPage;

    /**
     * 0表示不赠送1表示赠送
     */
    private Integer light;

    /**
     * 礼物格子数
     */
    private String giftBox;

    /**
     * 打榜关注数量
     */
    private Integer makeListNum;

    /**
     * 点击次数
     */
    private Integer clickNumber;
    /**
     * 结束任务的方式
     */
    private Integer overType;

    /**
     * 运行间隔时间 （分钟）
     */

    private Integer commentInterval;
    /**
     * 任务时间 (分钟)
     */
    private Integer taskExpectRunning;

    /**
     * 同城引流-城市名称
     */
    private String city;
    /**
     * 私信方式
     */
    private String letterType;
    /**
     * 私信数量
     */
    private Integer number;

    /**
     * 话术模板id
     */
    private Long commentTemplateId;

    /**
     * 任务内容
     */
    private String taskContent;

    /**
     * 定时执行 - 定时时间
     */
    private String taskStartTime;

    /**
     * 执行方式
     */
    private String taskRunCode;

    /**
     * 抢红包时间
     */
    private Integer redEnvelopeTime;

    /**
     * 设备升级方式 1 版本更新 2 全量更新
     */
    private Integer apkUpgrade;

    /**
     * 文本内容 （矩阵推流）
     */
    private String liveInContent;
    /**
     * 任务类型 0 抖音任务  1 直播任务
     */
    private Integer isLiveHot;

    /**
     * 定时任务开始时间
     */
    private Date taskStartDateTime;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 平台类型  默认 1.抖音 2.快手
     */
    private String platform;

    /**
     * 互动话术
     */
    private String content;
    /**
     * 当前登录人
     */
    private Admin admin;
    /**
     * 任务类型
     */
    private EnumConstants.TaskType eTaskType;

    public EnumConstants.TaskType getETaskType() {
        return eTaskType = EnumConstants.TaskType.getTaskType(this.taskType);
    }
}
