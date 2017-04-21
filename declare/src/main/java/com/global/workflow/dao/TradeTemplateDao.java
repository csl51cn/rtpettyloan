package com.global.workflow.dao;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.workflow.domain.TradeTemplate;

/**
 * 
 * @author chen.feng
 * @date 2015-6-20
 * @version v1.0
 */
public interface TradeTemplateDao {

	public TradeTemplate getTradeTemplate(String tradeNo) throws BaseException;

	public List<TradeTemplate> queryTradeTemplate() throws BaseException;
	
	/**
	 * 更新
	 * 
	 * @param tradeNo 交易编号
	 * @return
	 * @throws BaseException
	 */
	public int update(TradeTemplate temp) throws BaseException;
}
