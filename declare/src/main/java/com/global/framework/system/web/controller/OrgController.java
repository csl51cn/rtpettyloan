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
import com.global.framework.system.domain.Org;
import com.global.framework.system.domain.TreeNode;
import com.global.framework.system.domain.User;
import com.global.framework.system.service.OrgService;
import com.global.web.BaseController;

@Controller
@RequestMapping("/sys/orgController.do")
public class OrgController extends BaseController {

	@Autowired
	private OrgService orgService;

	@ResponseBody
	@RequestMapping(params = "method=loadOrgTree")
	public List<TreeNode> loadOrgTree() throws BaseException {
		return this.orgService.loadOrgTree();
	}
	
	@ResponseBody
	@RequestMapping(params = "method=loadRightOrgList")
	public Set<Org> loadRightOrgList(HttpServletRequest request) throws BaseException {
		User user = super.getSessionUser(request);
		return this.orgService.getRightOrgList(user);
	}

	/**
	 * 转到机构列表页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=list")
	public String list(Model model) throws BaseException {
		return "sys/org/orgList";
	}

	/**
	 * 查询机构列表
	 * 
	 * @param org
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "method=query")
	public Map<String, Object> query(Org org, PageBean page, Model model)
			throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>(2);
		PageBean pageBean = this.orgService.queryOrgForPage(org, page);
		map.put(LIST_TOTAL, pageBean.getTotalRows());
		map.put(LIST_ROWS, pageBean.getDataList());
		return map;
	}
	
	/**
	 * 保存机构信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "method=save")
	public Org save(Org org) throws BaseException {
		return this.orgService.saveOrUpdate(org);
	}

	/**
	 * 转到编辑机构页面
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(params = "method=edit")
	public String edit(Org org, Model model) throws BaseException {
		if (org != null && StringUtils.isNotBlank(org.getOrgId())) {
			org = this.orgService.getOrgInfo(org);
			model.addAttribute("org", org);
		}
		return "sys/org/orgEdit";
	}

	/**
	 * 删除机构信息
	 * 
	 * @return
	 * @throws BaseException 
	 */
	@ResponseBody
	@RequestMapping(params = "method=delete")
	public void delete(Org org) throws BaseException {
		this.orgService.delete(org);
	}
}
