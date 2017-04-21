package com.global.fems.message.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class MsgFaultDetail extends SerialBean {
	private String FaultCode; // 错误码
	private String FaultDesc; // 错误信息

	public String getFaultCode() {
		return FaultCode;
	}

	public void setFaultCode(String faultCode) {
		FaultCode = faultCode;
	}

	public String getFaultDesc() {
		return FaultDesc;
	}

	public void setFaultDesc(String faultDesc) {
		FaultDesc = faultDesc;
	}

}
