package com.global.framework.system.dao;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.Menu;

/**
 * @author cqchenf@qq.com
 * @date 2011-9-10 下午2:25:03
 * @version v1.0
 */
public interface MenuDao {

	/**
	 * 获取菜单组列表
	 * @return
	 */
	public List<Menu> getMenuGroupList() throws BaseException;

	/**
	 * 获取菜单列表
	 * @return
	 */
	public List<Menu> getMenuList() throws BaseException;

	public Menu insertMenu(Menu menuDto) throws BaseException;

	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<Menu> getMenuAll() throws BaseException;

	/**
	 * 自定义SQL更新菜单
	 * @param menu
	 */
	public void updateMenuBySql(Menu menu) throws BaseException;

	/**
	 * 删除菜单
	 * @param menu
	 */
	public void deleteMenu(Menu menu) throws BaseException;

	public Menu getMenuById(Menu menu) throws BaseException;

	public void updateMenu(Menu menu) throws BaseException;
	
	/**
	 * 根据角色获取菜单
	 * @param roleIds
	 * @return
	 * @throws BaseException
	 */
	public List<Menu> getMenuByRoleID(String roleIds) throws BaseException;

}
