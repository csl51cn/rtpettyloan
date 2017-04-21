package com.global.framework.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.Menu;
import com.global.framework.system.domain.TreeNode;
import com.global.framework.system.domain.UserRole;

/**
 * @author cqchenf@qq.com
 * @date 2011-9-10 下午2:29:59
 * @version v1.0
 */
public interface MenuService {

	/**
	 * 获取菜单组列表
	 * 
	 * @return
	 */
	public List<Menu> getMenuGroupList() throws BaseException;

	/**
	 * 获取菜单列表
	 * 
	 * @return
	 */
	public List<Menu> getMenuList() throws BaseException;

	/**
	 * 加载菜单树
	 * 
	 * @return
	 */
	public List<TreeNode> loadMenuTree() throws BaseException;

	/**
	 * 生成首页导航菜单html
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateNavMenuHtml(Set<Menu> menuGroups, Set<Menu> menus)
			throws BaseException;

	public Menu saveOrUpdate(Menu menu) throws BaseException;

	/**
	 * 获取菜单树列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMenuTreeGrid() throws BaseException;

	/**
	 * 获取所有菜单
	 * 
	 * @return
	 */
	public List<Menu> getMenuAll() throws BaseException;

	/**
	 * 删除菜单
	 * 
	 * @param menu
	 */
	public void deleteMenu(Menu menu) throws BaseException;

	public Map<String, Set<Menu>> getMenuByRole(List<UserRole> userRoleList)
			throws BaseException;

	public Menu getMenuById(Menu menu) throws BaseException;
}
