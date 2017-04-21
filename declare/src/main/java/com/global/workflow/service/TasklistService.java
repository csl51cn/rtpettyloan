package com.global.workflow.service;

import org.global.framework.xmlbeans.bean.DataCheckException;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TaskInfoHis;

/**
 * 
 * @author chen.feng
 * @date 2015-6-25
 * @version v1.0
 */
public interface TasklistService {

	PageBean queryWaitTaskListByUserID(TaskInfo task, User user, PageBean page)
			throws Exception;

	PageBean queryPartTaskListByUserID(TaskInfo task, User user, PageBean page)
			throws Exception;

	PageBean queryFinishTaskListByUserID(TaskInfo task, User user, PageBean page)
			throws Exception;

	PageBean queryFlowHistoryList(String txnSerialNo, PageBean page)
			throws Exception;

	String doCheckTask(String checkMsg, String opeid) throws Exception;
	
	String doAuthTask(String checkMsg, String opeid) throws Exception;
	
	String queryTradeUnionMenu(String tradeNo) throws BaseException;

	int queryTaskCountByUserID(User user, String string)throws BaseException;

	/**
	 * 取消任务代办
	 * @param txNo 业务流水号
	 * @param serviceBeanId 服务方法 spring beanId
	 * @param opeid 用户id
	 * @throws BaseException
	 */
	void  doCancelTask(String txNo, String serviceBeanId, String opeid) throws Exception;
	
	/**
	 * 根据行内渠道请求流水号检查任务是否存在
	 * 
	 * @param reqSeqNo
	 * @return
	 */
	void checkTaskExistByReqSeqNo(String reqSeqNo) throws DataCheckException;
	
	public TaskInfo getTaskInfoUnionHis(String refNo, String txnSerialNo) throws BaseException;
	
	public TaskInfoHis getTaskInfoHis(String txnSerialNo) throws BaseException;

	TaskInfoHis getTaskInfoHis(String txnSerialNo, String refNo);
	
	TaskInfo getTaskInfo(String txnSerialNo) throws BaseException;
	
	public void dodeleteTaskInfo(String txnSerialNo) throws BaseException;
	
	public TaskInfo getTaskInfobyReqSeqNo(String reqSeqNo) throws BaseException;

}
