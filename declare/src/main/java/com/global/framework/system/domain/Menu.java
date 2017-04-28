
package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * @author cqchenf@qq.com
 * @date 2011-9-5 下午11:03:44
 * @version v1.0
 */
@TableMapping(primaryKey = "menuId", primaryKeyType = "Single", tableName = "dc_sys_menu")
public class Menu extends Entity {
	private static final long serialVersionUID = -7575071118655045066L;
	
	@ColumnMapping(columnName = "menuId", columnType = "String")
	private String menuId;
	@ColumnMapping(columnName = "menuName", columnType = "String")
	private String menuName;
	@ColumnMapping(columnName = "parentMenuId", columnType = "String")
	private String parentMenuId;
	@ColumnMapping(columnName = "isFunction", columnType = "String")
	private String isFunction;//Y:是,N:否
	@ColumnMapping(columnName = "accessUrl", columnType = "String")
	private String accessUrl;
	@ColumnMapping(columnName = "sortNo", columnType = "Integer")
	private String sortNo;
	@ColumnMapping(columnName = "tradeNo", columnType = "String")
	private String tradeNo;
	
	private String mid;//前台页面菜单显示所需
	
	private String parentMenuName;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getIsFunction() {
		return isFunction;
	}

	public void setIsFunction(String isFunction) {
		this.isFunction = isFunction;
	}

	public String getAccessUrl() {
		return accessUrl;
	}

	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
}
