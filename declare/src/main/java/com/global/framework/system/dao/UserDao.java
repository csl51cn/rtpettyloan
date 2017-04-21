package com.global.framework.system.dao;

import java.util.LinkedList;
import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.User;
import com.global.framework.system.domain.UserRole;

/**
 * 用户DAO
 * @author cqchenf@163.com
 * @date 2011-8-21 上午11:56:04
 * @version v1.0
 */
public interface UserDao {

	/**
	 * 新增用户信息
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User insertUser(User user) throws BaseException;

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 */
	public void updateUser(User user) throws BaseException;

	/**
	 * 删除用户信息
	 * @param user
	 */
	public void deleteUser(User user) throws BaseException;

	/**
	 * 获取用户详情
	 * 
	 * @param user
	 * @return
	 */
	public User getUserInfo(User user) throws BaseException;

	/**
	 * 检查用户是否存在
	 * @param userCode
	 * @return
	 */
	public boolean checkUserExist(String userCode) throws BaseException;

	/**
	 * 分页查询用户
	 * @param user
	 * @param page
	 * @return
	 */
	public PageBean queryUserForPage(User user, PageBean page) throws BaseException;

	/**
	 * 根据用户ID获取角色信息
	 * @param userId
	 * @return
	 */
	public List<UserRole> getRolesByUserId(String userId) throws BaseException;

	public void deleteUserRoleByUserId(String userId) throws BaseException;

	public void saveUserRole(LinkedList<UserRole> userRoleList) throws BaseException;

	public void saveDataRight(DataRight dr) throws BaseException;

	public void deleteDataRightByUserId(String userId) throws BaseException;

	public void updateUserPwd(User user) throws BaseException;

	public void batchUpdateUser(List<Object[]> list) throws BaseException;

	public void batchUpdateUserPwd(List<Object[]> list) throws BaseException;

	public User findUserByUserCode(String userCode) throws BaseException;
}
