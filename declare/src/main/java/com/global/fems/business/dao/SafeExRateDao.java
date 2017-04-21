package com.global.fems.business.dao;

import java.util.List;

import com.global.fems.business.domain.SafeExRate;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;

public interface SafeExRateDao {

	public SafeExRate getSafeExRate(String curr, String yearMonth) throws BaseException;
	
	public List<SafeExRate> getSafeExRateList(String yearMonth) throws BaseException;
	
	public void updateSafeExRate(String yearMonth, String isValid) throws BaseException;

	public void deleteByYearMonth(String yearMonth) throws BaseException;

	public void insertBatchSafeExRate(List<SafeExRate> rates) throws BaseException;

	public PageBean queryForPage(String currencyCode, String yearMonth,
                                 PageBean page) throws BaseException;
}
