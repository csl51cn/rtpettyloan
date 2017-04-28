package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 
 * @author cqchenf@qq.com
 * @date 2011-2-28 下午4:29:10
 * @version v1.0
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "classId", primaryKeyType = "Single", tableName = "dc_sys_datadictclass")
public class DataDictClass extends Entity {

	@ColumnMapping(columnName = "classId", columnType = "String")
	private String classId;
	@ColumnMapping(columnName = "classCode", columnType = "String")
	private String classCode;
	@ColumnMapping(columnName = "className", columnType = "String")
	private String className;
	@ColumnMapping(columnName = "sortNo", columnType = "Integer")
	private String sortNo;
	
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

}
