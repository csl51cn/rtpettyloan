package com.global.framework.system.service;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.OperateLog;

/**
 * @author chen.feng
 * @date 2013-3-27
 * @version v1.0
 */
public interface LogService {

	public void insertLog(OperateLog log) throws BaseException;

	public PageBean queryLogForPage(OperateLog operateLog, PageBean page)
			throws BaseException;

	public void deleteLog(String logId) throws BaseException;
}
