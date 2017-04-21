package com.global.framework.system.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.ILogDao;
import com.global.framework.system.domain.OperateLog;
import com.global.framework.system.service.LogService;

/**
 * @author chen.feng
 * @date 2013-3-27
 * @version v1.0
 */
@Service("logService")
public class LogServiceImpl implements LogService {

	@Autowired
	private ILogDao logDao;

	
	public void insertLog(OperateLog log) throws BaseException {
		this.logDao.insertLog(log);
	}

	
	public PageBean queryLogForPage(OperateLog operateLog, PageBean page)
			throws BaseException {
		return this.logDao.queryLogForPage(operateLog, page);
	}

	
	public void deleteLog(String logId) throws BaseException {
		String[] logIds = logId.split(",");
		LinkedList<OperateLog> list = new LinkedList<OperateLog>();
		for (int i = 0; i < logIds.length; i++) {
			OperateLog log = new OperateLog();
			log.setLogId(logIds[i]);
			list.add(log);
		}
		this.logDao.deleteLog(list);
	}
}
