package com.global.fems.message.domain.business.receive;

import com.global.fems.message.domain.DataStores;

/**
 * 个人结售汇明细信息查询
 * @author Administrator
 *
 */
public class RecvQueryIndividualFXSEAInfo extends DataStores {

	private String PAGESIZE; //每页条数
	private String PAGENUMBER; //当前页码	
	/** 业务参号 */
	private String REFNO;
	private String SVRSEQNO;//前置系统业务流水号
	private String REQSEQNO;//渠道业务流水号
	/** 证件类型代码 */
	private String IDTYPE_CODE;
	/** 证件号码 */
	private String IDCODE;
	/** 国家/地区代码 */
	private String CTYCODE;
	/** 业务办理时间 */
	private String BIZ_TX_TIME;
	/** 交易类型 */
	private String TRADE_TYPE;
	
	public String getPAGESIZE() {
		return PAGESIZE;
	}
	public void setPAGESIZE(String pAGESIZE) {
		PAGESIZE = pAGESIZE;
	}
	public String getPAGENUMBER() {
		return PAGENUMBER;
	}
	public void setPAGENUMBER(String pAGENUMBER) {
		PAGENUMBER = pAGENUMBER;
	}
	public String getREFNO() {
		return REFNO;
	}
	public void setREFNO(String rEFNO) {
		REFNO = rEFNO;
	}
	public String getSVRSEQNO() {
		return SVRSEQNO;
	}
	public void setSVRSEQNO(String sVRSEQNO) {
		SVRSEQNO = sVRSEQNO;
	}
	public String getREQSEQNO() {
		return REQSEQNO;
	}
	public void setREQSEQNO(String rEQSEQNO) {
		REQSEQNO = rEQSEQNO;
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
	public String getBIZ_TX_TIME() {
		return BIZ_TX_TIME;
	}
	public void setBIZ_TX_TIME(String bIZ_TX_TIME) {
		BIZ_TX_TIME = bIZ_TX_TIME;
	}
	public String getTRADE_TYPE() {
		return TRADE_TYPE;
	}
	public void setTRADE_TYPE(String tRADE_TYPE) {
		TRADE_TYPE = tRADE_TYPE;
	}
}
