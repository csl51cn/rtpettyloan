package com.global.fems.message.domain.business.response;

import com.global.fems.message.domain.DataStores;
/**
 * 类描述：个人结售汇额度查询反馈 接口代码：000001
 *
 * @ClassName: RspIndividualLCYQuery
 * @Description:
 * @author leonardLeo
 * @date 2015-6-25 上午11:31:56
 *
 */
public class RspIndividualLCYQuery extends DataStores {
	/** 1.交易类型 */
	private String TRADE_TYPE;
	/** 2.本年额度内已结售汇金额折美元 */
	private String AMT_USD;
	/** 3.本年额度内剩余可结售汇金额折美元 */
	private String AMT_BALANCE_USD;
	/** 4.当日已发生的现钞结售汇金额折美元 */
	private String TODAY_AMT_USD;
	/** 5.交易主体姓名 */
	private String CUSTNAME;
	/** 6.交易主体类型代码 */
	private String CUSTTYPE_CODE;
	/** 7.个人主体分类状态代码 */
	private String TYPE_STATUS;
	/** 8.发布日期 */
	private String PUB_DATE;
	/** 9.到期日期 */
	private String END_DATE;
	/** 10.发布原因 */
	private String PUB_REASON;
	/** 11.发布原因代码 */
	private String PUB_CODE;
	/** 12.确认书签署状态 */
	private String SIGN_STATUS;

	public String getTRADE_TYPE() {
		return TRADE_TYPE;
	}

	public void setTRADE_TYPE(String tRADETYPE) {
		TRADE_TYPE = tRADETYPE;
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

	public String getTODAY_AMT_USD() {
		return TODAY_AMT_USD;
	}

	public void setTODAY_AMT_USD(String tODAY_AMT_USD) {
		TODAY_AMT_USD = tODAY_AMT_USD;
	}

	public String getCUSTNAME() {
		return CUSTNAME;
	}

	public void setCUSTNAME(String cUSTNAME) {
		CUSTNAME = cUSTNAME;
	}

	public String getCUSTTYPE_CODE() {
		return CUSTTYPE_CODE;
	}

	public void setCUSTTYPE_CODE(String cUSTTYPECODE) {
		CUSTTYPE_CODE = cUSTTYPECODE;
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

	public String getPUB_REASON() {
		return PUB_REASON;
	}

	public void setPUB_REASON(String pUBREASON) {
		PUB_REASON = pUBREASON;
	}

	public String getPUB_CODE() {
		return PUB_CODE;
	}

	public void setPUB_CODE(String pUBCODE) {
		PUB_CODE = pUBCODE;
	}

	public String getSIGN_STATUS() {
		return SIGN_STATUS;
	}

	public void setSIGN_STATUS(String sIGNSTATUS) {
		SIGN_STATUS = sIGNSTATUS;
	}

}
