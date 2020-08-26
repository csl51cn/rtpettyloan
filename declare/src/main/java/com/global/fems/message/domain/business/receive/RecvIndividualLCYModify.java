package com.global.fems.message.domain.business.receive;

import com.global.fems.message.domain.DataStores;

/**
 * 类描述：个人结售汇信息修改 接口代码：000006
 *
 * @ClassName: RecvIndividualLCYModify
 * @Description:
 * @author leonardLeo
 * @date 2015-6-24 下午09:28:23
 *
 */
public class RecvIndividualLCYModify extends DataStores {
	/** 1.业务流水号 */
	private String SEQNO;
	/** 2.交易类型 */
	private String TRADE_TYPE;
	/** 3.姓名 */
	private String PERSON_NAME;
	/** 4.结售汇资金属性代码 */
	private String TX_CODE;
	/** 5.结汇资金形态代码 */
	private String SALEFX_SETTLE_CODE;
	/** 6.币种 */
	private String TXCCY;
	/** 7.结售汇金额 */
	private String TXAMT;
	/** 8.购汇提钞金额 */
	private String PURFX_CASH_AMT;
	/** 9.汇出资金（包括外汇票据）金额 */
	private String FCY_REMIT_AMT;
	/** 10.存入个人外汇账户金额 */
	private String FCY_ACCT_AMT;
	/** 11.旅行支票金额 */
	private String TCHK_AMT;
	/** 12.结售汇人民币账户 */
	private String LCY_ACCTNO_CNY;
	/** 13.个人外汇账户账号 */
	private String LCY_ACCT_NO;
	/** 14.代理企业组织机构代码 */
	private String AGENT_CORP_CODE;
	/** 15.代理企业名称 */
	private String AGENT_CORP_NAME;
	/** 16.个体工商户组织机构代码 */
	private String INDIV_ORG_CODE;
	/** 17.个体工商户名称 */
	private String INDIV_ORG_NAME;
	/** 18.支付机构组织机构代码 */
	private String PAY_ORG_CODE;
	/** 19.外汇局批件号/备案表号/业务编号 */
	private String CAPITALNO;
	/** 20.备注 */
	private String REMARK;
	/** 21.修改原因代码 */
	private String MOD_REASON_CODE;
	/** 22.修改原因说明 */
	private String MODIFY_REMARK;
	/**	23.业务类型代码 */
	private String BIZ_TYPE_CODE;

	public String getBIZ_TYPE_CODE() {
		return BIZ_TYPE_CODE;
	}

	public void setBIZ_TYPE_CODE(String bIZTYPECODE) {
		BIZ_TYPE_CODE = bIZTYPECODE;
	}

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

	public String getPERSON_NAME() {
		return PERSON_NAME;
	}

	public void setPERSON_NAME(String pERSONNAME) {
		PERSON_NAME = pERSONNAME;
	}

	public String getTX_CODE() {
		return TX_CODE;
	}

	public void setTX_CODE(String tXCODE) {
		TX_CODE = tXCODE;
	}

	public String getSALEFX_SETTLE_CODE() {
		return SALEFX_SETTLE_CODE;
	}

	public void setSALEFX_SETTLE_CODE(String sALEFXSETTLECODE) {
		SALEFX_SETTLE_CODE = sALEFXSETTLECODE;
	}

	public String getTXCCY() {
		return TXCCY;
	}

	public void setTXCCY(String tXCCY) {
		TXCCY = tXCCY;
	}

	public String getTXAMT() {
		return TXAMT;
	}

	public void setTXAMT(String tXAMT) {
		TXAMT = tXAMT;
	}

	public String getPURFX_CASH_AMT() {
		return PURFX_CASH_AMT;
	}

	public void setPURFX_CASH_AMT(String pURFXCASHAMT) {
		PURFX_CASH_AMT = pURFXCASHAMT;
	}

	public String getFCY_REMIT_AMT() {
		return FCY_REMIT_AMT;
	}

	public void setFCY_REMIT_AMT(String fCYREMITAMT) {
		FCY_REMIT_AMT = fCYREMITAMT;
	}

	public String getFCY_ACCT_AMT() {
		return FCY_ACCT_AMT;
	}

	public void setFCY_ACCT_AMT(String fCYACCTAMT) {
		FCY_ACCT_AMT = fCYACCTAMT;
	}

	public String getTCHK_AMT() {
		return TCHK_AMT;
	}

	public void setTCHK_AMT(String tCHKAMT) {
		TCHK_AMT = tCHKAMT;
	}

	public String getLCY_ACCTNO_CNY() {
		return LCY_ACCTNO_CNY;
	}

	public void setLCY_ACCTNO_CNY(String lCYACCTNOCNY) {
		LCY_ACCTNO_CNY = lCYACCTNOCNY;
	}

	public String getLCY_ACCT_NO() {
		return LCY_ACCT_NO;
	}

	public void setLCY_ACCT_NO(String lCYACCTNO) {
		LCY_ACCT_NO = lCYACCTNO;
	}

	public String getAGENT_CORP_CODE() {
		return AGENT_CORP_CODE;
	}

	public void setAGENT_CORP_CODE(String aGENTCORPCODE) {
		AGENT_CORP_CODE = aGENTCORPCODE;
	}

	public String getAGENT_CORP_NAME() {
		return AGENT_CORP_NAME;
	}

	public void setAGENT_CORP_NAME(String aGENTCORPNAME) {
		AGENT_CORP_NAME = aGENTCORPNAME;
	}

	public String getINDIV_ORG_CODE() {
		return INDIV_ORG_CODE;
	}

	public void setINDIV_ORG_CODE(String iNDIVORGCODE) {
		INDIV_ORG_CODE = iNDIVORGCODE;
	}

	public String getINDIV_ORG_NAME() {
		return INDIV_ORG_NAME;
	}

	public void setINDIV_ORG_NAME(String iNDIVORGNAME) {
		INDIV_ORG_NAME = iNDIVORGNAME;
	}

	public String getPAY_ORG_CODE() {
		return PAY_ORG_CODE;
	}

	public void setPAY_ORG_CODE(String pAYORGCODE) {
		PAY_ORG_CODE = pAYORGCODE;
	}

	public String getCAPITALNO() {
		return CAPITALNO;
	}

	public void setCAPITALNO(String cAPITALNO) {
		CAPITALNO = cAPITALNO;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public String getMOD_REASON_CODE() {
		return MOD_REASON_CODE;
	}

	public void setMOD_REASON_CODE(String mODREASONCODE) {
		MOD_REASON_CODE = mODREASONCODE;
	}

	public String getMODIFY_REMARK() {
		return MODIFY_REMARK;
	}

	public void setMODIFY_REMARK(String mODIFYREMARK) {
		MODIFY_REMARK = mODIFYREMARK;
	}

}
