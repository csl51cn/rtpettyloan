//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.support;

public enum DAOErrorCode {
    SQL_NO_DATASOURCE("100100", "数据源DATASOURCE加载失败"),
    SQL_UPDATE_NOTPK("100101", "实体[{0}]主键为None时不支持UPDATE和SaveOrUpdate方法"),
    SQL_DELETE_NOTPK("100102", "实体[{0}]主键为None时不支持DELETE方法"),
    SQL_INVOKE_PROC_FAIL("100103", "调用存储过程[{0}]失败"),
    ENT_NOTFOUND_TABANNO("100201", "未找到实体[{0}]的表关系注解@TableMapping"),
    ENT_INVALID_TABANNO("100202", "无效的实体[{0}]的表关系注解@TableMapping"),
    ENT_TABNAME_NULL("100203", "实体[{0}]的表关系注解@TableMapping的tableName为空"),
    ENT_PKTYPE_NULL("100204", "实体[{0}]的表关系注解@TableMapping的primaryKeyType为空"),
    ENT_INVALID_PKTYPE("100205", "实体[{0}]的表关系注解@TableMapping的primaryKeyType[{1}]无效"),
    ENT_PK_NULL("100206", "实体[{0}]的主健类型不为None时,主键名不能为空"),
    ENT_INVALID_PK("100207", "实体[{0}]的表关系注解@TableMapping的primaryKey[{1}]无效,primaryKey非数据库字段名,必须为实体定义的属性名"),
    ENT_PKVALUE_NULL("100208", "实体[{0}]的主键[{1}]为联合(Combine)类型主键,主键值不能为空"),
    ENT_NOT_COMBINE_PK("100209", "实体[{0}]的主键[{1}]为联合(Combine)类型主键,联合主键的个数不能少于2个"),
    ENT_NOTFOUND_COLANNO("100210", "未找到实体{0}的表列名注解@ColumnMapping"),
    ENT_INVALID_COLANNO("100211", "无效的实体[{0}]表列名注解@ColumnMapping"),
    ENT_COLUMNNAME_NULL("100212", "实体[{0}]的表列名[{1}]对应的@ColumnMapping注解的columnName为空"),
    ENT_INVALID_COLUMTYPE("100213", "实体[{0}]的表列名[{1}]对应的@ColumnMapping注解的columnType[{2}]无效"),
    ENT_COLUMN_VALUE_NULL("100214", "实体[{0}]的属性[{1}]的value不能为空"),
    ENT_COLUMN_VALUE_THANLENGTH("100215", "实体[{0}]的属性[{1}]的value长度超过了定义的长度"),
    ENT_INVALID_COLUMN_LENGTH("100216", "实体[{0}]的属性[{1}]的长度定义无效"),
    DAO_SINGLE_RECORD("100301", "获取单条记录有多条记录返回"),
    DAO_SAVE_ENTITY("100302", "保存实体[{0}]记录时数据表已存在重复记录");

    private String code;
    private String value;

    private DAOErrorCode(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getErrorMsgByCode(String code) {
        DAOErrorCode[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            DAOErrorCode ecEnum = arr$[i$];
            if(code.equals(ecEnum.getCode())) {
                return ecEnum.getValue();
            }
        }

        return "";
    }
}
