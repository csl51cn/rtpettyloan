package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

@SuppressWarnings("serial")
@TableMapping(primaryKey = "OPERNO", primaryKeyType = "Single", tableName = "dc_PA_COMMON_ORG_USER")
public class CommonOrgUser extends Entity {

	@ColumnMapping(columnName = "common_org_code", columnType = "String")
	private String COMMON_ORG_CODE;
	@ColumnMapping(columnName = "common_user_code", columnType = "String")
	private String COMMON_USER_CODE;
	@ColumnMapping(columnName = "password", columnType = "String")
	private String PASSWORD;
	@ColumnMapping(columnName = "operno", columnType = "String")
	private String OPERNO;
	@ColumnMapping(columnName = "UPDATE_TYPE", columnType = "String")
	private String UPDATE_TYPE;
	@ColumnMapping(columnName = "UPDATE_USERID", columnType = "String")
	private String UPDATE_USERID;
	@ColumnMapping(columnName = "UPDATE_TIME", columnType = "Timestamp")
	private String UPDATE_TIME;

	public String getCOMMON_ORG_CODE() {
		return COMMON_ORG_CODE;
	}

	public void setCOMMON_ORG_CODE(String cOMMON_ORG_CODE) {
		COMMON_ORG_CODE = cOMMON_ORG_CODE;
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

	public String getOPERNO() {
		return OPERNO;
	}

	public void setOPERNO(String oPERNO) {
		OPERNO = oPERNO;
	}

	public String getUPDATE_TYPE() {
		return UPDATE_TYPE;
	}

	public void setUPDATE_TYPE(String uPDATE_TYPE) {
		UPDATE_TYPE = uPDATE_TYPE;
	}

	public String getUPDATE_USERID() {
		return UPDATE_USERID;
	}

	public void setUPDATE_USERID(String uPDATE_USERID) {
		UPDATE_USERID = uPDATE_USERID;
	}

	public String getUPDATE_TIME() {
		return UPDATE_TIME;
	}

	public void setUPDATE_TIME(String uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}

}
