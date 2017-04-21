package com.global.fems.business.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.global.fems.business.dao.SafeExRateDao;
import com.global.fems.business.domain.SafeExRate;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;

@Repository("safeExRateDao")
public class SafeExRateDaoImpl extends BaseDaoSupport implements SafeExRateDao {

	public SafeExRate getSafeExRate(String curr, String yearMonth)
			throws BaseException {
		if (StringUtils.isBlank(curr)) {
			throw new BaseException("币种不允许为空");
		}
		if (StringUtils.isBlank(yearMonth)) {
			throw new BaseException("年月不允许为空");
		}
		String sql = "select * from PA_SAFEEXRATE t where t.year_month=? and t.currency_code=? and t.isvalid=? ";
		return super.findForObjectBySql(sql, new Object[]{yearMonth, curr, "Y"}, SafeExRate.class);
	}

	@SuppressWarnings("unchecked")
	public List<SafeExRate> getSafeExRateList(String yearMonth)
			throws BaseException {
		if (StringUtils.isBlank(yearMonth)) {
			throw new BaseException("年月不允许为空");
		}
		String sql = "select * from PA_SAFEEXRATE t where t.year_month=? ";
		return (List<SafeExRate>) super.findForListBySql(sql, new Object[]{yearMonth}, SafeExRate.class);
	}

	public void updateSafeExRate(String yearMonth, String isValid)
			throws BaseException {
		if (StringUtils.isBlank(yearMonth)) {
			throw new BaseException("年月不允许为空");
		}
		if (StringUtils.isBlank(isValid)) {
			throw new BaseException("生效标识不允许为空");
		}
		String sql = "update PA_SAFEEXRATE t set t.isvalid=? where t.year_month=? ";
		super.updateBySql(sql, new Object[]{isValid, yearMonth});
	}
	
	public void deleteByYearMonth(String yearMonth) throws BaseException {
		String sql = "delete from PA_SAFEEXRATE where year_month = ?";
		if (StringUtils.isBlank(yearMonth)) {
			throw new BaseException("年月不允许为空");
		}
		super.delete(sql, new Object[]{yearMonth});
	}

	public void insertBatchSafeExRate(List<SafeExRate> rates)
			throws BaseException {
		super.batchInsert(rates);
	}
	
	public PageBean queryForPage(String currencyCode, String yearMonth,
			PageBean page) throws BaseException {
		List<String> args = new ArrayList<String>();
		String sql = "select * from PA_SAFEEXRATE t where 1=1 ";
		if (StringUtils.isNotBlank(yearMonth)) {
			sql += " and t.year_month=?";
			args.add(yearMonth);
		}
		if (StringUtils.isNotBlank(currencyCode)) {
			sql += " and t.currency_code=?";
			args.add(currencyCode);
		}
		return super.findForPage(sql, args.toArray(), page, SafeExRate.class);
	}
}
