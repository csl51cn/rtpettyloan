package com.global.framework.exception;

/**
 * 错误代码常量枚举
 * @author chen.feng
 * @date 2014-6-25 下午9:00:46
 */
public enum ErrorCodeEnum {

	/**D00001:违反唯一约束条件*/
	SQL_UNIQUE("D00001", "违反唯一约束条件"),
	/**D00900:无效的SQL语句*/
	SQL_INVALID_SQL("D00900", "无效的SQL语句"),
	SQL_INVALID_CREATE("D00901", "无效 CREATE 命令"),
	SQL_INVALID_DATATYPE("D00902", "无效数据类型"),
	SQL_INVALID_TABNAME("D00903", "表名无效"),
	SQL_INVALID_TAG("D00904", "无效的标识符"),
	SQL_LESS_KEYWORD("D00905", "缺少关键字"),
	SQL_LESS_LEFT_BRACKET("D00906", "缺少左括号"),
	SQL_LESS_RIGHT_BRACKET("D00907", "缺少右括号"),
	SQL_LESS_NULL_KEYWORD("D00908", "缺少 NULL 关键字"),
	SQL_INVALID_PARAMCOUNT("D00909", "参数个数无效"),
	SQL_INVALID_CHAR("D00911", "无效的字符"),
	SQL_VALUE_TOOMANY("D00913", "值过多"),
	SQL_LESS_ADD_KEYWORD("D00914", "缺少 ADD 关键字"),
	SQL_LESS_COMMA("D00917", "缺少逗号"),
	SQL_NOTFUND_FROM("D00923", "未找到预期 FROM 关键字"),
	SQL_NOTFUND_BY("D00924", "缺少 BY 关键字"),
	SQL_NOTFUND_INTO("D00925", "缺失 INTO 关键字"),
	SQL_NOTFUND_VALUES("D00926", "缺少 VALUES 关键字"),
	SQL_NOTFUND_EQUAL("D00927", "缺少等号"),
	SQL_NOTFUND_SELECT("D00928", "缺少 SELECT 关键字"),
	SQL_NOTEND_SQLCMD("D00933", "SQL 命令未正确结束"),
	
	ENT_TABLE_ANNO("100101", "未找到实体[{0}]的表关系注解"),
	ENT_COLUMN_ANNO("100102", "未找到实体[{0}]的表列名注解"),
	DAO_GET_ENTITY("100201", "获取单个实体[{0}]有重复记录"),
	DAO_SAVE_ENTITY("100202", "保存实体[{0}]记录时数据表已存在重复记录"),
	
	SQL_INSERT("100301", "SQL插入异常[{0}]"),
	SQL_UPDATE("100302", "SQL更新异常[{0}]"),
	SQL_DELETE("100303", "SQL删除异常[{0}]"),
	SQL_QUERY("100304", "SQL查询异常[{0}]");
	
	private String code;
	private String value;
	
	ErrorCodeEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 根据错误码获取错误信息
	 * @param code
	 * @return
	 */
	public static String getErrorMsgByCode(String code) {
		for (ErrorCodeEnum ecEnum : values()) {
			if (code.equals(ecEnum.getCode())) {
				return ecEnum.getValue();
			}
		}
		return "";
	}
}
