package com.global.workflow.service;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.workflow.domain.TradeCode;

/**
 * 
 * @author chen.feng
 * @date 2014-7-6 上午11:58:28
 */
public interface TradeCodeService {

	public List<TradeCode> getTradeCodeList() throws BaseException;

	public void saveTradeCode(String tradeCodes) throws BaseException;
}
