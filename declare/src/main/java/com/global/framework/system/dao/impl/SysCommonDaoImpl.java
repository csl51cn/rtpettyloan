package com.global.framework.system.dao.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.SysCommonDao;
import com.global.framework.system.domain.CommonOrgUser;
import com.global.framework.system.domain.Property;

/**
 * 
 * @author chen.feng
 * @date 2012-6-15 下午6:33:51
 */
@Repository("sysCommonDao")
public class SysCommonDaoImpl extends BaseDaoSupport implements SysCommonDao {

	public String getSeqNo(final String objName) throws BaseException {
		String sql = "{call p_generate_seqno(?,?)}";
		Object obj = this.getJdbcTemplate().execute(sql,
				new CallableStatementCallback<Object>() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException, DataAccessException {
						cs.setString(1, objName);
						cs.registerOutParameter(2, Types.VARCHAR);
						cs.execute();
						return new String(cs.getString(2));
					}
				});
		return (String) obj;
	}

	public String getBizNo(final String objName) throws BaseException {
		String sql = "{call p_generate_bizno(?,?)}";
		Object obj = this.getJdbcTemplate().execute(sql,
				new CallableStatementCallback<Object>() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException, DataAccessException {
						cs.setString(1, objName);
						cs.registerOutParameter(2, Types.VARCHAR);
						cs.execute();
						return new String(cs.getString(2));
					}
				});
		return (String) obj;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Property getProperty(String key) throws BaseException {
		String sql = "select * from SYS_PROPERTY where key=?";
		return (Property)super.getJdbcTemplate().queryForObject(sql, new Object[]{key}, new BeanPropertyRowMapper(Property.class));
	}
	
	public void updateProperty(Property p) throws BaseException {
		super.update(p);
	}
	
	@SuppressWarnings("unchecked")
	public List<Property> getPropertyList(String[] args) throws BaseException {
		StringBuffer sql = new StringBuffer("select * from sys_property s where 1=1 ");
		if (args != null) {
			sql.append(" and s.key in (");
			for (int i = 0; i < args.length; i++) {
				sql.append("'").append(args[i]).append("'").append(",");
			}
			sql.append(")");
			sql.deleteCharAt(sql.lastIndexOf(","));
		}
		return (List<Property>) super.findForListBySql(sql.toString(), null, Property.class);
	}
	
	public CommonOrgUser getCommonOrgUser(String operNo)
			throws BaseException {
		if (StringUtils.isBlank(operNo)) {
			throw new BaseException("柜员员不允许为空");
		}
		String sql = "select * from PA_COMMON_ORG_USER t where t.operno = ? ";
		return super.findForObjectBySql(sql, new Object[]{operNo}, CommonOrgUser.class);
	}
	
	public CommonOrgUser insertCommonOrgUser(CommonOrgUser user)
			throws BaseException {
		return super.insert(user);
	}
	
	public void updateCommonOrgUser(CommonOrgUser user)
			throws BaseException {
		super.update(user);
	}
	
	public PageBean queryCommonOrgUserForPage(CommonOrgUser user, PageBean page)
			throws BaseException {
		StringBuilder sql = new StringBuilder(256);
		sql.append("SELECT c.* FROM PA_COMMON_ORG_USER c WHERE 1 = 1");
		List<Object> args = new ArrayList<Object>();
		
		if(StringUtils.isNotBlank(user.getOPERNO())) {
			sql.append(" AND c.OPERNO = ? ");
			args.add(user.getOPERNO());
		}
		if(StringUtils.isNotBlank(user.getCOMMON_USER_CODE())) {
			sql.append(" AND c.COMMON_USER_CODE = ?");
			args.add(user.getCOMMON_USER_CODE());
		}
		
		return super.findForPage(sql.toString(), args.toArray(), page, CommonOrgUser.class);
	}
	
	public void delCommonOrgUser(String operNo)
			throws BaseException {
		String sql = "delete from PA_COMMON_ORG_USER where OPERNO=? ";
		super.delete(sql, new Object[]{operNo});
	}
	
	@SuppressWarnings("unchecked")
	public List<CommonOrgUser> getCommonOrgUserList() throws BaseException {
		return (List<CommonOrgUser>) super.findForList(CommonOrgUser.class);
	}
}
