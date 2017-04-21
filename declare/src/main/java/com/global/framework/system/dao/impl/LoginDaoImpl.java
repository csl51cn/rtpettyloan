package com.global.framework.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.LoginDao;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.User;
import com.global.framework.system.domain.UserLoginLog;
import com.global.framework.system.domain.UserRole;

/**
 * @author cqchenf@qq.com
 * @date 2012-1-5 下午10:10:20
 * @version v1.0
 */
@Repository("loginDao")
public class LoginDaoImpl extends BaseDaoSupport implements LoginDao {

	public User login(User user) throws BaseException {
		String sql = "select t.*,(select orgname from sys_org where orgid=t.orgid) orgName from sys_user t where t.userCode = ?";
		return (User) super.findForObjectBySql(sql, new Object[]{user.getUserCode()}, User.class);
	}

	public boolean isFirstLogin(String userCode) throws BaseException {
		return false;
	}

	public void insertLoginLog(UserLoginLog login) throws BaseException {
		super.insert(login);
	}

	public void updateLoginLog(UserLoginLog log) throws BaseException {
		String sql = "update sys_userloginlog set logouttime=?,logoutstatus=? where userid=? and logintime=?";
		super.updateBySql(sql, new Object[] { log.getLogoutTime(), 
				log.getLogoutStatus(),
				log.getUserId(), 
				log.getLoginTime() });
	}

	public UserLoginLog getLastLoginLog(String userId) throws BaseException {
		String sql = "select logid,loginip,logintime,logouttime,logoutstatus from sys_userloginlog where userid=? AND loginTime=(select max(logintime) from sys_userloginlog)";
		return (UserLoginLog) super.findForObjectBySql(sql, new Object[]{userId}, UserLoginLog.class);
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> getUserRoleList(String userId) throws BaseException {
		String sql = "select t.*,(select rolename from sys_role where roleid=t.roleid) rolename from sys_userrole t where t.userid = ?";
		return (List<UserRole>) super.findForListBySql(sql, new Object[]{userId}, UserRole.class);
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> getUserRoleList() throws BaseException {
		String sql = "select t.*,(select rolename from sys_role where roleid=t.roleid) rolename from sys_userrole t ";
		return (List<UserRole>) super.findForListBySql(sql, null, UserRole.class);
	}

	public DataRight getDataRight(String roleId) throws BaseException {
		String sql = "select * from sys_roledataright t where t.roleid = ? ";
		return (DataRight) super.findForObjectBySql(sql, new Object[]{roleId}, DataRight.class);
	}
}
