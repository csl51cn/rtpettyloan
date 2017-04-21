package com.global.fems.message.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class Msg extends SerialBean {

	private MsgHead head;
	private MsgBody body;
	private MsgFault fault;

	public MsgHead getHead() {
		return head;
	}

	public void setHead(MsgHead head) {
		this.head = head;
	}

	public MsgBody getBody() {
		return body;
	}

	public void setBody(MsgBody body) {
		this.body = body;
	}

	public MsgFault getFault() {
		return fault;
	}

	public void setFault(MsgFault fault) {
		this.fault = fault;
	}
}
