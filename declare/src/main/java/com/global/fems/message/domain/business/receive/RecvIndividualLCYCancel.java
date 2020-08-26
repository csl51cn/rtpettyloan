package com.global.fems.message.domain.business.receive;

import com.global.fems.message.domain.DataStores;

/**
 *类描述：个人结售汇信息撤消 接口代码：000005
 *
 * @ClassName: RecvIndividualLCYCancel
 * @Description:
 * @author leonardLeo
 * @date 2015-6-24 下午09:27:28
 *
 */
public class RecvIndividualLCYCancel extends DataStores {
	/** 1.业务流水号 */
	private String SEQNO;
	/** 2.交易类型 */
	private String TRADE_TYPE;
	/** 3.撤销原因 */
	private String CANCEL_REASON;
	/** 4.撤销说明 */
	private String CANCEL_REMARK;

	public String getSEQNO() {
		return SEQNO;
	}

	public void setSEQNO(String sEQNO) {
		SEQNO = sEQNO;
	}

	public String getTRADE_TYPE() {
		return TRADE_TYPE;
	}

	public void setTRADE_TYPE(String tRADETYPE) {
		TRADE_TYPE = tRADETYPE;
	}

	public String getCANCEL_REASON() {
		return CANCEL_REASON;
	}

	public void setCANCEL_REASON(String cANCELREASON) {
		CANCEL_REASON = cANCELREASON;
	}

	public String getCANCEL_REMARK() {
		return CANCEL_REMARK;
	}

	public void setCANCEL_REMARK(String cANCELREMARK) {
		CANCEL_REMARK = cANCELREMARK;
	}

}
