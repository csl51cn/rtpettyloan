package com.global.framework.system.service;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.framework.system.domain.UserRole;

/**
 * @author cqchenf@qq.com
 * @date 2011-8-20 下午8:21:06
 * @version v1.0
 */
public interface UserService {

	/**
	 * 查询用户详情
	 * 
	 * @param user
	 * @return
	 */
	public User getUserInfo(User user) throws BaseException;

	/**
	 * 分页查询用户列表
	 * 
	 * @param user
	 * @param page
	 * @return
	 */
	public PageBean queryUserForPage(User user, PageBean page)
			throws BaseException;

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @return
	 */
	public User saveOrUpdate(User user) throws BaseException;

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	public User updateUser(User user) throws BaseException;

	/**
	 * 删除用户信息
	 * 
	 * @param user
	 */
	public void deleteUser(User user) throws BaseException;

	/**
	 * 检查用户是否存在
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean checkUserExist(String userCode) throws BaseException;

	/**
	 * 根据用户ID获取角色信息
	 * 
	 * @param userNo
	 * @return
	 */
	public List<UserRole> getRolesByUserId(String userId) throws BaseException;

	public void updateUserPwd(User user) throws BaseException;

	public void updateUserStatus(String userIds, String status, User u)
			throws BaseException;

	public void batchUpdateUserPwd(String userIds) throws BaseException;

	public void saveUserRole(String userRoles) throws BaseException;

	public User findUserByUserCode(String operNo) throws BaseException;

}
