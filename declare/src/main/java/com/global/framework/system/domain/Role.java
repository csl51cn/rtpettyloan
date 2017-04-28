package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * @author cqchenf@qq.com
 * @date 2011-9-5 下午11:03:44
 * @version v1.0
 */
@TableMapping(primaryKey = "roleId", primaryKeyType = "Single", tableName = "dc_sys_role")
public class Role extends Entity {
	private static final long serialVersionUID = 1425115705396866724L;
	
	@ColumnMapping(columnName = "roleId", columnType = "String")
	private String roleId;
	@ColumnMapping(columnName = "roleName", columnType = "String")
	private String roleName;
	@ColumnMapping(columnName = "isFix", columnType = "String")
	private String isFix;
	@ColumnMapping(columnName = "sortNo", columnType = "Integer")
	private String sortNo;
	@ColumnMapping(columnName = "remark", columnType = "String")
	private String remark;
	
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
	public String getIsFix() {
		return isFix;
	}
	public void setIsFix(String isFix) {
		this.isFix = isFix;
	}
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
