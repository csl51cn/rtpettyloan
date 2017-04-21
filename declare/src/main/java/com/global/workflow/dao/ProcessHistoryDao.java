package com.global.workflow.dao;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.workflow.domain.ProcessHistory;

/**
 * 
 * @author chen.feng
 * @date 2013-6-11 下午5:44:30
 */
public interface ProcessHistoryDao {

	public void save(ProcessHistory processHistory) throws BaseException;

	public List<ProcessHistory> getProcessHistoryList() throws BaseException;

	public PageBean getProcessHistoryForPage(String txnSerialNo, PageBean page)
			throws BaseException;
}
