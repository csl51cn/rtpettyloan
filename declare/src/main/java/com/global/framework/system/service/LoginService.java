package com.global.framework.system.service;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.User;
import com.global.framework.system.domain.UserLoginLog;
import com.global.framework.system.domain.UserRole;

/**
 * @author cqchenf@qq.com
 * @date 2012-1-5 下午9:30:13
 * @version v1.0
 */
public interface LoginService {

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	public User login(User user) throws BaseException;

	/**
	 * 插入用户登录日志
	 * 
	 * @param log
	 */
	public void insertLoginLog(UserLoginLog log) throws Exception;

	public void updateLoginLog(UserLoginLog log) throws Exception;

	public UserLoginLog getLastLoginLog(String userId) throws BaseException;

	public List<UserRole> getUserRoleList(String userId) throws BaseException;

	/**
	 * 获取用户角色信息列表
	 * 
	 * @return
	 */
	public List<UserRole> getUserRoleList() throws BaseException;

	public List<DataRight> getDataRightByRole(List<UserRole> userRoles) throws BaseException;
}
