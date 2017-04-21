package com.global.fems.business.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.fems.business.dao.SpfeCsrDao;
import com.global.fems.business.dao.SpfeMdfDao;
import com.global.fems.business.domain.SpfeCsr;
import com.global.fems.business.domain.SpfeMdf;
import com.global.fems.business.service.SpfeCsrService;
import com.global.fems.business.service.SpfeMdfService;
import com.global.fems.business.support.ServiceSupport;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYCancel;
import com.global.fems.message.domain.business.response.RspIndividualLCYCancel;
import com.global.fems.message.service.impl.IndividualMsgBizManager;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TransStateEnum;
import com.global.workflow.service.WorkflowService;

@Service("spfeCsrService")
public class SpfeCsrServiceImpl implements SpfeCsrService{
	private static final Logger log = Logger.getLogger(SpfeCsrServiceImpl.class);
	private String TRADNO = "000050";
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private SpfeCsrDao spfeCsrDao;
	@Autowired
	private SpfeMdfDao spfeMdfDao;
	@Autowired
	private SpfeMdfService spfeMdfService;
	@Autowired
	private IndividualMsgBizManager  individualMsgBizManager;
	
	public SpfeCsr doHandle(SpfeCsr mode,User user,boolean isHandle,String reqSeqNo) throws Exception {
		
		if (StringUtils.isBlank(mode.getSEQNO())) {
			if(isHandle){
				TaskInfo task = workflowService.doHandle(null, TRADNO, mode.getRefNo(), user, null, reqSeqNo);
				mode.setSEQNO(task.getTxnSerialNo());
				mode.setBIZNO(task.getBizNo());
				spfeCsrDao.insert(mode);
				if (StringUtils.isBlank(reqSeqNo)) {//请求方流水号为空时表示手工界面
					doInvokeInterface(mode, task, user);
				}
			}else{
				TaskInfo task = workflowService.doSave(TRADNO, user, mode.getChannelId());
				mode.setSEQNO(task.getTxnSerialNo());
				mode.setBIZNO(task.getBizNo());
				spfeCsrDao.insert(mode);
			}
		} else {
			spfeCsrDao.update(mode);
			if(isHandle){
				TaskInfo task =  workflowService.doHandle(mode.getSEQNO(), TRADNO, mode.getRefNo(), user, mode.getChannelId(), reqSeqNo);
				if (StringUtils.isBlank(reqSeqNo)) {//请求方流水号为空时表示手工界面
					doInvokeInterface(mode, task, user);
				}
			}
		}
		//先将前次业务数据的sfzx更新为N
		this.spfeMdfService.doUpdateDataStatus(mode.getTradeNo(), mode.getPrimaryBizNo(), "N");
				
		return mode;
	}

	public String doCheck(String checkMsg, User user)  throws DataCheckException,Exception{
		String txnSerialNo = "";
		String isAgree = "";
		String msgs[] = ServiceSupport.analyticalMsg(checkMsg);
		txnSerialNo = msgs[0];
		isAgree = msgs[1];
		TaskInfo task = workflowService.doCheck(txnSerialNo, msgs[2], null, user.getUserId(),
				isAgree, msgs[3]);
		
		//调用接口
		doInvokeInterface(null, task, user);
		return isAgree;
	}
	
	public String doAuth(String checkMsg, User user)  throws DataCheckException,Exception{
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
	
	public void doAgain(SpfeCsr mode, User user) throws Exception {
		workflowService.doAgain(mode.getSEQNO(),null, TRADNO, user.getUserId());
	}
	public void doDelte(SpfeCsr mode) throws BaseException {
		this.spfeCsrDao.delete(mode);
	}

	public SpfeCsr findById(String id) throws BaseException {
		return this.spfeCsrDao.findById(id);
	}
	
	public List<SpfeCsr> findList(Map<String, Object> prm) throws BaseException {
		return this.spfeCsrDao.findList(prm);
	}

	public void doCancel(String txNo, String opeid) throws Exception {
		SpfeCsr mode = this.spfeCsrDao.findById(txNo);
		String primaryBizNo = mode.getPrimaryBizNo();
		 if(StringUtils.isNotBlank(primaryBizNo)){
			 this.spfeMdfService.doUpdateDataStatus(mode.getTradeNo(), mode.getPrimaryBizNo(), "Y");
		 }
	}
	
	public boolean doFinish(SpfeCsr spfeCsr, RspIndividualLCYCancel rsp,
			String transState, String recode, String remsg, User user) {
		SpfeCsr mode = new SpfeCsr();
		try {
			mode.setSEQNO(spfeCsr.getSEQNO());
			mode.setRECODE(recode);
			mode.setREMSG(remsg);
			this.spfeCsrDao.update(mode);
			if (!MsgErrorCodeEnum.ERRCODE_00000.getCode().equals(recode)) {
				//如果调用外管修改接口失败，则需要更新原来成功的那笔数据
				String tradeNo = spfeCsr.getTradeNo();
				if (tradeNo.equals("000040") || tradeNo.equals("000041") || tradeNo.equals("000042") || tradeNo.equals("000043")) {
					//查询bu_spfe_lmt
					this.spfeMdfDao.updateDataStatusByBizNo(spfeCsr.getPrimaryBizNo(), "bu_spfe_lmt", "Y");
				} else if (tradeNo.equals("000060")) {
					//查询bu_spfe_mdf
					this.spfeMdfDao.updateDataStatusByBizNo(spfeCsr.getPrimaryBizNo(), "bu_spfe_mdf", "Y");
				}
			}
			this.workflowService.doFinish(mode.getSEQNO(), rsp.getREFNO(), TransStateEnum.getByCode(transState), user);
			return true;
		} catch (Exception e) {
			log.error("接口调用完成，流程数据迁移失败:业务流水号["+mode.getSEQNO()+"],业务参号为["+rsp.getREFNO()+"]", e);
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
	private void doInvokeInterface(SpfeCsr mode, TaskInfo task, User user) throws BaseException, DataCheckException, Exception {
		boolean sucess = true;
		if(task.getTransState().equals(TransStateEnum.COMPLETE.getCode())){
			if (mode == null) {//这种情况是复核和授权时传入的是null，为了减少代码量
				//查询数据库
				mode = spfeCsrDao.findByKey(task.getTxnSerialNo());
			}
			
			//组装报文信息
			RecvIndividualLCYCancel ds = new RecvIndividualLCYCancel();
			try {
				BeanUtils.copyProperties(ds, mode);
			} catch (Exception e) {
				log.error("组装个人结售汇信息撤消的请求报文出错：", e);
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"组装个人结售汇信息撤消的请求报文出错");
			}
			RspIndividualLCYCancel rsp = new RspIndividualLCYCancel();
			String transState = TransStateEnum.COMPLETE.getCode();//结束
			String recode = MsgErrorCodeEnum.ERRCODE_00000.getCode();
			String remsg = MsgErrorCodeEnum.ERRCODE_00000.getValue();
			try {
				rsp = this.individualMsgBizManager.doDeleteIndividualFXSEAInfo(ds, mode.getSEQNO(), mode.getRefNo(), user.getUserCode());
			} catch(DataCheckException ce) {
				transState = TransStateEnum.FALIED.getCode();//失败
				recode = ce.getCode();
				remsg = ce.getReason();
				throw ce;
			} finally {
				sucess = doFinish(mode, rsp, transState, recode, remsg, user);
			}
			
			if (!sucess) {
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "接口调用完成，流程数据迁移失败:业务流水号["+task.getTxnSerialNo()+"],业务参号为["+rsp.getREFNO()+"]");
			}
		}
	}
	
	public SpfeMdf findSpfeInfoByCsrId(String txnSerialNo) {
		return this.spfeCsrDao.findSpfeInfoByCsrId(txnSerialNo);
	}
}
