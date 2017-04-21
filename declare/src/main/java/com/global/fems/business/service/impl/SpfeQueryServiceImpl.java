package com.global.fems.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.fems.business.domain.JSHMsg;
import com.global.fems.business.domain.SpfeAmtQuery;
import com.global.fems.business.domain.UsdCvsRate;
import com.global.fems.business.enums.ConfirmStatusEnum;
import com.global.fems.business.enums.TradeTypeEnum;
import com.global.fems.business.service.SpfeQueryService;
import com.global.fems.message.service.impl.IndividualMsgBizManager;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.Org;
import com.global.framework.system.domain.User;
import com.global.framework.system.web.common.CacheService;
import com.global.param.domain.Country;
import com.global.workflow.dao.TaskInfoDao;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TransStateEnum;
import com.pactera.fems.message.wg.constants.BizChannelTypeEnum;
import com.pactera.fems.message.wg.constants.BizTypeEnum;
import com.pactera.fems.message.wg.constants.CertTypeEnum;
import com.pactera.fems.message.wg.constants.CustTypeEnum;
import com.pactera.fems.message.wg.constants.GHZJSXCodeEnum;
import com.pactera.fems.message.wg.constants.JHZJSXCodeEnum;
import com.pactera.fems.message.wg.constants.JHZJXTCodeEnum;
import com.pactera.fems.message.wg.constants.OperateTypeEnum;
import com.pactera.fems.message.wg.constants.PersonCustTypeEnum;
import com.pactera.fems.message.wg.constants.PubResonEnum;

/**
 * 个人结售汇查询
 * 
 * @author Sili Jiang
 * @version 2015-07-06
 * 
 */
@Service("spfeQueryService")
public class SpfeQueryServiceImpl implements SpfeQueryService {

	private static final Logger log = Logger.getLogger(SpfeQueryServiceImpl.class);

	@Autowired
	private TaskInfoDao taskInfoDao;
	@Autowired
	private IndividualMsgBizManager  individualMsgBizManager;

	@SuppressWarnings("rawtypes")
	public SpfeAmtQuery spfeAmtQuery(SpfeAmtQuery spfeAmtQuery, User user) throws BaseException , DataCheckException {	
		Map retDS = individualMsgBizManager.doQueryIndividualFXSEAQuota(spfeAmtQuery.getIdTypeCode(),
				spfeAmtQuery.getIdCode(), spfeAmtQuery.getCityCode(), spfeAmtQuery.getTradeType(), user.getUserCode());	
		List rowsets = (List) retDS.get("ROWSET");
		if (!rowsets.isEmpty()) {
			Map rowset = (Map) rowsets.get(0);
			spfeAmtQuery.setAmtUsd(replaceNull(StringUtils.equals(spfeAmtQuery.getTradeType(), TradeTypeEnum.JH.getCode()) ? rowset.get("ANN_LCYAMT_USD") : rowset.get("ANN_FCYAMT_USD")));
			spfeAmtQuery.setAmtBalanceUsd(replaceNull(StringUtils.equals(spfeAmtQuery.getTradeType(), TradeTypeEnum.JH.getCode()) ? rowset.get("ANN_REM_LCYAMT_USD") : rowset.get("ANN_REM_FCYAMT_USD")));
			spfeAmtQuery.setTradeType(TradeTypeEnum.getNameByCode(spfeAmtQuery.getTradeType()));
			spfeAmtQuery.setTodayAmtUsd(replaceNull(rowset.get("TODAY_CASH_USD")));
			spfeAmtQuery.setCustName(replaceNull(rowset.get("CUSTNAME")));
			spfeAmtQuery.setCustType(CustTypeEnum.getValueByCode(replaceNull(rowset.get("CUSTTYPE_CODE"))));
			spfeAmtQuery.setTypeStatusCode(replaceNull(rowset.get("TYPE_STATUS")));
			spfeAmtQuery.setSignStatusCode(replaceNull(rowset.get("SIGN_STATUS")));
			spfeAmtQuery.setTypeStatus(PersonCustTypeEnum.getValueByCode(replaceNull(rowset.get("TYPE_STATUS"))));
			spfeAmtQuery.setPubDate(replaceNull(rowset.get("PUB_DATE")));
			spfeAmtQuery.setEndDate(replaceNull(rowset.get("END_DATE")));
			spfeAmtQuery.setPubReason(replaceNull(rowset.get("PUB_REASON")));
			spfeAmtQuery.setPubCode(PubResonEnum.getValueByCode(replaceNull(rowset.get("PUB_CODE"))));
			spfeAmtQuery.setSignStatus(ConfirmStatusEnum.getNameByCode(replaceNull(rowset.get("SIGN_STATUS"))));
		}
		return spfeAmtQuery;
	}

	public List<TaskInfo> spfeRegisterQuery(String refNo, String seqNo) throws BaseException {
		List<TaskInfo> list = new ArrayList<TaskInfo>();
		if (StringUtils.isEmpty(refNo) && StringUtils.isEmpty(seqNo)) {
			return list;
		}
		TaskInfo taskInfo = taskInfoDao.getTaskInfoUnionHis(refNo, seqNo);
		taskInfo.setTransStateName(TransStateEnum.getValueByCode(taskInfo.getTransState()));
		list.add(taskInfo);
		return list;
	}

	@SuppressWarnings("rawtypes")
	public PageBean usdCvsRateQuery(String currencyCode, String yearMonth, PageBean page, User user) throws BaseException,
			DataCheckException {
		Map ds = individualMsgBizManager.doQuerySafeExRate(currencyCode, yearMonth, page, user.getUserCode());
		List rowsets = (List) ds.get("ROWSET");
		List<UsdCvsRate> usdCvsRates = new ArrayList<UsdCvsRate>();
		for (int i = 0; i < rowsets.size(); i++) {
			UsdCvsRate usdCvsRate = new UsdCvsRate();
			Map rowset = (Map) rowsets.get(i);
			usdCvsRate.setCurrencyCode(String.valueOf(rowset.get("CURRENCY_CODE")));
			usdCvsRate.setExchange(String.valueOf(rowset.get("EXCHANGE")));
			usdCvsRate.setYearMonth(String.valueOf(rowset.get("YEAR_MONTH")));
			usdCvsRates.add(usdCvsRate);
		}
		PageBean pageBean = new PageBean();
		pageBean.setPage(Integer.parseInt(String.valueOf(ds.get("PAGENUMBER"))));
		pageBean.setRows(Integer.parseInt(String.valueOf(ds.get("PAGESIZE"))));
		pageBean.setTotalRows(Long.parseLong(String.valueOf(ds.get("RECORDCOUNT"))));
		pageBean.setDataList(usdCvsRates);
		return pageBean;
	}

	@SuppressWarnings("rawtypes")
	public PageBean jshMsgQuery(JSHMsg jshMsg, PageBean page, User user) throws BaseException, DataCheckException {
		if (StringUtils.isEmpty(jshMsg.getREFNO()) && StringUtils.isEmpty(jshMsg.getIDTYPE_CODE())
				&& StringUtils.isEmpty(jshMsg.getBIZ_TX_TIME())) {
			return new PageBean();
		}
		Map retDSMap = this.individualMsgBizManager.doQueryIndividualFXSEAInfo(jshMsg.getTRADETYPE(),
				jshMsg.getREFNO(), jshMsg.getIDTYPE_CODE(), jshMsg.getIDCODE(),
				jshMsg.getCTYCODE(), jshMsg.getBIZ_TX_TIME(), user.getUserCode(), page);
		return parseRspList(retDSMap, jshMsg);
	}

	@SuppressWarnings("rawtypes")
	public JSHMsg jshMsgDetailQuery(String refNo, String tardeType, User user) throws BaseException, DataCheckException {
		JSHMsg jshMsg = new JSHMsg();
		jshMsg.setTRADETYPE(tardeType);
		jshMsg.setREFNO(refNo);
		
		Org org = CacheService.getOrgById(user.getOrgId());
		Map retDSMap = this.individualMsgBizManager.doQueryIndividualFXSEAInfo(
				jshMsg.getTRADETYPE(), jshMsg.getREFNO(), org.getOrgCode(),
				user.getUserCode());
		return parseRspInfo(retDSMap, jshMsg);
	}

	/**
	 * 解析返回报文列表数据
	 * 
	 * @param map 返回报文数据
	 * @param jshMsg JSHMsg
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private PageBean parseRspList(Map ds, JSHMsg jshMsg) throws BaseException {
		List rowsets = (List) ds.get("ROWSET");
		List<JSHMsg> jshMsgs = new ArrayList<JSHMsg>();
		try {
			for (int i = 0; i < rowsets.size(); i++) {
				JSHMsg result = new JSHMsg();
				BeanUtils.copyProperties(result, rowsets.get(i));
				setJSHMsgInfo(result, jshMsg, (Map) rowsets.get(i));
				jshMsgs.add(result);
			}
		} catch (Exception e) {
			log.error("解析查询的个人结售汇信息失败", e);
			throw new BaseException("解析查询的个人结售汇信息失败");
		}
		PageBean pageBean = new PageBean();
		pageBean.setPage(Integer.parseInt(String.valueOf(ds.get("PAGENUMBER"))));
		pageBean.setRows(Integer.parseInt(String.valueOf(ds.get("PAGESIZE"))));
		pageBean.setTotalRows(Long.parseLong(String.valueOf(ds.get("RECORDCOUNT"))));
		pageBean.setDataList(jshMsgs);
		return pageBean;
	}

	/**
	 * 解析返回报文明细数据
	 * 
	 * @param map 返回报文数据
	 * @param jshMsg JSHMsg
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private JSHMsg parseRspInfo(Map ds, JSHMsg jshMsg) throws BaseException {
		List rowsets = (List) ds.get("ROWSET");
		if (rowsets == null || rowsets.size() == 0) {
			throw new BaseException("未查询到个人结售汇详细信息");
		}
		JSHMsg result = new JSHMsg();
		try {
			BeanUtils.copyProperties(result, rowsets.get(0));
			setJSHMsgInfo(result, jshMsg, (Map) rowsets.get(0));
		} catch (Exception e) {
			log.error("解析查询的个人结售汇详细信息失败", e);
			throw new BaseException("解析查询的个人结售汇详细信息失败");
		}
		return result;
	}

	/**
	 * 设置未能自动转换成实体bean值
	 * 
	 * @param jshMsg JSHMsg
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	private void setJSHMsgInfo(JSHMsg jshMsg, JSHMsg condition, Map map) {
		jshMsg.setBIZ_TYPE_CODE(BizTypeEnum.getValueByCode(jshMsg.getBIZ_TYPE_CODE()));
		jshMsg.setIDTYPE_CODE(CertTypeEnum.getValueByCode(jshMsg.getIDTYPE_CODE()));
		jshMsg.setBIZ_TX_CHNL_CODE(BizChannelTypeEnum.getValueByCode(jshMsg.getBIZ_TX_CHNL_CODE()));
		jshMsg.setACTIONTYPE(OperateTypeEnum.getValueByCode(jshMsg.getACTIONTYPE()));
		if (TradeTypeEnum.JH.getCode().equals(condition.getTRADETYPE())) {
			jshMsg.setTRADETYPE("JH");
			jshMsg.setTRADETYPENAME(TradeTypeEnum.JH.getName());
			jshMsg.setSALEFX_PURFX_TYPE_CODE(JHZJSXCodeEnum.getValueByCode(replaceNull(map.get("SALEFX_TX_CODE")))); // 结汇资金属性代码
			jshMsg.setSALEFX_PURFX_AMT(replaceNull(map.get("SALEFX_AMT"))); // 结汇金额
			jshMsg.setSALEFX_PURFX_ACCT_CNY(replaceNull(map.get("LCY_ACCTNO_CNY"))); // 结汇人民币账户
			jshMsg.setSALEFX_PURFX_AMT_USD(replaceNull(map.get("SALEFX_AMT_USD"))); // 结汇金额折美元
		} else if (TradeTypeEnum.GH.getCode().equals(condition.getTRADETYPE())){
			jshMsg.setTRADETYPE("GH");
			jshMsg.setTRADETYPENAME(TradeTypeEnum.GH.getName());
			jshMsg.setSALEFX_PURFX_TYPE_CODE(GHZJSXCodeEnum.getValueByCode(replaceNull(map.get("PURFX_TYPE_CODE")))); // 购汇资金属性
			jshMsg.setSALEFX_PURFX_AMT(replaceNull(map.get("PURFX_AMT"))); // 购汇金额
			jshMsg.setSALEFX_PURFX_ACCT_CNY(replaceNull(map.get("PURFX_ACCT_CNY"))); // 购汇金额折美元
			jshMsg.setSALEFX_PURFX_AMT_USD(replaceNull(map.get("PURFX_AMT_USD"))); // 购汇人民币账户
		}
		jshMsg.setBIZ_TYPE_NAME(BizTypeEnum.getValueByCode(replaceNull(map.get("BIZ_TYPE_CODE"))));
		Country country = CacheService.getCountryCacheById(replaceNull(jshMsg.getCTYCODE()));
		jshMsg.setCTYNAME(country == null ? jshMsg.getCTYCODE() :country.getCnName());
		jshMsg.setSALEFX_SETTLE_CODE(JHZJXTCodeEnum.getValueByCode(replaceNull(map.get("SALEFX_SETTLE_CODE"))));
	}

	/**
	 * 替换null值
	 * 
	 * @param str object
	 * @return
	 */
	private static String replaceNull(Object str) {
		return str == null ? "" : str.toString();
	}

}
