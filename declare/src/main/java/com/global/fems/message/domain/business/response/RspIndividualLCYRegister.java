package com.global.fems.message.domain.business.response;

import com.global.fems.message.domain.DataStores;
/**
 * 类描述：个人结售汇额度登记反馈 接口代码：000004
* @ClassName: RspIndividualLCYRegister
* @Description:
* @author leonardLeo
* @date 2015-6-25 上午11:41:36
*
 */
public class RspIndividualLCYRegister extends DataStores {
	/** 1.业务流水号 */
	private String SEQNO;
	/** 3.外管业务参号 */
	private String REFNO;
	/** 4.本次结售汇金额折美元 */
	private String AMT_USD;
	/** 5.本年额度内剩余可结售汇金额折美元 */
	private String AMT_BALANCE_USD;
	/** 6.个人主体分类状态代码 */
	private String TYPE_STATUS;
	/** 7.发布日期 */
	private String PUB_DATE;
	/** 8.到期日期 */
	private String END_DATE;
	/** 9.发布原因代码 */
	private String PUB_CODE;
	/** 10.发布原因 */
	private String PUB_REASON;

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
	public String getAMT_USD() {
		return AMT_USD;
	}
	public void setAMT_USD(String aMT_USD) {
		AMT_USD = aMT_USD;
	}
	public String getAMT_BALANCE_USD() {
		return AMT_BALANCE_USD;
	}
	public void setAMT_BALANCE_USD(String aMT_BALANCE_USD) {
		AMT_BALANCE_USD = aMT_BALANCE_USD;
	}
	public String getTYPE_STATUS() {
		return TYPE_STATUS;
	}
	public void setTYPE_STATUS(String tYPESTATUS) {
		TYPE_STATUS = tYPESTATUS;
	}
	public String getPUB_DATE() {
		return PUB_DATE;
	}
	public void setPUB_DATE(String pUBDATE) {
		PUB_DATE = pUBDATE;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String eNDDATE) {
		END_DATE = eNDDATE;
	}
	public String getPUB_CODE() {
		return PUB_CODE;
	}
	public void setPUB_CODE(String pUBCODE) {
		PUB_CODE = pUBCODE;
	}
	public String getPUB_REASON() {
		return PUB_REASON;
	}
	public void setPUB_REASON(String pUBREASON) {
		PUB_REASON = pUBREASON;
	}
}
