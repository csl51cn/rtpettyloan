package com.global.workflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.util.SysUtils;
import com.global.workflow.dao.ProcessHistoryDao;
import com.global.workflow.domain.ProcessHistory;
import com.global.workflow.domain.TransStateEnum;
import com.global.workflow.service.ProcessHistoryService;

/**
 * 
 * @author chen.feng
 * @date 2014-6-11 下午6:28:13
 */
@Service
public class ProcesshistoryServiceImpl implements ProcessHistoryService {

	@Autowired
	private ProcessHistoryDao processHistoryDao;

	public void saveProcessHistory(ProcessHistory processHistory)
			throws BaseException {
		processHistoryDao.save(processHistory);
	}

	public void saveProcessHistory(String txnSerialNo,
			TransStateEnum transState, String opeId, String remark)
			throws BaseException {
		ProcessHistory ph = new ProcessHistory();
		ph.setTxnSerialNo(txnSerialNo);
		ph.setStepName(transState.getValue());
		ph.setOperId(opeId);
		ph.setDealTime(SysUtils.getNowDateTime());
		ph.setRemark(remark);
		processHistoryDao.save(ph);
	}

	public List<ProcessHistory> getProcessHistoryList() throws BaseException {
		return processHistoryDao.getProcessHistoryList();
	}

	public PageBean getProcessHistoryForPage(String txnSerialNo, PageBean page)
			throws BaseException {
		return this.processHistoryDao.getProcessHistoryForPage(txnSerialNo,
				page);
	}

}
