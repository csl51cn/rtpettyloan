package com.global.param.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.CommonOrgUser;
import com.global.framework.system.domain.User;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.util.SysUtils;
import com.global.web.BaseController;

/**
 * 外管用户信息管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/param/commonUserController.do")
public class CommonUserController extends BaseController {
	
	@Autowired
	private SysCommonService sysCommonService;

	@RequestMapping(params = "method=commonUserList")
	public String commonUserList() {
		return "/param/commonUser/commonUserList";
	}
	
	@ResponseBody
	@RequestMapping(params = "method=queryCommonOrgUser")
	public Map<String, Object> queryCommonOrgUser(CommonOrgUser user, PageBean page) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>(2);
		PageBean pageBean = this.sysCommonService.queryCommonOrgUserForPage(user, page);
		map.put(LIST_TOTAL, pageBean.getTotalRows());
		map.put(LIST_ROWS, pageBean.getDataList());
		return map;
	}
	
	@RequestMapping(params = "method=editCommonOrgUser")
	public String editCommonOrgUser(String operNo, Model model) throws BaseException {
		if (StringUtils.isNotBlank(operNo)) {
			CommonOrgUser user = this.sysCommonService.getCommonOrgUser(operNo);
			model.addAttribute("commonUser", user);
		}
		return "param/commonUser/commonUserEdit";
	}
	
	@ResponseBody
	@RequestMapping(params = "method=saveCommonOrgUser")
	public CommonOrgUser saveCommonOrgUser(CommonOrgUser user, HttpServletRequest req) throws BaseException {
		User u = super.getSessionUser(req);
		user.setUPDATE_TYPE("1");
		user.setUPDATE_USERID(u.getUserCode());
		user.setUPDATE_TIME(SysUtils.getNowDateTime());
		return this.sysCommonService.saveOrUpdateCommonUser(user);
	}
	
	@ResponseBody
	@RequestMapping(params = "method=deleteCommonOrgUser")
	public void deleteCommonOrgUser(String operNo, Model model) throws BaseException {
		this.sysCommonService.delCommonOrgUser(operNo);
	}
}
