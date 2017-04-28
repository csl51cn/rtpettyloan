package com.global.workflow.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 
 * @author chen.feng
 * @date 2015-6-25
 * @version v1.0
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "privId", primaryKeyType = "Single", tableName = "dc_wfl_tradeprivilege")
public class TradePrivilege extends Entity {

	@ColumnMapping(columnName = "privId", columnType = "Integer")
	private String privId;
	@ColumnMapping(columnName = "tradeNo", columnType = "String")
	private String tradeNo;
	@ColumnMapping(columnName = "opeId", columnType = "String")
	private String opeId;

	public String getPrivId() {
		return privId;
	}

	public void setPrivId(String privId) {
		this.privId = privId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOpeId() {
		return opeId;
	}

	public void setOpeId(String opeId) {
		this.opeId = opeId;
	}

}
