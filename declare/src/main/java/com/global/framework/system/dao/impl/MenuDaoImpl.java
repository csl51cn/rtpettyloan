package com.global.framework.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.MenuDao;
import com.global.framework.system.domain.Menu;

/**
 * @author cqchenf@qq.com
 * @date 2011-9-10 下午2:27:27
 * @version v1.0
 */
@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoSupport implements MenuDao {

	@SuppressWarnings("unchecked")
	public List<Menu> getMenuGroupList() throws BaseException {
		String sql = "select menuId,menuName,sortNo from dc_sys_menu where parentMenuId='-1' order by sortno asc ";
		return (List<Menu>) super.findForListBySql(sql, null, Menu.class);
	}

	@SuppressWarnings("unchecked")
	public List<Menu> getMenuList() throws BaseException {
		String sql = "SELECT t.menuid,t.menuname,t.parentmenuid,t.isfunction,t.accessurl,t.sortno from dc_sys_menu t where t.parentMenuId <> '-1' order by sortno asc";
		return (List<Menu>) super.findForListBySql(sql, null, Menu.class);
	}

	@SuppressWarnings("unchecked")
	public List<Menu> getMenuAll() throws BaseException {
		String sql = "select t.*,COALESCE((select menuname from dc_sys_menu where menuid=t.parentMenuId),'-') parentMenuName from dc_sys_menu t order by sortNo asc";
		return (List<Menu>) super.findForListBySql(sql, null, Menu.class);
	}

	public Menu insertMenu(Menu menuDto) throws BaseException {
		return super.insert(menuDto);
	}

	public void updateMenuBySql(Menu menu) throws BaseException {
		String sql = "update dc_sys_menu set isFunction='N', accessUrl='' where menuId=?";
		super.updateBySql(sql, new Object[]{menu.getParentMenuId()});
	}

	public void deleteMenu(Menu menu) throws BaseException {
		//查询该菜单是否有下级菜单
		String sql1 = "select count(0) from dc_sys_menu where parentMenuId = ? ";
		int count = super.findForIntBySql(sql1, new Object[]{menu.getMenuId()});
		if (count > 0) {
			throw new BaseException("该菜单存在下级菜单，不允许删除!");
		}
		//查询该菜单是否被分配
		String sql2 = "select count(0) from dc_sys_roleright r where r.rightid=?";
		int count2 = super.findForIntBySql(sql2, new Object[]{menu.getMenuId()});
		if(count2 > 0) {
			throw new BaseException("该菜单已使用，不允许删除");
		}
		super.delete(menu);
	}
	
	public Menu getMenuById(Menu menu) throws BaseException {
		String sql = "select m.*, (select menuname from dc_sys_menu where menuid=m.parentmenuid) parentMenuName from dc_sys_menu m where m.menuid = ? ";
		return super.findForObjectBySql(sql, new Object[]{menu.getMenuId()}, Menu.class);
	}
	
	public void updateMenu(Menu menu) throws BaseException {
		super.update(menu);
	}

	public List<Menu> getMenuByRoleID(String roleIds) throws BaseException {
		String sql = "select * from dc_sys_menu m where m.menuid in (select r.rightid from dc_sys_roleright r where r.roleid in ("+roleIds+") and r.righttype='1') and not exists (select 1 from dc_wfl_tradeprivilege p where p.menuid=m.menuid) " +
						"union "+
						"select * from dc_sys_menu m where m.menuid in (select p.menuid from dc_wfl_tradeprivilege p where p.privid in (select r.rightid from dc_sys_roleright r where r.roleid in ("+roleIds+") and r.righttype='3') and p.opeid='1')";
		return (List<Menu>) super.findForListBySql(sql, null, Menu.class);
	}
}
