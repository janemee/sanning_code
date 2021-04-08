package com.huimi.core.po.personnel;

import com.huimi.common.baseMapper.GenericPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "人员表")
@Table(name = "personnel")
public class Personnel extends GenericPo<Integer> {
    public static final String TABLE_NAME = "personnel";


    /**
     * 分组id
     */
    @Column(name = "group_id")
    private Integer groupId;
    /**
     * 人员姓名
     */
    @Column(name = "name")
    private String name;
    /**
     * 手机号
     */
    @Column(name = "phone")
    private String phone;
    /**
     * 韦根编号
     */
    @Column(name = "weigen_code")
    private String weigenCode;

    /**
     * 头像地址
     */
    @Column(name = "pic_url")
    private String picUrl;

    /**
     * 有效期
     */
    @Column(name = "term_time")
    private Date termTime;
}
