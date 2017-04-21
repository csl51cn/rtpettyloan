//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.annotation;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public interface ColumnType {
    String SHORT = "Short";
    String INTEGER = "Integer";
    String LONG = "Long";
    String FLOAT = "Float";
    String DOUBLE = "Double";
    String BigDecimal = "BigDecimal";
    String VARCHAR = "String";
    String DATE = "Date";
    String DATETIME = "Date";
    String TIME = "Time";
    String CLOB = "CLOB";
    String BLOB = "BLOB";
    String Timestamp = "Timestamp";

    public static enum ColumnTypeEnum {
        SHORT("Short", Short.class),
        INTEGER("Integer", Integer.class),
        LONG("Long", Long.class),
        FLOAT("Float", Float.class),
        DOUBLE("Double", Double.class),
        BigDecimal("BigDecimal", BigDecimal.class),
        VARCHAR("String", String.class),
        DATE("Date", Date.class),
        DATETIME("DateTime", java.util.Date.class),
        TIME("Time", Time.class),
        CLOB("CLOB", String.class),
        BLOB("BLOB", Object.class),
        Timestamp("Timestamp", Timestamp.class);

        private String typeCode;
        private Class<?> typeClazz;

        private ColumnTypeEnum(String typeCode, Class<?> typeClazz) {
            this.typeCode = typeCode;
            this.typeClazz = typeClazz;
        }

        public String getTypeCode() {
            return this.typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }

        public Class<?> getTypeClazz() {
            return this.typeClazz;
        }

        public void setTypeClazz(Class<?> typeClazz) {
            this.typeClazz = typeClazz;
        }

        public static boolean isCheckTypeExist(String typeCode) {
            ColumnType.ColumnTypeEnum[] arr$ = values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                ColumnType.ColumnTypeEnum ct = arr$[i$];
                if(ct.getTypeCode().equals(typeCode)) {
                    return true;
                }
            }

            return false;
        }

        public static Class<?> getType(String typeCode) {
            ColumnType.ColumnTypeEnum[] arr$ = values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                ColumnType.ColumnTypeEnum ct = arr$[i$];
                if(ct.getTypeCode().equals(typeCode)) {
                    return ct.getTypeClazz();
                }
            }

            return null;
        }
    }
}
