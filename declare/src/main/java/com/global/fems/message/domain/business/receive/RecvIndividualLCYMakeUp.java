package com.global.fems.message.domain.business.receive;

import com.global.fems.message.domain.DataStores;

/**
 * 个人结售汇信息补录
 * @author Administrator
 *
 */
public class RecvIndividualLCYMakeUp extends DataStores {
	/** 2.交易类型 */
	private String TRADE_TYPE;
	/** 3.是否占用额度 */
	private String OCCUPY_LMT_STATUS;
	/** 4.业务类型代码 */
	private String BIZ_TYPE_CODE;
	/** 5.证件类型代码 */
	private String IDTYPE_CODE;
	/** 6.证件号码 */
	private String IDCODE;
	/** 7.国家/地区代码 */
	private String CTYCODE;
	/** 8.补充证件号码 */
	private String ADD_IDCODE;
	/** 9.姓名 */
	private String PERSON_NAME;
	/** 10.结售汇资金属性代码 */
	private String TX_CODE;
	/** 11.结汇资金形态代码 */
	private String SALEFX_SETTLE_CODE;
	/** 12.币种 */
	private String TXCCY;
	/** 13.结售汇金额 */
	private String TXAMT;
	/** 14.购汇提钞金额 */
	private String PURFX_CASH_AMT;
	/** 15.汇出资金（包括外汇票据）金额 */
	private String FCY_REMIT_AMT;
	/** 16.存入个人外汇账户金额 */
	private String FCY_ACCT_AMT;
	/** 17.旅行支票金额 */
	private String TCHK_AMT;
	/** 18.结售汇人民币账户 */
	private String LCY_ACCTNO_CNY;
	/** 19.个人外汇账户账号 */
	private String LCY_ACCT_NO;
	/** 20.客户是否阅读预关注风险提示/关注名单告知 */
	private String SIGNSTATUS;
	/** 21.代理企业组织机构代码 */
	private String AGENT_CORP_CODE;
	/** 22.代理企业名称 */
	private String AGENT_CORP_NAME;
	/** 23.个体工商户组织机构代码 */
	private String INDIV_ORG_CODE;
	/** 24.个体工商户名称 */
	private String INDIV_ORG_NAME;
	/** 25.支付机构组织机构代码 */
	private String PAY_ORG_CODE;
	/** 26.外汇局批件号/备案表号/业务编号 */
	private String CAPITALNO;
	/** 27.业务办理时间 */
	private String BIZ_TX_TIME;
	/** 28.备注 */
	private String REMARK;
	
	private String REIN_REASON_CODE;//补录原因代码//
	private String REIN_REMARK;//   补录说明
	
	public String getTRADE_TYPE() {
		return TRADE_TYPE;
	}
	public void setTRADE_TYPE(String tRADE_TYPE) {
		TRADE_TYPE = tRADE_TYPE;
	}
	public String getOCCUPY_LMT_STATUS() {
		return OCCUPY_LMT_STATUS;
	}
	public void setOCCUPY_LMT_STATUS(String oCCUPY_LMT_STATUS) {
		OCCUPY_LMT_STATUS = oCCUPY_LMT_STATUS;
	}
	public String getBIZ_TYPE_CODE() {
		return BIZ_TYPE_CODE;
	}
	public void setBIZ_TYPE_CODE(String bIZ_TYPE_CODE) {
		BIZ_TYPE_CODE = bIZ_TYPE_CODE;
	}
	public String getIDTYPE_CODE() {
		return IDTYPE_CODE;
	}
	public void setIDTYPE_CODE(String iDTYPE_CODE) {
		IDTYPE_CODE = iDTYPE_CODE;
	}
	public String getIDCODE() {
		return IDCODE;
	}
	public void setIDCODE(String iDCODE) {
		IDCODE = iDCODE;
	}
	public String getCTYCODE() {
		return CTYCODE;
	}
	public void setCTYCODE(String cTYCODE) {
		CTYCODE = cTYCODE;
	}
	public String getADD_IDCODE() {
		return ADD_IDCODE;
	}
	public void setADD_IDCODE(String aDD_IDCODE) {
		ADD_IDCODE = aDD_IDCODE;
	}
	public String getPERSON_NAME() {
		return PERSON_NAME;
	}
	public void setPERSON_NAME(String pERSON_NAME) {
		PERSON_NAME = pERSON_NAME;
	}
	public String getTX_CODE() {
		return TX_CODE;
	}
	public void setTX_CODE(String tX_CODE) {
		TX_CODE = tX_CODE;
	}
	public String getSALEFX_SETTLE_CODE() {
		return SALEFX_SETTLE_CODE;
	}
	public void setSALEFX_SETTLE_CODE(String sALEFX_SETTLE_CODE) {
		SALEFX_SETTLE_CODE = sALEFX_SETTLE_CODE;
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
	public void setPURFX_CASH_AMT(String pURFX_CASH_AMT) {
		PURFX_CASH_AMT = pURFX_CASH_AMT;
	}
	public String getFCY_REMIT_AMT() {
		return FCY_REMIT_AMT;
	}
	public void setFCY_REMIT_AMT(String fCY_REMIT_AMT) {
		FCY_REMIT_AMT = fCY_REMIT_AMT;
	}
	public String getFCY_ACCT_AMT() {
		return FCY_ACCT_AMT;
	}
	public void setFCY_ACCT_AMT(String fCY_ACCT_AMT) {
		FCY_ACCT_AMT = fCY_ACCT_AMT;
	}
	public String getTCHK_AMT() {
		return TCHK_AMT;
	}
	public void setTCHK_AMT(String tCHK_AMT) {
		TCHK_AMT = tCHK_AMT;
	}
	public String getLCY_ACCTNO_CNY() {
		return LCY_ACCTNO_CNY;
	}
	public void setLCY_ACCTNO_CNY(String lCY_ACCTNO_CNY) {
		LCY_ACCTNO_CNY = lCY_ACCTNO_CNY;
	}
	public String getLCY_ACCT_NO() {
		return LCY_ACCT_NO;
	}
	public void setLCY_ACCT_NO(String lCY_ACCT_NO) {
		LCY_ACCT_NO = lCY_ACCT_NO;
	}
	public String getSIGNSTATUS() {
		return SIGNSTATUS;
	}
	public void setSIGNSTATUS(String sIGNSTATUS) {
		SIGNSTATUS = sIGNSTATUS;
	}
	public String getAGENT_CORP_CODE() {
		return AGENT_CORP_CODE;
	}
	public void setAGENT_CORP_CODE(String aGENT_CORP_CODE) {
		AGENT_CORP_CODE = aGENT_CORP_CODE;
	}
	public String getAGENT_CORP_NAME() {
		return AGENT_CORP_NAME;
	}
	public void setAGENT_CORP_NAME(String aGENT_CORP_NAME) {
		AGENT_CORP_NAME = aGENT_CORP_NAME;
	}
	public String getINDIV_ORG_CODE() {
		return INDIV_ORG_CODE;
	}
	public void setINDIV_ORG_CODE(String iNDIV_ORG_CODE) {
		INDIV_ORG_CODE = iNDIV_ORG_CODE;
	}
	public String getINDIV_ORG_NAME() {
		return INDIV_ORG_NAME;
	}
	public void setINDIV_ORG_NAME(String iNDIV_ORG_NAME) {
		INDIV_ORG_NAME = iNDIV_ORG_NAME;
	}
	public String getPAY_ORG_CODE() {
		return PAY_ORG_CODE;
	}
	public void setPAY_ORG_CODE(String pAY_ORG_CODE) {
		PAY_ORG_CODE = pAY_ORG_CODE;
	}
	public String getCAPITALNO() {
		return CAPITALNO;
	}
	public void setCAPITALNO(String cAPITALNO) {
		CAPITALNO = cAPITALNO;
	}
	public String getBIZ_TX_TIME() {
		return BIZ_TX_TIME;
	}
	public void setBIZ_TX_TIME(String bIZ_TX_TIME) {
		BIZ_TX_TIME = bIZ_TX_TIME;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getREIN_REASON_CODE() {
		return REIN_REASON_CODE;
	}
	public void setREIN_REASON_CODE(String rEIN_REASON_CODE) {
		REIN_REASON_CODE = rEIN_REASON_CODE;
	}
	public String getREIN_REMARK() {
		return REIN_REMARK;
	}
	public void setREIN_REMARK(String rEIN_REMARK) {
		REIN_REMARK = rEIN_REMARK;
	}
}
