package com.global.workflow.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;
import com.global.workflow.dao.TradePrivilegeDao;
import com.global.workflow.domain.TradePrivilege;

/**
 * 
 * @author chen.feng
 * @date 2015-6-25
 * @version v1.0
 */
@Repository("tradePrivilegeDao")
public class TradePrivilegeDaoImpl extends BaseDaoSupport implements
		TradePrivilegeDao {

	@SuppressWarnings("unchecked")
	public List<TradePrivilege> queryTradePrivilege(String tradeNo)
			throws BaseException {
		String sql = "select * from wfl_tradeprivilege t where t.tradeno = ?";
		return (List<TradePrivilege>) super.findForListBySql(sql,
				new Object[] { tradeNo }, TradePrivilege.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TradePrivilege> queryTradePrivilegeByMenuID(String menuid)
			throws BaseException {
		String sql = "select * from wfl_tradeprivilege t where t.menuid = ?";
		return (List<TradePrivilege>) super.findForListBySql(sql,
				new Object[] { menuid }, TradePrivilege.class);
	}

	
	public int update(TradePrivilege privilege) throws BaseException {
		return super.update(privilege);
	}
}
