package com.global.fems.business.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 查询个人外汇账户实体
 * 
 * @author zhaoyating
 * 
 */
@TableMapping(primaryKey = "CISNO", primaryKeyType = "Single", tableName = "PA_ACCOUNT")
public class Account  extends Entity {

	@ColumnMapping(columnName = "CISNO", columnType = "String")
	private String CISNO; // 银行核心客户号 
	@ColumnMapping(columnName = "IDNO", columnType = "String")
	private String IDNO; // 证件号码 
	@ColumnMapping(columnName = "CUR", columnType = "String")
	private String CUR; // 币种
	@ColumnMapping(columnName = "OCCUPY_LMT_STATUS", columnType = "String")
	private String ACCT;// 外汇账号 
	public String getCISNO() {
		return CISNO;
	}
	public void setCISNO(String cISNO) {
		CISNO = cISNO;
	}
	public String getIDNO() {
		return IDNO;
	}
	public void setIDNO(String iDNO) {
		IDNO = iDNO;
	}
	public String getCUR() {
		return CUR;
	}
	public void setCUR(String cUR) {
		CUR = cUR;
	}
	public String getACCT() {
		return ACCT;
	}
	public void setACCT(String aCCT) {
		ACCT = aCCT;
	}
	
	
}