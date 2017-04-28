package com.global.param.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;
import com.global.param.dao.CurrencyDao;
import com.global.param.domain.Currency;

/**
 * 
 * @author xu.ke
 * @date 2014-6-18下午6:48:26 
 */
@Repository("currencyDao")
public class CurrencyDaoImpl extends BaseDaoSupport implements CurrencyDao {

	@SuppressWarnings("unchecked")
	public List<Currency> getCurrencyList() throws BaseException {
		String sql = "select * from dc_pa_currency_info where cursign <> 'CNY'";
		return (List<Currency>) super.findForListBySql(sql, null, Currency.class);
	}

}
