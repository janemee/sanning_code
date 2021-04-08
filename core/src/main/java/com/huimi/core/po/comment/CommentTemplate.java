package com.huimi.core.po.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huimi.common.baseMapper.GenericPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * create by lja on 2020/7/30 14:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "话术表")
@Table(name = "comment_template")
public class CommentTemplate extends GenericPo<Long> {
    public static final String TABLE_NAME = "equipment";

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

    @Column(name = "type")
    private Integer type;

    @Column(name = "sys_admin_id")
    private Integer sysAdminId;

    @Column(name = "`last_time`")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;


    @Column(name = "`update_time`")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;
}
