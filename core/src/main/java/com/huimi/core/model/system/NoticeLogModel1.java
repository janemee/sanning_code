package com.huimi.core.model.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.function.Consumer;

/**
 * 用户站内信列表元素
 */
@Data
public class NoticeLogModel1 {

    /**
     * 通知id
     */
    private Integer id;

    /**
     * 业务类型: 1系统通知;2订单通知;3其他;
     */
    private Integer txType;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 业务id
     */
    private Integer txId;

    /**
     * 业务详情 ;分割
     */
    private String txInfo;

    /**
     * 消息创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public NoticeLogModel1() {
    }

    public NoticeLogModel1(Consumer<NoticeLogModel1> consumer) {
        consumer.accept(this);
    }
}
