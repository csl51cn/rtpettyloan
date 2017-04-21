package com.global.fems.business.domain;

import com.global.framework.util.SysUtils;

/**
 * 个人结售汇额度查询响应实体
 * 
 * @author Sili Jiang
 * @version 2015-07-06
 */
public class SpfeAmtQuery{
	/**
	 * 证件类型代码
	 */
	private String idTypeCode;
	/**
	 * 国家/地区代码
	 */
	private String cityCode;
	/**
	 * 证件号码
	 */
	private String idCode;	
	/**
	 * 交易类型
	 */
	private String tradeType;
	/**
	 * 本年额度内已结售汇金额折美元
	 */
	private String amtUsd;
	/**
	 * 本年额度内剩余可结售汇金额折美元
	 */
	private String amtBalanceUsd;
	/**
	 * 当日已发生的现钞结售汇金额折美元
	 */
	private String todayAmtUsd;
	/**
	 * 交易主体姓名
	 */
	private String custName;
	/**
	 * 交易主体类型
	 */
	private String custType;
	/**
	 * 个人主体分类状态代码
	 */
	private String typeStatus;
	
	private String typeStatusCode;
	/**
	 * 发布日期
	 */
	private String pubDate;
	/**
	 * 到期日期
	 */
	private String endDate;
	/**
	 * 发布原因
	 */
	private String pubReason;
	/**
	 * 发布原因代码
	 */
	private String pubCode;
	/**
	 * 确认书签署状态
	 */
	private String signStatus;	
	
	private String signStatusCode;

	/**
	 * get 证件类型代码
	 * 
	 * @return 证件类型代码 
	 */
	public String getIdTypeCode() {
		return idTypeCode;
	}

	/**
	 * set 证件类型代码
	 * 
	 * @param idTypeCode 证件类型代码
	 */
	public void setIdTypeCode(String idTypeCode) {
		this.idTypeCode = idTypeCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getAmtUsd() {
		return SysUtils.formatAmt(amtUsd);
	}

	public void setAmtUsd(String amtUsd) {
		this.amtUsd = SysUtils.parseAmt(amtUsd);
	}

	public String getAmtBalanceUsd() {
		return SysUtils.formatAmt(amtBalanceUsd);
	}

	public void setAmtBalanceUsd(String amtBalanceUsd) {
		this.amtBalanceUsd = SysUtils.parseAmt(amtBalanceUsd);
	}

	public String getTodayAmtUsd() {
		return SysUtils.formatAmt(todayAmtUsd);
	}

	public void setTodayAmtUsd(String todayAmtUsd) {
		this.todayAmtUsd = SysUtils.parseAmt(todayAmtUsd);
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(String typeStatus) {
		this.typeStatus = typeStatus;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPubReason() {
		return pubReason;
	}

	public void setPubReason(String pubReason) {
		this.pubReason = pubReason;
	}

	public String getPubCode() {
		return pubCode;
	}

	public void setPubCode(String pubCode) {
		this.pubCode = pubCode;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public String getTypeStatusCode() {
		return typeStatusCode;
	}

	public void setTypeStatusCode(String typeStatusCode) {
		this.typeStatusCode = typeStatusCode;
	}

	public String getSignStatusCode() {
		return signStatusCode;
	}

	public void setSignStatusCode(String signStatusCode) {
		this.signStatusCode = signStatusCode;
	}
}
