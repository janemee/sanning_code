package com.huimi.common.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author tiankun
 * 
 */
public class GeneratorNo {

	protected final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 获取微信菜单key值
	 * 
	 * @return
	 */
	public static String getMenuKey() {
		int hashCode = getHashCode();
		return "WX" + String.format("%010d", hashCode);
	}

	/**
	 * 获取图文组UUID
	 * 
	 * @return
	 */
	public static String getSourceUuid() {
		int hashCode = getHashCode();
		return "S" + String.format("%010d", hashCode);
	}

	private static int getHashCode() {
		int hashCode = UUID.randomUUID().toString().hashCode();
		if (hashCode < 0) {
			hashCode = -hashCode;
		}
		return hashCode;
	}

    /**
     * 出金订单编号
     * @return 订单号
     */
    public static String getOrderNo() {
        return "CJ" + ToolDateTime.format(ToolDateTime.getDate(),"yyyyMMddHHmmssSSS");
    }

    /**
     * 生成出金批次号
     * @return 批次号
     */
    public static String getBatchNo() {
        return String.valueOf(ToolDateTime.getDateByTime());
    }


    /**
     * 出金订单编号
     * @return 订单
     */
    public static String getCashBillNo() {
        Date date = ToolDateTime.getDate();
        return "CJ" + date.getTime() + "";
    }

	public static void main(String[] args) {
		System.out.println(getMenuKey());
	}
}
