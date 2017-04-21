package com.global.fems.message.domain.business.response;

import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class RspSafeExRateQueryRec extends SerialBean {
	private String YEAR_MONTH    ;//年月
	private String CURRENCY_CODE ;//币种
	private String EXCHANGE;// 折算率

	public String getYEAR_MONTH() {
		return YEAR_MONTH;
	}

	public void setYEAR_MONTH(String yEAR_MONTH) {
		YEAR_MONTH = yEAR_MONTH;
	}

	public String getCURRENCY_CODE() {
		return CURRENCY_CODE;
	}

	public void setCURRENCY_CODE(String cURRENCY_CODE) {
		CURRENCY_CODE = cURRENCY_CODE;
	}

	public String getEXCHANGE() {
		return EXCHANGE;
	}

	public void setEXCHANGE(String eXCHANGE) {
		EXCHANGE = eXCHANGE;
	}
}
