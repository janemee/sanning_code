package com.huimi.core.util.yypay.model;

import com.huimi.core.constant.HMTypeColro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.function.Consumer;

@Data
public class ResultModel {

    /**
     * 商户订单号
     */
    private String orderNo;
    /**
     * 甬易流水号
     */
    private String tranSerialNo;
    /**
     * 状态
     */
    private String tranStat;
    /**
     * 金额
     */
    private String orderAmt;
    /**
     * 全部消息
     */
    private String fullMsg;
    /**
     * 消息类型  RECHARGE 充值回调，CASHBACK 提现回调，CASHTUIHUI 提现退汇
     */
    private String type;


    public ResultModel() {
    }

    public ResultModel(Consumer<ResultModel> consumer) {
        consumer.accept(this);
    }


    @AllArgsConstructor
    public enum Stat {
        T0("0", "未支付/待汇款", HMTypeColro.T3),
        T1("1", "已支付/成功", HMTypeColro.T1),
        T2("2", "支付失败/失败", HMTypeColro.T2),
        T3("其他", "汇款处理中（代付专用）", HMTypeColro.T2),

        ;
        @Getter
        public final String code;
        @Getter
        public final String value;
        public final String lableColor;
    }
}
