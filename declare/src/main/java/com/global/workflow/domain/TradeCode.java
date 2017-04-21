package com.global.workflow.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 交易类型定义
 * 
 * @author chen.feng
 * @date 2013-6-16 下午8:36:00
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "tradeNo", primaryKeyType = "Single", tableName = "wfl_tradecode")
public class TradeCode extends Entity {

	@ColumnMapping(columnName = "tradeNo", columnType = "String")
	private String tradeNo;
	@ColumnMapping(columnName = "tradeName", columnType = "String")
	private String tradeName;
	@ColumnMapping(columnName = "currentIndex", columnType = "String")
	private String currentIndex;
	@ColumnMapping(columnName = "rule", columnType = "String")
	private String rule;
	@ColumnMapping(columnName = "currDate", columnType = "String")
	private String currDate;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(String currentIndex) {
		this.currentIndex = currentIndex;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getCurrDate() {
		return currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

}
