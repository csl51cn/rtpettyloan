package com.global.fems.business.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

@SuppressWarnings("serial")
@TableMapping(primaryKey = "", primaryKeyType = "None", tableName = "PA_SAFEEXRATE")
public class SafeExRate extends Entity {

	@ColumnMapping(columnName = "year_month", columnType = "String")
	private String yearMonth;
	@ColumnMapping(columnName = "currency_code", columnType = "String")
	private String currencyCode;
	@ColumnMapping(columnName = "exchange", columnType = "Double")
	private String exchange;
	@ColumnMapping(columnName = "importdate", columnType = "Timestamp")
	private String importDate;
	@ColumnMapping(columnName = "isValid", columnType = "String")
	private String isValid;
	@ColumnMapping(columnName = "operno", columnType = "String")
	private String operno;
	
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
	public String getImportDate() {
		return importDate;
	}
	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getOperno() {
		return operno;
	}
	public void setOperno(String operno) {
		this.operno = operno;
	}
}
