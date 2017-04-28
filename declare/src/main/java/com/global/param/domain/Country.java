package com.global.param.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 国家信息
 * 
 * @author chen.feng
 * @date 2014-6-14 下午10:47:26
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "countryNo", primaryKeyType = "Single", tableName = "dc_pa_country_info")
public class Country extends Entity {

	@ColumnMapping(columnName = "countryNo", columnType = "String")
	private String countryNo;
	@ColumnMapping(columnName = "cnName", columnType = "String")
	private String cnName;
	@ColumnMapping(columnName = "enName", columnType = "String")
	private String enName;
	@ColumnMapping(columnName = "enShortName", columnType = "String")
	private String enShortName;
	@ColumnMapping(columnName = "safeCode", columnType = "String")
	private String safeCode;
	@ColumnMapping(columnName = "isSancTionist", columnType = "String")
	private String isSancTionist;// 是否制裁(Y/N 默认是N)

	public String getCountryNo() {
		return countryNo;
	}

	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getEnShortName() {
		return enShortName;
	}

	public void setEnShortName(String enShortName) {
		this.enShortName = enShortName;
	}

	public String getSafeCode() {
		return safeCode;
	}

	public void setSafeCode(String safeCode) {
		this.safeCode = safeCode;
	}

	public String getIsSancTionist() {
		return isSancTionist;
	}

	public void setIsSancTionist(String isSancTionist) {
		this.isSancTionist = isSancTionist;
	}
}
