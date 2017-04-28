package com.global.framework.system.domain;

import java.util.List;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

/**
 * @author cqchenf@qq.com
 * @date 2011-8-20 下午2:47:49
 * @version v1.0
 */
@SuppressWarnings("serial")
@TableMapping(primaryKey = "userId", primaryKeyType = "Single", tableName = "dc_sys_user")
public class User extends Entity {

	@ColumnMapping(columnName = "userId", columnType = "String")
	private String userId;
	@ColumnMapping(columnName = "userCode", columnType = "String")
	private String userCode;
	@ColumnMapping(columnName = "userName", columnType = "String")
	private String userName;
	@ColumnMapping(columnName = "password", columnType = "String")
	private String password;
	@ColumnMapping(columnName = "sex", columnType = "Integer")
	private String sex;
	@ColumnMapping(columnName = "orgId", columnType = "String")
	private String orgId;
	@ColumnMapping(columnName = "mobileNo", columnType = "String")
	private String mobileNo;
	@ColumnMapping(columnName = "officeTel", columnType = "String")
	private String officeTel;
	@ColumnMapping(columnName = "email", columnType = "String")
	private String email;
	@ColumnMapping(columnName = "msn", columnType = "String")
	private String msn;
	@ColumnMapping(columnName = "qq", columnType = "String")
	private String qq;
	@ColumnMapping(columnName = "status", columnType = "String")
	private String status;
	@ColumnMapping(columnName = "remark", columnType = "String")
	private String remark;

	private List<UserRole> roles;
	private List<RoleRight> rights;
	private List<DataRight> roleDataRight;
	private String loginErrorCode;
	private Integer maxWrongTimes;// 登录密码最大错误次数
	private Integer currentWrongTimes;// 登录密码当前错误次数
	private String loginTime;// 当前登录时间
	private String loginIp;// 当前登录IP

	private String orgName;// 所属机构名称

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
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

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	public String getLoginErrorCode() {
		return loginErrorCode;
	}

	public void setLoginErrorCode(String loginErrorCode) {
		this.loginErrorCode = loginErrorCode;
	}

	public Integer getMaxWrongTimes() {
		return maxWrongTimes;
	}

	public void setMaxWrongTimes(Integer maxWrongTimes) {
		this.maxWrongTimes = maxWrongTimes;
	}

	public Integer getCurrentWrongTimes() {
		return currentWrongTimes;
	}

	public void setCurrentWrongTimes(Integer currentWrongTimes) {
		this.currentWrongTimes = currentWrongTimes;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<RoleRight> getRights() {
		return rights;
	}

	public void setRights(List<RoleRight> rights) {
		this.rights = rights;
	}

	public List<DataRight> getRoleDataRight() {
		return roleDataRight;
	}

	public void setRoleDataRight(List<DataRight> roleDataRight) {
		this.roleDataRight = roleDataRight;
	}

}
