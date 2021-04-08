package com.huimi.core.po.bizApkHistory;

import com.huimi.common.baseMapper.GenericPo;
import com.huimi.core.constant.EnumConstants;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * create by lja on 2020/7/28 17:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文件上传历史记录表")
@Table(name = "biz_apk_history")
public class BizApkHistory extends GenericPo<Integer> {
    public static final String TABLE_NAME = "biz_apk_history";

    /**
     * 文件版本号
     */
    @Column(name = "version")
    private Integer version;

    /**
     * 文件名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 文件状态  0 未启用  1 启用
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 文件地址
     */
    @Column(name = "data_url")
    private String dataUrl;

    /**
     * 备注
     */
    @Column(name = "remake")
    private String remake;


    /**
     * 备注
     */
    @Column(name = "qr_code_url")
    private String qrCodeUrl;

    @Transient
    private String stateDesc;

    public String getStateDesc() {
        return EnumConstants.HistoryState.getHtmlStr(this.state);
    }
}
