package com.global.fems.message.domain.business.receive;

import com.global.fems.message.domain.DataStores;

/**
 * 类描述：个人结售汇额度查询 接口代码：000001
 * 
 * @ClassName: RecvIndividualLCYQuery
 * @Description: TODO
 * @author leonardLeo
 * @date 2015-6-24 下午09:21:38
 * 
 */
public class RecvIndividualLCYQuery extends DataStores {
	/** 1.证件类型代码 */
	private String IDTYPE_CODE;
	/** 2.国家/地区代码 */
	private String CTYCODE;
	/** 3.证件号码 */
	private String IDCODE;
	/** 4.交易类型 */
	private String TRADE_TYPE;

	public String getIDTYPE_CODE() {
		return IDTYPE_CODE;
	}

	public void setIDTYPE_CODE(String iDTYPECODE) {
		IDTYPE_CODE = iDTYPECODE;
	}

	public String getCTYCODE() {
		return CTYCODE;
	}

	public void setCTYCODE(String cTYCODE) {
		CTYCODE = cTYCODE;
	}

	public String getIDCODE() {
		return IDCODE;
	}

	public void setIDCODE(String iDCODE) {
		IDCODE = iDCODE;
	}

	public String getTRADE_TYPE() {
		return TRADE_TYPE;
	}

	public void setTRADE_TYPE(String tRADETYPE) {
		TRADE_TYPE = tRADETYPE;
	}
}
