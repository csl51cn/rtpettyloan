package com.global.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.OperateLog;
import com.global.framework.system.domain.User;
import com.global.framework.system.web.common.session.SessionManager;
import com.global.framework.system.web.common.util.SysLogUtil;
import com.global.framework.util.SysUtils;

/**
 * Controller公共类
 * 
 * @author chen.feng
 * 
 */
public class BaseController {

	/** 列表总数 */
	public static final String LIST_TOTAL = "total";
	/** 列表数据List */
	public static final String LIST_ROWS = "rows";

	/** 操作/异常提示信息 */
	public static final String OP_MESSAGE = "message";
	/** 操作异常堆栈信息 */
	public static final String OP_EXCEPTION = "exception";
	/** 操作状态,true/false */
	public static final String OP_STATUS = "status";
	/** 操作成功后返回的主键 */
	public static final String OP_PK = "key";

	/** 跳转到错误提示页面 */
	public static final String ERROR = "/error";
	/** 跳转到成功提示页面 */
	public static final String SUCCESS = "/success";

	protected User getSessionUser(HttpServletRequest request) {
		return SessionManager.getSession(request);
	}

	protected void log(String logType, String logContent,
			HttpServletRequest request) throws BaseException {
		OperateLog log = new OperateLog();
		log.setUserId(SessionManager.getSession(request).getUserCode());
		log.setOperateType(logType);
		log.setContent(logContent);
		log.setOperateIp(SessionManager.getIpAddr(request));
		log.setOperateDate(SysUtils.getNowDateTime());
		SysLogUtil.log(log);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleException(Exception ex, HttpServletRequest request) {
		return new ModelAndView().addObject("error", ex.getMessage());
	}
}
