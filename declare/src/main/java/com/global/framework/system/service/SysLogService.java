package com.global.framework.system.service;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.ExceptionLog;

/**
 * @author bai.shulun
 * @date 2015-7-28
 * 
 */
public interface SysLogService {

	public PageBean queryLogForPage(ExceptionLog log, PageBean page)
			throws BaseException;

	public void delete(String exceptionlogId) throws BaseException;
 
	public ExceptionLog findById(String exceptionLogId) throws BaseException;

}
