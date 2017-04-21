package com.global.workflow.service;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.workflow.domain.ProcessHistory;
import com.global.workflow.domain.TransStateEnum;

/**
 * 
 * @author chen.feng
 * @date 2013-6-11 下午6:27:39
 */
public interface ProcessHistoryService {

	public void saveProcessHistory(ProcessHistory processHistory)
			throws BaseException;

	public void saveProcessHistory(String txnSerialNo,
                                   TransStateEnum transState, String opeId, String remark)
			throws BaseException;

	public List<ProcessHistory> getProcessHistoryList() throws BaseException;

	public PageBean getProcessHistoryForPage(String txnSerialNo, PageBean page)
			throws BaseException;
}
