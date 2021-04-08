package com.huimi.core.model.system;

import lombok.Data;

import java.util.function.Consumer;

/**
 * @author fc_prc@126.com
 * @since 2018-12-24 11:38:22
 */
@Data
public class BankInfoVO {


    private Integer id;
    /**
     * 银行名称,
     */
    private String name;

    /**
     * 银行编码,
     */
    private String code;

    /**
     * 图标地址,
     */
    private String icon;

    public BankInfoVO() {
    }

    public BankInfoVO(Consumer<BankInfoVO> consumer) {
        consumer.accept(this);
    }

}