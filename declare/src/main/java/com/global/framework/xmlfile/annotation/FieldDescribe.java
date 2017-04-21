package com.global.framework.xmlfile.annotation;

import com.global.framework.dbutils.support.Entity;


/**
 * 
 * @author chen.feng
 * @date 2014-6-30 下午4:06:17
 */
@SuppressWarnings("serial")
public class FieldDescribe extends Entity {

	/**
	 * JAVA实体属性名
	 */
	private String attrName;
	/**
	 * XML文件字段名
	 */
	private String fieldName;
	/**
	 * XML文件字段类型
	 */
	private String fieldType;
	
	private String fieldLength = "";
	
	private boolean fieldIsNotNull = false;
	
	private String dateFormatPattern = "";
	
	private String className = "";

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}

	public boolean isFieldIsNotNull() {
		return fieldIsNotNull;
	}

	public void setFieldIsNotNull(boolean fieldIsNotNull) {
		this.fieldIsNotNull = fieldIsNotNull;
	}

	public String getDateFormatPattern() {
		return dateFormatPattern;
	}

	public void setDateFormatPattern(String dateFormatPattern) {
		this.dateFormatPattern = dateFormatPattern;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
