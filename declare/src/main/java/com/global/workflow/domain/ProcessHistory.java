package com.global.workflow.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 流程历史
 * 
 * @author chen.feng
 * @date 2013-6-11 下午5:36:48
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "", primaryKeyType = "None", tableName = "wfl_processhistory")
public class ProcessHistory extends Entity {

	@ColumnMapping(columnName = "txnSerialNo", columnType = "String")
	private String txnSerialNo;
	@ColumnMapping(columnName = "stepName", columnType = "String")
	private String stepName;// 活动名称，即步骤
	@ColumnMapping(columnName = "operId", columnType = "String")
	private String operId; 
	@ColumnMapping(columnName = "dealTime", columnType = "Timestamp")
	private String dealTime;
	@ColumnMapping(columnName = "remark", columnType = "String")
	private String remark;
	private String operName;

	public String getTxnSerialNo() {
		return txnSerialNo;
	}

	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

}
