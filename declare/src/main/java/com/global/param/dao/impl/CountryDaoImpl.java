package com.global.param.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;
import com.global.param.dao.CountryDao;
import com.global.param.domain.Country;

/**
 * 
 * @author chen.feng
 * @date 2014-6-14 下午10:52:28
 */
@Repository("countryDao")
public class CountryDaoImpl extends BaseDaoSupport implements CountryDao {

	@SuppressWarnings("unchecked")
	public List<Country> getCountryList() throws BaseException {
		return (List<Country>) super.findForList(Country.class);
	}

}
