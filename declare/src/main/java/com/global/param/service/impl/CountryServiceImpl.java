package com.global.param.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.exception.BaseException;
import com.global.param.dao.CountryDao;
import com.global.param.domain.Country;
import com.global.param.service.CountryService;

/**
 * 
 * @author chen.feng
 * @date 2014-6-14 下午10:54:02
 */
@Service("countryService")
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryDao countryDao;
	
	public List<Country> getCountryList() throws BaseException {
		return this.countryDao.getCountryList();
	}

}
