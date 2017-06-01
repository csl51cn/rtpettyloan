package com.global.framework.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.UserDao;
import com.global.framework.system.domain.User;
import com.global.framework.system.domain.UserRole;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.system.service.UserService;
import com.global.framework.system.web.common.util.MD5Util;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author cqchenf@qq.com
 * @date 2011-8-21 下午12:07:01
 * @version v1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private SysCommonService sysCommonService;

	public User getUserInfo(User user) throws BaseException {
		return this.userDao.getUserInfo(user);
	}

	
	public boolean checkUserExist(String userCode) throws BaseException {
		return this.userDao.checkUserExist(userCode);
	}

	
	public PageBean queryUserForPage(User user, PageBean page)
			throws BaseException {
		return this.userDao.queryUserForPage(user, page);
	}

	
	public User saveOrUpdate(User user) throws BaseException {
		if (user != null && StringUtils.isNotBlank(user.getUserId())) {
			return this.updateUser(user);
		} else {
			user.setUserId(sysCommonService.getNo("SYS_USER"));
			user.setPassword(MD5Util.getMD5("123456"));// 初始密码123456
			user.setStatus("Y");
			return this.userDao.insertUser(user);
		}
	}

	
	public User updateUser(User user) throws BaseException {
		this.userDao.updateUser(user);
		return user;
	}

	
	public void deleteUser(User user) throws BaseException {
		this.userDao.deleteUser(user);
		//删除用户角色关系表
		this.userDao.deleteUserRoleByUserId(user.getUserId());
	}

	
	public List<UserRole> getRolesByUserId(String userId) throws BaseException {
		return this.userDao.getRolesByUserId(userId);
	}

	
	public void updateUserPwd(User user) throws BaseException {
		user.setPassword(MD5Util.getMD5(user.getPassword()));
		this.userDao.updateUserPwd(user);
	}

	
	public void updateUserStatus(String userIds, String status, User u)
			throws BaseException {
		String[] ids = userIds.split(",");
		List<Object[]> list = new ArrayList<Object[]>();
		for (int i = 0; i < ids.length; i++) {
			Object[] objs = new Object[2];
			objs[0] = status;
			objs[1] = ids[i];
			if (ids[i].equals(u.getUserId())) {
				throw new BaseException("不允许停用或激活自己【"+u.getUserCode()+"】!");
			}
			list.add(objs);
		}
		

		this.userDao.batchUpdateUser(list);
	}

	
	public void batchUpdateUserPwd(String userIds) throws BaseException {
		String ids[] = userIds.split(",");
		List<Object[]> list = new ArrayList<Object[]>();
		for (int i = 0; i < ids.length; i++) {
			Object[] objs = new Object[2];
			objs[0] = MD5Util.getMD5("a123456");
			objs[1] = ids[i];
			list.add(objs);
		}
		this.userDao.batchUpdateUserPwd(list);
	}
	
	@SuppressWarnings("rawtypes")
	public void saveUserRole(String userRoles) throws BaseException {
		String userId = null;
		LinkedList<UserRole> userRoleList = new LinkedList<UserRole>();
		List<Map> list = JSON.parseArray(userRoles, Map.class);
		for (Map map : list) {
			UserRole ur = new UserRole();
			try {
				BeanUtils.copyProperties(ur, map);
			} catch (Exception e) {
				throw new BaseException(e);
			}
			userId = ur.getUserId();
			userRoleList.add(ur);
		}
		// 1.先删除该用户的已分配的角色
		this.userDao.deleteUserRoleByUserId(userId);
		// 2.再保存该用户分配的角色
		this.userDao.saveUserRole(userRoleList);
	}
	
	public User findUserByUserCode(String operNo) throws BaseException {
		return this.userDao.findUserByUserCode(operNo);
	}
}
