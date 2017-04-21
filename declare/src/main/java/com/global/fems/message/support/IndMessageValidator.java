package com.global.fems.message.support;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;

import com.global.fems.business.service.SafeExRateService;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYMakeUp;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYModify;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYRegQuery;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYRegister;
import com.global.framework.util.SpringContextUtil;
import com.global.framework.util.SysUtils;
import com.pactera.fems.message.wg.constants.CertTypeEnum;
import com.pactera.fems.message.wg.constants.JHZJXTCodeEnum;
import com.pactera.fems.message.wg.support.WGDSValitorHander;

/**
 * 前置接口校验工具类
 * 
 * @author chen.feng
 * @date 2015-7-2
 * @version v1.0
 */
@SuppressWarnings("rawtypes")
public class IndMessageValidator {
	
	private static SafeExRateService safeExRateService = (SafeExRateService) SpringContextUtil.getBean("safeExRateService");

	/**
	 * 000003-美元折算率查询
	 */
	public static void validateSafeExRateQuery(Map data)
			throws DataCheckException {
		WGDSValitorHander.valiteWGReqQuerySafeExRate(data);
	}

	/**
	 * 000005-个人结售汇信息撤消
	 */
	public static void validateIndividualLCYCancel(Map data)
			throws DataCheckException {
		WGDSValitorHander.valiteWGReqDeleteIndividualFXSEInfo(data);
	}
	
	/**
	 * 000004-个人结售汇额度登记校验，供系统联机接口调用验证使用
	 */
	public static void validateIndividualLCYRegister(
			Map data, RecvIndividualLCYRegister ds, Map dsMap) throws DataCheckException {
		Map lmtMap = (Map)((List)dsMap.get("ROWSET")).get(0);
		String today_cash_usd = String.valueOf(lmtMap.get("TODAY_CASH_USD"));//当日已经结汇的金额折美元    当日已发生的购汇提钞金额折美元
		if ("JH".equals(ds.getTRADE_TYPE())) {
			String TXAMT_USD = safeExRateService.transTxAmtToUsdAmt(ds.getTXAMT(), ds.getTXCCY());
			if (!CertTypeEnum.TYPE_10.getCode().equals(ds.getIDTYPE_CODE()) 
					&& ds.getSALEFX_SETTLE_CODE().equals(JHZJXTCodeEnum.CODE_01.getCode()) 
					&& new BigDecimal(SysUtils.parseAmt(today_cash_usd)).add(new BigDecimal(SysUtils.parseAmt(TXAMT_USD))).subtract(new BigDecimal("5000")).doubleValue() > 0
					&& StringUtils.isBlank(ds.getREMARK())) {
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450004.getCode(), "当日累计现钞结汇金额为：“"+new BigDecimal(SysUtils.parseAmt(today_cash_usd)).add(new BigDecimal(SysUtils.parseAmt(TXAMT_USD))).toString()+"”,超过等值5000美元，请审核现钞来源证明材料，并在备注栏注明！");
			}
			if ("Y".equals(ds.getOCCUPY_LMT_STATUS())) {
				//6.2. 占用额度的个人结汇信息录入
				WGDSValitorHander.valiteWGReqAddLimitedIndividualFXSEInfo(data);
			} else {
				//6.3. 不占额度的结汇信息录入
				WGDSValitorHander.valiteWGReqAddUnLimitedIndividualFXSEInfo(data);
			}
		}else if ("GH".equals(ds.getTRADE_TYPE())) {
			String PURFX_CASH_AMT = safeExRateService.transTxAmtToUsdAmt(ds.getPURFX_CASH_AMT(), ds.getTXCCY());
			if (!CertTypeEnum.TYPE_10.getCode().equals(ds.getIDTYPE_CODE()) 
					&& new BigDecimal(SysUtils.parseAmt(today_cash_usd)).add(new BigDecimal(SysUtils.parseAmt(PURFX_CASH_AMT))).subtract(new BigDecimal("10000")).doubleValue() > 0
					&& StringUtils.isBlank(ds.getREMARK())) {
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450010.getCode(), "当日累计提钞：“"+new BigDecimal(SysUtils.parseAmt(today_cash_usd)).add(new BigDecimal(SysUtils.parseAmt(PURFX_CASH_AMT))).toString()+"”,超过等值10000美元，请在备注栏注明提钞备案表号！");
			}
			if ("Y".equals(ds.getOCCUPY_LMT_STATUS())) {
				//6.10. 占用额度的个人购汇信息录入
				WGDSValitorHander.valiteWGReqAddLimitedIndividualFXSAInfo(data);
			} else {
				//6.11. 不占额度的购汇信息录入
				WGDSValitorHander.valiteWGReqAddUnLimitedIndividualFXSAInfo(data);
			}
		}
		
	}
	
	
	/**
	 * 000004-个人结售汇额度登记校验，供系统前台界面验证使用
	 * @param ds 登记信息实体
	 * @param BIZ_TX_CHNL_CODE
	 * @param ret 查询的额度信息
	 * @throws DataCheckException
	 */
	public static void validateIndividualLCYRegister(
			RecvIndividualLCYRegister ds, String BIZ_TX_CHNL_CODE, Map dsMap) throws DataCheckException {
		Map data = null;
		try {
			data = IndividualLCYDataConver.toRecvIndividualLCYRegister(ds, "20150723", BIZ_TX_CHNL_CODE);
		} catch (Exception e1) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),e1.getMessage());
		}
		
		validateIndividualLCYRegister(data, ds, dsMap);
		
	}
	
	/**
	 * 个人结售汇信息补录校验，供系统联机接口调用验证使用
	 */
	public static void validateIndividualLCYMakeUp(Map data,
			RecvIndividualLCYMakeUp ds, Map quotaQueryDSMap) throws DataCheckException {
		//Map lmtMap = (Map)((List)quotaQueryDSMap.get("ROWSET")).get(0);
		//String today_cash_usd = String.valueOf(lmtMap.get("TODAY_CASH_USD"));//当日已经结汇的金额折美元    当日已发生的购汇提钞金额折美元
		if ("JH".equals(ds.getTRADE_TYPE())) {
			String TXAMT_USD = safeExRateService.transTxAmtToUsdAmt(ds.getTXAMT(), ds.getTXCCY());		
			if (!CertTypeEnum.TYPE_10.getCode().equals(ds.getIDTYPE_CODE()) 
					&& ds.getSALEFX_SETTLE_CODE().equals(JHZJXTCodeEnum.CODE_01.getCode()) 
					&& new BigDecimal(SysUtils.parseAmt(TXAMT_USD)).doubleValue() > 5000
					&& StringUtils.isBlank(ds.getREMARK())) {
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450020.getCode(), "单笔现钞结汇金额：“"+ new BigDecimal(SysUtils.parseAmt(TXAMT_USD)).toString()+"”,超过等值5000美元，请审核现钞来源证明材料，并在备注栏注明！");
			}
			if ("Y".equals(ds.getOCCUPY_LMT_STATUS())) {
				//占用额度的个人结汇信息补录
				WGDSValitorHander.valiteWGReqMakeUpOtherIndividualFXSEInfo(data);
			} else {
				//不占额度的结汇信息补录
				WGDSValitorHander.valiteWGReqMakeUpIndividualTradeFXSEInfo(data);
			}
		}else if ("GH".equals(ds.getTRADE_TYPE())) {
			String PURFX_CASH_AMT = safeExRateService.transTxAmtToUsdAmt(ds.getPURFX_CASH_AMT(), ds.getTXCCY());
			if (!CertTypeEnum.TYPE_10.getCode().equals(ds.getIDTYPE_CODE()) 
					&& new BigDecimal(SysUtils.parseAmt(PURFX_CASH_AMT)).doubleValue() > 10000
					&& StringUtils.isBlank(ds.getREMARK())) {
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450021.getCode(), "单笔提钞：“"+ new BigDecimal(SysUtils.parseAmt(PURFX_CASH_AMT)).toString()+"”,超过等值10000美元，请在备注栏注明提钞用途！");
			}
			if ("Y".equals(ds.getOCCUPY_LMT_STATUS())) {
				//占用额度的个人购汇信息补录
				WGDSValitorHander.valiteWGReqMakeUpOtherIndividualFXSAInfo(data);
			} else {
				//不占额度的购汇信息补录
				WGDSValitorHander.valiteWGReqMakeUpIndividualTradeFXSAInfo(data);
			}
		}
	}
	
	/**
	 * 个人结售汇信息补录校验，供系统前台界面验证使用
	 * @param ds 登记信息实体
	 * @param BIZ_TX_CHNL_CODE
	 * @param ret 查询的额度信息
	 * @throws DataCheckException
	 */
	public static void validateIndividualLCYMakeUp(
			RecvIndividualLCYMakeUp ds, String BIZ_TX_CHNL_CODE, Map dsMap) throws DataCheckException {
		Map data = null;
		try {
			data = IndividualLCYDataConver.toRecvIndividualLCYMakeUp(ds, "20150723", BIZ_TX_CHNL_CODE);
		} catch (Exception e1) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),e1.getMessage());
		}
		
		validateIndividualLCYMakeUp(data, ds, dsMap);
		
	}

	/**
	 * 000002-个人结售汇额度登记指令查询
	 */
	public static void validateIndividualLCYRegQuery(
			RecvIndividualLCYRegQuery ds) throws DataCheckException {
		/** 前置系统业务流水号，业务流水号与业务参号不能同时为空 */
		if (StringUtils.isBlank(ds.getREFNO())
				&& StringUtils.isBlank(ds.getSEQNO())) {
			throw new DataCheckException(
					MsgErrorCodeEnum.ERRCODE_450017.getCode(),
					"业务流水号与业务参号不能同时为空");
		}

	}
	
	/**
	 * 000006-个人结售汇信息修改，供系统联机接口调用验证使用
	 */
	public static void validateIndividualLCYModify(Map data, RecvIndividualLCYModify ds)
			throws DataCheckException {
		if ("JH".equals(ds.getTRADE_TYPE())) {
			//6.5. 个人结汇信息修改
			WGDSValitorHander.valiteWGReqModifyIndividualFXSEInfo(data);
		}else if ("GH".equals(ds.getTRADE_TYPE())) {
			//6.13. 个人购汇信息修改
			WGDSValitorHander.valiteWGReqModifyIndividualFXSAInfo(data);
		}
	}

	/**
	 * 000006-个人结售汇信息修改，供系统前台界面功能验证使用
	 */
	public static void validateIndividualLCYModify(RecvIndividualLCYModify ds)
			throws DataCheckException {
		Map data = IndividualLCYDataConver.toRecvIndividualLCYModifyMap(ds, null, null);
		validateIndividualLCYModify(data, ds);
	}

}
