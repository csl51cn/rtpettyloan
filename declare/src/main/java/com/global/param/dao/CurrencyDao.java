package com.global.param.dao;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.param.domain.Currency;

/**
 * 
 * @author xu.ke
 * @date 2014-6-18下午3:55:16 
 */
public interface CurrencyDao {

	public List<Currency> getCurrencyList() throws BaseException;
}
