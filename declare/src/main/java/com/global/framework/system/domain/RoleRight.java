package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * @author cqchenf@qq.com
 * @date 2011-9-5 下午11:03:44
 * @version v1.0
 */
@TableMapping(primaryKey = "guid", primaryKeyType = "Single", tableName = "dc_sys_roleright")
public class RoleRight extends Entity {

	private static final long serialVersionUID = 7488459558084664838L;
	
	@ColumnMapping(columnName = "guid", columnType = "String")
	private String guid;
	@ColumnMapping(columnName = "roleId", columnType = "String")
	private String roleId;
	@ColumnMapping(columnName = "rightId", columnType = "String")
	private String rightId;
	@ColumnMapping(columnName = "rightType", columnType = "String")
	private String rightType;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRightId() {
		return rightId;
	}
	public void setRightId(String rightId) {
		this.rightId = rightId;
	}
	public String getRightType() {
		return rightType;
	}
	public void setRightType(String rightType) {
		this.rightType = rightType;
	}

}
