package com.global.param.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 币种表
 * 
 * @author xu.ke
 * @date 2014-6-18下午4:04:11
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "curSign", primaryKeyType = "Single", tableName = "dc_pa_currency_info")
public class Currency extends Entity {
	@ColumnMapping(columnName = "CURSIGN", columnType = "String")
	private String curSign;// 货币符号
	@ColumnMapping(columnName = "STANDARDCODE", columnType = "String")
	private String standardCode;// 标准代号
	@ColumnMapping(columnName = "CUSTOMCODE", columnType = "String")
	private String customCode;// 自定义代号
	@ColumnMapping(columnName = "NAME", columnType = "String")
	private String name;// 货币名称
	@ColumnMapping(columnName = "CNNAME", columnType = "String")
	private String cnName;// 中文名称
	@ColumnMapping(columnName = "EUROPEANIZE", columnType = "String")
	private String europeanize;// 是否欧共体(Y/N)
	@ColumnMapping(columnName = "FORECHARGERATE", columnType = "String")
	private String forEcharGerate;// 外管局折算率
	@ColumnMapping(columnName = "TALLYID", columnType = "String")
	private String tallyID;// 记帐货币编号
	@ColumnMapping(columnName = "ENDCODE", columnType = "String")
	private String endCode;// 尾代码
	@ColumnMapping(columnName = "ISINT", columnType = "String")
	private String isInt;// 是否有小数位(Y/N)
	@ColumnMapping(columnName = "COUNTRY", columnType = "String")
	private String country;// 所属国别
	@ColumnMapping(columnName = "USEORDER", columnType = "String")
	private String useOrder;// 使用顺序
	@ColumnMapping(columnName = "ISOFTEN", columnType = "String")
	private String isOften;// 是否为常用货币(Y/N)

	public String getCurSign() {
		return curSign;
	}

	public void setCurSign(String curSign) {
		this.curSign = curSign;
	}

	public String getStandardCode() {
		return standardCode;
	}

	public void setStandardCode(String standardCode) {
		this.standardCode = standardCode;
	}

	public String getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEuropeanize() {
		return europeanize;
	}

	public void setEuropeanize(String europeanize) {
		this.europeanize = europeanize;
	}

	public String getForEcharGerate() {
		return forEcharGerate;
	}

	public void setForEcharGerate(String forEcharGerate) {
		this.forEcharGerate = forEcharGerate;
	}

	public String getTallyID() {
		return tallyID;
	}

	public void setTallyID(String tallyID) {
		this.tallyID = tallyID;
	}

	public String getEndCode() {
		return endCode;
	}

	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	public String getIsInt() {
		return isInt;
	}

	public void setIsInt(String isInt) {
		this.isInt = isInt;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUseOrder() {
		return useOrder;
	}

	public void setUseOrder(String useOrder) {
		this.useOrder = useOrder;
	}

	public String getIsOften() {
		return isOften;
	}

	public void setIsOften(String isOften) {
		this.isOften = isOften;
	}

}
