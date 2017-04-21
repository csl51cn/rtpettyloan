package com.global.framework.system.service;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.Role;
import com.global.framework.system.domain.RoleRight;
import com.global.framework.system.domain.UserRole;

public interface RoleService {

	/**
	 * 查询角色信息
	 * 
	 * @param role
	 * @param pageBean
	 * @return
	 */
	public PageBean queryRoleForPage(Role role, PageBean pageBean, String userId)
			throws BaseException;

	/**
	 * 删除角色信息
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void deleteRole(Role role) throws BaseException;

	/**
	 * 获取角色对应的菜单权限和按钮权限
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RoleRight> getRightsByRole(List<UserRole> roles)
			throws BaseException;

	/**
	 * 获取角色列表
	 * 
	 * @return
	 */
	public List<Role> getRoleList() throws BaseException;

	public Role saveOrUpdateRole(Role role) throws BaseException;

	public Role updateRole(Role role) throws BaseException;

	public Role getRole(Role role) throws BaseException;

	public void saveRoleRight(String roleId, String rightIds)
			throws BaseException;

	public void saveRoleDataRight(DataRight dr) throws BaseException;

	public DataRight getDataRightByRole(String roleId) throws BaseException;
}
