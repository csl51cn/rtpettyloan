package com.global.fems.message.domain.business.response;

import java.util.List;

import com.global.fems.message.domain.DataStores;


/**
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class RspSafeExRateQuery extends DataStores {
	
	private String PAGESIZE; //每页的条数
	private String PAGENUMBER;//当前页码
	private String totalCount;// 总条数
	private List rec;// 循环记录

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

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List getRec() {
		return rec;
	}

	public void setRec(List rec) {
		this.rec = rec;
	}

}
