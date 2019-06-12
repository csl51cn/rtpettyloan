package com.global.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取上下文中Bean对象的工具类
 * @author cqchenf@qq.com
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取Bean对象
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}
	/**
	 * 通过class获取Bean.
	 *
	 * @param clazz 目标类的Class对象
	 * @param <T>   目标类类型
	 * @return 返回目标类实例
	 * @throws BeansException 如果目标对象不能被创建,抛出异常
	 */
	public static <T> T getBean(Class<T> clazz) throws BeansException {
		return getApplicationContext().getBean(clazz);
	}

}