package com.global.fems.message.domain.business.receive;

import com.global.fems.message.domain.DataStores;

/**
 * 外管登录用户信息更新
 * @author Administrator
 *
 */
public class RecvCommonUser extends DataStores {

	private String COMMON_ORG_CODE;//金融机构代码 当CREATE_FLAG为1-新增时，必输
	private String OPERNO;//核心柜员号
	private String COMMON_USER_CODE;//用户名
	private String PASSWORD;//密码 （明文密码需经MD5（32位，小写）加密）
	private String CREATE_FLAG;//创建标识 1:新增，2：修改
	
	public String getCOMMON_ORG_CODE() {
		return COMMON_ORG_CODE;
	}
	public void setCOMMON_ORG_CODE(String cOMMON_ORG_CODE) {
		COMMON_ORG_CODE = cOMMON_ORG_CODE;
	}
	public String getOPERNO() {
		return OPERNO;
	}
	public void setOPERNO(String oPERNO) {
		OPERNO = oPERNO;
	}
	public String getCOMMON_USER_CODE() {
		return COMMON_USER_CODE;
	}
	public void setCOMMON_USER_CODE(String cOMMON_USER_CODE) {
		COMMON_USER_CODE = cOMMON_USER_CODE;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getCREATE_FLAG() {
		return CREATE_FLAG;
	}
	public void setCREATE_FLAG(String cREATE_FLAG) {
		CREATE_FLAG = cREATE_FLAG;
	}
}
