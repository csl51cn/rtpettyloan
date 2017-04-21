package com.global.framework.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.global.framework.system.domain.ExceptionLog;
import com.global.framework.system.service.ExceptionLogService;
import com.global.framework.util.SpringContextUtil;

public class ExceptionServiceHandle {
	
	private final static Logger logger = LoggerFactory.getLogger(ExceptionServiceHandle.class);
	
	private static ExceptionLogService exceptionLogService;
	
	private static ExceptionLogService getOperationLogService(){
		if(null == exceptionLogService){
			exceptionLogService = (ExceptionLogService) SpringContextUtil.getBean("exceptionLogService");
		}
		return exceptionLogService;
	}
	
	public  static void saveExceptionLog(Exception e,String methodName){
		ExceptionLog elog = new ExceptionLog();
//		elog.setBizeno("");
//		elog.setExceptiontype("");
//		elog.setIsDealed("N");
//		elog.setOperatdate(new Date());
		try {
			getOperationLogService().insert(elog);
		} catch (BaseException e1) {
			logger.error("发生的异常信息：{}", e1);
		}
	};
	
}
