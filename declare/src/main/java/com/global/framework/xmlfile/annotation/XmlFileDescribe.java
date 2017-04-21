package com.global.framework.xmlfile.annotation;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.Map;

import com.global.framework.dbutils.support.Entity;

/**
 * 
 * @author chen.feng
 * @date 2014-6-30 下午4:09:22
 */
@SuppressWarnings("serial")
public class XmlFileDescribe extends Entity {

	/** 实体名 */
	private String entityName;
	/** TableMapping */
	private Annotation classAnnotation;
	/** key为实体字段名，value为ColumnDescribe */
	private Map<String, FieldDescribe> fieldMap;
	private LinkedList<FieldDescribe> fieldList;
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Annotation getClassAnnotation() {
		return classAnnotation;
	}
	public void setClassAnnotation(Annotation classAnnotation) {
		this.classAnnotation = classAnnotation;
	}
	public Map<String, FieldDescribe> getFieldMap() {
		return fieldMap;
	}
	public void setFieldMap(Map<String, FieldDescribe> fieldMap) {
		this.fieldMap = fieldMap;
	}
	public LinkedList<FieldDescribe> getFieldList() {
		return fieldList;
	}
	public void setFieldList(LinkedList<FieldDescribe> fieldList) {
		this.fieldList = fieldList;
	}
}
