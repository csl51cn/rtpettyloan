package com.global.fems.message.domain.business.response;

import com.global.fems.message.domain.DataStores;

/**
 * 类描述：个人结售汇信息撤消反馈 接口代码：000005
 *
 * @ClassName: RspIndividualLCYCancel
 * @Description:
 * @author leonardLeo
 * @date 2015-6-25 上午11:42:11
 *
 */
public class RspIndividualLCYCancel extends DataStores {
	/** 1.业务流水号 */
	private String SEQNO;
	/** 服务端流水号 */
	private String SVRSEQNO;
	/** 3.业务参号 */
	private String REFNO;

	public String getSEQNO() {
		return SEQNO;
	}

	public void setSEQNO(String sEQNO) {
		SEQNO = sEQNO;
	}

	public String getSVRSEQNO() {
		return SVRSEQNO;
	}

	public void setSVRSEQNO(String sVRSEQNO) {
		SVRSEQNO = sVRSEQNO;
	}

	public String getREFNO() {
		return REFNO;
	}

	public void setREFNO(String rEFNO) {
		REFNO = rEFNO;
	}

}
