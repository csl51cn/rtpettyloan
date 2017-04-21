package com.global.framework.exception;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

/**
 * 由Spring AOP调用 输出异常信息，把程序异常抛向业务异常
 * 
 * @author:feng.chen
 * @date:2014-2-10
 * @version:V1.0
 */
public class ExceptionAdvisor implements ThrowsAdvice {

	/**
	 * 
	 * @param method
	 *            抛出异常的执行方法
	 * @param args
	 *            方法的参数
	 * @param target
	 *            抛出异常的对象
	 * @param throwable
	 *            异常信息
	 */
	public void afterThrowing(Method method, Object[] args, Object target,
			Exception ex) {
		System.out.println("产生异常的方法名称：  " + method.getName());

		for (Object o : args) {
			System.out.println("方法的参数：   " + o.toString());
		}

		System.out.println("代理对象：   " + target.getClass().getName());
		System.out.println("抛出的异常:    " + ex.getMessage() + ">>>>>>>"
				+ ex.getCause());
		System.out.println("异常详细信息：　　　" + ex.fillInStackTrace());

		// 在这里判断异常，根据不同的异常返回错误。
//		if (ex.getClass().equals(DataAccessException.class)) {
//			ex.printStackTrace();
//			throw new BaseException("数据库操作失败！");
//		} else if (ex.getClass().toString()
//				.equals(NullPointerException.class.toString())) {
//			ex.printStackTrace();
//			throw new BaseException("调用了未经初始化的对象或者是不存在的对象！");
//		} else if (ex.getClass().equals(IOException.class)) {
//			ex.printStackTrace();
//			throw new BaseException("IO异常！");
//		} else if (ex.getClass().equals(ClassNotFoundException.class)) {
//			ex.printStackTrace();
//			throw new BaseException("指定的类不存在！");
//		} else if (ex.getClass().equals(ArithmeticException.class)) {
//			ex.printStackTrace();
//			throw new BaseException("数学运算异常！");
//		} else if (ex.getClass().equals(ArrayIndexOutOfBoundsException.class)) {
//			ex.printStackTrace();
//			throw new BaseException("数组下标越界!");
//		} else if (ex.getClass().equals(IllegalArgumentException.class)) {
//			ex.printStackTrace();
//			throw new BaseException("方法的参数错误！");
//		} else if (ex.getClass().equals(ClassCastException.class)) {
//			ex.printStackTrace();
//			throw new BaseException("类型强制转换错误！");
//		} else if (ex.getClass().equals(SecurityException.class)) {
//			ex.printStackTrace();
//			throw new BaseException("违背安全原则异常！");
//		} else if (ex.getClass().equals(SQLException.class)) {
//			ex.printStackTrace();
//			throw new BaseException("操作数据库异常！");
//		} else if (ex.getClass().equals(NoSuchMethodError.class)) {
//			ex.printStackTrace();
//			throw new BaseException("方法末找到异常！");
//		} else if (ex.getClass().equals(InternalError.class)) {
//			ex.printStackTrace();
//			throw new BaseException("Java虚拟机发生了内部错误");
//		} else {
//			ex.printStackTrace();
//			throw new BaseException("程序内部错误，操作失败！" + ex.getMessage());
//		}
	}

}