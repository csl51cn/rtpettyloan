package com.global.framework.exception;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

/**
 * 自定义业务异常处理类
 * 
 * @author:feng.chen
 * @date:2013-2-17
 * @version:V1.0
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = -3557798839576777848L;
	
	/**
	 * 抛出系统定义的异常错误码
	 * 
	 * @param errorCode
	 */
	public BaseException(String errorCode) {
		super(getErrorMessage(errorCode));
	}

	/**
	 * 直接抛出异常堆栈信息
	 * 
	 * @param throwable
	 */
	public BaseException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * 抛出错误码和堆栈信息
	 * 
	 * @param errorCode
	 * @param ex
	 */
	public BaseException(String errorCode, Throwable ex) {
		super(getErrorMessage(errorCode), ex);
	}

	/**
	 * 根据抛出的错误码查找错误信息时将args替换掉错误信息的占位符
	 * 
	 * @param errorCode
	 * @param args
	 *            此参数对应errorCode枚举中定义的错误描述信息的占位符值
	 */
	public BaseException(String errorCode, String[] args) {
		super(getErrorMessage(errorCode, args));
	}

	/**
	 * 根据抛出的错误码查找错误信息时将args替换掉错误信息的占位符
	 * 
	 * @param errorCode
	 * @param ex
	 * @param args
	 *            此参数对应errorCode枚举中定义的错误描述信息的占位符值
	 */
	public BaseException(String errorCode, Throwable ex, String[] args) {
		super(getErrorMessage(errorCode, args), ex);
	}

	private static String getErrorMessage(String errorCode) {
		String message = null;
		if (StringUtils.isBlank(message)) {
			return errorCode;
		}
		message = message.replaceAll("\\[", "").replaceAll("\\]", "");
		message = MessageFormat.format(message, "");
		return errorCode + ":" + message;
	}
	
	private static String getErrorMessage(String errorCode, Object[] args) {
		String message = null;
		if (StringUtils.isBlank(message)) {
			return errorCode;
		}
		if (args == null || args.length == 0) {
			message = message.replaceAll("\\[", "").replaceAll("\\]", "");
			message = MessageFormat.format(message, "");
		} else {
			message = MessageFormat.format(message, args);
		}
		return errorCode + ":" + message;
	}
	
}
