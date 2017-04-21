package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 
 * @author cqchenf@qq.com
 * @date 2013-1-27 下午7:05:38
 * @version v1.0
 */
@TableMapping(primaryKey = "logId", primaryKeyType = "Single", tableName = "sys_userloginlog")
public class UserLoginLog extends Entity {

	private static final long serialVersionUID = -6234750868696355754L;
	@ColumnMapping(columnName = "logId", columnType = "String")
	private String logId;
	@ColumnMapping(columnName = "userId", columnType = "String")
	private String userId;
	@ColumnMapping(columnName = "loginIp", columnType = "String")
	private String loginIp;
	@ColumnMapping(columnName = "loginTime", columnType = "Date")
	private String loginTime;
	@ColumnMapping(columnName = "logoutTime", columnType = "Date")
	private String logoutTime;
	@ColumnMapping(columnName = "logoutStatus", columnType = "Integer")
	private String logoutStatus;
	
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
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}
	public String getLogoutStatus() {
		return logoutStatus;
	}
	public void setLogoutStatus(String logoutStatus) {
		this.logoutStatus = logoutStatus;
	}
}
