package com.global.framework.system.dao.impl;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.UserDao;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.User;
import com.global.framework.system.domain.UserRole;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cqchenf@qq.com
 * @date 2011-8-21 上午11:57:37
 * @version v1.0
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoSupport implements UserDao {

	public User insertUser(User user) throws BaseException {
		return super.insert(user);
	}

	public void updateUser(User user) throws BaseException {
		super.update(user);
	}

	public void deleteUser(User user) throws BaseException {
		try {
			super.delete(user);
		} catch (Exception e) {
			if (e.getMessage().contains("ORA-02292")) {
				throw new BaseException("该用户被引用，不允许删除，请先删除子记录！");
			}
		}
	}

	public void updateUserPwd(User user) throws BaseException {
		String sql = "update dc_sys_user set password = ? where usercode = ? ";
		super.updateBySql(sql,
				new Object[] { user.getPassword(), user.getUserCode() });
	}

	public User getUserInfo(User user) throws BaseException {
		user = (User) super.findForObject(user);
		return user;
	}

	public boolean checkUserExist(String userCode) throws BaseException {
		String sql = "select count(1) from dc_sys_user where userCode = ? ";
		int count = super.findForIntBySql(sql, new Object[] { userCode });
		return count > 0 ? true : false;
	}

	public PageBean queryUserForPage(User user, PageBean page)
			throws BaseException {
		StringBuilder sql = new StringBuilder(256);
		sql.append("SELECT TOP (100) PERCENT t.*,"
				+ "(select orgName from dc_sys_org where orgId=t.orgId) orgName "
				+ "from dc_sys_user t WHERE 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(user.getOrgId())) {
			sql.append(" and t.orgId = ? ");
			args.add(user.getOrgId());
		}
		if (StringUtils.isNotBlank(user.getUserCode())) {
			sql.append(" and t.userCode = ? ");
			args.add(user.getUserCode());
		}
		if (StringUtils.isNotBlank(user.getUserName())) {
			sql.append(" and t.userName like ? ");
			args.add("%" + user.getUserName() + "%");
		}
		if (StringUtils.isNotBlank(user.getStatus())) {
			sql.append(" and t.status = ? ");
			args.add(user.getStatus());
		}
		return super.findForPage(sql.toString(), args.toArray(), page,
				User.class);
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> getRolesByUserId(String userId) throws BaseException {
		String sql = "select t.*,(select roleName from dc_sys_role where roleid=t.roleid) roleName from dc_sys_userrole t where t.userid = ? ";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		return (List<UserRole>) super.findForListBySql(sql, args.toArray(),
				UserRole.class);
	}

	public void deleteUserRoleByUserId(String userId) throws BaseException {
		String sql = "delete from dc_sys_userrole  where userid = ? ";
		super.delete(sql, new Object[] { userId });
	}

	public void saveUserRole(LinkedList<UserRole> userRoleList)
			throws BaseException {
		super.batchInsert(userRoleList);
	}

	public void saveDataRight(DataRight dr) throws BaseException {
		super.insert(dr);
	}

	public void deleteDataRightByUserId(String userId) throws BaseException {
		String sql = "delete from dc_sys_dataright t where t.userid = ?　";
		super.delete(sql, new Object[] { userId });
	}

	public void batchUpdateUser(List<Object[]> list) {
		String sql = "update dc_sys_user set status=? where userid = ? ";
		super.batchUpdateBySql(sql, list);
	}

	public void batchUpdateUserPwd(List<Object[]> list) {
		String sql = "update dc_sys_user set password=? where userid = ? ";
		super.batchUpdateBySql(sql, list);
	}

	public User findUserByUserCode(String userCode) throws BaseException {
		String sql = "select * from dc_sys_user where usercode=?";
		return super.findForObjectBySql(sql, new Object[]{userCode}, User.class);
	}
}
