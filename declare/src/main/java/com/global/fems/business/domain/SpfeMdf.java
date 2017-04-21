package com.global.fems.business.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;
import com.global.framework.util.SysUtils;

/**
 * 个人结售汇信息修改
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "SEQNO", primaryKeyType = "Single", tableName = "BU_spfe_mdf")
public class SpfeMdf extends Entity {

	@ColumnMapping(columnName = "SEQNO", columnType = "String")
	private String SEQNO; // 业务流水号
	@ColumnMapping(columnName = "BIZNO", columnType = "String")
	private String BIZNO;// 业务编号
	@ColumnMapping(columnName = "primaryBizNo", columnType = "String")
	private String primaryBizNo;// 主业务流水号
	@ColumnMapping(columnName = "TRADE_TYPE", columnType = "String")
	private String TRADE_TYPE; // 交易类型 
	@ColumnMapping(columnName = "OCCUPY_LMT_STATUS", columnType = "String")
	private String OCCUPY_LMT_STATUS;// 是否占用额度 
	@ColumnMapping(columnName = "BIZ_TYPE_CODE", columnType = "String")
	private String BIZ_TYPE_CODE; // 业务类型代码 
	@ColumnMapping(columnName = "IDTYPE_CODE", columnType = "String")
	private String IDTYPE_CODE; // 证件类型代码 
	@ColumnMapping(columnName = "IDCODE", columnType = "String")
	private String IDCODE; // 证件号码 
	@ColumnMapping(columnName = "CTYCODE", columnType = "String")
	private String CTYCODE; // 国家/地区代码 
	@ColumnMapping(columnName = "ADD_IDCODE", columnType = "String")
	private String ADD_IDCODE; // 补充证件号码 
	@ColumnMapping(columnName = "PERSON_NAME", columnType = "String")
	private String PERSON_NAME; // 姓名 
	@ColumnMapping(columnName = "TX_CODE", columnType = "String")
	private String TX_CODE; // 结售汇资金属性代码 
	@ColumnMapping(columnName = "SALEFX_SETTLE_CODE", columnType = "String")
	private String SALEFX_SETTLE_CODE;// 结汇资金形态代码  
	@ColumnMapping(columnName = "TXCCY", columnType = "String")
	private String TXCCY; // 币种 
	@ColumnMapping(columnName = "SIGNSTATUS", columnType = "String")
	private String SIGNSTATUS; // 客户是否阅读预关注风险提示/关注名单告知 
	@ColumnMapping(columnName = "BIZ_TX_TIME", columnType = "Date")
	private String BIZ_TX_TIME; // 业务办理时间 
	@ColumnMapping(columnName = "TXAMT", columnType = "Double")
	private String     TXAMT ;//                    结售汇金额
	@ColumnMapping(columnName = "PURFX_CASH_AMT", columnType = "Double")
	private String     PURFX_CASH_AMT ;//           购汇提钞金额
	@ColumnMapping(columnName = "FCY_REMIT_AMT", columnType = "Double")
	private String     FCY_REMIT_AMT ;//            汇出资金（包括外汇票据）金额
	@ColumnMapping(columnName = "FCY_ACCT_AMT", columnType = "Double")
	private String     FCY_ACCT_AMT ;//             存入个人外汇账户金额
	@ColumnMapping(columnName = "TCHK_AMT", columnType = "Double")
	private String     TCHK_AMT ;//                 旅行支票金额
	@ColumnMapping(columnName = "LCY_ACCTNO_CNY", columnType = "String")
	private String     LCY_ACCTNO_CNY ;//           结售汇人民币账户
	@ColumnMapping(columnName = "LCY_ACCT_NO", columnType = "String")
	private String     LCY_ACCT_NO ;//              个人外汇账户账号
	@ColumnMapping(columnName = "AGENT_CORP_CODE", columnType = "String")
	private String     AGENT_CORP_CODE ;//          代理企业组织机构代码
	@ColumnMapping(columnName = "AGENT_CORP_NAME", columnType = "String")
	private String     AGENT_CORP_NAME ;//          代理企业名称
	@ColumnMapping(columnName = "INDIV_ORG_CODE", columnType = "String")
	private String     INDIV_ORG_CODE ;//           个体工商户组织机构代码
	@ColumnMapping(columnName = "INDIV_ORG_NAME", columnType = "String")
	private String     INDIV_ORG_NAME ;//           个体工商户名称
	@ColumnMapping(columnName = "PAY_ORG_CODE", columnType = "String")
	private String     PAY_ORG_CODE ;//             支付机构组织机构代码
	@ColumnMapping(columnName = "CAPITALNO", columnType = "String")
	private String     CAPITALNO ;//                外汇局批件号/备案表号/业务编号
	@ColumnMapping(columnName = "REMARK", columnType = "String")
	private String     REMARK ;//                   备注
	@ColumnMapping(columnName = "TXAMTUSD", columnType = "Double")
	private String TXAMTUSD; // 结售汇金额 折美元
	@ColumnMapping(columnName = "PURFX_CASH_AMTUSD", columnType = "Double")
	private String PURFX_CASH_AMTUSD; // 购汇提钞金额 
	@ColumnMapping(columnName = "MOD_REASON_CODE", columnType = "String")
	private String MOD_REASON_CODE ;//          修改原因代码
	@ColumnMapping(columnName = "MODIFY_REMARK", columnType = "String")
	private String MODIFY_REMARK ;//            修改原因说明
	@ColumnMapping(columnName = "AMT_USD", columnType = "Double")
	private String AMT_USD; // 本次结售汇金额折美元 
	@ColumnMapping(columnName = "AMT_BALANCE_USD", columnType = "Double")
	private String AMT_BALANCE_USD; // 本年额度内剩余可结售汇金额折美元 
	@ColumnMapping(columnName = "TYPE_STATUS", columnType = "String")
	private String TYPE_STATUS; // 个人主体分类状态代码 
	@ColumnMapping(columnName = "RECODE", columnType = "String")
	private String RECODE;
	@ColumnMapping(columnName = "REMSG", columnType = "String")
	private String REMSG;
	@ColumnMapping(columnName = "sfzx", columnType = "String")
	private String sfzx;
	
	@ColumnMapping(columnName = "BIZ_TX_CHNL_CODE", columnType = "String")
	private String BIZ_TX_CHNL_CODE;//
	private String primarySeqNo;//主业务流水号
	private String channelId;
	private String REFNO;
	
	private String CANCEL_REASON;
	private String CANCEL_REMARK;
	
	private String tradeNo;//前序业务的业务编号
	
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
	public String getPrimaryBizNo() {
		return primaryBizNo;
	}
	public void setPrimaryBizNo(String primaryBizNo) {
		this.primaryBizNo = primaryBizNo;
	}
	public String getTRADE_TYPE() {
		return TRADE_TYPE;
	}
	public void setTRADE_TYPE(String tRADE_TYPE) {
		TRADE_TYPE = tRADE_TYPE;
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
		return SysUtils.formatAmt(TXAMT);
	}
	public void setTXAMT(String tXAMT) {
		TXAMT = SysUtils.parseAmt(tXAMT);
	}
	public String getPURFX_CASH_AMT() {
		return SysUtils.formatAmt(PURFX_CASH_AMT);
	}
	public void setPURFX_CASH_AMT(String pURFX_CASH_AMT) {
		PURFX_CASH_AMT = SysUtils.parseAmt(pURFX_CASH_AMT);
	}
	public String getFCY_REMIT_AMT() {
		return SysUtils.formatAmt(FCY_REMIT_AMT);
	}
	public void setFCY_REMIT_AMT(String fCY_REMIT_AMT) {
		FCY_REMIT_AMT = SysUtils.parseAmt(fCY_REMIT_AMT);
	}
	public String getFCY_ACCT_AMT() {
		return SysUtils.formatAmt(FCY_ACCT_AMT);
	}
	public void setFCY_ACCT_AMT(String fCY_ACCT_AMT) {
		FCY_ACCT_AMT = SysUtils.parseAmt(fCY_ACCT_AMT);
	}
	public String getTCHK_AMT() {
		return SysUtils.formatAmt(TCHK_AMT);
	}
	public void setTCHK_AMT(String tCHK_AMT) {
		TCHK_AMT = SysUtils.parseAmt(tCHK_AMT);
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
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getMOD_REASON_CODE() {
		return MOD_REASON_CODE;
	}
	public void setMOD_REASON_CODE(String mOD_REASON_CODE) {
		MOD_REASON_CODE = mOD_REASON_CODE;
	}
	public String getMODIFY_REMARK() {
		return MODIFY_REMARK;
	}
	public void setMODIFY_REMARK(String mODIFY_REMARK) {
		MODIFY_REMARK = mODIFY_REMARK;
	}
	public String getRECODE() {
		return RECODE;
	}
	public void setRECODE(String rECODE) {
		RECODE = rECODE;
	}
	public String getREMSG() {
		return REMSG;
	}
	public void setREMSG(String rEMSG) {
		REMSG = rEMSG;
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
	public String getSIGNSTATUS() {
		return SIGNSTATUS;
	}
	public void setSIGNSTATUS(String sIGNSTATUS) {
		SIGNSTATUS = sIGNSTATUS;
	}
	public String getBIZ_TX_TIME() {
		return BIZ_TX_TIME;
	}
	public void setBIZ_TX_TIME(String bIZ_TX_TIME) {
		if(bIZ_TX_TIME!=null && bIZ_TX_TIME.contains(" ") ){
			bIZ_TX_TIME = bIZ_TX_TIME.substring(0,bIZ_TX_TIME.indexOf(" "));
		}
		BIZ_TX_TIME = bIZ_TX_TIME;
	}
	public String getAMT_USD() {
		return SysUtils.formatAmt(AMT_USD);
	}
	public void setAMT_USD(String aMT_USD) {
		AMT_USD = SysUtils.parseAmt(aMT_USD);
	}
	public String getAMT_BALANCE_USD() {
		return SysUtils.formatAmt(AMT_BALANCE_USD);
	}
	public void setAMT_BALANCE_USD(String aMT_BALANCE_USD) {
		AMT_BALANCE_USD = SysUtils.parseAmt(aMT_BALANCE_USD);
	}
	public String getTYPE_STATUS() {
		return TYPE_STATUS;
	}
	public void setTYPE_STATUS(String tYPE_STATUS) {
		TYPE_STATUS = tYPE_STATUS;
	}
	public String getPrimarySeqNo() {
		return primarySeqNo;
	}
	public void setPrimarySeqNo(String primarySeqNo) {
		this.primarySeqNo = primarySeqNo;
	}
	public String getBIZ_TX_CHNL_CODE() {
		return BIZ_TX_CHNL_CODE;
	}
	public void setBIZ_TX_CHNL_CODE(String bIZ_TX_CHNL_CODE) {
		BIZ_TX_CHNL_CODE = bIZ_TX_CHNL_CODE;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getTXAMTUSD() {
		return SysUtils.formatAmt(TXAMTUSD);
	}
	public void setTXAMTUSD(String tXAMTUSD) {
		TXAMTUSD = SysUtils.parseAmt(tXAMTUSD);
	}
	public String getPURFX_CASH_AMTUSD() {
		return SysUtils.formatAmt(PURFX_CASH_AMTUSD);
	}
	public void setPURFX_CASH_AMTUSD(String pURFX_CASH_AMTUSD) {
		PURFX_CASH_AMTUSD = SysUtils.parseAmt(pURFX_CASH_AMTUSD);
	}
	public String getSfzx() {
		return sfzx;
	}
	public void setSfzx(String sfzx) {
		this.sfzx = sfzx;
	}
	public String getREFNO() {
		return REFNO;
	}
	public void setREFNO(String rEFNO) {
		REFNO = rEFNO;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getCANCEL_REASON() {
		return CANCEL_REASON;
	}
	public void setCANCEL_REASON(String cANCEL_REASON) {
		CANCEL_REASON = cANCEL_REASON;
	}
	public String getCANCEL_REMARK() {
		return CANCEL_REMARK;
	}
	public void setCANCEL_REMARK(String cANCEL_REMARK) {
		CANCEL_REMARK = cANCEL_REMARK;
	}
	
}
