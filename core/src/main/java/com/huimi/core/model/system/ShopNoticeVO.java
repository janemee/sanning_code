package com.huimi.core.model.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.function.Consumer;

/**
 *
 * @author fc_prc@126.com
 * @since 2018-12-25 18:46:02
 */
@Data
public class ShopNoticeVO {

    /**
     * 通知id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer shopId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public ShopNoticeVO() {
    }

    public ShopNoticeVO(Consumer<ShopNoticeVO> consumer) {
        consumer.accept(this);
    }

}