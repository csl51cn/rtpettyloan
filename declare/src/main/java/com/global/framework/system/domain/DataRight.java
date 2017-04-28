package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * @author chen.feng
 * @date 2013-3-26
 * @version v1.0
 */
@TableMapping(primaryKey = "roleId", primaryKeyType = "Single", tableName = "dc_sys_roledataright")
public class DataRight extends Entity {
	private static final long serialVersionUID = -241715378123495689L;
	
	/**本人*/
	public final static String RIGHT_TYPE_SELF = "0";
	/**本机构*/
	public final static String RIGHT_TYPE_SELFORG = "1";
	/**本机构及下级机构*/
	public final static String RIGHT_TYPE_SELFORG_CHILDORG = "2";
	/**指定机构*/
	public final static String RIGHT_TYPE_CHECKORG = "3";

	@ColumnMapping(columnName = "roleId", columnType = "String")
	private String roleId;// 角色ID
	@ColumnMapping(columnName = "rightType", columnType = "Integer")
	private String rightType;// 数据权限类型0:本人,1:本机构,2:本机构及下级机构,3:指定机构
	@ColumnMapping(columnName = "actionType", columnType = "Integer")
	private String actionType;// 操作类型0:全部,1:查看,2:操作
	@ColumnMapping(columnName = "orgnolist", columnType = "String")
	private String orgNoList;// 权限机构列表

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRightType() {
		return rightType;
	}

	public void setRightType(String rightType) {
		this.rightType = rightType;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getOrgNoList() {
		return orgNoList;
	}

	public void setOrgNoList(String orgNoList) {
		this.orgNoList = orgNoList;
	}

}
