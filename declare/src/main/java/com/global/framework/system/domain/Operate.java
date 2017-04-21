package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 
 * @author cqchenf@qq.com
 * @date 2011-11-15 下午3:07:03
 * @version v1.0
 */
@TableMapping(primaryKey = "operateId", primaryKeyType = "Single", tableName = "sys_operate")
public class Operate extends Entity {

	private static final long serialVersionUID = -4389771240618068526L;

	@ColumnMapping(columnName = "operateId", columnType = "String")
	private String operateId;
	@ColumnMapping(columnName = "operateName", columnType = "String")
	private String operateName;
	@ColumnMapping(columnName = "menuId", columnType = "String")
	private String menuId;
	@ColumnMapping(columnName = "reqUrl", columnType = "String")
	private String reqUrl;
	@ColumnMapping(columnName = "isCheck", columnType = "String")
	private String isCheck;
	@ColumnMapping(columnName = "sortNo", columnType = "Integer")
	private String sortNo;
	
	private String menuName;

	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
}
