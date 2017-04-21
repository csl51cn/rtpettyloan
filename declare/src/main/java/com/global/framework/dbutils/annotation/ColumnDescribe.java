//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.annotation;

public class ColumnDescribe {
    private String fieldName;
    private String columnName;
    private String columnType;
    private String columnLength = "";
    private boolean columnIsNotNull = false;
    private String dateFormatPattern = "";

    public ColumnDescribe() {
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnLength() {
        return this.columnLength;
    }

    public void setColumnLength(String columnLength) {
        this.columnLength = columnLength;
    }

    public boolean getColumnIsNotNull() {
        return this.columnIsNotNull;
    }

    public void setColumnIsNotNull(boolean columnIsNotNull) {
        this.columnIsNotNull = columnIsNotNull;
    }

    public String getDateFormatPattern() {
        return this.dateFormatPattern;
    }

    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }
}
