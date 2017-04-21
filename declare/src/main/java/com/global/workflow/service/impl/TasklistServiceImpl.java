package com.global.workflow.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.Org;
import com.global.framework.system.domain.User;
import com.global.framework.system.service.OrgService;
import com.global.framework.util.SpringContextUtil;
import com.global.workflow.dao.TaskInfoDao;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TaskInfoHis;
import com.global.workflow.domain.TransStateEnum;
import com.global.workflow.service.TasklistService;
import com.global.workflow.service.WorkflowService;

/**
 * 
 * @author chen.feng
 * @date 2015-6-25
 * @version v1.0
 */
@Service("tasklistService")
public class TasklistServiceImpl implements TasklistService {

	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private TaskInfoDao taskInfoDao;

	
	public PageBean queryWaitTaskListByUserID(TaskInfo task, User user,
			PageBean page) throws Exception {
		// 解析机构权限
		Set<Org> rightOrgList = orgService.getRightOrgList(user);
		List<String> list = new ArrayList<String>();
		for (Iterator<Org> iterator = rightOrgList.iterator(); iterator
				.hasNext();) {
			Org org = (Org) iterator.next();
			list.add(org.getOrgId());
		}
		return this.taskInfoDao.queryWaitTaskListByUserId(task, user, list,
				page);
	}

	
	public PageBean queryFinishTaskListByUserID(TaskInfo task, User user,
			PageBean page) throws Exception {
		return this.taskInfoDao.queryFinishTaskListByUserID(task, user, page);
	}

	
	public PageBean queryPartTaskListByUserID(TaskInfo task, User user,
			PageBean page) throws Exception {
		return this.taskInfoDao.queryPartTaskListByUserID(task, user, page);
	}

	
	public PageBean queryFlowHistoryList(String txnSerialNo, PageBean page)
			throws Exception {
		return this.taskInfoDao.queryFlowHistoryList(txnSerialNo, page);
	}
	
	
	public String doCheckTask(String checkMsg, String opeid) throws Exception {
		JSONObject json = JSON.parseObject(checkMsg);
		String tradeNo = json.getString("tradeNo");
		String txnSerialNo = json.getString("txnSerialNo");
		String isAgree = json.getString("isAgree");
		String opinion = json.getString("opinion");
		if (StringUtils.isBlank(txnSerialNo)) {
			throw new BaseException("复核时业务流水号不能为空");
		}
		if (StringUtils.isBlank(isAgree)) {
			throw new BaseException("请选择是否通过复核");
		}
		if ("N".equals(isAgree) && StringUtils.isBlank(opinion)) {
			throw new BaseException("复核不通过时复核意见不能为空");
		}
		this.workflowService.doCheck(txnSerialNo, tradeNo,null, opeid, isAgree, opinion);
		return isAgree;
	}
	
	
	public String doAuthTask(String checkMsg, String opeid) throws Exception {
		JSONObject json = JSON.parseObject(checkMsg);
		String tradeNo = json.getString("tradeNo");
		String txnSerialNo = json.getString("txnSerialNo");
		String isAgree = json.getString("isAgree");
		String opinion = json.getString("opinion");
		if (StringUtils.isBlank(txnSerialNo)) {
			throw new BaseException("授权时业务流水号不能为空");
		}
		if (StringUtils.isBlank(isAgree)) {
			throw new BaseException("请选择是否通过授权");
		}
		if ("N".equals(isAgree) && StringUtils.isBlank(opinion)) {
			throw new BaseException("授权不通过时授权意见不能为空");
		}
		this.workflowService.doAuth(txnSerialNo, tradeNo,null, opeid, isAgree, opinion);
		return isAgree;
	}
	public String queryTradeUnionMenu(String tradeNo) throws BaseException{
		return  this.taskInfoDao.queryTradeUnionMenu(tradeNo);
	}
	
	
	public int queryTaskCountByUserID(User user, String flag)
			throws BaseException {
		// 解析机构权限
			Set<Org> rightOrgList = orgService.getRightOrgList(user);
			List<String> list = new ArrayList<String>();
			for (Iterator<Org> iterator = rightOrgList.iterator(); iterator
					.hasNext();) {
				Org org = (Org) iterator.next();
				list.add(org.getOrgId());
			}
		return this.taskInfoDao.queryTaskCountByUserID(user.getUserId(), flag, list);
	}


	public void doCancelTask(String txNo, String serviceBeanId, String opeid)
			throws Exception {
		this.workflowService.doCancel(txNo, opeid);
		if(StringUtils.isNotBlank(serviceBeanId)){//如果传入服务类 不为空 则反射调用
			Object service = SpringContextUtil.getBean(serviceBeanId);
			if(service == null){
				throw new Exception("传入的service 服务 beanId：“"+serviceBeanId+"” 错误，找不到 这样的bean");
			}
			Method method = service.getClass().getDeclaredMethod("doCancel", new Class[]{String.class,String.class});
			if(method == null){
				throw new Exception("传入的service 服务 beanId：“"+serviceBeanId+"” 没有找到 doCancel(String,String)方法，找不到 这样的bean");
			}
			method.invoke(service, new Object[]{txNo,opeid});
		}
	}
	
	public void checkTaskExistByReqSeqNo(String reqSeqNo) throws DataCheckException {
		TaskInfo taskInfo = null;
		try {
			taskInfo = this.taskInfoDao.getTaskInfobyReqSeqNo(reqSeqNo);
		} catch (BaseException e) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),e.getMessage());
		}
		if (taskInfo != null) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_560000.getCode(),"请求方流水号[" + reqSeqNo + "]之前发生的业务还在处理中！");
		}
		
		TaskInfoHis taskInfoHis = null;
		try {
			taskInfoHis = this.taskInfoDao.getTaskInfoHisByReqSeqNo(reqSeqNo);
		} catch (BaseException e) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),e.getMessage());
		}
		if (taskInfoHis != null) {
			if (TransStateEnum.COMPLETE.getCode().equals(taskInfoHis.getTransState())) {
				throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_560000.getCode(),"请求方流水号[" + reqSeqNo + "]之前发生的业务还在处理中！");
			} else {
				//删除垃圾数据
				try {
					this.taskInfoDao.doDeleteTaskInfoHis(taskInfoHis.getTxnSerialNo());
				} catch (BaseException e) {
					throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),e.getMessage());
				}
			}
		}
	}
	
	public TaskInfo getTaskInfoUnionHis(String refNo, String txnSerialNo)
			throws BaseException {
		return this.taskInfoDao.getTaskInfoUnionHis(refNo, txnSerialNo);
	}
	
	public TaskInfoHis getTaskInfoHis(String txnSerialNo) throws BaseException {
		return this.taskInfoDao.getTaskInfoHis(txnSerialNo);
	}
	
	public TaskInfoHis getTaskInfoHis(String txnSerialNo, String refNo) {
		return this.taskInfoDao.getTaskInfoHis(txnSerialNo, refNo);
	}
	
	public TaskInfo getTaskInfo(String txnSerialNo) throws BaseException {
		return this.taskInfoDao.getTaskInfo(txnSerialNo);
	}
	
	public void dodeleteTaskInfo(String txnSerialNo) throws BaseException {
		this.taskInfoDao.deleteTaskInfo(txnSerialNo);
	}
	
	public TaskInfo getTaskInfobyReqSeqNo(String reqSeqNo) throws BaseException {
		return this.taskInfoDao.getTaskInfobyReqSeqNo(reqSeqNo);
	}
	
	
}
