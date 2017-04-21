package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 
 * @author cqchenf@qq.com
 * @date 2013-2-28 下午4:23:50
 * @version v1.0
 */
@TableMapping(primaryKey = "guid", primaryKeyType = "Single", tableName = "sys_userdataright")
public class UserDataRight extends Entity {

	private static final long serialVersionUID = -7445870074170771677L;
	
	@ColumnMapping(columnName = "guid", columnType = "String")
	private String guid;
	@ColumnMapping(columnName = "userId", columnType = "String")
	private String userId;
	@ColumnMapping(columnName = "dataRight", columnType = "String")
	private String dataRight;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDataRight() {
		return dataRight;
	}
	public void setDataRight(String dataRight) {
		this.dataRight = dataRight;
	}

}
