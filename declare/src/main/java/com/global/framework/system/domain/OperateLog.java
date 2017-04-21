package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

@TableMapping(primaryKey = "logId", primaryKeyType = "Single", tableName = "sys_operatelog")
public class OperateLog extends Entity {
	private static final long serialVersionUID = -3240304915824370285L;

	@ColumnMapping(columnName = "logId", columnType = "String")
	private String logId;
	@ColumnMapping(columnName = "userId", columnType = "String")
	private String userId;//操作人ID
	@ColumnMapping(columnName = "userName", columnType = "String")
	private String userName;// 操作人姓名
	@ColumnMapping(columnName = "operateType", columnType = "String")
	private String operateType;// 日志类型
	@ColumnMapping(columnName = "content", columnType = "String")
	private String content;// 日志
	@ColumnMapping(columnName = "operateIp", columnType = "String")
	private String operateIp;// 操作员登录IP
	@ColumnMapping(columnName = "operateDate", columnType = "Timestamp")
	private String operateDate;// 操作时间

	private String startDate;
	private String endDate;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperateIp() {
		return operateIp;
	}

	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
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
