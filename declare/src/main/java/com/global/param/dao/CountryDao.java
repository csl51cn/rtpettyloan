package com.global.param.dao;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.param.domain.Country;

/**
 * 
 * @author chen.feng
 * @date 2014-6-14 下午10:51:32
 */
public interface CountryDao {

	public List<Country> getCountryList() throws BaseException;
}
