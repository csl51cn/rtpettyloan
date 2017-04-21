package com.global.param.service;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.param.domain.Country;

/**
 * 
 * @author chen.feng
 * @date 2014-6-14 下午10:53:11
 */
public interface CountryService {

	public List<Country> getCountryList() throws BaseException;
}
