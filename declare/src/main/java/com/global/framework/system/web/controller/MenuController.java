package com.global.framework.system.web.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.Menu;
import com.global.framework.system.domain.TreeNode;
import com.global.framework.system.service.MenuService;
import com.global.web.BaseController;

@Controller
@RequestMapping("/sys/menuController.do")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;

	@ResponseBody
	@RequestMapping(params = "method=loadMenuTree")
	public List<TreeNode> loadMenuTree() throws BaseException {
		return this.menuService.loadMenuTree();
	}

	/**
	 * 转到菜单列表页面
	 * @return
	 */
	@RequestMapping(params = "method=list")
	public String list(Model model) {
		return "sys/menu/menuList";
	}

	/**
	 * 菜单列表
	 * @param user
	 * @param page
	 * @return
	 * @throws BaseException 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "method=query")
	public List<Map<String, Object>> query(Menu menu,PageBean page) throws BaseException {
		return this.menuService.getMenuTreeGrid();
	}

	/**
	 * 新增菜单
	 * @return
	 * @throws BaseException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(params = "method=edit")
	public String edit(Model model, Menu menu) throws BaseException {
		if(StringUtils.isNotBlank(menu.getMenuId())) {
			//修改操作
			menu = this.menuService.getMenuById(menu);
		}else {
			if(StringUtils.isNotBlank(menu.getParentMenuName())){
				menu.setParentMenuName(URLDecoder.decode(menu.getParentMenuName()));
			}
		}
		model.addAttribute("menu", menu);
		return "sys/menu/menuEdit";
	}

	@ResponseBody
	@RequestMapping(params = "method=save")
	public Menu save(Menu menu) throws BaseException {
		return this.menuService.saveOrUpdate(menu);
	}

	@ResponseBody
	@RequestMapping(params = "method=delete")
	public void delete(Menu menu) throws BaseException {
		this.menuService.deleteMenu(menu);
	}
}
