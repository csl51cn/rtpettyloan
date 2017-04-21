package com.global.fems.message.domain.business.receive;

import com.global.fems.message.domain.DataStores;

/**
 * 预关注风险提示/关注名单告知接口
 * @author Administrator
 *
 */
public class RecvMakeUpSignStatus extends DataStores {

	private String IDTYPE_CODE;
	private String IDCODE;
	private String CTYCODE;
	private String PERSON_NAME;
	
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
	public String getPERSON_NAME() {
		return PERSON_NAME;
	}
	public void setPERSON_NAME(String pERSON_NAME) {
		PERSON_NAME = pERSON_NAME;
	}
}
