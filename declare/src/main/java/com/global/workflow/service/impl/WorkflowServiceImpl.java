package com.global.workflow.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.util.SysUtils;
import com.global.workflow.dao.TaskInfoDao;
import com.global.workflow.dao.TradeTemplateDao;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TaskInfoHis;
import com.global.workflow.domain.TradeTemplate;
import com.global.workflow.domain.TransStateEnum;
import com.global.workflow.service.ProcessHistoryService;
import com.global.workflow.service.WorkflowService;

/**
 * 
 * @author chen.feng
 * @date 2015-6-20
 * @version v1.0
 */
@Service("workflowService")
public class WorkflowServiceImpl implements WorkflowService {

	@Autowired
	private SysCommonService sysCommonService;
	@Autowired
	private ProcessHistoryService processHistoryService;
	@Autowired
	private TaskInfoDao taskInfoDao;
	@Autowired
	private TradeTemplateDao tradeTemplateDao;

	public TaskInfo doSave(String tradeNo, User user, String channelId) throws Exception {
		TaskInfo task = generateTaskInfo(tradeNo, user, channelId);
		this.taskInfoDao.saveTaskInfo(task);
		return task;
	}

	public TaskInfo doHandle(String txnSerialNo, String tradeNo,String refNo, User user, String channelId, String reqSeqNo)
			throws Exception {
		TaskInfo task = null;
		if (StringUtils.isBlank(txnSerialNo)) {
			task = generateTaskInfo(tradeNo, user, channelId);
		} else {
			task = new TaskInfo();
			task.setTxnSerialNo(txnSerialNo);
		}
		task.setRefNo(refNo);
		task.setReqSeqNo(reqSeqNo);
		task.setTransState(TransStateEnum.HANDLE.getCode());

		if (StringUtils.isBlank(txnSerialNo)) {
			this.taskInfoDao.saveTaskInfo(task);
		} else {
			this.taskInfoDao.updateTaskInfo(task);
		}
		
		if (!checkTradeUseFlow(tradeNo, TransStateEnum.HANDLE)) {
			// 未使用工作流，直接保存至历史表中，任务状态为完成状态
			//doFinish(user.getUserId(), task, TransStateEnum.COMPLETE);
			task.setTransState(TransStateEnum.COMPLETE.getCode());
		}

		// 保存审批流程历史信息
		this.processHistoryService.saveProcessHistory(task.getTxnSerialNo(),
				TransStateEnum.HANDLE, user.getUserId(), null);
		return task;
	}

	public TaskInfo doCheck(String txnSerialNo, String tradeNo,String refNo, String operId,
			String isPass, String opinion) throws Exception {
		TaskInfo task = this.taskInfoDao.getTaskInfo(txnSerialNo);
		task.setRefNo(refNo);
		task.setActors(task.getActors() + operId + ",");
		if ("Y".equals(isPass)) {
			// 判断该交易是否还有后续流程处理
			if (!checkTradeUseFlow(tradeNo, TransStateEnum.CHECK)) {
				// 没有后续流程处理，直接结束流程
//				doFinish(operId, task, TransStateEnum.COMPLETE);
				this.taskInfoDao.updateTaskInfo(task);//只把操作人更新进表
				task.setTransState(TransStateEnum.COMPLETE.getCode());
			} else {
				// 更新流程状态
				task.setTransState(TransStateEnum.CHECK.getCode());
				this.taskInfoDao.updateTaskInfo(task);
			}
		} else {
			//打回经办更正
			task.setTransState(TransStateEnum.AGAIN.getCode());
			this.taskInfoDao.updateTaskInfo(task);
		}

		// 保存审批流程历史信息
		this.processHistoryService.saveProcessHistory(txnSerialNo,
				TransStateEnum.CHECK, operId, opinion);
		return task;
	}

	public TaskInfo doAuth(String txnSerialNo, String tradeNo,String refNo, String operId,
			String isPass, String opinion) throws Exception {
		TaskInfo task = this.taskInfoDao.getTaskInfo(txnSerialNo);
		task.setRefNo(refNo);
		task.setActors(task.getActors() + operId + ",");
		if ("Y".equals(isPass)) {// 授权通过直接结束流程
//			this.doFinish(operId, task, TransStateEnum.COMPLETE);
			this.taskInfoDao.updateTaskInfo(task);//只把操作人更新进表
			task.setTransState(TransStateEnum.COMPLETE.getCode());
		} else {// 不通过打回经办更正
			task.setTransState(TransStateEnum.AGAIN.getCode());
			this.taskInfoDao.updateTaskInfo(task);
		}
		// 保存审批流程历史信息
		this.processHistoryService.saveProcessHistory(txnSerialNo,
				TransStateEnum.AUTH, operId, opinion);
		return task;
	}

	public TaskInfo doAgain(String txnSerialNo, String tradeNo,String refNo, String operId)
			throws Exception {
		TaskInfo task = this.taskInfoDao.getTaskInfo(txnSerialNo);
		task.setRefNo(refNo);
		task.setTransState(TransStateEnum.HANDLE.getCode());
		this.taskInfoDao.updateTaskInfo(task);
		return task;
	}

	public TaskInfo doCancel(String txnSerialNo, String operId) throws Exception {
		TaskInfo task = this.taskInfoDao.getTaskInfo(txnSerialNo);
		this.taskInfoDao.deleteTaskInfo(txnSerialNo);
		this.doFinish(operId, task, TransStateEnum.CANCEL);
		// 保存审批流程历史信息
		this.processHistoryService.saveProcessHistory(txnSerialNo,
				TransStateEnum.CANCEL, operId, "操作员[" + operId + "]执行的删除操作");
		return task;
	}

	public void doFinish(String txnSerialNo,String refNo, TransStateEnum transState, User user) throws Exception{
		TaskInfo task = this.taskInfoDao.getTaskInfo(txnSerialNo);
		task.setRefNo(refNo);
		this.doFinish(user.getUserId(), task, transState);
	}
	
	public void doFinish(String operId, TaskInfo task,
			TransStateEnum transState) throws Exception {
		TaskInfoHis taskInfoHis = new TaskInfoHis();
		BeanUtils.copyProperties(taskInfoHis, task);
		taskInfoHis.setTransState(transState.getCode());
		taskInfoHis.setFinishDate(SysUtils.getNowDateTime());
		taskInfoHis.setTransState(transState.getCode());
		taskInfoHis.setActors(StringUtils.isBlank(task.getActors()) ? ",AUTO," : (task.getActors() + operId + ","));
		this.taskInfoDao.deleteTaskInfo(task.getTxnSerialNo());
		this.taskInfoDao.saveTaskInfoHis(taskInfoHis);
		
		// 保存审批流程历史信息
		this.processHistoryService.saveProcessHistory(task.getTxnSerialNo(),
				TransStateEnum.COMPLETE, (StringUtils.isBlank(operId) ? "AUTO" : operId), "流程完成");
	}

	private TaskInfo generateTaskInfo(String tradeNo, User user, String channelId)
			throws BaseException {
		TaskInfo task = new TaskInfo();
		// 获取交易流水号
		String txnSerialNo = this.sysCommonService.getSeqNoByOperNo("wfl_taskinfo", user.getUserCode());
		// 获取业务编号
		String bizNo = this.sysCommonService.getBizNo(tradeNo);

		task.setTxnSerialNo(txnSerialNo);
		task.setBizNo(bizNo);
		task.setTradeNo(tradeNo);
		task.setBelongOrgNo(user.getOrgId());// 业务所属机构
		task.setTransOrgNo(user.getOrgId());// 交易执行机构
		task.setCreateDate(SysUtils.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
		task.setCreateUser(user.getUserId());
		task.setTransState(TransStateEnum.SAVE.getCode());
		task.setActors("," + user.getUserId() + ",");
		task.setChannelId(channelId);
		return task;
	}

	/**
	 * 检查指定交易是否使用工作流程管理
	 * 
	 * @param tradeNo
	 *            交易编号
	 * @param transState
	 *            当前交易状态
	 * @return
	 * @throws Exception
	 */
	private boolean checkTradeUseFlow(String tradeNo, TransStateEnum transState)
			throws Exception {
		// 获取交易流程信息
		TradeTemplate tradeTemplate = tradeTemplateDao
				.getTradeTemplate(tradeNo);
		if (tradeTemplate == null) {
			throw new Exception("交易[" + tradeNo + "]未配置工作流模板");
		}

		if ("Y".equals(tradeTemplate.getIsUsed())) {
			if (TransStateEnum.HANDLE.equals(transState)) {// 经办时判断是否有复核步骤
				if ("Y".equals(tradeTemplate.getIsCheck())) {
					return true;
				}
				return false;
			}
			if (TransStateEnum.CHECK.equals(transState)) {
				if ("Y".equals(tradeTemplate.getIsAuth())) {// 复核时判断是否有授权步骤
					return true;
				}
				return false;
			}
		}
		return false;
	}

}
