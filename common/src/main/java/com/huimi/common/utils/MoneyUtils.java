package com.huimi.common.utils;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;

public class MoneyUtils {

    /**
     * 计算利息 四舍五入
     *
     * @param money        投资金额
     * @param annualized   投资年化(%)
     * @param loanDate     投资时间(年/月/天)
     * @param loandatetype 日期类型 2月,3天
     * @return
     */
    public static BigDecimal getInterest(final BigDecimal money, final BigDecimal annualized, Integer loanDate, Integer loandatetype) {
        if (money == null || money.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("投资金额错误");
        if (annualized == null || annualized.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("年化比例错误");
        BigDecimal result;
        switch (loandatetype) {
            case 3:// 利息=投资金额*年化/365*投资天数
                result = NumberUtil.div(money.multiply(annualized).multiply(new BigDecimal(0.01)).multiply(new BigDecimal(loanDate)), 365, 4);
                break;
            case 2:// 利息=投资金额*年化/12*投资月数
                result = NumberUtil.div(money.multiply(annualized).multiply(new BigDecimal(0.01)).multiply(new BigDecimal(loanDate)), 12, 4);
                break;
            default:
                throw new RuntimeException("日期类型错误");
        }
        return BigDecimalUtils.round(result, 2);
    }

    /**
     * 金额无差额分期(-/+0.01) 四舍五入
     *
     * @param money
     * @param period
     * @return 0:perio-1期  1:最后一期
     */
    public static BigDecimal[] averageMoney(final BigDecimal money, final Integer period) {
        BigDecimal[] result = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO};
        if (money == null) throw new RuntimeException("金额错误");
        if (period == null) throw new RuntimeException("期数错误");
        if (period <= 1) {
            result[0] = money;
            result[1] = money;
        } else {
            BigDecimal n = NumberUtil.div(money, period, 2);
            result[0] = n;
            result[1] = money.subtract(n.multiply(new BigDecimal(period - 1)));
        }
        return result;
    }

    /**
     * 获得负数
     *
     * @param num
     * @return
     */
    public static BigDecimal neg(BigDecimal num) {
        if (num == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.ZERO.subtract(num);
    }

}
