package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 
 * @author cqchenf@qq.com
 * @date 2011-2-28 下午4:26:47
 * @version v1.0
 */
@TableMapping(primaryKey = "dictId", primaryKeyType = "Single", tableName = "dc_sys_datadict")
public class DataDict extends Entity {

	private static final long serialVersionUID = -8325772235645609521L;

	@ColumnMapping(columnName = "dictId", columnType = "String")
	private String dictId;
	@ColumnMapping(columnName = "dictCode", columnType = "String")
	private String dictCode;
	@ColumnMapping(columnName = "dictName", columnType = "String")
	private String dictName;
	@ColumnMapping(columnName = "classId", columnType = "String")
	private String classId;
	@ColumnMapping(columnName = "sortNo", columnType = "Integer")
	private String sortNo;
	
	private String className;
	
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
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
