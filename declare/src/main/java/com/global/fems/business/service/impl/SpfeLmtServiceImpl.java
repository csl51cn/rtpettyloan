package com.global.fems.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.fems.business.dao.SpfeLmtDao;
import com.global.fems.business.domain.SpfeLmt;
import com.global.fems.business.enums.SpfeLmtTradType;
import com.global.fems.business.service.SpfeLmtService;
import com.global.fems.business.support.ServiceSupport;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYRegister;
import com.global.fems.message.domain.business.response.RspIndividualLCYRegister;
import com.global.fems.message.service.impl.IndividualMsgBizManager;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.framework.util.DateTimeUtil;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TransStateEnum;
import com.global.workflow.service.WorkflowService;

@Service("spfeLmtService")
public class SpfeLmtServiceImpl implements SpfeLmtService {
	private static final Logger log = Logger.getLogger(SpfeLmtServiceImpl.class);
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private SpfeLmtDao spfeLmtDao;
	@Autowired
	private IndividualMsgBizManager  individualMsgBizManager;
	
	public SpfeLmt doHandle(SpfeLmt mode, User user, boolean isHandle,String reqSeqNo)
			throws Exception {
		if (mode.getSEQNO() == null || "".equals(mode.getSEQNO())) {
			if (isHandle) {//提交经办
				TaskInfo task = workflowService.doHandle("", SpfeLmtTradType.get(mode.getOCCUPY_LMT_STATUS(), mode.getTRADE_TYPE()).getCode(), null, user, mode.getChannelId(), reqSeqNo);
				mode.setSEQNO(task.getTxnSerialNo());
				mode.setBIZNO(task.getBizNo());
				String biTxTime = mode.getBIZ_TX_TIME();
				if (StringUtils.isBlank(biTxTime)) {
					mode.setBIZ_TX_TIME(DateTimeUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
				}
				spfeLmtDao.insert(mode);
				if (StringUtils.isBlank(reqSeqNo)) {//请求方流水号为空时表示手工界面
					doInvokeInterface(mode, task, user);
				}
			} else {//暂存
				TaskInfo task = workflowService.doSave(SpfeLmtTradType.get(mode.getOCCUPY_LMT_STATUS(), mode.getTRADE_TYPE()).getCode(), user, mode.getChannelId());
				String seqNo = task.getTxnSerialNo();
				mode.setSEQNO(seqNo);
				mode.setBIZNO(task.getBizNo());
				spfeLmtDao.insert(mode);
			}
		} else {
			spfeLmtDao.update(mode);
			if (isHandle) {
				TaskInfo task = workflowService.doHandle(mode.getSEQNO(), SpfeLmtTradType.get(mode.getOCCUPY_LMT_STATUS(), mode.getTRADE_TYPE()).getCode(),
						mode.getREFNO(), user, mode.getChannelId(), reqSeqNo);
				if (StringUtils.isBlank(reqSeqNo)) {//请求方流水号为空时表示手工界面
					doInvokeInterface(mode, task, user);
				}
			}
		}
		return mode;
	}
	
	public String doCheck(String checkMsg, User user) throws DataCheckException, Exception {
		String txnSerialNo = "";
		String isAgree = "";
		String msgs[] = ServiceSupport.analyticalMsg(checkMsg);
		txnSerialNo = msgs[0];
		isAgree = msgs[1];
		TaskInfo task = this.workflowService.doCheck(txnSerialNo, msgs[2], null, user.getUserId(), isAgree, msgs[3]);
		
		doInvokeInterface(null, task, user);
		return isAgree;
	}
	
	public String doAuth(String checkMsg, User user)
			throws DataCheckException, Exception {
		String txnSerialNo = "", isAgree = "";
		String msgs[] = ServiceSupport.analyticalMsg(checkMsg);
		txnSerialNo = msgs[0];
		isAgree = msgs[1];
		TaskInfo task = workflowService.doAuth(txnSerialNo, msgs[2], null, user.getUserId(), isAgree, msgs[3]);
		
		doInvokeInterface(null, task, user);
		return isAgree;
	}

	public void doAgain(SpfeLmt mode, User user) throws Exception {
		workflowService.doAgain(mode.getSEQNO(), null, SpfeLmtTradType.get(mode.getOCCUPY_LMT_STATUS(), mode.getTRADE_TYPE()).getCode(),
				user.getUserId());
	}

	public SpfeLmt findById(String id) throws BaseException {
		return this.spfeLmtDao.findById(id);
	}

	public List<SpfeLmt> findList(Map<String, Object> prm) throws BaseException {
		return this.spfeLmtDao.findList(prm);
	}
	public SpfeLmt findByBizNo(String bizNo) throws BaseException {
		return this.spfeLmtDao.findByBizNo(bizNo);
	}
	
	public void doCancel(String txNo, String opeid) throws Exception {
		 SpfeLmt mode = new SpfeLmt();
		 mode.setSEQNO(txNo);
		 mode.setSfzx("N");
		 this.spfeLmtDao.update(mode);
	}
	
	public boolean doFinish(String seqno, RspIndividualLCYRegister rsp, String transState, String recode, String remsg, User user) {
		try {
			SpfeLmt mode = new SpfeLmt();
			mode.setSEQNO(seqno);
			mode.setAMT_USD(String.valueOf(rsp.getAMT_USD()));
			mode.setAMT_BALANCE_USD(String.valueOf(rsp.getAMT_BALANCE_USD()));
			mode.setTYPE_STATUS(rsp.getTYPE_STATUS());
			mode.setRECODE(recode);
			mode.setREMSG(remsg);
			this.spfeLmtDao.updateBySql(mode);
			this.workflowService.doFinish(seqno, rsp.getREFNO(), TransStateEnum.getByCode(transState), user);
			return true;
		} catch (Exception e) {
			log.error("接口调用完成，流程数据迁移失败:业务流水号["+seqno+"],业务参号为["+rsp.getREFNO()+"]", e);
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
	private void doInvokeInterface(SpfeLmt mode, TaskInfo task, User user) throws BaseException, DataCheckException, Exception {
		boolean sucess = true;
		if(task.getTransState().equals(TransStateEnum.COMPLETE.getCode())){
			if (mode == null) {//这种情况是复核和授权时传入的是null，为了减少代码量
				//查询数据库
				mode = spfeLmtDao.findById(task.getTxnSerialNo());
			}
			
			//组装报文信息
			RecvIndividualLCYRegister ds = new RecvIndividualLCYRegister();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(null!=mode.getBIZ_TX_TIME() && !"".equals(mode.getBIZ_TX_TIME())){
					mode.setBIZ_TX_TIME(sdf.format(sdf.parse(mode.getBIZ_TX_TIME())));
				}
				BeanUtils.copyProperties(ds, mode);
				ds.setSIGNSTATUS(StringUtils.isBlank(mode.getSIGNSTATUS()) ? "N" : mode.getSIGNSTATUS());
			} catch (Exception e) {
				log.error("组装个人结售汇额度信息录入的请求报文出错：", e);
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"组装个人结售汇额度信息录入的请求报文出错");
			}
			RspIndividualLCYRegister rsp = new RspIndividualLCYRegister();
			String transState = TransStateEnum.COMPLETE.getCode();//结束
			String recode = MsgErrorCodeEnum.ERRCODE_00000.getCode();
			String remsg = MsgErrorCodeEnum.ERRCODE_00000.getValue();
			try {
				rsp = this.individualMsgBizManager.doAddIndividualFXSEAQuota(ds, mode.getBIZ_TX_CHNL_CODE(), mode.getSEQNO(), user.getUserCode());
				mode.setREFNO(rsp.getREFNO());
			} catch(DataCheckException ce) {
				transState = TransStateEnum.FALIED.getCode();//失败
				recode = ce.getCode();
				remsg = ce.getReason();
				throw ce;
			} finally {
				sucess = doFinish(task.getTxnSerialNo(), rsp, transState, recode, remsg, user);
			}
			
			if (!sucess) {
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "接口调用完成，流程数据迁移失败:业务流水号["+task.getTxnSerialNo()+"],业务参号为["+rsp.getREFNO()+"]");
			}
		}
	}
	
	public void dodelete(SpfeLmt spfeLmt) throws BaseException {
		this.spfeLmtDao.delete(spfeLmt);
	}
}
