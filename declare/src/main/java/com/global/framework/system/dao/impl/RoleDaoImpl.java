package com.global.framework.system.dao.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.RoleDao;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.Role;
import com.global.framework.system.domain.RoleRight;
import com.global.framework.system.domain.UserRole;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoSupport implements RoleDao {

	public PageBean queryRoleForPage(Role role, PageBean pageBean, String userId) throws BaseException {
		StringBuilder sql = new StringBuilder(256);
		sql.append("SELECT t.* FROM dc_sys_role t WHERE 1 = 1 ");
		if (!"admin".equals(userId)) {//不是超级管理员不允许操作超级管理员这个角色信息
			sql.append(" and t.isfix = 'N'");
		}
		
		List<Object> args = new ArrayList<Object>();
		if (null != role) {
			if (StringUtils.isNotBlank(role.getRoleName())) {
				sql.append(" and t.roleName like ? ");
				args.add("%"+role.getRoleName()+"%");
			}
		}
		
		return super.findForPage(sql.toString(), args.toArray(), pageBean, Role.class);
	}

	public void deleteRole(Role role) throws BaseException {
		//查询该角色是否被分配
		String sql = "select count(1) from dc_sys_userrole ur where ur.roleid=? ";
		int count = super.findForIntBySql(sql, new Object[]{role.getRoleId()});
		if (count > 0) {
			throw new BaseException("该角色已被分配，不允许删除，请先取消用户角色分配！");
		}
		super.delete(role);
		
		//删除角色权限关系表
		String sql2 = "delete from dc_sys_roleright r where r.roleid=?";
		super.delete(sql2, new Object[]{role.getRoleId()});
		
		String sql3 = "delete from dc_sys_roledataright r where r.roleid=?";
		super.delete(sql3, new Object[]{role.getRoleId()});
	}

	@SuppressWarnings("unchecked")
	public List<RoleRight> getRightsByRole(List<UserRole> roles) throws BaseException {
		StringBuffer sql = new StringBuffer("select * from dc_sys_roleright t where t.roleid in (");
		Object[] obj = new Object[roles.size()];
		boolean done = false;
		for (int i = 0; i < roles.size(); i++) {
			if(done){
				sql.append(",");
			}
			sql.append("?");
			obj[i] = roles.get(i).getRoleId();
			done = true;
		}
		sql.append(")");
		return (List<RoleRight>) super.findForListBySql(sql.toString(), obj, RoleRight.class);
	}

	@SuppressWarnings("unchecked")
	public List<Role> getRoleList() throws BaseException {
		return (List<Role>) super.findForList(Role.class);
	}

	@SuppressWarnings("unchecked")
	public List<RoleRight> getRoleRightList() throws BaseException {
		return (List<RoleRight>) super.findForList(RoleRight.class);
	}

	public Role saveRole(Role role) throws BaseException {
		return super.insert(role);
	}

	public Role updateRole(Role role) throws BaseException {
		super.update(role);
		return role;
	}

	public Role getRole(Role role) throws BaseException {
		return (Role) super.findForObject(role);
	}
	
	public void saveRoleRight(LinkedList<RoleRight> roleRightList)
			throws BaseException {
		super.batchInsert(roleRightList);
	}

	public void deleteRoleRightByRoleId(String roleId) throws BaseException {
		String sql = "delete from dc_sys_roleright t where t.roleid = ? ";
		super.delete(sql, new Object[] { roleId });
	}
	
	public void saveOrUpdateRoleDataRight(DataRight dr) throws BaseException {
		super.saveOrUpdate(dr);
	}
	
	public DataRight getDataRightByRole(String roleId) throws BaseException {
		return super.findForObject(DataRight.class, roleId);
	}
}
