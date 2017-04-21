package com.global.fems.message.domain.business.receive;

import com.global.fems.message.domain.DataStores;

/**
 * 类描述：美元折算率查询 接口代码：000003
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class RecvSafeExRateQuery extends DataStores {
	
	/** 每页条数  */
	private String PAGESIZE;	
	/** 当前页码 */
	private String PAGENUMBER;
	/** 1.币种代码 */
	private String CURRENCY_CODE;
	/** 2.年月 */
	private String YEAR_MONTH;

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

	public String getCURRENCY_CODE() {
		return CURRENCY_CODE;
	}

	public void setCURRENCY_CODE(String cURRENCY_CODE) {
		CURRENCY_CODE = cURRENCY_CODE;
	}

	public String getYEAR_MONTH() {
		return YEAR_MONTH;
	}

	public void setYEAR_MONTH(String yEAR_MONTH) {
		YEAR_MONTH = yEAR_MONTH;
	}
}
