package com.global.workflow.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;
import com.global.workflow.dao.TradeTemplateDao;
import com.global.workflow.domain.TradeTemplate;

/**
 * 
 * @author chen.feng
 * @date 2015-6-20
 * @version v1.0
 */
@Repository("tradeTemplateDao")
public class TradeTemplateDaoImpl extends BaseDaoSupport implements
		TradeTemplateDao {

	
	public TradeTemplate getTradeTemplate(String tradeNo) throws BaseException {
		return super.findForObject(TradeTemplate.class, tradeNo);
	}

	@SuppressWarnings("unchecked")
	public List<TradeTemplate> queryTradeTemplate() throws BaseException {
		String sql = "select t.*,(select tradename from dc_wfl_tradecode where tradeno=t.tradeno) tradeName from dc_wfl_tradetemplate t";
		return (List<TradeTemplate>) super.findForListBySql(sql, null,
				TradeTemplate.class);
	}

	
	public int update(TradeTemplate temp) throws BaseException {
		return super.update(temp);
	}
	
}
