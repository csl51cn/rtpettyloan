package com.global.workflow.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;
import com.global.workflow.dao.TradeCodeDao;
import com.global.workflow.domain.TradeCode;

/**
 * 
 * @author chen.feng
 * @date 2014-7-6 上午11:56:52
 */
@Repository("tradeCodeDao")
public class TradeCodeDaoImpl extends BaseDaoSupport implements TradeCodeDao {

	@SuppressWarnings("unchecked")
	public List<TradeCode> getTradeCodeList() throws BaseException {
		String sql = "select t.* from wfl_tradecode t where t.tradeno not in('CWDD','CWDC')";
		return (List<TradeCode>) super.findForListBySql(sql, null, TradeCode.class);
	}
	
	
	public TradeCode saveTradeCode(TradeCode tc) throws BaseException {
		super.update(tc);
		return tc;
	}

}
