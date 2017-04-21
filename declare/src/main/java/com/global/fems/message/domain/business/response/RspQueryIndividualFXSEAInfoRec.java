package com.global.fems.message.domain.business.response;

import com.global.fems.message.domain.SerialBean;

/**
 * 个人结售明细查询回执信息
 * 
 * @author Administrator
 * 
 */
public class RspQueryIndividualFXSEAInfoRec extends SerialBean {
	private String REFNO; // 业务参号
	private String BANK_SELF_NUM; // 银行自身流水号
	private String BIZ_TYPE_CODE; // 业务类型代码
	private String IDTYPE_CODE; // 证件类型代码
	private String IDCODE; // 证件号码
	private String ADD_IDCODE; // 补充证件号码
	private String CTYCODE; // 国家/地区代码
	private String PERSON_NAME; // 姓名

	private String SPFE_TYPE_CODE; // 结购汇资金属性
	private String TXCCY; // 币种
	private String TXAMT; // 结购汇金额
	private String TXAMT_USD;// 结售汇金额折美元
	private String SPFE_ACCTNO_CNY;// 结售汇人民币账户
	private String SALEFX_SETTLE_CODE; // 结汇资金形态
	private String LCY_ACCT_NO; // 个人外汇账户账号

	private String AGENT_CORP_NAME; // 代理企业名称
	private String AGENT_CORP_CODE; // 代理企业组织机构代码
	private String INDIV_ORG_CODE; // 个体工商户组织机构代码
	private String INDIV_ORG_NAME; // 个体工商户名称
	private String PAY_ORG_NAME; // 支付机构名称
	private String PAY_ORG_CODE; // 支付机构组织机构代码
	private String CAPITALNO; // 外汇局批件号/备案表号/业务编号

	private String PURFX_CASH_AMT; // 购汇提钞金额
	private String PURFX_CASH_AMT_USD;// 购汇提钞金额折美元
	private String FCY_REMIT_AMT; // 汇出资金（包括外汇票据）金额
	private String FCY_REMIT_AMT_USD; // 汇出资金（包括外汇票据）金额折美元
	private String FCY_ACCT_AMT; // 存入个人外汇账户金额
	private String FCY_ACCT_AMT_USD; // 存入个人外汇账户金额折美元
	private String TCHK_AMT; // 旅行支票金额
	private String TCHK_AMT_USD;// 旅行支票金额折美元

	private String BIZ_TX_CHNL_CODE; // 业务办理渠道代码
	private String BIZ_TX_TIME; // 业务办理时间
	private String BRANCH_CODE; // 金融机构标识码
	private String BRANCH_NAME; // 金融机构名称
	private String OPERCODE; // 经办人代码
	private String ACTIONTYPE;// 操作类型
	private String REMARK; // 备注

	public String getREFNO() {
		return REFNO;
	}

	public void setREFNO(String rEFNO) {
		REFNO = rEFNO;
	}

	public String getBANK_SELF_NUM() {
		return BANK_SELF_NUM;
	}

	public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
		BANK_SELF_NUM = bANK_SELF_NUM;
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

	public String getADD_IDCODE() {
		return ADD_IDCODE;
	}

	public void setADD_IDCODE(String aDD_IDCODE) {
		ADD_IDCODE = aDD_IDCODE;
	}

	public String getCTYCODE() {
		return CTYCODE;
	}

	public void setCTYCODE(String cTYCODE) {
		CTYCODE = cTYCODE;
	}

	public String getPERSON_NAME() {
		return PERSON_NAME;
	}

	public void setPERSON_NAME(String pERSON_NAME) {
		PERSON_NAME = pERSON_NAME;
	}

	public String getSPFE_TYPE_CODE() {
		return SPFE_TYPE_CODE;
	}

	public void setSPFE_TYPE_CODE(String sPFE_TYPE_CODE) {
		SPFE_TYPE_CODE = sPFE_TYPE_CODE;
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

	public String getTXAMT_USD() {
		return TXAMT_USD;
	}

	public void setTXAMT_USD(String tXAMT_USD) {
		TXAMT_USD = tXAMT_USD;
	}

	public String getSPFE_ACCTNO_CNY() {
		return SPFE_ACCTNO_CNY;
	}

	public void setSPFE_ACCTNO_CNY(String sPFE_ACCTNO_CNY) {
		SPFE_ACCTNO_CNY = sPFE_ACCTNO_CNY;
	}

	public String getSALEFX_SETTLE_CODE() {
		return SALEFX_SETTLE_CODE;
	}

	public void setSALEFX_SETTLE_CODE(String sALEFX_SETTLE_CODE) {
		SALEFX_SETTLE_CODE = sALEFX_SETTLE_CODE;
	}

	public String getLCY_ACCT_NO() {
		return LCY_ACCT_NO;
	}

	public void setLCY_ACCT_NO(String lCY_ACCT_NO) {
		LCY_ACCT_NO = lCY_ACCT_NO;
	}

	public String getAGENT_CORP_NAME() {
		return AGENT_CORP_NAME;
	}

	public void setAGENT_CORP_NAME(String aGENT_CORP_NAME) {
		AGENT_CORP_NAME = aGENT_CORP_NAME;
	}

	public String getAGENT_CORP_CODE() {
		return AGENT_CORP_CODE;
	}

	public void setAGENT_CORP_CODE(String aGENT_CORP_CODE) {
		AGENT_CORP_CODE = aGENT_CORP_CODE;
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

	public String getPAY_ORG_NAME() {
		return PAY_ORG_NAME;
	}

	public void setPAY_ORG_NAME(String pAY_ORG_NAME) {
		PAY_ORG_NAME = pAY_ORG_NAME;
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

	public String getPURFX_CASH_AMT() {
		return PURFX_CASH_AMT;
	}

	public void setPURFX_CASH_AMT(String pURFX_CASH_AMT) {
		PURFX_CASH_AMT = pURFX_CASH_AMT;
	}

	public String getPURFX_CASH_AMT_USD() {
		return PURFX_CASH_AMT_USD;
	}

	public void setPURFX_CASH_AMT_USD(String pURFX_CASH_AMT_USD) {
		PURFX_CASH_AMT_USD = pURFX_CASH_AMT_USD;
	}

	public String getFCY_REMIT_AMT() {
		return FCY_REMIT_AMT;
	}

	public void setFCY_REMIT_AMT(String fCY_REMIT_AMT) {
		FCY_REMIT_AMT = fCY_REMIT_AMT;
	}

	public String getFCY_REMIT_AMT_USD() {
		return FCY_REMIT_AMT_USD;
	}

	public void setFCY_REMIT_AMT_USD(String fCY_REMIT_AMT_USD) {
		FCY_REMIT_AMT_USD = fCY_REMIT_AMT_USD;
	}

	public String getFCY_ACCT_AMT() {
		return FCY_ACCT_AMT;
	}

	public void setFCY_ACCT_AMT(String fCY_ACCT_AMT) {
		FCY_ACCT_AMT = fCY_ACCT_AMT;
	}

	public String getFCY_ACCT_AMT_USD() {
		return FCY_ACCT_AMT_USD;
	}

	public void setFCY_ACCT_AMT_USD(String fCY_ACCT_AMT_USD) {
		FCY_ACCT_AMT_USD = fCY_ACCT_AMT_USD;
	}

	public String getTCHK_AMT() {
		return TCHK_AMT;
	}

	public void setTCHK_AMT(String tCHK_AMT) {
		TCHK_AMT = tCHK_AMT;
	}

	public String getTCHK_AMT_USD() {
		return TCHK_AMT_USD;
	}

	public void setTCHK_AMT_USD(String tCHK_AMT_USD) {
		TCHK_AMT_USD = tCHK_AMT_USD;
	}

	public String getBIZ_TX_CHNL_CODE() {
		return BIZ_TX_CHNL_CODE;
	}

	public void setBIZ_TX_CHNL_CODE(String bIZ_TX_CHNL_CODE) {
		BIZ_TX_CHNL_CODE = bIZ_TX_CHNL_CODE;
	}

	public String getBIZ_TX_TIME() {
		return BIZ_TX_TIME;
	}

	public void setBIZ_TX_TIME(String bIZ_TX_TIME) {
		BIZ_TX_TIME = bIZ_TX_TIME;
	}

	public String getBRANCH_CODE() {
		return BRANCH_CODE;
	}

	public void setBRANCH_CODE(String bRANCH_CODE) {
		BRANCH_CODE = bRANCH_CODE;
	}

	public String getBRANCH_NAME() {
		return BRANCH_NAME;
	}

	public void setBRANCH_NAME(String bRANCH_NAME) {
		BRANCH_NAME = bRANCH_NAME;
	}

	public String getOPERCODE() {
		return OPERCODE;
	}

	public void setOPERCODE(String oPERCODE) {
		OPERCODE = oPERCODE;
	}

	public String getACTIONTYPE() {
		return ACTIONTYPE;
	}

	public void setACTIONTYPE(String aCTIONTYPE) {
		ACTIONTYPE = aCTIONTYPE;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
}
