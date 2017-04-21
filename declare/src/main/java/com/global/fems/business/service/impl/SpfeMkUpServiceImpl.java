package com.global.fems.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.fems.business.dao.SpfeMkUpDao;
import com.global.fems.business.domain.SpfeMkUp;
import com.global.fems.business.enums.SpfeMkUpTradType;
import com.global.fems.business.service.SpfeMkUpService;
import com.global.fems.business.support.ServiceSupport;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYMakeUp;
import com.global.fems.message.service.impl.IndividualMsgBizManager;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.framework.util.DateTimeUtil;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TransStateEnum;
import com.global.workflow.service.WorkflowService;

@Service("SpfeMkUpService")
@SuppressWarnings("rawtypes")
public class SpfeMkUpServiceImpl implements SpfeMkUpService {
	private static final Logger log = Logger.getLogger(SpfeMkUpServiceImpl.class);
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private SpfeMkUpDao spfeMkUpDao;
	@Autowired
	private IndividualMsgBizManager  individualMsgBizManager;
	
	public SpfeMkUp doHandle(SpfeMkUp mode, User user, boolean isHandle, String reqSeqNo)
			throws Exception {
		SpfeMkUp spfeMkUp = new SpfeMkUp();
		String biTxTime = mode.getBIZ_TX_TIME();
		if (StringUtils.isBlank(biTxTime)) {
			mode.setBIZ_TX_TIME(DateTimeUtil.getNowDateTime("yyyy-MM-dd"));
		}
		if (mode.getSEQNO() == null || "".equals(mode.getSEQNO())) {
			if (isHandle) {
				TaskInfo task = workflowService.doHandle("", SpfeMkUpTradType.get(mode.getOCCUPY_LMT_STATUS(), mode.getTRADE_TYPE()).getCode(), mode.getREFNO(), user,mode.getChannelId(), null);
				mode.setSEQNO(task.getTxnSerialNo());
				mode.setBIZNO(task.getBizNo());
				spfeMkUp = spfeMkUpDao.insert(mode);
				if (StringUtils.isBlank(reqSeqNo)) {//请求方流水号为空时表示手工界面
					doInvokeInterface(mode, task, user);
				}
			} else {
				TaskInfo task = workflowService.doSave(SpfeMkUpTradType.get(mode.getOCCUPY_LMT_STATUS(), mode.getTRADE_TYPE()).getCode(), user,mode.getChannelId());
				String SEQNO = task.getTxnSerialNo();
				mode.setSEQNO(SEQNO);
				mode.setBIZNO(task.getBizNo());
				spfeMkUp = spfeMkUpDao.insert(mode);
			}
		} else {
			spfeMkUpDao.update(mode);
			spfeMkUp = mode;
			if (isHandle) {
				TaskInfo task = workflowService.doHandle(mode.getSEQNO(), SpfeMkUpTradType.get(mode.getOCCUPY_LMT_STATUS(), mode.getTRADE_TYPE()).getCode(),
						mode.getREFNO(), user,mode.getChannelId(), null);
				if (StringUtils.isBlank(reqSeqNo)) {//请求方流水号为空时表示手工界面
					doInvokeInterface(mode, task, user);
				}
			}
		}
		return spfeMkUp;
	}

	public String doCheck(String checkMsg, User user)
			throws DataCheckException, Exception {
		String txnSerialNo = "";
		String isAgree = "";
		String msgs[] = ServiceSupport.analyticalMsg(checkMsg);
		txnSerialNo = msgs[0];
		isAgree = msgs[1];
		TaskInfo task = this.workflowService.doCheck(txnSerialNo, msgs[2], null, user.getUserId(), isAgree, msgs[3]);

		//调用接口
		doInvokeInterface(null, task, user);
		return isAgree;
	}
	public String doAuth(String checkMsg, User user)
			throws DataCheckException, Exception {
		String txnSerialNo = "";
		String isAgree = "";
		String msgs[] = ServiceSupport.analyticalMsg(checkMsg);
		txnSerialNo = msgs[0];
		isAgree = msgs[1];
		TaskInfo task = workflowService.doAuth(txnSerialNo, msgs[2], null, user.getUserId(),
				isAgree, msgs[3]);
		
		//调用接口
		doInvokeInterface(null, task, user);
		return isAgree;
	}

	public void doAgain(SpfeMkUp mode, User user) throws Exception {
		workflowService.doAgain(mode.getSEQNO(), mode.getREFNO(), SpfeMkUpTradType.get(mode.getOCCUPY_LMT_STATUS(), mode.getTRADE_TYPE()).getCode(),
				user.getUserId());
	}

	public void doCancel(String txNo, String opeid) throws Exception {
		SpfeMkUp mode = new SpfeMkUp();
		mode.setSEQNO(txNo);
		mode.setSfzx("N");
		this.spfeMkUpDao.update(mode);
	}


	public SpfeMkUp findById(String id) throws BaseException {
		return this.spfeMkUpDao.findById(id);
	}

	public List<SpfeMkUp> findList(Map<String, Object> prm) throws BaseException {
		return this.spfeMkUpDao.findList(prm);
	}

	public boolean doFinish(String txnSerialNo, String refNo,
			String transState, String recode, String remsg, User user) {
		try {
			SpfeMkUp mode = new SpfeMkUp();
			mode.setSEQNO(txnSerialNo);
			mode.setRECODE(recode);
			mode.setREMSG(remsg);
			mode.setREFNO(refNo);
			this.spfeMkUpDao.update(mode);
			this.workflowService.doFinish(txnSerialNo, refNo, TransStateEnum.getByCode(transState), user);
			return true;
		} catch (Exception e) {
			log.error("接口调用完成，流程数据迁移失败:业务流水号["+txnSerialNo+"],业务参号为["+refNo+"]", e);
			return false;
		}
	}
	
	/**
	 * 交易流程执行完成后调用接口
	 * @param mode
	 * @param task
	 * @throws BaseException
	 * @throws DataCheckException
	 * @throws Exception
	 */
	private void doInvokeInterface(SpfeMkUp mode, TaskInfo task, User user) throws BaseException, DataCheckException, Exception {
		boolean sucess = true;
		if(task.getTransState().equals(TransStateEnum.COMPLETE.getCode())){
			if (mode == null) {//这种情况是复核和授权时传入的是null，为了减少代码量
				//查询数据库
				mode = spfeMkUpDao.findById(task.getTxnSerialNo());
			}
			
			//组装报文信息
			RecvIndividualLCYMakeUp ds = new RecvIndividualLCYMakeUp();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(null!=mode.getBIZ_TX_TIME() && !"".equals(mode.getBIZ_TX_TIME())){
					mode.setBIZ_TX_TIME(sdf.format(sdf.parse(mode.getBIZ_TX_TIME())));
				}
				BeanUtils.copyProperties(ds, mode);
				ds.setSIGNSTATUS(StringUtils.isBlank(mode.getSIGNSTATUS()) ? "N" : mode.getSIGNSTATUS());
			} catch (Exception e) {
				log.error("组装个人结售汇信息补录的请求报文出错：", e);
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"组装个人结售汇信息补录的请求报文出错");
			}

			Map retMap = new HashMap();
			String refNo = "";//业务参号
			String transState = TransStateEnum.COMPLETE.getCode();//结束
			String recode = MsgErrorCodeEnum.ERRCODE_00000.getCode();
			String remsg = MsgErrorCodeEnum.ERRCODE_00000.getValue();
			try {
				retMap = this.individualMsgBizManager.doMakeUpOtherIndividualFXSEAInfo(ds, mode.getSEQNO(), mode.getBIZ_TX_CHNL_CODE(), user.getUserCode());
				//解析返回的DSMAP
				List rowset = (List) retMap.get("ROWSET");
				Map row = (Map) rowset.get(0);
				refNo = (String) row.get("REFNO");
				mode.setREFNO(refNo);
			} catch(DataCheckException ce) {
				transState = TransStateEnum.FALIED.getCode();//失败
				recode = ce.getCode();
				remsg = ce.getReason();
				throw ce;
			} finally {
				sucess = doFinish(task.getTxnSerialNo(), refNo, transState, recode, remsg, user);
			}
			
			if (!sucess) {
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "接口调用完成，流程数据迁移失败:业务流水号["+task.getTxnSerialNo()+"],业务参号为["+refNo+"]");
			}
		}
	}
}
