package com.global.workflow.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.workflow.dao.ProcessHistoryDao;
import com.global.workflow.domain.ProcessHistory;

/**
 * 
 * @author chen.feng
 * @date 2013-6-11 下午5:47:21
 */
@Repository
public class ProcessHistoryDaoImpl extends BaseDaoSupport implements
		ProcessHistoryDao {

	public void save(ProcessHistory processHistory) throws BaseException {
		super.insert(processHistory);
	}

	@SuppressWarnings("unchecked")
	public List<ProcessHistory> getProcessHistoryList() throws BaseException {
		return (List<ProcessHistory>) super.findForList(ProcessHistory.class);
	}

	public PageBean getProcessHistoryForPage(String txnSerialNo, PageBean page)
			throws BaseException {
		String sql = "select ph.*,(select username from sys_user where userid=ph.procuserid) procUserName from wfl_processhistory ph where ph.txnserialno=?";
		return super.findForPage(sql, new Object[] { txnSerialNo }, page,
				ProcessHistory.class);
	}
}
