package com.global.fems.message.util;

/**
 * 类描述：接口交易码配置实体类
 * 
 * @author chen.feng
 * @date 2015-6-13
 * @version v1.0
 */
public class IntfCodeCfg {

	private String txCode;
	private String txName;
	private String recvEntity;
	private String rspEntity;
	private String recvXmlMapping;
	private String rspXmlMapping;
	private String serviceMethod;
	private String isValid;
	private String version;

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getTxName() {
		return txName;
	}

	public void setTxName(String txName) {
		this.txName = txName;
	}

	public String getRecvEntity() {
		return recvEntity;
	}

	public void setRecvEntity(String recvEntity) {
		this.recvEntity = recvEntity;
	}

	public String getRspEntity() {
		return rspEntity;
	}

	public void setRspEntity(String rspEntity) {
		this.rspEntity = rspEntity;
	}

	public String getRecvXmlMapping() {
		return recvXmlMapping;
	}

	public void setRecvXmlMapping(String recvXmlMapping) {
		this.recvXmlMapping = recvXmlMapping;
	}

	public String getRspXmlMapping() {
		return rspXmlMapping;
	}

	public void setRspXmlMapping(String rspXmlMapping) {
		this.rspXmlMapping = rspXmlMapping;
	}

	public String getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
