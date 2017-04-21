package com.global.param.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.exception.BaseException;
import com.global.param.dao.CurrencyDao;
import com.global.param.domain.Currency;
import com.global.param.service.CurrencyService;

/**
 * 
 * @author xu.ke
 * @date 2014-6-18下午7:01:23 
 */
@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService {
	
	@Autowired
	private CurrencyDao currencyDao;
	

	
	public List<Currency> getCurrencyList() throws BaseException {
		return this.currencyDao.getCurrencyList();
	}

}
