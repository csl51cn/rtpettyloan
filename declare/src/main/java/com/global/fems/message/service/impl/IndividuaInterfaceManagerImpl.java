package com.global.fems.message.service.impl;

import com.global.fems.business.domain.SpfeCsr;
import com.global.fems.business.domain.SpfeLmt;
import com.global.fems.business.domain.SpfeMdf;
import com.global.fems.business.domain.SpfeMkUp;
import com.global.fems.business.enums.TradeTypeEnum;
import com.global.fems.business.service.SafeExRateService;
import com.global.fems.business.service.SpfeMdfService;
import com.global.fems.business.service.SpfeMkUpService;
import com.global.fems.message.domain.MsgHead;
import com.global.fems.message.domain.business.receive.*;
import com.global.fems.message.domain.business.response.*;
import com.global.fems.message.service.IndividuaInterfaceManager;
import com.global.fems.message.support.IndMessageValidator;
import com.global.fems.message.support.IndividualLCYDataConver;
import com.global.fems.message.util.DateTimeUtil;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.CommonOrgUser;
import com.global.framework.system.domain.Org;
import com.global.framework.system.domain.User;
import com.global.framework.system.service.UserService;
import com.global.framework.system.web.common.CacheService;
import com.global.framework.util.SysUtils;
import com.global.param.domain.Channel;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TransStateEnum;
import com.global.workflow.service.TasklistService;
import com.pactera.fems.message.wg.constants.BizTypeEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 行内接口门面服务实现类
 * @author chen.feng
 *
 */
@SuppressWarnings({ "rawtypes"})
@Component("individuaInterfaceManager")
public class IndividuaInterfaceManagerImpl implements IndividuaInterfaceManager {

	private static final Logger log = Logger.getLogger(IndividuaInterfaceManagerImpl.class);
	@Autowired
	private IndividualMsgBizManager individualMsgBizManager;
	//@Autowired
	//private SpfeLmtService spfeLmtService;
	@Autowired
	private SpfeMdfService spfeMdfService;
//	@Autowired
//	private SpfeCsrService spfeCsrService;
	@Autowired
	private SpfeMkUpService SpfeMkUpService;
	@Autowired
	private TasklistService tasklistService;
	@Autowired
	private UserService userService;
	@Autowired
	private SafeExRateService safeExRateService;

	/**
	 * 外围系统查询折算率改为查询前置系统导入的折算率
	 */
	public Object[] doSafeExRateQuery(RecvSafeExRateQuery ds, MsgHead head, Channel channel) throws DataCheckException {
		PageBean pageBean = setPageBean(ds.getPAGESIZE(), ds.getPAGENUMBER());
//		Map retDS = individualMsgBizManager.doQuerySafeExRate(ds.getCURRENCY_CODE(),
//				ds.getYEAR_MONTH(), pageBean, head.getOperNo());
//		// 处理响应结果
//		RspSafeExRateQuery rsp = IndividualLCYDataConver.toRspSafeExRateQuery(retDS);
		RspSafeExRateQuery rsp = new RspSafeExRateQuery();
		try {
			PageBean page = safeExRateService.queryForPage(ds.getCURRENCY_CODE(), ds.getYEAR_MONTH(), pageBean);
			rsp = IndividualLCYDataConver.toRspSafeExRateQuery(page);
		} catch (BaseException e) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_899080.getCode(), "["+ds.getYEAR_MONTH()+"]月美元折算率未发布");
		}
//		return new Object[] { rsp, (String) retDS.get("SvrSeqNo") };
		return new Object[] { rsp, SysUtils.getSeqNo()};
	}

	public Object[] doIndividualLCYQuery(RecvIndividualLCYQuery ds, MsgHead head, Channel channel) throws DataCheckException {
		Map retDS = individualMsgBizManager.doQueryIndividualFXSEAQuota(ds.getIDTYPE_CODE(),
				ds.getIDCODE(), ds.getCTYCODE(), ds.getTRADE_TYPE(), head.getOperNo());
		// 处理响应结果
		RspIndividualLCYQuery rsp = IndividualLCYDataConver
				.toRspIndividualLCYQuery(retDS, ds.getTRADE_TYPE());
		return new Object[] { rsp, (String) retDS.get("SvrSeqNo") };
	}

	public Object[] doIndividualLCYRegister(RecvIndividualLCYRegister ds, MsgHead head, Channel channel) throws DataCheckException {
		//先检查重复流水
		tasklistService.checkTaskExistByReqSeqNo(head.getReqSeqNo());
		
		Org org = CacheService.getOrgByCode(head.getOrgNo());
		if (org == null) {
			log.error("核心机构号["+head.getOrgNo()+"]未维护");
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "核心机构号["+head.getOrgNo()+"]未维护");
		}
		//保存个人结售汇信息登记接口信息
		String txnSerialNo = "";//交易流水号
		SpfeLmt mode = new SpfeLmt();
		User user = null;
		try {
			user = this.userService.findUserByUserCode(head.getOperNo());
		} catch (BaseException e1) {
			log.error("柜员号["+head.getOperNo()+"]未维护", e1);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"柜员号["+head.getOperNo()+"]未维护");
		}
		if (user == null) {
			log.error("柜员号["+head.getOperNo()+"]未维护");
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"柜员号["+head.getOperNo()+"]未维护");
		}
		try {
			//将报文实体转换为数据库存储实体
			BeanUtils.copyProperties(mode, ds);
			mode.setBIZ_TX_CHNL_CODE(channel.getBizChnlCode());
			mode.setChannelId(channel.getChannelId());//存储任务表
			if (channel.getBizChnlCode().equals(BizTypeEnum.TYPE_05.getCode()) && "GH".equals(ds.getTRADE_TYPE())) {
				ds.setFCY_REMIT_AMT(ds.getTXAMT());//通过支付机构渠道时，汇出资金等于售汇交易金额
				mode.setFCY_REMIT_AMT(ds.getTXAMT());
			}
			//mode = spfeLmtService.doHandle(mode, user, true, head.getReqSeqNo());
			txnSerialNo = mode.getSEQNO();
		} catch (Exception e) {
			log.error("个人结售汇信息录入数据库操作失败:请求方流水号为[" + head.getReqSeqNo() + "]", e);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_150003.getCode(),"个人结售汇信息录入数据库操作失败:请求方流水号为[" + head.getReqSeqNo() + "]");
		}
		
		//调用接口
		RspIndividualLCYRegister rsp = new RspIndividualLCYRegister();
		String transState = TransStateEnum.COMPLETE.getCode();//结束
		String recode = MsgErrorCodeEnum.ERRCODE_00000.getCode();
		String remsg = MsgErrorCodeEnum.ERRCODE_00000.getValue();
		try {
			rsp = individualMsgBizManager.doAddIndividualFXSEAQuota(ds, channel.getBizChnlCode(), txnSerialNo, head.getOperNo());
		} catch (DataCheckException e) {
			transState = TransStateEnum.FALIED.getCode();//失败
			recode = e.getCode();
			remsg = e.getReason();
			throw e;
		} finally {
			//更新个人结售汇信息登记接口信息
			//spfeLmtService.doFinish(txnSerialNo, rsp, transState, recode, remsg, user);
		}
		return new Object[]{rsp, txnSerialNo };
	}


	public Object[] doIndividualLCYModify(RecvIndividualLCYModify ds, MsgHead head, Channel channel) throws DataCheckException {
		// 查询上一次的登记或修改信息的业务参号
		Map<String, Object> map = spfeMdfService.findRefNoBySeqNo(ds.getSEQNO());
		String refNo = (String) map.get("REFNO");
		String primaryBizNo = (String) map.get("BIZNO");
		String tradeNo = (String) map.get("TRADENO");
		String transOrgNo = (String) map.get("TRANSORGNO");
		
		if (StringUtils.isBlank(refNo)){
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450013.getCode(), "个人结售汇信息修改失败:该笔业务流水号["+ds.getSEQNO()+"]未查到相关业务参号");
		}
		
		Org org = CacheService.getOrgByCode(head.getOrgNo());
		if (org == null) {
			log.error("核心机构号["+head.getOrgNo()+"]未维护");
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"核心机构号["+head.getOrgNo()+"]未维护");
		}
		
		if (!transOrgNo.equals(org.getOrgId())) {
			log.error("该机构["+head.getOrgNo()+"]不能对["+transOrgNo+"]机构的业务[业务参号:"+refNo+"]进行修改操作");
			throw new DataCheckException("JH".equals(ds.getTRADE_TYPE()) ? MsgErrorCodeEnum.ERRCODE_450068.getCode() : MsgErrorCodeEnum.ERRCODE_450064.getCode(),"该机构["+head.getOrgNo()+"]不能对["+transOrgNo+"]机构的业务[业务参号:"+refNo+"]进行修改操作");
		}
		
		//保存记录
		String txnSerialNo = "";
		SpfeMdf mode = new SpfeMdf();
		User user = null;
		try {
			user = this.userService.findUserByUserCode(head.getOperNo());
		} catch (BaseException e1) {
			log.error("柜员号["+head.getOperNo()+"]未维护", e1);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"柜员号["+head.getOperNo()+"]未维护");
		}
		if (user == null) {
			log.error("柜员号["+head.getOperNo()+"]未维护");
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"柜员号["+head.getOperNo()+"]未维护");
		}
		try {
			BeanUtils.copyProperties(mode, ds);
			mode.setBIZ_TX_CHNL_CODE(channel.getBizChnlCode());
			mode.setChannelId(channel.getChannelId());
			mode.setPrimaryBizNo(primaryBizNo);
			mode.setTradeNo(tradeNo);
			mode.setREFNO(refNo);
			mode.setSEQNO("");
			mode = spfeMdfService.doHandle(mode, user, true, head.getReqSeqNo());
			txnSerialNo = mode.getSEQNO();
		} catch (Exception e) {
			log.error("个人结售汇信息修改数据库操作失败:请求方流水号为[" + head.getReqSeqNo() + "]", e);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_150003.getCode(),"个人结售汇信息修改数据库操作失败:请求方流水号为[" + head.getReqSeqNo() + "]");
		}
		
		//调用接口
		RspIndividualLCYModify rsp = new RspIndividualLCYModify();
		String transState = TransStateEnum.COMPLETE.getCode();//结束
		String recode = MsgErrorCodeEnum.ERRCODE_00000.getCode();
		String remsg = MsgErrorCodeEnum.ERRCODE_00000.getValue();
		
		try {
			rsp = individualMsgBizManager.doModifyIndividualFXSEAInfo(ds, txnSerialNo, refNo, head.getOperNo());
		} catch (DataCheckException e) {
			transState = TransStateEnum.FALIED.getCode();//失败
			recode = e.getCode();
			remsg = e.getReason();
			throw e;
		} finally {
			//更新个人结售汇修改接口信息
			spfeMdfService.doFinish(mode, rsp, transState, recode, remsg, user);
		}
		return new Object[]{rsp, txnSerialNo };
	}

	public Object[] doIndividualLCYCancel(RecvIndividualLCYCancel ds, MsgHead head, Channel channel) throws DataCheckException {
		// 查询上一次的登记或修改信息的业务参号
		Map<String, Object> map = spfeMdfService.findRefNoBySeqNo(ds.getSEQNO());
		String refNo = (String) map.get("REFNO");
		String primaryBizNo = (String) map.get("BIZNO");
		String tradeNo = (String) map.get("TRADENO");
		String transOrgNo = (String) map.get("TRANSORGNO");

		if (StringUtils.isBlank(refNo)){
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450013.getCode(), "个人结售汇信息撤消失败:该笔业务流水号["+ds.getSEQNO()+"]未查到相关业务参号");
		}

		Org org = CacheService.getOrgByCode(head.getOrgNo());
		if (org == null) {
			log.error("核心机构号["+head.getOrgNo()+"]未维护");
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"核心机构号["+head.getOrgNo()+"]未维护");
		}
		
		if (!transOrgNo.equals(org.getOrgId())) {
			log.error("该机构["+head.getOrgNo()+"]不能对["+transOrgNo+"]机构的业务[业务参号:"+refNo+"]进行撤消操作");
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450067.getCode(),"该机构["+head.getOrgNo()+"]不能对["+transOrgNo+"]机构的业务[业务参号:"+refNo+"]进行撤消操作");
		}
		
		//保存记录
		String txnSerialNo = "";
		SpfeCsr mode = new SpfeCsr();
		User user = null;
		try {
			user = this.userService.findUserByUserCode(head.getOperNo());
		} catch (BaseException e1) {
			log.error("柜员号["+head.getOperNo()+"]未维护", e1);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"柜员号["+head.getOperNo()+"]未维护");
		}
		if (user == null) {
			log.error("柜员号["+head.getOperNo()+"]未维护");
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"柜员号["+head.getOperNo()+"]未维护");
		}
		try {
			BeanUtils.copyProperties(mode, ds);
			mode.setChannelId(channel.getChannelId());
			mode.setRefNo(refNo);
			mode.setPrimaryBizNo(primaryBizNo);
			mode.setTradeNo(tradeNo);
			mode.setSEQNO("");
			//mode = spfeCsrService.doHandle(mode, user, true, head.getReqSeqNo());
			txnSerialNo = mode.getSEQNO();
		} catch (Exception e) {
			log.error("个人结售汇信息撤消数据库操作失败:请求方流水号为[" + head.getReqSeqNo() + "]", e);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_150003.getCode(),"个人结售汇信息撤消数据库操作失败:请求方流水号为[" + head.getReqSeqNo() + "]");
		}

		//调用接口
		RspIndividualLCYCancel rsp = new RspIndividualLCYCancel();
		String transState = TransStateEnum.COMPLETE.getCode();//结束
		String recode = MsgErrorCodeEnum.ERRCODE_00000.getCode();
		String remsg = MsgErrorCodeEnum.ERRCODE_00000.getValue();

		try {
			rsp = individualMsgBizManager.doDeleteIndividualFXSEAInfo(ds, txnSerialNo, refNo, head.getOperNo());
		} catch (DataCheckException e) {
			transState = TransStateEnum.FALIED.getCode();//失败
			recode = e.getCode();
			remsg = e.getReason();
			throw e;
		} finally {
			//更新个人结售汇撤消接口信息
			//.doFinish(mode, rsp, transState, recode, remsg, user);
		}
		return new Object[]{rsp, txnSerialNo };
	}

	public Object[] doIndividualLCYRegCheck(RecvIndividualLCYRegCheck ds, MsgHead head, Channel channel) throws DataCheckException {
		RspIndividualLCYRegCheck rsp = new RspIndividualLCYRegCheck();
		String fileName = "JSH_LMT_SYNC_"+ds.getSYNC_DATE().replaceAll("-", "")+".txt";
		rsp.setSYNC_FILE_NAME(fileName);
		return new Object[]{rsp, head.getReqSeqNo()};
	}

	public Object[] doIndividualLCYRegQuery(RecvIndividualLCYRegQuery ds, MsgHead head, Channel channel) throws DataCheckException {
		IndMessageValidator.validateIndividualLCYRegQuery(ds);
		
		RspIndividualLCYRegQuery rsp = new RspIndividualLCYRegQuery();
		
		TaskInfo task = null;
		try {
			task = this.tasklistService.getTaskInfoUnionHis(ds.getREFNO(), ds.getSEQNO());
		} catch (BaseException e) {
			log.error("个人结售汇指令查询数据库操作失败:请求方流水号为[" + head.getReqSeqNo() + "]", e);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_150003.getCode(),"个人结售汇指令查询数据库操作失败:请求方流水号为[" + head.getReqSeqNo() + "]");
		}
		if (task == null) {
			task = new TaskInfo();
		}
		
		rsp.setSEQNO(task.getTxnSerialNo());
		rsp.setBIZNO(task.getReqSeqNo());
		rsp.setREFNO(task.getRefNo());
		rsp.setDEAL_DATE(task.getFinishDate());
		if ("4".equals(task.getTransState())) {
			rsp.setDEAL_STATUS("1");//1-成功,2-处理中,3-失败
		} else if ("7".equals(task.getTransState())) {
			rsp.setDEAL_STATUS("3");
		} else {
			rsp.setDEAL_STATUS("2");
		}
		return new Object[]{rsp, head.getReqSeqNo()};
	}

	public Object[] doQueryIndividualFXSEAInfo(RecvQueryIndividualFXSEAInfo ds,
			MsgHead head, Channel channel) throws DataCheckException {
		PageBean pageBean = setPageBean(ds.getPAGESIZE(), ds.getPAGENUMBER());
		if (StringUtils.isBlank(ds.getREFNO())
				&& StringUtils.isBlank(ds.getIDTYPE_CODE())
				&& StringUtils.isBlank(ds.getIDCODE())
				&& StringUtils.isBlank(ds.getCTYCODE())
				&& StringUtils.isBlank(ds.getBIZ_TX_TIME())) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450017.getCode(), "");
		}
		Map retDSMap = individualMsgBizManager.doQueryIndividualFXSEAInfo(ds.getTRADE_TYPE(),
				ds.getREFNO(), ds.getIDTYPE_CODE(), ds.getIDCODE(), ds.getCTYCODE(),
				ds.getBIZ_TX_TIME(), head.getOperNo(), pageBean);
		
		RspQueryIndividualFXSEAInfo rsp = new RspQueryIndividualFXSEAInfo();
		List rowsets = (List) retDSMap.get("ROWSET");
		if (rowsets != null && rowsets.size() > 0) {
			List<RspQueryIndividualFXSEAInfoRec> rows = new ArrayList<RspQueryIndividualFXSEAInfoRec>();
			try {
				for (int i = 0; i < rowsets.size(); i++) {
					Map m = (Map) rowsets.get(i);
					RspQueryIndividualFXSEAInfoRec rec = new RspQueryIndividualFXSEAInfoRec();
					BeanUtils.copyProperties(rec, m);
					if (TradeTypeEnum.JH.getCode().equals(ds.getTRADE_TYPE())) {
						rec.setSPFE_TYPE_CODE((String) m.get("SALEFX_TX_CODE"));
						rec.setTXAMT((String) m.get("SALEFX_AMT"));
						rec.setTXAMT_USD((String) m.get("SALEFX_AMT_USD"));
						rec.setSPFE_ACCTNO_CNY((String) m.get("LCY_ACCTNO_CNY"));
					} else {
						rec.setSPFE_TYPE_CODE((String) m.get("PURFX_TYPE_CODE"));
						rec.setTXAMT((String) m.get("PURFX_AMT"));
						rec.setTXAMT_USD((String) m.get("PURFX_AMT_USD"));
						rec.setSPFE_ACCTNO_CNY((String) m.get("PURFX_ACCT_CNY"));
					}
					rows.add(rec);
				}
			} catch (Exception e) {
				log.error("解析查询的个人结售汇明细信息失败", e);
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"解析查询的个人结售汇明细信息失败");
			}
			rsp.setRec(rows);
		}
		rsp.setTRADE_TYPE(ds.getTRADE_TYPE());
		rsp.setPAGENUMBER(String.valueOf(retDSMap.get("PAGENUMBER")));
		rsp.setPAGESIZE(String.valueOf(retDSMap.get("PAGESIZE")));
		rsp.setTOTALCOUNT(String.valueOf(retDSMap.get("RECORDCOUNT")));
		return new Object[]{rsp, retDSMap.get("SvrSeqNo")};
	}
	
	public Object[] doIndividualLCYMakeUp(
			RecvIndividualLCYMakeUp ds, MsgHead head, Channel channel)
			throws DataCheckException {
		//先检查重复流水
		tasklistService.checkTaskExistByReqSeqNo(head.getReqSeqNo());

		Org org = CacheService.getOrgByCode(head.getOrgNo());
		if (org == null) {
			log.error("核心机构号["+head.getOrgNo()+"]未维护");
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"核心机构号["+head.getOrgNo()+"]未维护");
		}
		
		//保存个人结售汇信息登记接口信息
		String txnSerialNo = "";//交易流水号
		SpfeMkUp mode = new SpfeMkUp();
		User user = null;
		try {
			user = this.userService.findUserByUserCode(head.getOperNo());
		} catch (BaseException e1) {
			log.error("柜员号["+head.getOperNo()+"]未维护", e1);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"柜员号["+head.getOperNo()+"]未维护");
		}
		if (user == null) {
			log.error("柜员号["+head.getOperNo()+"]未维护");
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"柜员号["+head.getOperNo()+"]未维护");
		}
		try {
			//将报文实体转换为数据库存储实体
			BeanUtils.copyProperties(mode, ds);
			mode.setBIZ_TX_CHNL_CODE(channel.getBizChnlCode());
			mode.setChannelId(channel.getChannelId());//存储任务表
			if (channel.getBizChnlCode().equals(BizTypeEnum.TYPE_05.getCode()) && "GH".equals(ds.getTRADE_TYPE())) {
				ds.setFCY_REMIT_AMT(ds.getTXAMT());//通过支付机构渠道时，汇出资金等于售汇交易金额
				mode.setFCY_REMIT_AMT(ds.getTXAMT());
			}
			
			mode = SpfeMkUpService.doHandle(mode, user, true, head.getReqSeqNo());
			txnSerialNo = mode.getSEQNO();
		} catch (Exception e) {
			log.error("个人结售汇信息补录数据库操作失败:请求方流水号为[" + head.getReqSeqNo() + "]", e);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_150003.getCode(),"个人结售汇信息补录数据库操作失败:请求方流水号为[" + head.getReqSeqNo() + "]");
		}

		//调用接口
		RspIndividualLCYMakeUp rsp = new RspIndividualLCYMakeUp();
		String transState = TransStateEnum.COMPLETE.getCode();//结束
		String recode = MsgErrorCodeEnum.ERRCODE_00000.getCode();
		String remsg = MsgErrorCodeEnum.ERRCODE_00000.getValue();
		String refNo = "";
		try {
			Map retDSMap = individualMsgBizManager.doMakeUpOtherIndividualFXSEAInfo(ds, txnSerialNo, channel.getBizChnlCode(), head.getOperNo());
			//解析返回的DSMAP
			List rowset = (List) retDSMap.get("ROWSET");
			Map row = (Map) rowset.get(0);
			refNo = (String) row.get("REFNO");
		} catch (DataCheckException e) {
			transState = TransStateEnum.FALIED.getCode();//失败
			recode = e.getCode();
			remsg = e.getReason();
			throw e;
		} finally {
			//更新个人结售汇信息补录接口信息
			SpfeMkUpService.doFinish(txnSerialNo, refNo, transState, recode, remsg, user);
		}
		rsp.setSEQNO(txnSerialNo);
		rsp.setREFNO(refNo);
		rsp.setBIZNO(head.getReqSeqNo());
		
		return new Object[]{rsp, txnSerialNo };
	}
	
	public void doMakeUpSignStatus(RecvMakeUpSignStatus ds, MsgHead head,
			Channel channel) throws DataCheckException {
		boolean flag = individualMsgBizManager.doMakeUpSignStatus(ds.getIDTYPE_CODE(),
				ds.getIDCODE(), ds.getCTYCODE(), ds.getPERSON_NAME(),
				channel.getBizChnlCode(), head.getOperNo());
		if (flag) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_00000.getCode(), "确认书签署成功");
		}
	}
	
	public void doUpdateCommonUser(RecvCommonUser ds, MsgHead head,
			Channel channel)
			throws DataCheckException {
		if ("1".equals(ds.getCREATE_FLAG())){
			//新增
			if (StringUtils.isBlank(ds.getCOMMON_ORG_CODE())) {
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "当CREATE_FLAG为1-新增时，必输");
			}
		}
		CommonOrgUser user = new CommonOrgUser();
		try {
			BeanUtils.copyProperties(user, ds);
		} catch (Exception e) {
			log.error("用户信息更新出错", e);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "用户信息更新出错");
		}
		user.setUPDATE_TYPE("2");
		user.setUPDATE_TIME(DateTimeUtil.getCurrentDateTime());
		user.setUPDATE_USERID(head.getOperNo());
		boolean flag = individualMsgBizManager.doUpdateCommonUser(user, ds.getCREATE_FLAG());
		if (flag) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_00000.getCode(), "用户信息更新成功");
		}
	}
	
	/**
	 * 设置每页条数和页码  
	 * <p>
	 * pageSize每页条数默认为20,pageNumber默认为1,每页最大条数为50 
	 * 
	 * @param pageSize 每页条数
	 * @param pageNumber 页码
	 * @return PageBean
	 * @throws DataCheckException
	 */
	private PageBean setPageBean(String pageSize, String pageNumber) throws DataCheckException {
		PageBean pageBean = new PageBean();
		if (StringUtils.isNotEmpty(pageSize)) {
			if (Integer.parseInt(pageSize) > 50) {
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "每页最大条数不能超过50条记录!");
			}
			pageBean.setRows(Integer.parseInt(pageSize));
		}
		if (StringUtils.isNotEmpty(pageNumber)) {
			pageBean.setPage(Integer.parseInt(pageNumber));
		}
		return pageBean;
	}
	
	/**
	 * 删除错误的外围系统登记数据
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void dodeleteFalse(String reqSeqNo) throws Exception {
		TaskInfo taskinfo = tasklistService.getTaskInfobyReqSeqNo(reqSeqNo);
		if(taskinfo!=null && taskinfo.getTxnSerialNo()!=null){
			SpfeLmt delLmt = new SpfeLmt();
			delLmt.setSEQNO(taskinfo.getTxnSerialNo());
			//spfeLmtService.dodelete(delLmt);
			tasklistService.dodeleteTaskInfo(taskinfo.getTxnSerialNo());
		}
		
	}
}

	