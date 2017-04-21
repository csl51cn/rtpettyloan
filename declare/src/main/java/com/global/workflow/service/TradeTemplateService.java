package com.global.workflow.service;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.workflow.domain.TradeTemplate;

/**
 * 
 * @author chen.feng
 * @date 2015-6-25
 * @version v1.0
 */
public interface TradeTemplateService {

	List<TradeTemplate> queryTradeTemplate() throws BaseException;
	
	void update(String tradeTempForms) throws BaseException;

}
