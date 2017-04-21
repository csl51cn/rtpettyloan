package com.global.framework.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.SysLogDao;
import com.global.framework.system.domain.ExceptionLog;
import com.global.framework.system.service.SysLogService;
/**
 * @author bai.shulun
 * @date 2015-7-28
 * 
 */


@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService{
	
	@Autowired
	private SysLogDao sysLogDao;
	
	public PageBean queryLogForPage(ExceptionLog log, PageBean page)
			throws BaseException {
		return this.sysLogDao.queryLogForPage(log, page);
	}

	public void delete(String exceptionlogId) throws BaseException {		
		sysLogDao.delete(exceptionlogId);
	}

	public ExceptionLog findById(String exceptionLogId) throws BaseException {
		return sysLogDao.findById(exceptionLogId);
		
	}
	
}
