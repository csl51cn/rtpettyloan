package com.global.framework.system.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.constants.Constants;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.Role;
import com.global.framework.system.domain.User;
import com.global.framework.system.service.RoleService;
import com.global.web.BaseController;

@Controller
@RequestMapping("/sys/roleController.do")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(params = "method=list")
	public String list() {
		return "sys/role/roleList";
	}

	@ResponseBody
	@RequestMapping(params = "method=query")
	public Map<String, Object> query(Role role, PageBean page,HttpServletRequest request) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>(2);
		User user = super.getSessionUser(request);
		PageBean pageBean = this.roleService.queryRoleForPage(role, page, user.getUserId());
		map.put(LIST_TOTAL, pageBean.getTotalRows());
		map.put(LIST_ROWS, pageBean.getDataList());
		return map;
	}

	@RequestMapping(params = "method=edit")
	public String edit(Role role, Model model) throws BaseException {
		if (role != null && StringUtils.isNotBlank(role.getRoleId())) {
			role = this.roleService.getRole(role);
			model.addAttribute("role", role);
		}
		model.addAttribute("isFix", Constants.IsFix.values());
		return "sys/role/roleEdit";
	}

	@ResponseBody
	@RequestMapping(params = "method=save")
	public Role save(Role role) throws BaseException {
		return this.roleService.saveOrUpdateRole(role);
	}


	@ResponseBody
	@RequestMapping(params = "method=delete")
	public void delete(Role role) throws BaseException {
		if(Constants.IsFix.YES.getCode().equals(role.getIsFix())) {
			throw new BaseException("系统预置的角色不允许删除！");
		}
		this.roleService.deleteRole(role);
	}
	
	/**
	 * 角色操作授权
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(params = "method=authRoleOpeRight")
	public String authRoleOpeRight(String roleId, Model model) {
		model.addAttribute("roleId", roleId);
		return "sys/role/authRoleOpeRight";
	}
	
	@ResponseBody
	@RequestMapping(params = "method=saveRoleRight")
	public void saveRoleRight(String roleId, String rightIds)
			throws BaseException {
		this.roleService.saveRoleRight(roleId, rightIds);
	}

	/**
	 * 角色数据授权
	 * 
	 * @param roleId
	 * @return
	 * @throws BaseException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params = "method=authRoleDataRight")
	public String authRoleDataRight(String roleId, String roleName, Model model) throws BaseException, UnsupportedEncodingException {
		//查询该角色已有的数据权限
		DataRight dr = this.roleService.getDataRightByRole(roleId);
		model.addAttribute("dataRight", dr);
		model.addAttribute("roleId", roleId);
		model.addAttribute("roleName", URLDecoder.decode(roleName, "UTF-8"));
		return "sys/role/authRoleDataRight";
	}

	@ResponseBody
	@RequestMapping(params = "method=saveRoleDataRight")
	public void saveRoleDataRight(String roleDataRights)
			throws BaseException {
		if (StringUtils.isBlank(roleDataRights)) {
			throw new BaseException("数据权限设置信息为空");
		}
		JSONObject json = JSON.parseObject(roleDataRights);
		DataRight dr = new DataRight();
		dr.setRoleId(json.getString("roleId"));
		dr.setActionType(json.getString("actionType"));
		dr.setRightType(json.getString("rightType"));
		dr.setOrgNoList(json.getString("orgNoList"));
		
		if(StringUtils.isBlank(dr.getRightType())) {
			throw new BaseException("数据权限设置失败:[角色ID为空]");
		}
		this.roleService.saveRoleDataRight(dr);
	}
}
