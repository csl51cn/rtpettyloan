package com.global.workflow.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 交易工作流模板
 * 
 * @author chen.feng
 * @date 2015-6-20
 * @version v1.0
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "tradeNo", primaryKeyType = "Single", tableName = "wfl_tradetemplate")
public class TradeTemplate extends Entity {

	@ColumnMapping(columnName = "tradeNo", columnType = "String")
	private String tradeNo;// 交易编号
	@ColumnMapping(columnName = "isUsed", columnType = "String")
	private String isUsed;// 是否使用工作流
	@ColumnMapping(columnName = "isHandle", columnType = "String")
	private String isHandle;// 是否经办
	@ColumnMapping(columnName = "isCheck", columnType = "String")
	private String isCheck;// 是否复核
	@ColumnMapping(columnName = "isAuth", columnType = "String")
	private String isAuth;// 是否授权
	
	private String tradeName;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
}
