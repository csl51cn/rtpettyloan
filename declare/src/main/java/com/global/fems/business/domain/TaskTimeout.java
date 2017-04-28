package com.global.fems.business.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 类描述： 任务超时记录
 * 
 * @author chen.feng
 * @date 2015-7-8
 * @version v1.0
 */
@TableMapping(primaryKey = "nguid", primaryKeyType = "Single", tableName = "dc_bu_task_timeout")
public class TaskTimeout extends Entity {

	@ColumnMapping(columnName = "nguid", columnType = "String")
	private String nguid;
	@ColumnMapping(columnName = "txnSerialNo", columnType = "String")
	private String txnSerialNo;
	@ColumnMapping(columnName = "bizType", columnType = "String")
	private String bizType;
	@ColumnMapping(columnName = "idTypeCode", columnType = "String")
	private String idTypeCode;
	@ColumnMapping(columnName = "idCode", columnType = "String")
	private String idCode;
	@ColumnMapping(columnName = "ctyCode", columnType = "String")
	private String ctyCode;
	@ColumnMapping(columnName = "txDate", columnType = "Timestamp")
	private String txDate;
	@ColumnMapping(columnName = "dealTime", columnType = "Timestamp")
	private String dealTime;
	@ColumnMapping(columnName = "dealState", columnType = "String")
	private String dealState;// 0:未处理，1：处理成功，2：处理失败
	@ColumnMapping(columnName = "refNo", columnType = "String")
	private String refNo;
	@ColumnMapping(columnName = "retCode", columnType = "String")
	private String retCode;
	@ColumnMapping(columnName = "retMsg", columnType = "String")
	private String retMsg;
	@ColumnMapping(columnName = "launchOperNo", columnType = "String")
	private String launchOperNo;

	public String getTxnSerialNo() {
		return txnSerialNo;
	}

	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getIdTypeCode() {
		return idTypeCode;
	}

	public void setIdTypeCode(String idTypeCode) {
		this.idTypeCode = idTypeCode;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getCtyCode() {
		return ctyCode;
	}

	public void setCtyCode(String ctyCode) {
		this.ctyCode = ctyCode;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getDealState() {
		return dealState;
	}

	public void setDealState(String dealState) {
		this.dealState = dealState;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getNguid() {
		return nguid;
	}

	public void setNguid(String nguid) {
		this.nguid = nguid;
	}

	public String getLaunchOperNo() {
		return launchOperNo;
	}

	public void setLaunchOperNo(String launchOperNo) {
		this.launchOperNo = launchOperNo;
	}
}
