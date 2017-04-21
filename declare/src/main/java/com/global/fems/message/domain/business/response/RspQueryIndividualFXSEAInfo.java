package com.global.fems.message.domain.business.response;

import java.util.List;

import com.global.fems.message.domain.DataStores;

/**
 * 个人结售汇明细信息查询
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class RspQueryIndividualFXSEAInfo extends DataStores {

	private String PAGESIZE; //每页条数
	private String PAGENUMBER; //当前页码
	private String TOTALCOUNT; //总条数
	
	private String TRADE_TYPE; // 交易类型

	private List<RspQueryIndividualFXSEAInfoRec> rec;

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

	public String getTOTALCOUNT() {
		return TOTALCOUNT;
	}

	public void setTOTALCOUNT(String tOTALCOUNT) {
		TOTALCOUNT = tOTALCOUNT;
	}

	public String getTRADE_TYPE() {
		return TRADE_TYPE;
	}

	public void setTRADE_TYPE(String tRADE_TYPE) {
		TRADE_TYPE = tRADE_TYPE;
	}

	public List<RspQueryIndividualFXSEAInfoRec> getRec() {
		return rec;
	}

	public void setRec(List<RspQueryIndividualFXSEAInfoRec> rec) {
		this.rec = rec;
	}

}
