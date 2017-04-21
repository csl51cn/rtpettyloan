package com.global.workflow.dao;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TaskInfoHis;

/**
 * 
 * @author chen.feng
 * @date 2013-6-7 下午11:57:34
 */
public interface TaskInfoDao {

	public void saveTaskInfo(TaskInfo taskInfo) throws BaseException;

	public TaskInfo getTaskInfo(String txnSerialNo) throws BaseException;
	
	public TaskInfoHis getTaskInfoHis(String txnSerialNo) throws BaseException;

	public TaskInfo getTaskInfoUnionHis(String refNo, String txnSerialNo) throws BaseException;
	
	public TaskInfo getTaskInfoForCancel(String txnSerialNo)  throws BaseException;
	
	public void updateTaskInfo(TaskInfo taskInfo) throws BaseException;

	public void deleteTaskInfo(String txnSerialNo) throws BaseException;

	public void saveTaskInfoHis(TaskInfoHis taskInfoHis) throws BaseException;

	public PageBean queryWaitTaskListByUserId(TaskInfo task, User user,
                                              List<String> orgPrivList, PageBean page) throws BaseException;

	public PageBean queryFinishTaskListByUserID(TaskInfo task, User user,
                                                PageBean page) throws BaseException;

	public PageBean queryPartTaskListByUserID(TaskInfo task, User user,
                                              PageBean page) throws BaseException;

	public PageBean queryFlowHistoryList(String txnSerialNo, PageBean page)
			throws BaseException;
	
	public String queryTradeUnionMenu(String tradeNo) throws BaseException;
	
	/**
	 * 查询历史任务信息
	 * 
	 * @param txnSerialNo  交易流水号
	 * @return
	 * @throws BaseException
	 */
	public TaskInfoHis queryTaksInfoHis(String txnSerialNo) throws BaseException;

	public int queryTaskCountByUserID(String userId, String flag, List<String> orgPrivList) throws BaseException;

	public int getTaskCountByReqSeqNo(String reqSeqNo);

	public TaskInfoHis getTaskInfoHis(String txnSerialNo, String refNo);

	public void updateTaskInfoHisStatus(String primaryBizNo, String sfzx);
	
	public TaskInfo getTaskInfobyReqSeqNo(String reqSeqNo) throws BaseException;

	public TaskInfoHis getTaskInfoHisByReqSeqNo(String reqSeqNo) throws BaseException;

	public void doDeleteTaskInfoHis(String txnSerialNo) throws BaseException;
	
}
