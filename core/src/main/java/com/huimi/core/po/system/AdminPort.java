package com.huimi.core.po.system;

import com.huimi.common.baseMapper.GenericPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * create by lja on 2020/8/27 15:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "端口表")
@Table(name = "sys_admin_port")
public class AdminPort extends GenericPo<Integer> {
    public static final String TABLE_NAME = "sys_admin_port";

    @Column(name = "sys_admin_id")
    private Integer sysAdminId;

    @Column(name = "start_port")
    private Integer startPort;

    @Column(name = "update_port")
    private Integer updatePort;
    @Column(name = "operat_admin")
    private Integer operatAdmin;
   //被改变者的用户姓名
    @Transient
    private String adminUserName;
   //操作者的用户姓名
    @Transient
    private String operatAdminName;
}
