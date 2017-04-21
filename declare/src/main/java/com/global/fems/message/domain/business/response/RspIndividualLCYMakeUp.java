package com.global.fems.message.domain.business.response;

import com.global.fems.message.domain.DataStores;

/**
 * 个人结售汇补录回执信息
 * @author Administrator
 *
 */
public class RspIndividualLCYMakeUp extends DataStores {
	/** 1.业务流水号 */
	private String SEQNO;
	/** 2.业务编号 */
	private String BIZNO;
	/** 3.外管业务参号 */
	private String REFNO;

	public String getSEQNO() {
		return SEQNO;
	}

	public void setSEQNO(String sEQNO) {
		SEQNO = sEQNO;
	}

	public String getBIZNO() {
		return BIZNO;
	}

	public void setBIZNO(String bIZNO) {
		BIZNO = bIZNO;
	}

	public String getREFNO() {
		return REFNO;
	}

	public void setREFNO(String rEFNO) {
		REFNO = rEFNO;
	}
}
