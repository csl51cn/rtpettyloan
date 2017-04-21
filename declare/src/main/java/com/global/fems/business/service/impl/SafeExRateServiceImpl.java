package com.global.fems.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.fems.business.dao.SafeExRateDao;
import com.global.fems.business.domain.SafeExRate;
import com.global.fems.business.domain.UsdCvsRate;
import com.global.fems.business.service.SafeExRateService;
import com.global.fems.message.util.DateTimeUtil;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.framework.util.SysUtils;

@Service("safeExRateService")
public class SafeExRateServiceImpl implements SafeExRateService {

	private static final Logger log = Logger.getLogger(SafeExRateServiceImpl.class);
	
	@Autowired
	private SafeExRateDao safeExRateDao;
	
	public SafeExRate getSafeExRate(String curr, String yearMonth)
			throws BaseException {
		return safeExRateDao.getSafeExRate(curr, yearMonth);
	}

	public List<SafeExRate> getSafeExRateList(String yearMonth)
			throws BaseException {
		return safeExRateDao.getSafeExRateList(yearMonth);
	}

	public void updateSafeExRate(String yearMonth, String isValid)
			throws BaseException {
		safeExRateDao.updateSafeExRate(yearMonth, isValid);
	}
	
	/**
	 * 计算折美元金额
	 * 
	 * @param txAmt 交易金额
	 * @param txCurr 交易币种
	 * @param mainOrgNo 核心机构号
	 * @param operNo 核心柜员号
	 * @return
	 * @throws DataCheckException
	 */
	public String transTxAmtToUsdAmt(String txAmt, String txCurr) throws DataCheckException {
		if ("USD".equals(txCurr)){
			return SysUtils.parseAmt(txAmt);
		}
		
		//查询当前汇率
		String currentMonth = DateTimeUtil.getCurrentDate("yyyyMM");
		SafeExRate rate = null;
		try {
			rate = this.getSafeExRate(txCurr, currentMonth);
		} catch (BaseException e) {
			log.error("获取["+currentMonth+"]月["+txCurr+"]的美元折算率失败", e);
			throw new DataCheckException("", "获取美元折算率失败");
		}
		
		if (rate == null) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_810028.getCode(), "折美元金额失败["+txCurr+"币种折美元汇率不存在]");
		}
		

		BigDecimal tx_amt = new BigDecimal(SysUtils.parseAmt(txAmt));//交易金额
		BigDecimal tx_rate = new BigDecimal(rate.getExchange());
		BigDecimal usdAmt = tx_amt.multiply(tx_rate);// 交易金额折美元金额
		// 四舍五入
		BigDecimal b = new BigDecimal(String.valueOf(usdAmt));
		// 保留2位小数
		BigDecimal f = b.setScale(2, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(f);
	}
	
	public void insertSafeExRate(String yearMonth, List<UsdCvsRate> list, User user) throws BaseException {
		if (list != null && list.size() > 0) {
			//先删除当月的折算率
			this.safeExRateDao.deleteByYearMonth(yearMonth);
			
			//导入当月 折算率
			List<SafeExRate> rates = new ArrayList<SafeExRate>();
			String importDate = SysUtils.getNowDateTime();
			for (UsdCvsRate usdCvsRate : list) {
				SafeExRate info = new SafeExRate();
				info.setYearMonth(usdCvsRate.getYearMonth());
				info.setCurrencyCode(usdCvsRate.getCurrencyCode());
				info.setExchange(usdCvsRate.getExchange());
				info.setIsValid("Y");
				info.setOperno(user.getUserId());
				info.setImportDate(importDate);
				rates.add(info);
			}
			this.safeExRateDao.insertBatchSafeExRate(rates);
			
			//更新上月折算率生效标识
			String preYearMonth = SysUtils.getLastDayOfPreMonth(SysUtils.getStrToDate(yearMonth, "yyyyMM"), "yyyyMM");
			this.safeExRateDao.updateSafeExRate(preYearMonth, "N");
		}
		
	}

	public void copyPreMonthExRate(User user) throws BaseException {
		String yearMonth = SysUtils.getNowDateTime("yyyyMM");
		//先查询当月是否存在折算率，如存在无法沿用
		List<SafeExRate> list = this.safeExRateDao.getSafeExRateList(yearMonth);
		if (list != null && list.size() > 0) {
			throw new BaseException("当月【"+yearMonth+"】美元折算率已导入，无需沿用！");
		}
		String preYearMonth = SysUtils.getLastDayOfPreMonth(SysUtils.getStrToDate(yearMonth, "yyyyMM"), "yyyyMM");
		List<SafeExRate> preList = this.safeExRateDao.getSafeExRateList(preYearMonth);
		if (preList != null && preList.size() > 0) {
			String importDate = SysUtils.getNowDateTime();
			for (SafeExRate safeExRate : preList) {
				safeExRate.setYearMonth(yearMonth);
				safeExRate.setImportDate(importDate);
				safeExRate.setOperno(user.getUserId());
				safeExRate.setIsValid("Y");
			}
			this.safeExRateDao.insertBatchSafeExRate(preList);
			
			//更新上月折算率生效标识
			this.safeExRateDao.updateSafeExRate(preYearMonth, "N");
		}
	}
	
	public PageBean queryForPage(String currencyCode, String yearMonth,
			PageBean page) throws BaseException {
		return this.safeExRateDao.queryForPage(currencyCode, yearMonth, page);
	}
}
