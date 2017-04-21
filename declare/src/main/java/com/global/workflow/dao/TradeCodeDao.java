package com.global.workflow.dao;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.workflow.domain.TradeCode;

/**
 * 
 * @author chen.feng
 * @date 2014-7-6 上午11:56:14
 */
public interface TradeCodeDao {

	public List<TradeCode> getTradeCodeList() throws BaseException;

	public TradeCode saveTradeCode(TradeCode tc) throws BaseException;
}
