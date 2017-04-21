package com.global.framework.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.exception.BaseException;
import com.global.framework.system.constants.LoginStatusCodeConstants;
import com.global.framework.system.dao.LoginDao;
import com.global.framework.system.dao.UserDao;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.User;
import com.global.framework.system.domain.UserLoginLog;
import com.global.framework.system.domain.UserRole;
import com.global.framework.system.service.LoginService;
import com.global.framework.system.web.common.util.MD5Util;

/**
 * 用户登录Service
 * 
 * @author cqchenf@qq.com
 * @date 2012-1-5 下午9:30:42
 * @version v1.0
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDao;
	@Autowired
	private UserDao userDao;

	
	public User login(User userInfo) throws BaseException {
		// 初始化登录成功状态
		String loginErrorCode = LoginStatusCodeConstants.OK.getCode();

		User user = loginDao.login(userInfo);

		// 1.检查帐号是否存在
		if (null == user || StringUtils.isBlank(user.getUserId())) {
			loginErrorCode = LoginStatusCodeConstants.USERNAME_ERROR.getCode();
			user = new User();
			user.setLoginErrorCode(loginErrorCode);
			return user;
		}

		// 2.检查帐号是否被锁定
		if ("N".equals(user.getStatus())) {
			loginErrorCode = LoginStatusCodeConstants.ACCOUNT_LOCKED.getCode();
			user = new User();
			user.setLoginErrorCode(loginErrorCode);
			return user;
		}

		// 3.检查帐号的密码是否正确
		boolean isRight = false;
		if (MD5Util.getMD5(userInfo.getPassword()).equals(user.getPassword())) {
			// 密码正确
			isRight = true;

			// 检查是否首次登录
			boolean isFirstLogin = loginDao.isFirstLogin(user.getUserId());
			if (isFirstLogin) {
				loginErrorCode = LoginStatusCodeConstants.FIRST_LOGIN.getCode();
			} else {
				// 非首次登录则检查密码是否过期，过期则提醒

			}
		}

		// 密码错误
		if (!isRight) {
			int count = user.getCurrentWrongTimes();
			count++;
			user.setCurrentWrongTimes(count);
			// 超过最大错误次数，账号将被锁定
			if (count > user.getMaxWrongTimes()) {
				// user.setStatus(UserConstants.UserStatus.LOCK.getCode());
				loginErrorCode = LoginStatusCodeConstants.MAX_WRONG_TIMES
						.getCode();

				// 产生系统告警

			} else {
				loginErrorCode = LoginStatusCodeConstants.PASSWORD_ERROR
						.getCode();
			}
			userDao.updateUser(user);// 更新用户状态和当前密码错误次数
		}
		user.setLoginErrorCode(loginErrorCode);

		// 4.查询用户权限
		// List<MenuDto> menuPrivs =
		// loginDao.getMenuPrivileges(user.getUserNo());
		// List<MenuOperationDto> operatePrivs =
		// loginDao.getOperatePrivileges(user.getUserNo());
		// List<FileDto> filePrivs =
		// loginDao.getFilePrivileges(user.getUserNo());
		// List<ElementDto> elementPrivs =
		// loginDao.getElementPrivileges(user.getUserNo());
		//
		// Map<Integer, Object> privileges = new HashMap<Integer, Object>();
		// privileges.put(PrivilegeTypeConstants.MENU, menuPrivs);
		// privileges.put(PrivilegeTypeConstants.OPERATE, operatePrivs);
		// privileges.put(PrivilegeTypeConstants.FILE, filePrivs);
		// privileges.put(PrivilegeTypeConstants.ELEMENT, elementPrivs);
		//
		// user.setPrivileges(privileges);

		// 5.写用户访问日志信息

		return user;
	}

	
	public void insertLoginLog(UserLoginLog login) throws Exception {
		this.loginDao.insertLoginLog(login);
	}

	
	public void updateLoginLog(UserLoginLog log) throws Exception {
		this.loginDao.updateLoginLog(log);
	}

	
	public UserLoginLog getLastLoginLog(String userNo) throws BaseException {
		return this.loginDao.getLastLoginLog(userNo);
	}

	
	public List<UserRole> getUserRoleList(String userNo) throws BaseException {
		return this.loginDao.getUserRoleList(userNo);
	}

	
	public List<UserRole> getUserRoleList() throws BaseException {
		return this.loginDao.getUserRoleList();
	}

	
	public List<DataRight> getDataRightByRole(List<UserRole> userRoles) throws BaseException {
		List<DataRight> list = new ArrayList<DataRight>();
		for (UserRole userRole : userRoles) {
			DataRight dr = this.loginDao.getDataRight(userRole.getRoleId());
			if (dr != null) {
				list.add(dr);
			}
		}
		return list;
	}
}
