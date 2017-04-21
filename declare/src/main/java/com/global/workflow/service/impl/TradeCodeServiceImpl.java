package com.global.workflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.global.framework.exception.BaseException;
import com.global.workflow.dao.TradeCodeDao;
import com.global.workflow.domain.TradeCode;
import com.global.workflow.service.TradeCodeService;

/**
 * 
 * @author chen.feng
 * @date 2014-7-6 上午11:59:09
 */
@Service("tradeCodeService")
public class TradeCodeServiceImpl implements TradeCodeService {

	@Autowired
	private TradeCodeDao tradeCodeDao;
	
	
	public List<TradeCode> getTradeCodeList() throws BaseException {
		return tradeCodeDao.getTradeCodeList();
	}

	
	public void saveTradeCode(String tradeCodes) throws BaseException {
		List<TradeCode> list = JSON.parseArray(tradeCodes, TradeCode.class);
		for (TradeCode tc : list) {
			tradeCodeDao.saveTradeCode(tc);
		}
	}
}
