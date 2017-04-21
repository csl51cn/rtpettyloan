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
@TableMapping(primaryKey = "channelId,faceCode", primaryKeyType = "Combine", tableName = "pa_channel_interface")
public class ChannelInterface extends Entity {

	@ColumnMapping(columnName="channelId", columnType="String")
	private String channelId;
	@ColumnMapping(columnName="faceCode", columnType="String")
	private String faceCode;
	@ColumnMapping(columnName="faceName", columnType="String")
	private String faceName;
	@ColumnMapping(columnName="isValid", columnType="String")
	private String isValid;
	
	private String channelCode;
	private String channelName;
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getFaceCode() {
		return faceCode;
	}
	public void setFaceCode(String faceCode) {
		this.faceCode = faceCode;
	}
	public String getFaceName() {
		return faceName;
	}
	public void setFaceName(String faceName) {
		this.faceName = faceName;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
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
}
