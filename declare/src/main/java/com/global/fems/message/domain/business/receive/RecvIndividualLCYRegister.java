package com.global.fems.message.domain.business.receive;

import com.global.fems.message.domain.DataStores;

/**
 * 类描述：个人结售汇额度登记 接口代码：000004
 *
 * @ClassName: RecvIndividualLCYRegister
 * @Description:
 * @author leonardLeo
 * @date 2015-6-24 下午09:24:58
 *
 */
public class RecvIndividualLCYRegister extends DataStores {
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

	public String getTRADE_TYPE() {
		return TRADE_TYPE;
	}

	public void setTRADE_TYPE(String tRADETYPE) {
		TRADE_TYPE = tRADETYPE;
	}

	public String getOCCUPY_LMT_STATUS() {
		return OCCUPY_LMT_STATUS;
	}

	public void setOCCUPY_LMT_STATUS(String oCCUPYLMTSTATUS) {
		OCCUPY_LMT_STATUS = oCCUPYLMTSTATUS;
	}

	public String getBIZ_TYPE_CODE() {
		return BIZ_TYPE_CODE;
	}

	public void setBIZ_TYPE_CODE(String bIZTYPECODE) {
		BIZ_TYPE_CODE = bIZTYPECODE;
	}

	public String getIDTYPE_CODE() {
		return IDTYPE_CODE;
	}

	public void setIDTYPE_CODE(String iDTYPECODE) {
		IDTYPE_CODE = iDTYPECODE;
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

	public void setADD_IDCODE(String aDDIDCODE) {
		ADD_IDCODE = aDDIDCODE;
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

	public String getSIGNSTATUS() {
		return SIGNSTATUS;
	}

	public void setSIGNSTATUS(String sIGNSTATUS) {
		SIGNSTATUS = sIGNSTATUS;
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

	public String getBIZ_TX_TIME() {
		return BIZ_TX_TIME;
	}

	public void setBIZ_TX_TIME(String bIZTXTIME) {
		BIZ_TX_TIME = bIZTXTIME;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

}
