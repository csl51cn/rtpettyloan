package com.global.workflow.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 
 * @author chen.feng
 * @date 2013-6-20
 * @version v1.0
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "txnSerialNo", primaryKeyType = "Single", tableName = "dc_wfl_taskinfo")
public class TaskInfo extends Entity {

	@ColumnMapping(columnName = "txnSerialNo", columnType = "String")
	private String txnSerialNo;
	@ColumnMapping(columnName = "bizNo", columnType = "String")
	private String bizNo;
	@ColumnMapping(columnName = "tradeNo", columnType = "String")
	private String tradeNo;
	@ColumnMapping(columnName = "belongOrgNo", columnType = "String")
	private String belongOrgNo;
	@ColumnMapping(columnName = "transOrgNo", columnType = "String")
	private String transOrgNo;
	@ColumnMapping(columnName = "createDate", columnType = "Timestamp", dateFormatPattern = "yyyy-MM-dd HH:mm:ss")
	private String createDate;
	@ColumnMapping(columnName = "finishDate", columnType = "Timestamp", dateFormatPattern = "yyyy-MM-dd HH:mm:ss")
	private String finishDate;
	@ColumnMapping(columnName = "createUser", columnType = "String")
	private String createUser;
	@ColumnMapping(columnName = "transState", columnType = "String")
	private String transState;
	@ColumnMapping(columnName = "actors", columnType = "String")
	private String actors;
	@ColumnMapping(columnName = "refNo", columnType = "String")
	private String refNo;
	@ColumnMapping(columnName = "channelId", columnType = "String")
	private String channelId;
	@ColumnMapping(columnName = "reqSeqNo", columnType = "String")
	private String reqSeqNo;
	@ColumnMapping(columnName = "sfzx", columnType = "String")
	private String sfzx;
	
	//查询条件
	private String transStateName;
	private String startCreateTime;
	private String endCreateTime;
	private String startFinishTime;
	private String endFinishTime;
	private String tradeName;
	private String url;

	public String getTxnSerialNo() {
		return txnSerialNo;
	}

	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getBelongOrgNo() {
		return belongOrgNo;
	}

	public void setBelongOrgNo(String belongOrgNo) {
		this.belongOrgNo = belongOrgNo;
	}

	public String getTransOrgNo() {
		return transOrgNo;
	}

	public void setTransOrgNo(String transOrgNo) {
		this.transOrgNo = transOrgNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getTransState() {
		return transState;
	}

	public void setTransState(String transState) {
		this.transState = transState;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(String startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getStartFinishTime() {
		return startFinishTime;
	}

	public void setStartFinishTime(String startFinishTime) {
		this.startFinishTime = startFinishTime;
	}

	public String getEndFinishTime() {
		return endFinishTime;
	}

	public void setEndFinishTime(String endFinishTime) {
		this.endFinishTime = endFinishTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTransStateName() {
		return transStateName;
	}

	public void setTransStateName(String transStateName) {
		this.transStateName = transStateName;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getReqSeqNo() {
		return reqSeqNo;
	}

	public void setReqSeqNo(String reqSeqNo) {
		this.reqSeqNo = reqSeqNo;
	}

	public String getSfzx() {
		return sfzx;
	}

	public void setSfzx(String sfzx) {
		this.sfzx = sfzx;
	}
}
