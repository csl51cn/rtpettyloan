package com.global.framework.system.dao;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.User;
import com.global.framework.system.domain.UserLoginLog;
import com.global.framework.system.domain.UserRole;

/**
 * @author cqchenf@qq.com
 * @date 2012-1-5 下午10:09:59
 * @version v1.0
 */
public interface LoginDao {

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	public User login(User user) throws BaseException;

	/**
	 * 检查是否首次登录
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean isFirstLogin(String userCode) throws BaseException;

	public void insertLoginLog(UserLoginLog login) throws BaseException;

	public void updateLoginLog(UserLoginLog login) throws BaseException;

	public UserLoginLog getLastLoginLog(String userId) throws BaseException;

	public List<UserRole> getUserRoleList(String userId) throws BaseException;

	/**
	 * 获取用户角色信息列表
	 * 
	 * @return
	 */
	public List<UserRole> getUserRoleList() throws BaseException;

	public DataRight getDataRight(String roleId) throws BaseException;
}
