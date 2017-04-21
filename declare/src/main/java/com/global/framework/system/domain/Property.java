package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

@SuppressWarnings("serial")
@TableMapping(primaryKey = "key", primaryKeyType = "Single", tableName = "SYS_PROPERTY")
public class Property extends Entity {

	@ColumnMapping(columnName = "KEY", columnType = "String")
	private String key;
	@ColumnMapping(columnName = "VALUE", columnType = "String")
	private String value;
	@ColumnMapping(columnName = "REMARK", columnType = "String")
	private String remark;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
