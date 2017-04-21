package com.global.fems.business.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 结售汇额度登记指令同步核对接口
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "", primaryKeyType = "Single", tableName = "")
public class SpfeSynOdr extends Entity{
	
	@ColumnMapping(columnName = "SYNC_DATE", columnType = "String")
	private String SYNC_DATE; // 同步日期
	
	@ColumnMapping(columnName = "SYNC_FILE_NAME", columnType = "String")
	private String SYNC_FILE_NAME;//文件名
	@ColumnMapping(columnName = "QTY", columnType = "String")
	private String QTY;//总条数
	@ColumnMapping(columnName = "SEQNO", columnType = "String")
	private String SEQNO;//业务流水号
	@ColumnMapping(columnName = "BIZNO", columnType = "String")
	private String BIZNO;//业务编号
	@ColumnMapping(columnName = "REFNO", columnType = "String")
	private String REFNO;//业务参号
	@ColumnMapping(columnName = "TRADE_TYPE", columnType = "String")
	private String TRADE_TYPE;//交易类型
	@ColumnMapping(columnName = "TXCCY", columnType = "String")
	private String TXCCY;//币种
	@ColumnMapping(columnName = "TXAMT", columnType = "String")
	private String TXAMT;//金额
	@ColumnMapping(columnName = "TRADE_TIME", columnType = "String")
	private String TRADE_TIME;//交易时间
	@ColumnMapping(columnName = "HANDLE_TIME", columnType = "String")
	private String HANDLE_TIME;//处理时间
	@ColumnMapping(columnName = "SYNC_DATE", columnType = "String")
	private String STATUS;//处理状态

	public String getSYNC_DATE() {
		return SYNC_DATE;
	}

	public void setSYNC_DATE(String sYNC_DATE) {
		SYNC_DATE = sYNC_DATE;
	}

	public String getSYNC_FILE_NAME() {
		return SYNC_FILE_NAME;
	}

	public void setSYNC_FILE_NAME(String sYNC_FILE_NAME) {
		SYNC_FILE_NAME = sYNC_FILE_NAME;
	}

	public String getQTY() {
		return QTY;
	}

	public void setQTY(String qTY) {
		QTY = qTY;
	}

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

	public String getREFNO() {
		return REFNO;
	}

	public void setREFNO(String rEFNO) {
		REFNO = rEFNO;
	}

	public String getTRADE_TYPE() {
		return TRADE_TYPE;
	}

	public void setTRADE_TYPE(String tRADE_TYPE) {
		TRADE_TYPE = tRADE_TYPE;
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

	public String getTRADE_TIME() {
		return TRADE_TIME;
	}

	public void setTRADE_TIME(String tRADE_TIME) {
		TRADE_TIME = tRADE_TIME;
	}

	public String getHANDLE_TIME() {
		return HANDLE_TIME;
	}

	public void setHANDLE_TIME(String hANDLE_TIME) {
		HANDLE_TIME = hANDLE_TIME;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	
	
}
