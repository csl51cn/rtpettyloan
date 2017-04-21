//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.annotation;

import com.global.framework.dbutils.annotation.ColumnDescribe;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.Map;

public class EntityDescribe {
    private String entityName;
    private Annotation classAnnotation;
    private Map<String, ColumnDescribe> columnMap;
    private LinkedList<ColumnDescribe> columnList;

    public EntityDescribe() {
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public LinkedList<ColumnDescribe> getColumnList() {
        return this.columnList;
    }

    public void setColumnList(LinkedList<ColumnDescribe> columnList) {
        this.columnList = columnList;
    }

    public Annotation getClassAnnotation() {
        return this.classAnnotation;
    }

    public void setClassAnnotation(Annotation classAnnotation) {
        this.classAnnotation = classAnnotation;
    }

    public Map<String, ColumnDescribe> getColumnMap() {
        return this.columnMap;
    }

    public void setColumnMap(Map<String, ColumnDescribe> columnMap) {
        this.columnMap = columnMap;
    }
}
