package com.global.fems.business.domain;

/**
 * 个人结售汇额度登记指令查询响应实体
 * 
 * @author Sili Jiang
 * @version 2015-07-06
 * 
 */
public class SpfeRegisterQuery {
	/**
	 * 业务流水号
	 */
	private String seqNo;
	/**
	 * 业务编号
	 */
	private String bizNo;
	/**
	 * 业务参号
	 */
	private String refNo;
	/**
	 * 处理时间
	 */
	private String dealDate;
	/**
	 * 处理状态
	 */
	private String dealStatus;

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	
}
