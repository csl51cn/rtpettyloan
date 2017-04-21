package com.global.framework.system.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * 机构实体类
 * @author cqchenf@qq.com
 * @date 2011-7-2 下午01:38:20
 * @version v1.0
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "orgId", primaryKeyType = "Single", tableName = "sys_org")
public class Org extends Entity {
	
	@ColumnMapping(columnName = "orgId", columnType = "String")
	private String orgId;
	@ColumnMapping(columnName = "orgCode", columnType = "String")
	private String orgCode;
	@ColumnMapping(columnName = "orgName", columnType = "String")
	private String orgName;
	@ColumnMapping(columnName = "orgShortName", columnType = "String")
	private String orgShortName;
	@ColumnMapping(columnName = "parentOrgId", columnType = "String")
	private String parentOrgId;
	@ColumnMapping(columnName = "orgLevel", columnType = "String")
	private String orgLevel;
	@ColumnMapping(columnName = "address", columnType = "String")
	private String address;
	@ColumnMapping(columnName = "tel", columnType = "String")
	private String tel;
	@ColumnMapping(columnName = "fax", columnType = "String")
	private String fax;
	@ColumnMapping(columnName = "linkPerson", columnType = "String")
	private String linkPerson;
	@ColumnMapping(columnName = "status", columnType = "String")
	private String status;
	@ColumnMapping(columnName = "sortNo", columnType = "Integer")
	private String sortNo;
	@ColumnMapping(columnName = "remark", columnType = "String")
	private String remark;
	
	@ColumnMapping(columnName = "bankCode", columnType = "String")
	private String bankCode;//金融机构标识码
	@ColumnMapping(columnName = "swiftCode", columnType = "String")
	private String swiftCode;
	@ColumnMapping(columnName = "enName", columnType = "String")
	private String enName;
	@ColumnMapping(columnName = "enAddr", columnType = "String")
	private String enAddr;//英文地址
	@ColumnMapping(columnName = "economyType", columnType = "String")
	private String economyType;//外管局经济类型
	@ColumnMapping(columnName = "certType", columnType = "String")
	private String certType;//证件类型
	@ColumnMapping(columnName = "certCode", columnType = "String")
	private String certCode;//证件号码
	@ColumnMapping(columnName = "industryType", columnType = "String")
	private String industryType;//行业属性代码
	@ColumnMapping(columnName = "areaId", columnType = "String")
	private String areaId;//所在地
	@ColumnMapping(columnName = "dptName", columnType = "String")
	private String dptName;//牵头部门名称
	@ColumnMapping(columnName = "principal", columnType = "String")
	private String principal;//统计负责人
	@ColumnMapping(columnName = "principalTel", columnType = "String")
	private String principalTel;//统计负责人联系电话
	@ColumnMapping(columnName = "ISDECLAREORG", columnType = "String")
	private String isDeclareOrg;//是否申报机构
	
	private String parentOrgName;
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgShortName() {
		return orgShortName;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	public String getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLinkPerson() {
		return linkPerson;
	}

	public void setLinkPerson(String linkPerson) {
		this.linkPerson = linkPerson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParentOrgName() {
		return parentOrgName;
	}

	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getEnAddr() {
		return enAddr;
	}

	public void setEnAddr(String enAddr) {
		this.enAddr = enAddr;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getEconomyType() {
		return economyType;
	}

	public void setEconomyType(String economyType) {
		this.economyType = economyType;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getDptName() {
		return dptName;
	}

	public void setDptName(String dptName) {
		this.dptName = dptName;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPrincipalTel() {
		return principalTel;
	}

	public void setPrincipalTel(String principalTel) {
		this.principalTel = principalTel;
	}

	public String getIsDeclareOrg() {
		return isDeclareOrg;
	}

	public void setIsDeclareOrg(String isDeclareOrg) {
		this.isDeclareOrg = isDeclareOrg;
	}
	
}
