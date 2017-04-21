package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 异常日志
 * 
 * @author bai.shulun and longjun
 * 
 */
@TableMapping(primaryKey = "exceptionlogId", primaryKeyType = "Single", tableName = "sys_exceptionlog")
public class ExceptionLog extends Entity {

	@ColumnMapping(columnName = "exceptionlogId", columnType = "String")
	private String exceptionlogId;// 主键

	@ColumnMapping(columnName = "bizNo", columnType = "String")
	private String bizNo;// 业务编号

	@ColumnMapping(columnName = "bizName", columnType = "String")
	private String bizName;// 业务名称

	@ColumnMapping(columnName = "operateDate", columnType = "String")
	private String operateDate;// 操作日期

	@ColumnMapping(columnName = "exceptionType", columnType = "String")
	private String exceptionType;// 异常日志类型

	@ColumnMapping(columnName = "exceptionLog", columnType = "String")
	private String exceptionLog;// 异常日志

	@ColumnMapping(columnName = "isDealed", columnType = "String")
	private String isDealed; // 状态

	private String startDate;
	private String endDate;

	public String getExceptionlogId() {
		return exceptionlogId;
	}

	public void setExceptionlogId(String exceptionlogId) {
		this.exceptionlogId = exceptionlogId;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getExceptionLog() {
		return exceptionLog;
	}

	public void setExceptionLog(String exceptionLog) {
		this.exceptionLog = exceptionLog;
	}

	public String getIsDealed() {
		return isDealed;
	}

	public void setIsDealed(String isDealed) {
		this.isDealed = isDealed;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
