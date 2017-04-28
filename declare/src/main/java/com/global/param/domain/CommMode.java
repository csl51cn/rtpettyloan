package com.global.param.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 类描述： 
 *
 * @author chen.feng
 * @date 2015-7-10
 * @version v1.0
 */
@TableMapping(primaryKey = "channelId", primaryKeyType = "Single", tableName = "dc_pa_channel_commmode")
public class CommMode extends Entity {

	@ColumnMapping(columnName="channelId", columnType="String")
	private String channelId;
	@ColumnMapping(columnName="commMode", columnType="String")
	private String commMode;
	@ColumnMapping(columnName="format", columnType="String")
	private String format;
	@ColumnMapping(columnName="ip", columnType="String")
	private String ip;
	
	private String channelCode;
	private String channelName;
	private String reqSysCode;
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getCommMode() {
		return commMode;
	}
	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getReqSysCode() {
		return reqSysCode;
	}
	public void setReqSysCode(String reqSysCode) {
		this.reqSysCode = reqSysCode;
	}
}
