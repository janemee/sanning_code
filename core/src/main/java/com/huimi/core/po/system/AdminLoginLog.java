package com.huimi.core.po.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.huimi.common.baseMapper.GenericPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.util.Date;
import java.util.function.Consumer;


/**
 * 系统管理员登陆日志实体信息<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "系统管理员登陆日志")
@Table(name = "sys_admin_login_log")
public class AdminLoginLog extends GenericPo<Integer> {

    public static final String TABLE_NAME = "sys_admin_login_log";

    /**
     * uuid
     **/
    @ApiModelProperty(value = "uuid")
    private String uuid;
    /**
     * 登录用户名
     **/
    @ApiModelProperty(value = "登录用户名")
    private String username;
    /**
     * 后台用户ID
     **/
    @ApiModelProperty(value = "后台用户ID")
    private String adminId;
    /**
     * 登录的密码
     **/
    @ApiModelProperty(value = "登录的密码")
    private String pwd;
    /**
     * 登录状态 成功 失败
     **/
    @ApiModelProperty(value = "登录状态 成功 失败")
    private String state;
    /**
     * 登录ip
     **/
    @ApiModelProperty(value = "登录ip")
    private String loginIp;
    /**
     * 创建时间
     **/
    @ApiModelProperty(value = "创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     **/
    @ApiModelProperty(value = "更新时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public AdminLoginLog() {
    }

    public AdminLoginLog(Consumer<AdminLoginLog> consumer) {
        consumer.accept(this);
    }
}

