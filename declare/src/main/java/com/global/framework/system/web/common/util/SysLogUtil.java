package com.global.framework.system.web.common.util;

import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.OperateLog;
import com.global.framework.system.service.LogService;
import com.global.framework.util.SpringContextUtil;

/**
 * @author chen.feng
 * @date 2013-3-27 
 * @version v1.0
 */
public class SysLogUtil {

	private static LogService logService = (LogService) SpringContextUtil.getBean("logService");
	
	public static void log(OperateLog log) throws BaseException{
		logService.insertLog(log);
	}
}
