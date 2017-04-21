package com.global.fems.business.service;

import java.util.List;

import org.global.framework.xmlbeans.bean.DataCheckException;

import com.global.fems.business.domain.SafeExRate;
import com.global.fems.business.domain.UsdCvsRate;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;

public interface SafeExRateService {

	public SafeExRate getSafeExRate(String curr, String yearMonth) throws BaseException;
	
	public List<SafeExRate> getSafeExRateList(String yearMonth) throws BaseException;
	
	public void updateSafeExRate(String yearMonth, String isValid) throws BaseException;
	
	public String transTxAmtToUsdAmt(String txAmt, String txCurr) throws DataCheckException ;

	public void insertSafeExRate(String yearMonth, List<UsdCvsRate> ret, User user) throws BaseException;

	public void copyPreMonthExRate(User user) throws BaseException;

	public PageBean queryForPage(String currencyCode, String yearMonth,
                                 PageBean page) throws BaseException;
}
