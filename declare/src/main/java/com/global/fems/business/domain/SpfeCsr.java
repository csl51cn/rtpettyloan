package com.global.fems.business.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 个人结售汇信息撤消
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "SEQNO", primaryKeyType = "Single", tableName = "dc_BU_spfe_csr")
public class SpfeCsr extends Entity {
	@ColumnMapping(columnName = "SEQNO", columnType = "String")
	private String SEQNO; // 业务流水号
	@ColumnMapping(columnName = "BIZNO", columnType = "String")
	private String BIZNO;// 业务编号
	@ColumnMapping(columnName = "primaryBizNo", columnType = "String")
	private String primaryBizNo;// 主业务流水号
	@ColumnMapping(columnName = "TRADE_TYPE", columnType = "String")
	private String TRADE_TYPE; // 交易类型
	@ColumnMapping(columnName = "CANCEL_REASON", columnType = "String")
	private String CANCEL_REASON;//撤销原因
	@ColumnMapping(columnName = "CANCEL_REMARK", columnType = "String")
	private String CANCEL_REMARK;// 撤销说明
	@ColumnMapping(columnName = "RECODE", columnType = "String")
	private String RECODE;
	@ColumnMapping(columnName = "REMSG", columnType = "String")
	private String REMSG;
	
	private String channelId;
	private String primarySeqNo;
	private String tradeNo;//前序业务交易编号
	private String refNo;//业务参号
	
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
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getPrimarySeqNo() {
		return primarySeqNo;
	}
	public void setPrimarySeqNo(String primarySeqNo) {
		this.primarySeqNo = primarySeqNo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
}
