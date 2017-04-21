package com.global.fems.message.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class MsgHead extends SerialBean {
	private String VerCode; // 版本号
	private String ReqSysCode; // 请求方系统代码
	private String TxMode; // 交易标识
	private String TxCode; // 接口交易代码
	private String ReqDateTime; // 请求时间
	private String ReqSeqNo; // 请求方交易流水号
	private String SvrDateTime; // 服务方时间
	private String SvrSeqNo; // 服务方交易流水号
	private String ChanlNo; // 渠道号（字符）
	private String OrgNo;// 核心机构编号
	private String TermNo;// 终端号
	private String OperNo; // 柜员

	public String getVerCode() {
		return VerCode;
	}

	public void setVerCode(String verCode) {
		VerCode = verCode;
	}

	public String getReqSysCode() {
		return ReqSysCode;
	}

	public void setReqSysCode(String reqSysCode) {
		ReqSysCode = reqSysCode;
	}

	public String getTxMode() {
		return TxMode;
	}

	public void setTxMode(String txMode) {
		TxMode = txMode;
	}

	public String getTxCode() {
		return TxCode;
	}

	public void setTxCode(String txCode) {
		TxCode = txCode;
	}

	public String getReqDateTime() {
		return ReqDateTime;
	}

	public void setReqDateTime(String reqDateTime) {
		ReqDateTime = reqDateTime;
	}

	public String getReqSeqNo() {
		return ReqSeqNo;
	}

	public void setReqSeqNo(String reqSeqNo) {
		ReqSeqNo = reqSeqNo;
	}

	public String getSvrDateTime() {
		return SvrDateTime;
	}

	public void setSvrDateTime(String svrDateTime) {
		SvrDateTime = svrDateTime;
	}

	public String getSvrSeqNo() {
		return SvrSeqNo;
	}

	public void setSvrSeqNo(String svrSeqNo) {
		SvrSeqNo = svrSeqNo;
	}

	public String getChanlNo() {
		return ChanlNo;
	}

	public void setChanlNo(String chanlNo) {
		ChanlNo = chanlNo;
	}

	public String getOrgNo() {
		return OrgNo;
	}

	public void setOrgNo(String orgNo) {
		OrgNo = orgNo;
	}

	public String getTermNo() {
		return TermNo;
	}

	public void setTermNo(String termNo) {
		TermNo = termNo;
	}

	public String getOperNo() {
		return OperNo;
	}

	public void setOperNo(String operNo) {
		OperNo = operNo;
	}

}
