package com.huimi.common.tools.excel;

/**
 * 用于批量处理，逐行读取excel并记录处理结果
 * @author eproo
 */
public class ExcelModel {

	/**
	 * 原数据
	 */
	private String data ;
	
	/**
	 * 处理结果
	 */
	private String result ;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
