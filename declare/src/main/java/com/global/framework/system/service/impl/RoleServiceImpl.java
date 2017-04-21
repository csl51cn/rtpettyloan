package com.global.framework.system.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.RoleDao;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.Role;
import com.global.framework.system.domain.RoleRight;
import com.global.framework.system.domain.UserRole;
import com.global.framework.system.service.RoleService;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.system.web.common.CacheService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private SysCommonService sysCommonService;
	
	
	public PageBean queryRoleForPage(Role roleDto, PageBean pageBean, String userId)
			throws BaseException {
		return this.roleDao.queryRoleForPage(roleDto, pageBean, userId);
	}

	
	public void deleteRole(Role role) throws BaseException {
		this.roleDao.deleteRole(role);
		CacheService.getInstance().loadRoleAll();
	}

	
	public List<RoleRight> getRightsByRole(List<UserRole> roles)
			throws BaseException {
		return this.roleDao.getRightsByRole(roles);
	}

	
	public List<Role> getRoleList() throws BaseException {
		return this.roleDao.getRoleList();
	}

	
	public Role saveOrUpdateRole(Role role) throws BaseException {
		Role r = null;
		if (role != null && StringUtils.isNotBlank(role.getRoleId())) {
			r = this.updateRole(role);
		}else {
			role.setRoleId(sysCommonService.getSeqNo("SYS_ROLE"));
			role.setIsFix("N");
			r = this.roleDao.saveRole(role);
		}
		CacheService.getInstance().loadRoleAll();
		return r;
	}

	
	public Role updateRole(Role role) throws BaseException {
		role = this.roleDao.updateRole(role);
		CacheService.getInstance().loadRoleAll();
		return role;
	}

	
	public Role getRole(Role role) throws BaseException {
		return this.roleDao.getRole(role);
	}

	@SuppressWarnings("rawtypes")
	public void saveRoleRight(String roleId, String rightIds)
			throws BaseException {
		// 1.先删除该角色的所有权限
		this.roleDao.deleteRoleRightByRoleId(roleId);

		// 2.再保存该角色分配的权限
		LinkedList<RoleRight> roleRightList = new LinkedList<RoleRight>();
		List<Map> jsonArray = JSON.parseArray(rightIds, Map.class);
		for (Map map : jsonArray) {
			RoleRight roleRight = new RoleRight();
			try {
				BeanUtils.copyProperties(roleRight, map);
				roleRight.setRoleId(roleId);
			} catch (Exception e) {
				throw new BaseException("解析JSON数组出错", e);
			}
			roleRightList.add(roleRight);
		}
		this.roleDao.saveRoleRight(roleRightList);
	}
	
	
	public void saveRoleDataRight(DataRight dr) throws BaseException {
		this.roleDao.saveOrUpdateRoleDataRight(dr);
	}
	
	
	public DataRight getDataRightByRole(String roleId) throws BaseException {
		return this.roleDao.getDataRightByRole(roleId);
	}
}
