package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * @author cqchenf@qq.com
 * @date 2011-9-5 下午11:03:44
 * @version v1.0
 */
@TableMapping(primaryKey = "guid", primaryKeyType = "Single", tableName = "dc_sys_userrole")
public class UserRole extends Entity {

	private static final long serialVersionUID = -3477490441824955680L;

	@ColumnMapping(columnName = "guid", columnType = "String")
	private String guid;
	@ColumnMapping(columnName = "userId", columnType = "String")
	private String userId;
	@ColumnMapping(columnName = "roleId", columnType = "String")
	private String roleId;
	
	private String roleName;
	
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
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
