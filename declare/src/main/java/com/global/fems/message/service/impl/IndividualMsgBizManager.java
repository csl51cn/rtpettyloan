package com.global.fems.message.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.global.fems.business.dao.TaskTimeoutDao;
import com.global.fems.business.domain.TaskTimeout;
import com.global.fems.business.service.SafeExRateService;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYCancel;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYMakeUp;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYModify;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYRegister;
import com.global.fems.message.domain.business.response.RspIndividualLCYCancel;
import com.global.fems.message.domain.business.response.RspIndividualLCYModify;
import com.global.fems.message.domain.business.response.RspIndividualLCYRegister;
import com.global.fems.message.support.IndMessageValidator;
import com.global.fems.message.support.IndividualLCYDataConver;
import com.global.fems.message.util.DateTimeUtil;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.CommonOrgUser;
import com.global.framework.system.domain.Property;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.util.StringUtil;
import com.global.param.domain.Channel;
import com.pactera.fems.message.wg.constants.BizTypeEnum;
import com.pactera.fems.message.wg.domain.WGReqMsgHead;
import com.pactera.fems.message.wg.service.WGIndividualLCYService;
import com.pactera.fems.message.wg.service.impl.WGIndividualLCYServiceImpl;

/**
 * 行内报文与外管报文门面管理类
 * 
 * @author chen.feng
 * 
 */
@Component("individualMsgBizManager")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class IndividualMsgBizManager {
	private static final Logger log = Logger.getLogger(IndividualMsgBizManager.class);

	private WGIndividualLCYService wgIndividualLCYService = null;
	@Autowired
	private SysCommonService sysCommonService;
	@Autowired
	private TaskTimeoutDao taskTimeoutDao;
	@Autowired
	private SafeExRateService safeExRateService;

	public IndividualMsgBizManager() {
		wgIndividualLCYService = new WGIndividualLCYServiceImpl();
	}

	/**
	 * 美元折算率查询
	 * 
	 * @param currencyCode
	 *            币种
	 * @param yearMonth
	 *            年月
	 * @param page
	 *            分页对象
	 * @param reqSeqNo
	 *            请求流水号
	 * @param mainOrgNo
	 *            核心机构号
	 * @param operNo
	 *            核心柜员号
	 * @return
	 * @throws DataCheckException
	 */
	public Map doQuerySafeExRate(String currencyCode, String yearMonth,
			PageBean page, String operNo)
			throws DataCheckException {
		if (page == null) {
			page = new PageBean();
			page.setRows(1000);
			page.setPage(1);
		}
		
		if (StringUtils.isEmpty(currencyCode) && StringUtils.isEmpty(yearMonth)) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450017.getCode(), "币种或年月不能全部为空");
		}

		// 组装请求报文数据
		Map<String, Object> req = new HashMap<String, Object>();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("CURRENCY_CODE", currencyCode);
		parameters.put("YEAR_MONTH", yearMonth);
		req.put("PAGESIZE", String.valueOf(page.getRows()));//页条数
		req.put("PAGENUMBER", String.valueOf(page.getPage()));//页码
		req.put("PARAMETERS", parameters);

		// 调用外管接口
		WGReqMsgHead head = generateWGReqMsgHead(operNo, null);
		Map ret = wgIndividualLCYService.doQuerySafeExRate(req, head);

		Map dsMap = getRespResultToDSMap(ret);
//		String totalCount = (String) dsMap.get("RECORDCOUNT");
//		if ("0".equals(totalCount.trim())) {
//			//表示未取到当月美元折算率
//			String preYearMonth = SysUtils.getLastDayOfPreMonth(SysUtils.getStrToDate(yearMonth, "yyyyMM"), "yyyyMM");
//			log.info("未获取到["+yearMonth+"]月的美元折算率，再次获取上一月["+preYearMonth+"]的美元折算率");
//			dsMap = doQuerySafeExRate(currencyCode, preYearMonth, page, operNo);
//		}
		
		dsMap.put("SvrSeqNo", head.getMSGNO());// 查询类的接口因服务端无交易流水号，直接沿用送外管的流水号
		return dsMap;
	}

	/**
	 * 个人结售汇额度查询
	 * 
	 * @param idTypeCode
	 *            证件类型
	 * @param idCode
	 *            证件号码
	 * @param ctyCode
	 *            国家地区代码
	 * @param tradeType
	 *            交易类型
	 * @param mainOrgNo
	 *            核心机构号
	 * @param operNo
	 *            核心柜员号
	 * @return
	 * @throws DataCheckException
	 */
	public Map doQueryIndividualFXSEAQuota(String idTypeCode, String idCode,
			String ctyCode, String tradeType, String operNo)
			throws DataCheckException {

		// 组装请求报文数据
		Map data = new HashMap();
		Map param = new HashMap();
		param.put("IDTYPE_CODE", idTypeCode);
		param.put("CTYCODE", ctyCode);
		param.put("IDCODE", idCode);
		data.put("PARAMETERS", param);

		// 调用外管接口
		Map ret = new HashMap();
		WGReqMsgHead head = generateWGReqMsgHead(operNo, null);
		if ("JH".equals(tradeType)) {
			ret = wgIndividualLCYService.doQueryIndividualFXSEQuota(data, head);
		} else if ("GH".equals(tradeType)) {
			ret = wgIndividualLCYService.doQueryIndividualFXSAQuota(data, head);
		}

		// 处理响应结果
		Map dsMap = getRespResultToDSMap(ret);
		dsMap.put("SvrSeqNo", head.getMSGNO());// 查询类的接口因服务端无交易流水号，直接沿用送外管的流水号
		return dsMap;
	}
	
	/**
	 * 个人结售汇信息查询
	 * 
	 * @param tradeType 交易类型
	 * @param REFNO 业务参号
	 * @param IDTYPE_CODE 证件类型代码
	 * @param IDCODE 证件号码
	 * @param CTYCODE 国家地区代码
	 * @param BIZ_TX_TIME 业务办理时间
	 * @param mainOrgNo 核心机构号
	 * @param operNo 核心柜员号
	 * @param page 分页对象
	 * @return
	 * @throws DataCheckException
	 */
	public Map doQueryIndividualFXSEAInfo(String tradeType, String REFNO,
			String IDTYPE_CODE, String IDCODE, String CTYCODE,
			String BIZ_TX_TIME, String operNo, PageBean page) throws DataCheckException {
		//组装请求报文数据
		Map<String, Object> req = new HashMap<String, Object>();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("REFNO", REFNO);
		parameters.put("IDTYPE_CODE", IDTYPE_CODE);
		parameters.put("IDCODE", IDCODE);
		parameters.put("CTYCODE", CTYCODE);
		parameters.put("BIZ_TX_TIME", BIZ_TX_TIME);
		
		if (page != null) {
			req.put("PAGESIZE", String.valueOf(page.getRows()));//页条数
			req.put("PAGENUMBER", String.valueOf(page.getPage()));//页码
		}
		req.put("PARAMETERS", parameters);
		
		return invokeQueryIndividualFXSEAInfo(tradeType, operNo, req);
	}
	
	/**
	 * 个人结售汇详细信息查询
	 * 
	 * @param tradeType 交易类型
	 * @param REFNO 业务参号
	 * @param mainOrgNo 核心机构号
	 * @param operNo 核心柜员号
	 * @return
	 * @throws DataCheckException
	 */
	public Map doQueryIndividualFXSEAInfo(String tradeType, String REFNO, String mainOrgNo, String operNo) throws DataCheckException {
		//组装请求报文数据
		Map<String, Object> req = new HashMap<String, Object>();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("REFNO", REFNO);
		
		req.put("PARAMETERS", parameters);
		req.put("PAGESIZE", String.valueOf(1));//页条数
		req.put("PAGENUMBER", String.valueOf(1));//页码
		
		// 调用外管接口
		return invokeQueryIndividualFXSEAInfo(tradeType, operNo, req);
	}

	/**
	 * 个人结售汇信息查询接口内部逻辑处理方法
	 * 
	 * @param tradeType
	 * @param mainOrgNo
	 * @param operNo
	 * @param req
	 * @return
	 * @throws DataCheckException
	 */
	private Map invokeQueryIndividualFXSEAInfo(String tradeType,
			String operNo, Map<String, Object> req)
			throws DataCheckException {
		Map ret = new HashMap();
		
		//调用接口
		WGReqMsgHead head = generateWGReqMsgHead(operNo, null);
		if (StringUtils.equals(tradeType, "JH")) {
			ret = wgIndividualLCYService.doQueryIndividualFXSEInfo(req, head);
		} else {
			ret = wgIndividualLCYService.doQueryIndividualFXSAInfo(req, head);
		}
		
		// 处理响应结果
		Map dsMap = getRespResultToDSMap(ret);
		dsMap.put("SvrSeqNo", head.getMSGNO());// 查询类的接口因服务端无交易流水号，直接沿用送外管的流水号
		return dsMap;
	}

	/**
	 * 个人结售汇信息录入
	 * 
	 * @param ds 额度登记请求对象
	 * @param bizChnlCode 外管业务办理渠道代码
	 * @param txnSerialNo 交易流水号
	 * @param mainOrgNo 核心机构号
	 * @param operNo 核心柜员号
	 * @return
	 * @throws DataCheckException
	 */
	public RspIndividualLCYRegister doAddIndividualFXSEAQuota(RecvIndividualLCYRegister ds, String bizChnlCode, String txnSerialNo, String operNo) throws DataCheckException {
		// 转换成个人结售汇额度登记MAP
		if (bizChnlCode.equals(BizTypeEnum.TYPE_05.getCode()) && "GH".equals(ds.getTRADE_TYPE())) {
			ds.setFCY_REMIT_AMT(ds.getTXAMT());//通过支付机构渠道时，汇出资金等于售汇交易金额
		}
		Map quotaAddMap = IndividualLCYDataConver.toRecvIndividualLCYRegister(ds,
				txnSerialNo, bizChnlCode);

		// 查询个人结售汇额度
		Map quotaQueryDSMap = doQueryIndividualFXSEAQuota(ds.getIDTYPE_CODE(), ds.getIDCODE(),
				ds.getCTYCODE(), ds.getTRADE_TYPE(), operNo);
		log.info("个人结售汇信息录入交易流水：[" + txnSerialNo + "]\r\n个人结售汇额度和分类查询结果：" + quotaQueryDSMap);
		
		// 校验额度登记报文内容
		IndMessageValidator.validateIndividualLCYRegister(quotaAddMap, ds, quotaQueryDSMap);
		
		//额度登记响应结果
		Map retMap = new HashMap();
		RspIndividualLCYRegister rsp = new RspIndividualLCYRegister();
		WGReqMsgHead head = generateWGReqMsgHead(operNo, txnSerialNo);
		try {
			if("Y".equals(ds.getOCCUPY_LMT_STATUS())){//占额度
				List quotaRowSet = (List) quotaQueryDSMap.get("ROWSET");
				Map quotaMap = (Map) quotaRowSet.get(0);
				if ("JH".equals(ds.getTRADE_TYPE())) {
					String ANN_REM_LCYAMT_USD = (String) quotaMap.get("ANN_REM_LCYAMT_USD");//本年额度内剩余可结汇金额折美元
					if (DateTimeUtil.subtract(String.valueOf(this.safeExRateService.transTxAmtToUsdAmt(ds.getTXAMT(), ds.getTXCCY())), ANN_REM_LCYAMT_USD) <= 0) {
						//当前交易金额必须小于等于本年额度内剩余可结汇金额折美元
						doAddLimitedDeal(ds, operNo, quotaQueryDSMap, bizChnlCode);
						//调用占用额度的个人结汇信息录入
						retMap = wgIndividualLCYService.doAddLimitedIndividualFXSEInfo(quotaAddMap, head);
					} else {
						throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450002.getCode(),"本年额度内剩余可结汇金额折美元为“"+ANN_REM_LCYAMT_USD+"”");
					}
				} else if ("GH".equals(ds.getTRADE_TYPE())) {
					String ANN_REM_FCYAMT_USD = (String) quotaMap.get("ANN_REM_FCYAMT_USD");//本年额度内剩余可购汇金额折美元
					if (DateTimeUtil.subtract(String.valueOf(this.safeExRateService.transTxAmtToUsdAmt(ds.getTXAMT(), ds.getTXCCY())), ANN_REM_FCYAMT_USD) <= 0) {
						//当前交易金额必须小于等于本年额度内剩余可购汇金额折美元
						doAddLimitedDeal(ds, operNo, quotaQueryDSMap, bizChnlCode);
						//调用占用额度的个人结汇信息录入
						retMap = wgIndividualLCYService.doAddLimitedIndividualFXSAInfo(quotaAddMap, head);
					} else {
						throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450007.getCode(),"本年额度内剩余可购汇金额折美元为“"+ANN_REM_FCYAMT_USD+"”");
					}
				} 
			}else {
				//不占额度
				doAddLimitedDeal(ds, operNo, quotaQueryDSMap, bizChnlCode);
				if ("JH".equals(ds.getTRADE_TYPE())) {
					retMap = wgIndividualLCYService.doAddUnLimitedIndividualFXSEInfo(quotaAddMap, head);
				} else if ("GH".equals(ds.getTRADE_TYPE())) {
					retMap = wgIndividualLCYService.doAddUnLimitedIndividualFXSAInfo(quotaAddMap, head);
				}
			}
			
			// 处理响应结果
			Map dsMap = getRespResultToDSMap(retMap);
			
			//取回执报文BODY中的CODE
			if(!"Y".equals(ds.getOCCUPY_LMT_STATUS())){
				getRecodeByBody(dsMap);
			}
			
			//将响应结果MAP转换为响应实体对象
			rsp = IndividualLCYDataConver.toRspIndividualLCYRegister(dsMap,ds.getTRADE_TYPE(),ds.getOCCUPY_LMT_STATUS());
			rsp.setSEQNO(txnSerialNo);//前置系统业务流水号
		} catch (DataCheckException e) {
			//可能连接外管超时的情况
			log.error("个人结售汇信息录入操作失败", e);
			if (MsgErrorCodeEnum.ERRCODE_400003.getCode().equals(e.getCode())) {
				try {
					TaskTimeout info = new TaskTimeout();
					info.setTxnSerialNo(txnSerialNo);
					info.setBizType(ds.getTRADE_TYPE());
					info.setIdTypeCode((String) quotaAddMap.get("IDTYPE_CODE"));
					info.setIdCode((String) quotaAddMap.get("IDCODE"));
					info.setCtyCode((String) quotaAddMap.get("CTYCODE"));
					info.setTxDate(DateTimeUtil.getCurrentDateTime());
					info.setDealState("0");
					info.setLaunchOperNo(operNo);
					info.setRetCode(MsgErrorCodeEnum.ERRCODE_400003.getCode());
					info.setRetMsg(MsgErrorCodeEnum.ERRCODE_400003.getValue()+":连接外汇管局业务系统超时，未收到回执报文");
					taskTimeoutDao.insert(info);
				} catch (Exception e1) {
					log.error("增加个人结售汇信息录入调用接口超时记录失败：", e1);
				}
			}
			throw e;
		} 
		return rsp;
	}

	/**
	 * 个人结售汇额度登记时的内部业务逻辑处理
	 * 
	 * @param ds 个人结售汇额度登记对象
	 * @param mainOrgNo 核心机构号
	 * @param operNo 核心柜员 号
	 * @param quotaQueryDSMap 额度查询结果
	 * @throws DataCheckException
	 */
	private void doAddLimitedDeal(RecvIndividualLCYRegister ds, String operNo, Map quotaQueryDSMap, String bizChnlCode)
			throws DataCheckException {
		List rowsets = (List) quotaQueryDSMap.get("ROWSET");
		Map rowsetMap = (Map) rowsets.get(0);
		String TYPE_STATUS = (String) rowsetMap.get("TYPE_STATUS");// 个人分类状态，01正常02预关注03关注名单
		if ("02".equals(TYPE_STATUS) || "03".equals(TYPE_STATUS)) {
			String SIGN_STATUS = (String) rowsetMap.get("SIGN_STATUS");//风险提示函/告知书告知状态 // 0未告知1已告知
			if ("0".equals(SIGN_STATUS)) {
				if ("Y".equals(ds.getSIGNSTATUS())) {// 客户是否阅读风险提示书/告知书 Y:是,N:否
					// 调用预关注风险提示/关注名单告知接口
					doMakeUpSignStatus(ds.getIDTYPE_CODE(), ds.getIDCODE(),
							ds.getCTYCODE(), ds.getPERSON_NAME(),
							bizChnlCode, operNo);
				} else {
					throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450001.getCode(), "预关注风险提示函或关注名单告知书未告知个人，不能提交!");
				}
			}
		}
	}
	
	/**
	 * 个人结售汇信息修改
	 * 
	 * @param ds 结售汇信息修改请求对象
	 * @param txnSerialNo 修改交易流水号
	 * @param refNo 业务参号
	 * @param bizChnlCode 业务办理渠道代码
	 * @param mainOrgNo 核心机构号
	 * @param operNo 核心柜员号
	 * @return
	 * @throws DataCheckException
	 */
	public RspIndividualLCYModify doModifyIndividualFXSEAInfo(RecvIndividualLCYModify ds, String txnSerialNo, String refNo, String operNo) throws DataCheckException {
		Map data = IndividualLCYDataConver.toRecvIndividualLCYModifyMap(ds, txnSerialNo, refNo);
		
		// 报文内容校验
		IndMessageValidator.validateIndividualLCYModify(data, ds);
		
		//调用接口
		//结售汇信息修改报文响应结果
		Map retMap = new HashMap();
		WGReqMsgHead head = generateWGReqMsgHead(operNo, txnSerialNo);
		
		if ("JH".equals(ds.getTRADE_TYPE())) {
			retMap = wgIndividualLCYService.doModifyIndividualFXSEInfo(data, head);
		}else if ("GH".equals(ds.getTRADE_TYPE())) {
			retMap = wgIndividualLCYService.doModifyIndividualFXSAInfo(data, head);
		}
		
		// 处理响应结果
	    Map dsMap = getRespResultToDSMap(retMap);
	    
	    //取回执报文BODY中的CODE
		getRecodeByBody(dsMap);
		
		RspIndividualLCYModify rsp = new RspIndividualLCYModify();
		rsp.setSEQNO(ds.getSEQNO());//请求要修改的交易流水号
		rsp.setSVRSEQNO(txnSerialNo);//请求修改后处理的修改交易流水号
		rsp.setREFNO(refNo);
		
		return rsp;
	}
	
	/**
	 * 个人结售汇信息撤消 
	 * 
	 * @param ds 结售汇信息撤消请求对象
	 * @param txnSerialNo 撤消交易流水号
	 * @param refNo 业务参号
	 * @param mainOrgNo 核心机构号
	 * @param operNo 核心柜员号
	 * @return
	 * @throws DataCheckException
	 */
	public RspIndividualLCYCancel doDeleteIndividualFXSEAInfo(RecvIndividualLCYCancel ds, String txnSerialNo, String refNo, String operNo) throws DataCheckException {
		Map data = IndividualLCYDataConver.toRecvIndividualLCYCancelMap(ds, txnSerialNo, refNo);
		
		// 报文内容校验
		IndMessageValidator.validateIndividualLCYCancel(data);
		
		//调用接口
		//结售汇信息撤消报文响应结果
		Map retMap = new HashMap();
		WGReqMsgHead head = generateWGReqMsgHead(operNo, txnSerialNo);

		if ("JH".equals(ds.getTRADE_TYPE())) {
			//结汇,调用外管6.6个人结汇信息撤消接口
			retMap = wgIndividualLCYService.doDeleteIndividualFXSEInfo(data, head);
		}else if ("GH".equals(ds.getTRADE_TYPE())) {
			// 购汇 调用外管6.14个人购汇 信息撤消接口
			retMap = wgIndividualLCYService.doDeleteIndividualFXSAInfo(data, head);
		}

		// 处理响应结果
		getRespResultToDSMap(retMap);

		RspIndividualLCYCancel rsp = new RspIndividualLCYCancel();
		rsp.setSEQNO(ds.getSEQNO());//请求要修改的交易流水号
		rsp.setSVRSEQNO(txnSerialNo);//请求修改后处理的修改交易流水号
		rsp.setREFNO(refNo);

		return rsp;
	}
	
	/**
	 * 个人结售汇信息补录
	 * 
	 * @param ds 结售汇信息补录对象
	 * @param txnSerialNo 补录交易流水号
	 * @param bizChnlCode 业务办理渠道码
	 * @param mainOrgNo 核心机构号
	 * @param operNo 核心柜员号
	 * @return
	 * @throws DataCheckException
	 */
	public Map doMakeUpOtherIndividualFXSEAInfo(RecvIndividualLCYMakeUp ds, String txnSerialNo, String bizChnlCode, String operNo) throws DataCheckException {
		if (bizChnlCode.equals(BizTypeEnum.TYPE_05.getCode()) && "GH".equals(ds.getTRADE_TYPE())) {
			ds.setFCY_REMIT_AMT(ds.getTXAMT());//通过支付机构渠道时，汇出资金等于售汇交易金额
		}
		
		Map data = IndividualLCYDataConver.toRecvIndividualLCYMakeUp(ds, txnSerialNo, bizChnlCode);

		// 查询个人结售汇额度
		Map quotaQueryDSMap = doQueryIndividualFXSEAQuota(ds.getIDTYPE_CODE(), ds.getIDCODE(),
				ds.getCTYCODE(), ds.getTRADE_TYPE(), operNo);
		log.info("个人结售汇信息补录交易流水：[" + txnSerialNo + "]\r\n个人结售汇额度和分类查询结果：" + quotaQueryDSMap);

		// 校验报文内容
		IndMessageValidator.validateIndividualLCYMakeUp(data, ds, quotaQueryDSMap);
				
		//响应结果
		Map retMap = new HashMap();
		WGReqMsgHead head = generateWGReqMsgHead(operNo, txnSerialNo);
		RecvIndividualLCYRegister regDS = new RecvIndividualLCYRegister();//个人结售汇信息录入对象
		//将补录信息COPY到录入对象中，因为补录操作的校验沿用录入
		try {
			BeanUtils.copyProperties(regDS, ds);
		} catch (Exception e1) {
			log.error("个人结售汇补录对象转换为个人结售汇录入对象出错", e1);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"个人结售汇补录对象转换为个人结售汇录入对象出错");
		}
		try {
			if("Y".equals(ds.getOCCUPY_LMT_STATUS())){//占额度
//				doAddLimitedDeal(regDS, operNo, quotaQueryDSMap, bizChnlCode);
				if ("JH".equals(ds.getTRADE_TYPE())) {
					//山东农信提出与外管确认补录不需要校验是否超额
					retMap = wgIndividualLCYService.doMakeUpOtherIndividualFXSEInfo(data, head);
				} else if ("GH".equals(ds.getTRADE_TYPE())) {
					//山东农信提出与外管确认补录不需要校验是否超额
					retMap = wgIndividualLCYService.doMakeUpOtherIndividualFXSAInfo(data, head);
				} 
			}else {
				//不占额度
				if ("JH".equals(ds.getTRADE_TYPE())) {
					retMap = wgIndividualLCYService.doMakeUpIndividualTradeFXSEInfo(data, head);
				} else if ("GH".equals(ds.getTRADE_TYPE())) {
					retMap = wgIndividualLCYService.doMakeUpIndividualTradeFXSAInfo(data, head);
				}
			}

			// 处理响应结果
			Map dsMap = getRespResultToDSMap(retMap);

			//取回执报文BODY中的CODE
			getRecodeByBody(dsMap);
			return dsMap;
		} catch (DataCheckException e) {
			//可能连接外管超时的情况
			log.error("个人结售汇信息补录操作失败", e);
			throw e;
		} 
	}

	/**
	 * 取回执报文BODY中的CODE
	 * @param dsMap
	 * @throws DataCheckException
	 */
	private void getRecodeByBody(Map dsMap) throws DataCheckException {
		List rowset = (List) dsMap.get("ROWSET");
		Map row = (Map) rowset.get(0);
		String recode = (String) row.get("CODE");
		String remsg = (String) row.get("DETAIL");
		if (!MsgErrorCodeEnum.ERRCODE_00000.getCode().equals(recode)) {
			throw new DataCheckException(recode, remsg);
		}
	}
	
	/**
	 * 调用重点关注对象签署接口
	 * 
	 * @param IDTYPE_CODE
	 *            证件类型代码
	 * @param IDCODE
	 *            证件代码
	 * @param CTYCODE
	 *            国家地区代码
	 * @param PERSON_NAME
	 *            姓名
	 * @param BIZ_TX_CHNL_CODE
	 *            业务办理渠道代码
	 * @param mainOrgNo
	 *            核心机构号
	 * @param operNo
	 *            核心柜员号
	 * @throws DataCheckException
	 */
	public boolean doMakeUpSignStatus(String IDTYPE_CODE, String IDCODE,
			String CTYCODE, String PERSON_NAME, String BIZ_TX_CHNL_CODE, String operNo) throws DataCheckException {
		//组装请求报文数据
		Map data = new HashMap();
		Map param = new HashMap();
		param.put("IDTYPE_CODE", IDTYPE_CODE);
		param.put("CTYCODE", CTYCODE);
		param.put("IDCODE", IDCODE);
		param.put("PERSON_NAME", PERSON_NAME);
		param.put("BIZ_TX_CHNL_CODE", BIZ_TX_CHNL_CODE);
		data.put("PARAMETERS", param);
		
		//调用接口
		WGReqMsgHead head = generateWGReqMsgHead(operNo, null);
		Map ret = wgIndividualLCYService.doMakeUpSignStatus(data, head);
		
		//处理响应结果
		Map retHead = IndividualLCYDataConver.getRetHead(ret);
		String code = (String) retHead.get("CODE");
		String detail = (String) retHead.get("DETAIL");
		if (!MsgErrorCodeEnum.ERRCODE_00000.getCode().equals(code)
				&& !MsgErrorCodeEnum.ERRCODE_450071.getCode().equals(code)) {
			throw new DataCheckException(code, detail);
		}
		return true;
	}
	
	/**
	 * 获取接口响应结果
	 * 
	 * @param ret
	 * @return
	 * @throws DataCheckException
	 */
	private Map getRespResultToDSMap(Map ret) throws DataCheckException {
		if (StringUtil.isNullOrEmpty(ret)) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400003.getCode(), "调用外管接口未取到返回结果");
		}
		Map retHead = IndividualLCYDataConver.getRetHead(ret);
		String code = (String) retHead.get("CODE");
		String detail = (String) retHead.get("DETAIL");
		if (!MsgErrorCodeEnum.ERRCODE_00000.getCode().equals(code)) {
			throw new DataCheckException(code, detail);
		}
		Map ds = null;
		try {
			ds = IndividualLCYDataConver.getDSRetMap(ret);
		} catch (Exception e) {
			//处理响应报文只有HEAD的情况
			ds = ret;
		}
		return ds;
	}

	/**
	 * 通过数据库配置生成外管的请求公共报文头
	 * 
	 * @param operNo
	 * @param txnSerialNo
	 * @return
	 */
	private WGReqMsgHead generateWGReqMsgHead(String operNo, String txnSerialNo)
			throws DataCheckException {
		// 获取登录外管系统的柜员号和密码等信息
		WGReqMsgHead head = new WGReqMsgHead();
		try {
			CommonOrgUser orgUser = sysCommonService.getCommonOrgUser(operNo);
			if (orgUser == null) {
				throw new DataCheckException(
						MsgErrorCodeEnum.ERRCODE_999999.getCode(), "未获取到柜员[" + operNo + "]对应的外管登录用户名");
			}
			head.setCOMMON_ORG_CODE(orgUser.getCOMMON_ORG_CODE());
			head.setCOMMON_USER_CODE(orgUser.getCOMMON_USER_CODE());
			head.setPASSWORD(orgUser.getPASSWORD());

			String args[] = { "VER", "COMMON_ORG_TYPE", "SRC", "DES", "SRCAPP", "DESAPP" };
			List<Property> list = sysCommonService.getPropertyList(args);
			if (list != null && list.size() > 0) {
				for (Property property : list) {
					if ("VER".equals(property.getKey())) {
						head.setVER(property.getValue());
					} else if ("COMMON_ORG_TYPE".equals(property.getKey())) {
						head.setCOMMON_ORG_TYPE(property.getValue());
					} else if ("SRC".equals(property.getKey())) {
						head.setSRC(property.getValue());
					} else if ("DES".equals(property.getKey())) {
						head.setDES(property.getValue());
					} else if ("SRCAPP".equals(property.getKey())) {
						head.setSRCAPP(property.getValue());
					} else if ("DESAPP".equals(property.getKey())) {
						head.setDESAPP(property.getValue());
					}
				}
			}

			String reqSeqNo = "";
			if (StringUtils.isNotBlank(txnSerialNo)) {
				reqSeqNo = txnSerialNo;
			} else {
				try {
					reqSeqNo = this.sysCommonService.getSeqNoByOperNo("wfl_taskinfo", operNo);
				} catch (BaseException e1) {
					log.error("生成报文请求流水号出错", e1);
					throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "生成报文请求流水号出错");
				}
			}
			head.setMSGNO(reqSeqNo);// 银行：金融机构代码（4位）+年月日+8位唯一代码
		} catch (BaseException e) {
			throw new DataCheckException(
					MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
		}
		return head;
	}

	public boolean doUpdateCommonUser(CommonOrgUser user, String createFlag) throws DataCheckException {
		try {
			this.sysCommonService.saveOrUpdateCommonUser(user);
		} catch (BaseException e) {
			log.error("更新用户信息出错", e);
			throw new DataCheckException(
					MsgErrorCodeEnum.ERRCODE_999999.getCode(), "更新用户信息出错");
		}
		return true;
	}

}
