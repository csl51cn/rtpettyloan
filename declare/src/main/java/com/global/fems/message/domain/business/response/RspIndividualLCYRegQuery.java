package com.global.fems.message.domain.business.response;

import com.global.fems.message.domain.DataStores;
/**
 * 类描述：个人结售汇额度登记指令查询反馈 接口代码：000002
 *
 * @ClassName: RspIndividualLCYRegQuery
 * @Description:
 * @author leonardLeo
 * @date 2015-6-25 上午11:40:47
 *
 */
public class RspIndividualLCYRegQuery extends DataStores {
	/** 1.业务流水号 */
	private String SEQNO;
	/** 2.业务编号 */
	private String BIZNO;
	/** 3.业务参号 */
	private String REFNO;
	/** 4.处理时间 */
	private String DEAL_DATE;
	/** 5.处理状态 */
	private String DEAL_STATUS;

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

	public String getDEAL_DATE() {
		return DEAL_DATE;
	}

	public void setDEAL_DATE(String dEALDATE) {
		DEAL_DATE = dEALDATE;
	}

	public String getDEAL_STATUS() {
		return DEAL_STATUS;
	}

	public void setDEAL_STATUS(String dEALSTATUS) {
		DEAL_STATUS = dEALSTATUS;
	}

}
