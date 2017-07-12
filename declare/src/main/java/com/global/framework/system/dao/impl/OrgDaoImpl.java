package com.global.framework.system.dao.impl;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.OrgDao;
import com.global.framework.system.domain.Org;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cqchenf@qq.com
 * @date 2011-9-7 下午11:00:40
 * @version v1.0
 */
@Repository("orgDao")
public class OrgDaoImpl extends BaseDaoSupport implements OrgDao {

	@SuppressWarnings("unchecked")
	public List<Org> getOrgList() throws BaseException {
		return (List<Org>) super.findForList(Org.class);
	}

	public PageBean queryOrgForPage(Org org, PageBean page)
			throws BaseException {
		StringBuilder sql = new StringBuilder(256);
		sql.append("SELECT  t.*,(select orgName from dc_sys_org where orgId=t.parentOrgId) parentOrgName FROM dc_sys_org t WHERE 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(org.getOrgName())) {
			sql.append(" and t.orgName like ? ");
			args.add("%" + org.getOrgName() + "%");
		}
		if (StringUtils.isNotBlank(org.getOrgId())) {
			sql.append(" and t.orgId = ? ");
			args.add(org.getOrgId());
		}
		if (StringUtils.isNotBlank(org.getOrgCode())) {
			sql.append(" and t.orgCode = ? ");
			args.add(org.getOrgCode());
		}
		page.setSort("orgid");
		return super.findForPage(sql.toString(), args.toArray(), page,
				Org.class);
	}

	public Org insertOrg(Org org) throws BaseException {
		return super.insert(org);
	}

	public Org updateOrg(Org org) throws BaseException {
		super.update(org);
		return org;
	}

	public void deleteOrg(Org org) throws BaseException {
		//查询该机构是否有下级机构
		String sql = "select count(0) from dc_sys_org o where o.parentorgid=? ";
		int count = super.findForIntBySql(sql, new Object[]{org.getOrgId()});
		if (count > 0) {
			throw new BaseException("该机构含有下级机构不允许删除！");
		}
		
//		String sql2 = "select * from dc_sys_user u where u.orgid=? ";
//		int count2 = super.findForIntBySql(sql2, new Object[]{org.getOrgId()});
//		if (count2 > 0) {
//			throw new BaseException("该机构下有用户信息存在，不允许删除");
//		}
		
		super.delete(org);
	}

	public Org getOrgInfo(Org org) throws BaseException {
		return (Org) super.findForObject(org);
	}

	public String getMaxOrgNoByLevel(String orgLevel) throws BaseException {
		String sql = "select max(o.orgid) orgid from dc_sys_org o where o.orglevel=?";
		Org org = (Org) super.findForObjectBySql(sql,
				new Object[] { orgLevel }, Org.class);
		return org.getOrgId();
	}

	public Org getOrgInfo(String bankCode) throws BaseException {
		String sql = "select * from dc_sys_org o where o.bankCode = ?";
		return super.findForObjectBySql(sql, new Object[] { bankCode }, Org.class);
	}
}
