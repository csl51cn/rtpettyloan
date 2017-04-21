package com.global.param.service;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.param.domain.Currency;

/**
 * 
 * @author xu.ke
 * @date 2014-6-18下午7:00:43 
 */
public interface CurrencyService {
	public List<Currency> getCurrencyList() throws BaseException;
}
