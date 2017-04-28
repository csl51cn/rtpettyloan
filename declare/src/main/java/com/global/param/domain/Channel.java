/**
 * 
 */
package com.global.param.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * @author Administrator
 * 
 */
@TableMapping(primaryKey = "channelId", primaryKeyType = "Single", tableName = "dc_pa_channel")
public class Channel extends Entity {
	@ColumnMapping(columnName = "channelId", columnType = "String")
	private String channelId;
	@ColumnMapping(columnName = "channelCode", columnType = "String")
	private String channelCode;
	@ColumnMapping(columnName = "channelCnName", columnType = "String")
	private String channelCnName;
	@ColumnMapping(columnName = "channelEnName", columnType = "String")
	private String channelEnName;
	@ColumnMapping(columnName = "reqSysCode", columnType = "String")
	private String reqSysCode;
	@ColumnMapping(columnName = "bizChnlCode", columnType = "String")
	private String bizChnlCode;
	@ColumnMapping(columnName = "bizChnlName", columnType = "String")
	private String bizChnlName;
	@ColumnMapping(columnName = "isValid", columnType = "String")
	private String isValid;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getBizChnlName() {
		return bizChnlName;
	}

	public void setBizChnlName(String bizChnlName) {
		this.bizChnlName = bizChnlName;
	}

	public String getChannelCnName() {
		return channelCnName;
	}

	public void setChannelCnName(String channelCnName) {
		this.channelCnName = channelCnName;
	}

	public String getChannelEnName() {
		return channelEnName;
	}

	public void setChannelEnName(String channelEnName) {
		this.channelEnName = channelEnName;
	}

	public String getReqSysCode() {
		return reqSysCode;
	}

	public void setReqSysCode(String reqSysCode) {
		this.reqSysCode = reqSysCode;
	}

	public String getBizChnlCode() {
		return bizChnlCode;
	}

	public void setBizChnlCode(String bizChnlCode) {
		this.bizChnlCode = bizChnlCode;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

}
