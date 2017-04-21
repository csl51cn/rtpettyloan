package com.global.fems.business.domain;

/**
 * 美元折算率查询请求响应实体
 * 
 * @author Sili Jiang
 * @version 2015-07-07
 * 
 */
public class UsdCvsRate {
	
	/**
	 * 年月
	 */
	private String yearMonth;
	/**
	 * 币种
	 */
	private String currencyCode;

	/**
	 * 折算率
	 */
	private String exchange;

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

}
