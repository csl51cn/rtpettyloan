package com.global.framework.system.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.constants.Constants;
import com.global.framework.system.domain.Org;
import com.global.framework.system.domain.User;
import com.global.framework.system.domain.UserRole;
import com.global.framework.system.service.OrgService;
import com.global.framework.system.service.UserService;
import com.global.framework.system.web.common.CacheService;
import com.global.framework.system.web.common.session.SessionManager;
import com.global.framework.system.web.common.util.MD5Util;
import com.global.web.BaseController;

@Controller
@RequestMapping("/sys/userController.do")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private OrgService orgService;
	
	/**
	 * 编辑用户
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=edit")
	public String edit(User user, Model model) throws BaseException {
		if (user != null && StringUtils.isNotBlank(user.getUserId())) {
			user = this.userService.getUserInfo(user);
			user.setOrgName(CacheService.getOrgById(user.getOrgId()).getOrgName());
			model.addAttribute("user", user);
			model.addAttribute("sex", Constants.Sex.values());
		}
		return "sys/user/userEdit";
	}

	/**
	 * 保存用户
	 * 
	 * @param user
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=save")
	public User save(User user, HttpServletRequest request) throws BaseException {
		User u = super.getSessionUser(request);
		Set<Org> list = this.orgService.getRightOrgList(u);
		boolean isRight = false;
		if (list != null && list.size() > 0) {
			for (Org org : list) {
				if (user.getOrgId().equals(org.getOrgId())) {
					isRight = true;
					break;
				}
			}
		} else if (user.getOrgId().equals(u.getOrgId())) {
			isRight = true;
		}
		
		if (!isRight) {
			throw new BaseException("你无权限增加或修改该机构的用户信息!");
		}
		return this.userService.saveOrUpdate(user);
	}

	@ResponseBody
	@RequestMapping(params = "method=delete")
	public void delete(User user, HttpServletRequest request) throws BaseException {
		User u = SessionManager.getSession(request);
		if (user.getUserId().equals(u.getUserId())) {
			throw new BaseException("不允许删除自己!");
		}
		this.userService.deleteUser(user);
	}

	/**
	 * 检查用户是否存在
	 * 
	 * @param user
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=checkUserExist")
	public boolean checkUserExist(HttpServletRequest request)
			throws BaseException {
		String userCode = request.getParameter("param");
		return this.userService.checkUserExist(userCode);
	}

	/**
	 * 转到用户列表页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=list")
	public String list(Model model, HttpServletRequest request) {
		model.addAttribute("status", Constants.Status.values());
		return "sys/user/userList";
	}

	/**
	 * 分页查询用户列表
	 * 
	 * @param user
	 * @param page
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=query")
	public Map<String, Object> query(User user, PageBean page,
			HttpServletRequest request) throws BaseException {
		Map<String, Object> result = new HashMap<String, Object>(2);
		User u = super.getSessionUser(request);
		if (StringUtils.isBlank(user.getOrgId())) {
			user.setOrgId(u.getOrgId());
		}
		PageBean pageBean = this.userService.queryUserForPage(user, page);
		result.put(LIST_TOTAL, pageBean.getTotalRows());
		result.put(LIST_ROWS, pageBean.getDataList());
		return result;
	}

	/**
	 * 用户授权
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(params = "method=userAuth")
	public String userAuth(String userId, Model model) throws BaseException {
		model.addAttribute("userId", userId);
		return "sys/user/userAuth";
	}
	
	@ResponseBody
	@RequestMapping(params = "method=getRolesByUserId")
	public List<UserRole> getRolesByUserId(String userId) throws BaseException {
		List<UserRole> userRoles = this.userService.getRolesByUserId(userId);
		return userRoles;
	}
	
	@ResponseBody
	@RequestMapping(params = "method=saveUserRole")
	public void saveUserRole(String userRoles) throws BaseException {
		if (StringUtils.isBlank(userRoles)) {
			throw new BaseException("请选中需要分配的角色信息");
		}
		this.userService.saveUserRole(userRoles);
	}

	@RequestMapping(params = "method=showUserInfo")
	public String showUserInfo(Model model, HttpServletRequest request)
			throws BaseException {
		User user = SessionManager.getSession(request);
		model.addAttribute("user", user);
		model.addAttribute("flag", "1");// 修改个人资料标志位
		model.addAttribute("sex", Constants.Sex.values());
		return "sys/user/userEdit";
	}

	/**
	 * 进入用户密码修改页面
	 * 
	 * @param userCode
	 *            用户登录名
	 * @param model
	 * @return
	 */
	@RequestMapping(params = "method=toChangePwd")
	public String toChangePwd(String userCode, String flag, Model model)
			throws BaseException {
		model.addAttribute("userCode", userCode);
		model.addAttribute("flag", flag);
		return "sys/user/changePwd";
	}

	@ResponseBody
	@RequestMapping(params = "method=changePwd")
	public boolean changePwd(User user) throws BaseException {
		this.userService.updateUserPwd(user);
		return true;
	}

	@ResponseBody
	@RequestMapping(params = "method=checkOldPwd")
	public Map<String, Object> checkOldPwd(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = SessionManager.getSession(request);
		String oldPwd = request.getParameter("param");
		if (user.getPassword().equals(MD5Util.getMD5(oldPwd))) {
			map.put("info", "验证通过！");
			map.put("status", "y");
		} else {
			map.put("info", "当前密码输入错误");
			map.put("status", "");
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(params = "method=update")
	public void update(String userIds, String status, HttpServletRequest request) throws BaseException {
		User u = SessionManager.getSession(request);
		this.userService.updateUserStatus(userIds, status, u);
	}

	@ResponseBody
	@RequestMapping(params = "method=batchChangePwd")
	public void batchChangePwd(String userIds) throws BaseException {
		this.userService.batchUpdateUserPwd(userIds);
	}
}
