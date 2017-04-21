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

import com.global.fems.business.dao.SpfeMdfDao;
import com.global.fems.business.domain.SpfeLmt;
import com.global.fems.business.domain.SpfeMdf;
import com.global.fems.business.service.SpfeMdfService;
import com.global.fems.business.support.ServiceSupport;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYModify;
import com.global.fems.message.domain.business.response.RspIndividualLCYModify;
import com.global.fems.message.service.impl.IndividualMsgBizManager;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.workflow.dao.TaskInfoDao;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TransStateEnum;
import com.global.workflow.service.WorkflowService;

@Service("spfeMdfService")
public class SpfeMdfServiceImpl implements SpfeMdfService{
	private static final Logger log = Logger.getLogger(SpfeMdfServiceImpl.class);
	private String TRADNO = "000060";
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private SpfeMdfDao spfeMdfDao;
	@Autowired
	private IndividualMsgBizManager  individualMsgBizManager;
	@Autowired
	private TaskInfoDao taskInfoDao;

	public SpfeMdf doHandle(SpfeMdf mode,User user,boolean isHandle, String reqSeqNo) throws Exception {
		
		if (StringUtils.isBlank(mode.getSEQNO())) {
			if(isHandle){
				TaskInfo task = workflowService.doHandle(null, TRADNO, mode.getREFNO(), user, mode.getChannelId(), reqSeqNo);
				mode.setSEQNO(task.getTxnSerialNo());
				mode.setBIZNO(task.getBizNo());
				spfeMdfDao.insert(mode);
				if (StringUtils.isBlank(reqSeqNo)) {//请求方流水号为空时表示手工界面
					doInvokeInterface(mode, task, user);
				}
			}else{
				TaskInfo task = workflowService.doSave(TRADNO, user, mode.getChannelId());
				String seqNo =task.getTxnSerialNo();
				mode.setSEQNO(seqNo);
				mode.setBIZNO(task.getBizNo());
				spfeMdfDao.insert(mode);
			}
		} else {
			spfeMdfDao.update(mode);
			if(isHandle){
				TaskInfo task = workflowService.doHandle(mode.getSEQNO(), TRADNO, mode.getREFNO(), user, mode.getChannelId(), reqSeqNo);
				if (StringUtils.isBlank(reqSeqNo)) {//请求方流水号为空时表示手工界面
					doInvokeInterface(mode, task, user);
				}
			}
		}
		//先将前次业务数据的sfzx更新为N
		this.doUpdateDataStatus(mode.getTradeNo(), mode.getPrimaryBizNo(), "N");
				
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
	
	public void doAgain(SpfeMdf mode, User user) throws Exception {
		workflowService.doAgain(mode.getSEQNO(),null, TRADNO, user.getUserId());
	}

	public SpfeMdf findById(String id) throws BaseException {
		return this.spfeMdfDao.findById(id);
	}
	
	public List<SpfeMdf> findList(Map<String, Object> prm) throws BaseException {
		return this.spfeMdfDao.findList(prm);
	}

	public SpfeLmt findNotCancelById(String id,String csrId) throws BaseException {
		return this.spfeMdfDao.findNotCancelById(id,csrId);
	}

	public List<SpfeLmt> findNotCancelAll() throws BaseException {
		return this.spfeMdfDao.findNotCancelAll();
	}
	

	/**
	 * 修改数据 更新标记
	 * @param primaryBizNo
	 * @throws Exception 
	 */
	public void doUpdateDataStatus(String tradeNo, String primaryBizNo, String sfzx) throws Exception{
		if (tradeNo.equals("000040") || tradeNo.equals("000041") || tradeNo.equals("000042") || tradeNo.equals("000043")) {
			//修改bu_spfe_lmt
			this.spfeMdfDao.updateLmtDataStatus(primaryBizNo, sfzx);
		} else if (tradeNo.equals("000060")) {
			//修改bu_spfe_mdf
			this.spfeMdfDao.updateMdfDataStatus(primaryBizNo, sfzx);
		}
		this.taskInfoDao.updateTaskInfoHisStatus(primaryBizNo, sfzx);
	}
	
	public SpfeMdf findSpfeMdfByMdfSeqNo(String seqNo,String refNo,String sfzx) throws BaseException {
		return this.spfeMdfDao.findSpfeMdfByMdfSeqNo(seqNo, refNo, sfzx);
	}

	public SpfeMdf findSpfeMdfByLmtBizNo(String bizNo,String sfzx) throws BaseException {
		return  this.spfeMdfDao.findSpfeMdfByLmtBizNo(bizNo,sfzx);
	}

	public void updateDataStatus(String seqno, String tableName,String status)
			throws BaseException {
		this.spfeMdfDao.updateDataStatus(seqno, tableName,status);
	}

	public SpfeMdf findSpfeMdfByLmtSeqNo(String seqNo) throws BaseException {
		return this.spfeMdfDao.findSpfeMdfByLmtSeqNo(seqNo);
	}

	public SpfeMdf findByPrimaryKey(String key) throws BaseException {
		return this.spfeMdfDao.findByPrimaryKey(key);
	}

	public void doCancel(String txNo, String opeid) throws Exception {
		 SpfeMdf mode = this.spfeMdfDao.findById(txNo);
		 String primaryBizNo = mode.getPrimaryBizNo();
		 if(StringUtils.isNotBlank(primaryBizNo)){
			 if(primaryBizNo.contains("MF")){
				 this.spfeMdfDao.updateDataStatusByBizNo(primaryBizNo, "BU_spfe_mdf", "Y");
			 }else{
				 this.spfeMdfDao.updateDataStatusByBizNo(primaryBizNo, "BU_spfe_lmt", "Y");
			 }
		 }
		 mode = new SpfeMdf();
		 mode.setSEQNO(txNo);
		 mode.setSfzx("N");
		 this.spfeMdfDao.update(mode);
	}

	public String getBizNoByRefNo(String refNo) throws BaseException {
		return this.spfeMdfDao.getBizNoByRefNo(refNo);
	}

	public SpfeLmt findNotCancelByBizNo(String bizno,boolean isDetailQuey)   throws BaseException {
		return this.spfeMdfDao.findNotCancelByBizNo(bizno,isDetailQuey);
	}

	public PageBean queryFinishTaskListByUserID(TaskInfo task,
			PageBean page) throws BaseException {
		return this.spfeMdfDao.queryFinishTaskListByUserID(task, page);
	}
	
	public Map<String, Object> findRefNoBySeqNo(String seqno) {
		return this.spfeMdfDao.findRefNoBySeqNo(seqno);
	}
	
	public void doFinish(SpfeMdf spfeMdf, RspIndividualLCYModify rsp,
			String transState, String recode, String remsg, User user) throws DataCheckException {
		try {
			SpfeMdf mode = new SpfeMdf();
			mode.setSEQNO(spfeMdf.getSEQNO());
			mode.setRECODE(recode);
			mode.setREMSG(remsg);
			if (TransStateEnum.COMPLETE.getCode().equals(TransStateEnum.getByCode(transState).getCode())){
				mode.setSfzx("Y");
			} else {
				mode.setSfzx("N");
			}
			this.spfeMdfDao.update(mode);
			if (!MsgErrorCodeEnum.ERRCODE_00000.getCode().equals(recode)) {
				//如果调用外管修改接口失败，则需要更新原来成功的那笔数据
				String tradeNo = spfeMdf.getTradeNo();
				if (tradeNo.equals("000040") || tradeNo.equals("000041") || tradeNo.equals("000042") || tradeNo.equals("000043")) {
					//查询bu_spfe_lmt
					this.spfeMdfDao.updateDataStatusByBizNo(spfeMdf.getPrimaryBizNo(), "bu_spfe_lmt", "Y");
				} else if (tradeNo.equals("000060")) {
					//查询bu_spfe_mdf
					this.spfeMdfDao.updateDataStatusByBizNo(spfeMdf.getPrimaryBizNo(), "bu_spfe_mdf", "Y");
				}
			}
			this.workflowService.doFinish(mode.getSEQNO(), rsp.getREFNO(), TransStateEnum.getByCode(transState), user);
		} catch (Exception e) {
			log.error("接口调用完成，流程数据迁移失败:业务流水号["+spfeMdf.getSEQNO()+"],业务参号为["+rsp.getREFNO()+"]", e);
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "接口调用完成，流程数据迁移失败:业务流水号["+spfeMdf.getSEQNO()+"],业务参号为["+rsp.getREFNO()+"]");
		}
	}
	
	public SpfeMdf findSpfeMdfByLmtSeqNo(String txnSerialNo, String refNo, String sfzx) {
		return this.spfeMdfDao.findSpfeMdfByLmtSeqNo(txnSerialNo, refNo, sfzx);
	}
	
	public SpfeMdf findSpfeMkupBySeqNo(String txnSerialNo, String refNo,
			String string) {
		return this.spfeMdfDao.findSpfeMkupBySeqNo(txnSerialNo, refNo, string);
	}
	
	/**
	 * 交易流程执行完成后调用接口
	 * @param mode
	 * @param task
	 * @throws BaseException
	 * @throws DataCheckException
	 * @throws Exception
	 */
	private void doInvokeInterface(SpfeMdf mode, TaskInfo task, User user) throws BaseException, DataCheckException, Exception {
		if(task.getTransState().equals(TransStateEnum.COMPLETE.getCode())){
			if (mode == null) {//这种情况是复核和授权时传入的是null，为了减少代码量
				//查询数据库
				mode = spfeMdfDao.findById(task.getTxnSerialNo());
			}
			
			//组装报文信息
			RecvIndividualLCYModify ds = new RecvIndividualLCYModify();
			try {
				BeanUtils.copyProperties(ds, mode);
			} catch (Exception e) {
				log.error("组装个人结售汇信息修改的请求报文出错：", e);
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"组装个人结售汇信息修改的请求报文出错");
			}
			RspIndividualLCYModify rsp = new RspIndividualLCYModify();
			String transState = TransStateEnum.COMPLETE.getCode();//结束
			String recode = MsgErrorCodeEnum.ERRCODE_00000.getCode();
			String remsg = MsgErrorCodeEnum.ERRCODE_00000.getValue();
			try {
				rsp = this.individualMsgBizManager.doModifyIndividualFXSEAInfo(ds, mode.getSEQNO(), mode.getREFNO(), user.getUserCode());
				doFinish(mode, rsp, transState, recode, remsg, user);
			} catch(DataCheckException ce) {
				log.error("个人结售汇修改操作失败", ce);
				throw ce;
			}
		}
	}
}
