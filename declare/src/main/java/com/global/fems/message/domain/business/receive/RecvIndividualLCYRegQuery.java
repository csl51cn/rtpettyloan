package com.global.fems.message.domain.business.receive;

import com.global.fems.message.domain.DataStores;

/**
 * 类描述：个人结售汇额度登记指令查询 接口代码：000002
 * 
 * @ClassName: RecvIndividualLCYRegQuery
 * @Description: TODO
 * @author leonardLeo
 * @date 2015-6-24 下午09:23:17
 * 
 */
public class RecvIndividualLCYRegQuery extends DataStores {
	/** 1.业务流水号 */
	private String SEQNO;
	/** 2.业务参号 */
	private String REFNO;
	/** 3.交易类型 */
	private String TRADE_TYPE;

	public String getSEQNO() {
		return SEQNO;
	}

	public void setSEQNO(String sEQNO) {
		SEQNO = sEQNO;
	}

	public String getREFNO() {
		return REFNO;
	}

	public void setREFNO(String rEFNO) {
		REFNO = rEFNO;
	}

	public String getTRADE_TYPE() {
		return TRADE_TYPE;
	}

	public void setTRADE_TYPE(String tRADETYPE) {
		TRADE_TYPE = tRADETYPE;
	}

}
