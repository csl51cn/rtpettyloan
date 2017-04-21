package com.global.framework.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.MenuDao;
import com.global.framework.system.dao.RoleDao;
import com.global.framework.system.domain.Menu;
import com.global.framework.system.domain.RoleRight;
import com.global.framework.system.domain.TreeNode;
import com.global.framework.system.domain.UserRole;
import com.global.framework.system.service.MenuService;
import com.global.framework.system.web.common.CacheService;

/**
 * @author cqchenf@qq.com
 * @date 2011-9-10 下午2:30:31
 * @version v1.0
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RoleDao roleDao;
	
	public List<Menu> getMenuGroupList() throws BaseException {
		return this.menuDao.getMenuGroupList();
	}

	
	public List<Menu> getMenuList() throws BaseException {
		return this.menuDao.getMenuList();
	}

	
	public List<Menu> getMenuAll() throws BaseException {
		return this.menuDao.getMenuAll();
	}

	
	public Menu getMenuById(Menu menu) throws BaseException {
		return this.menuDao.getMenuById(menu);
	}
	
	
	public List<Map<String, Object>> getMenuTreeGrid() throws BaseException {
		List<Map<String, Object>> root = new ArrayList<Map<String, Object>>();
		List<Menu> menuList = this.menuDao.getMenuAll();
		for (Menu menu : menuList) {
			if ("-1".equals(menu.getParentMenuId())) {
				Map<String, Object> mapNode = new LinkedHashMap<String, Object>();
				Set<Map<String, Object>> subNodes = getMenuSubNodes(
						menu.getMenuId(), menuList);
				// 生成根节点
				mapNode.put("menuId", menu.getMenuId());
				mapNode.put("menuName", menu.getMenuName());
				mapNode.put("parentMenuName", menu.getParentMenuName());
				mapNode.put("isFunction", menu.getIsFunction());
				mapNode.put("accessUrl", menu.getAccessUrl());
				mapNode.put("sortNo", menu.getSortNo());
				mapNode.put("children", subNodes);
				root.add(mapNode);
			}
		}
		return root;
	}

	/**
	 * 循环遍历出菜单树状结构
	 * 
	 * @param id
	 * @param menuList
	 * @return
	 */
	private Set<Map<String, Object>> getMenuSubNodes(String menuId,
			List<Menu> menuList) throws BaseException {
		Set<Map<String, Object>> subNodes = new LinkedHashSet<Map<String, Object>>();
		for (Menu menu : menuList) {
			if (menuId.equals(menu.getParentMenuId())) {
				Map<String, Object> mapNode = new LinkedHashMap<String, Object>();
				mapNode.put("menuId", menu.getMenuId());
				mapNode.put("menuName", menu.getMenuName());
				mapNode.put("parentMenuName", menu.getParentMenuName());
				mapNode.put("isFunction", menu.getIsFunction());
				mapNode.put("accessUrl", menu.getAccessUrl());
				mapNode.put("sortNo", menu.getSortNo());
				Set<Map<String, Object>> list = getMenuSubNodes(
						menu.getMenuId(), menuList);
				if (list != null && list.size() > 0) {
					mapNode.put("children", list);
				}
				// mapNode.put("isexpand", "false");
				subNodes.add(mapNode);
			}
		}
		return subNodes;
	}

	public List<TreeNode> loadMenuTree() throws BaseException {
		List<Menu> menuGroups = CacheService.getMenuGroupCacheList();
		List<Menu> menuList = CacheService.getMenuCacheList();

		// List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		// TreeNode node = new TreeNode();
		// node.setId("-1");
		// node.setText("全部模块");
		// node.setIconCls("icons-tree");
		// Map<String, Object> attributes = new HashMap<String, Object>();
		// attributes.put("src", "");
		// attributes.put("isFunction", "N");
		// node.setAttributes(attributes);

		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for (Menu menuGroup : menuGroups) {
			treeNodeList.add(getMenuTreeGroup(menuGroup, menuList));
		}
		// node.setChildren(treeNodeList);
		// treeNodes.add(node);
		return treeNodeList;
	}

	private TreeNode getMenuTreeGroup(Menu menuGroup, List<Menu> menus)
			throws BaseException {
		TreeNode node = new TreeNode();
		node.setId(menuGroup.getMenuId());
		node.setText(menuGroup.getMenuName());
		node.setIconCls("");
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("src", "");
		attributes.put("isFunction", "N");
		node.setAttributes(attributes);

		if (isHadSubMenu(menuGroup.getMenuId(), menus)) {
			node.setState("closed");
		}

		List<TreeNode> menuTreeNode = new ArrayList<TreeNode>();
		for (Menu menu : menus) {
			if (menu.getParentMenuId().equals(menuGroup.getMenuId())) {
				TreeNode t = getMenuTree(menu, menus);
				menuTreeNode.add(t);
			}
		}
		node.setChildren(menuTreeNode);
		return node;
	}

	private TreeNode getMenuTree(Menu menu, List<Menu> menus)
			throws BaseException {
		TreeNode node = new TreeNode();
		node.setId(menu.getMenuId());
		node.setText(menu.getMenuName());
		node.setIconCls("");
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("src", menu.getAccessUrl());
		attributes.put("isFunction", menu.getIsFunction());
		node.setAttributes(attributes);
		if ("N".equals(menu.getIsFunction())) {
			node.setState("closed");
			List<TreeNode> children = new ArrayList<TreeNode>();
			for (Menu m : menus) {
				if (m.getParentMenuId().equals(menu.getMenuId())) {
					TreeNode t = getMenuTree(m, menus);
					children.add(t);
				}
			}
			node.setChildren(children);
		}
		return node;
	}

	/**
	 * 判断已知的菜单项目是否有子菜单项
	 * 
	 * @param menuId
	 * @param menus
	 * @return
	 */
	private boolean isHadSubMenu(String menuId, List<Menu> menus)
			throws BaseException {
		for (Menu menu : menus) {
			if (menu.getParentMenuId().equals(menuId)) {
				return true;
			}
		}
		return false;
	}

	
	public String generateNavMenuHtml(Set<Menu> menuGroups, Set<Menu> menus)
			throws BaseException {
		StringBuffer sb = new StringBuffer();
		for (Menu menuGroup : menuGroups) {
			sb.append("<div id=\"").append(menuGroup.getMenuId())
					.append("\" style=\"width:150px;\">");
			sb.append(getMenus(menuGroup.getMenuId(), menus));
			sb.append("</div>");
		}
		return sb.toString();
	}

	private String getMenus(String menuGroupId, Set<Menu> menus)
			throws BaseException {
		StringBuffer sb = new StringBuffer();
		for (Menu menu : menus) {
			if (menuGroupId.equals(menu.getParentMenuId())) {
				sb.append("<div");
				if ("Y".equals(menu.getIsFunction())) {
					sb.append(" onclick=\"f_addTab('")
							.append(menu.getMenuName())
							.append("','")
							.append(menu.getAccessUrl() + "&menuId="
									+ menu.getMenuId()).append("')\" ");
				}
				sb.append(">").append(menu.getMenuName());

				if (isHadSubMenu(menu.getMenuId(), menus)) {
					sb.append("<div style=\"width:150px;\">");
				}
				sb.append(getMenus(menu.getMenuId(), menus));
				if (isHadSubMenu(menu.getMenuId(), menus)) {
					sb.append("</div>");
				}
				sb.append("</div>");
			}
		}
		return sb.toString();
	}

	/**
	 * 判断已知的菜单项目是否有子菜单项
	 * 
	 * @param menuId
	 * @param menus
	 * @return
	 */
	private boolean isHadSubMenu(String menuId, Set<Menu> menus)
			throws BaseException {
		for (Menu menu : menus) {
			if (menu.getParentMenuId().equals(menuId)) {
				return true;
			}
		}
		return false;
	}

	
	public Menu saveOrUpdate(Menu menu) throws BaseException {
		if (menu != null && StringUtils.isNotBlank(menu.getMenuId())) {
			this.menuDao.updateMenu(menu);
		} else {
			menu = this.menuDao.insertMenu(menu);
			Menu m = CacheService.getMenuById(menu.getParentMenuId());
			if (m != null && "Y".equals(m.getIsFunction())) {
				// 如果上级菜单为功能菜单，则更新为目录菜单并清空请求URL
				this.menuDao.updateMenuBySql(menu);
			}
		}
		CacheService.getInstance().loadMenuAll();
		return menu;
	}

	
	public void deleteMenu(Menu menu) throws BaseException {
		this.menuDao.deleteMenu(menu);
		CacheService.getInstance().loadMenuAll();
	}

	
	public Map<String, Set<Menu>> getMenuByRole(List<UserRole> userRoleList)
			throws BaseException {
		Map<String, Set<Menu>> map = new ConcurrentHashMap<String, Set<Menu>>();
		List<RoleRight> roleRightList = this.roleDao.getRoleRightList();
		List<Menu> menuGroupCacheList = CacheService.getMenuGroupCacheList();
		List<Menu> menuCacheList = CacheService.getMenuCacheList();

		map.put("menuGroup",
				getMenuGroupCacheList(userRoleList, roleRightList,
						menuGroupCacheList));
		
		map.put("menu",
				getMenuCacheList(userRoleList, roleRightList, menuCacheList));
		return map;
	}

	/**
	 * 根据角色获取有访问权限的菜单组信息
	 * 
	 * @param userRoleList
	 * @return
	 */
	private Set<Menu> getMenuGroupCacheList(List<UserRole> userRoleList,
			List<RoleRight> roleRightList, List<Menu> menuGroupCacheList)
			throws BaseException {
		Set<Menu> menuGroupList = new LinkedHashSet<Menu>();

		for (RoleRight roleRight : roleRightList) {
			for (UserRole userRole : userRoleList) {
				if (roleRight.getRoleId().equals(userRole.getRoleId())) {
					for (Menu menuGroup : menuGroupCacheList) {
						if (menuGroup.getMenuId()
								.equals(roleRight.getRightId())) {
							menuGroupList.add(menuGroup);
						}
					}
				}
			}
		}
		return sortMenu(menuGroupList);
	}

	/**
	 * 根据角色获取有访问权限的菜单信息
	 * 
	 * @param userRoleList
	 * @return
	 */
	private Set<Menu> getMenuCacheList(List<UserRole> userRoleList,
			List<RoleRight> roleRightList, List<Menu> menuCacheList)
			throws BaseException {
		//获取用户的交易权限
		Set<Menu> menus = new LinkedHashSet<Menu>();
		StringBuffer sb = new StringBuffer();
		for (UserRole ur : userRoleList) {
			sb.append("'").append(ur.getRoleId()).append("'").append(",");
		}
		if (StringUtils.isNotBlank(sb.toString())) {
			sb.deleteCharAt(sb.lastIndexOf(","));
			List<Menu> mList = this.menuDao.getMenuByRoleID(sb.toString());
			menus.addAll(mList);
		}
//		for (RoleRight roleRight : roleRightList) {
//			for (UserRole userRole : userRoleList) {
//				if (roleRight.getRoleId().equals(userRole.getRoleId())) {
//					for (Menu menu : menuCacheList) {
//						if (menu.getMenuId().equals(roleRight.getRightId())) {
//							List<TradePrivilege> trList = tradePrivilegeService.queryTradePrivilegeByMenuID(menu.getMenuId());
//							if (trList != null && trList.size() > 0){//该菜单有交易权限信息
//								for (TradePrivilege tr : trList) {
//									if ("1".equals(tr.getOpeId())){
//										//判断该角色是否有经办权限
//										for (RoleRight r : roleRightList) {
//											if (r.getRightId().equals(tr.getPrivId())) {
//												menus.add(menu);
//												break;
//											}
//										}
//									}
//								}
//							} else {
//								menus.add(menu);
//							}
//						}
//					}
//				}
//			}
//		}
		return sortMenu(menus);
	}

	private Set<Menu> sortMenu(Set<Menu> menus) throws BaseException {
		Set<Integer> sort = new TreeSet<Integer>();
		for (Menu menu : menus) {
			sort.add(Integer.parseInt(menu.getSortNo()));
		}
		Set<Menu> m = new LinkedHashSet<Menu>();
		for (Integer sortNo : sort) {
			for (Menu menu : menus) {
				if (sortNo == Integer.parseInt(menu.getSortNo())) {
					m.add(menu);
				}
			}
		}
		return m;
	}
}
