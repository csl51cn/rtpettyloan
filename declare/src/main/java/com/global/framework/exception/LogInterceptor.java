package com.global.framework.exception;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Spring 统一日志处理实现类
 * 
 * @author:feng.chen
 * @date:2014-2-10
 * @version:V1.0
 */
public class LogInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {

		System.out.println(invocation.getMethod() + ":BEGIN!--");// 方法前的操作
		Object obj = invocation.proceed();// 执行需要Log的方法
		System.out.println(invocation.getMethod() + ":END!--");// 方法后的操作

		return obj;
	}

}
