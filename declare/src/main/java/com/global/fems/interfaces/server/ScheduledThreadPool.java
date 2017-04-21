package com.global.fems.interfaces.server;

import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class ScheduledThreadPool {
	private static final Logger log = Logger
			.getLogger(ScheduledThreadPool.class);
	private static ScheduledThreadPool instance = null;
	
	/**
	 * 线程池维护线程的最少数量,默认为10
	 */
	private int core_pool_size = 10;

	/**
	 * ThreadPoolExecutor.AbortPolicy():抛出RejectedExecutionException
	 */
	private static RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
	private ScheduledThreadPoolExecutor threadPool = null;

	/**
	 * 定时线程池
	 * 
	 * @param core_pool_size
	 *            线程池维护线程的最少数量
	 * @param delay_time
	 *            线程池延迟执行的时间(秒)
	 */
	private ScheduledThreadPool() {
		try {
			this.core_pool_size = Integer.parseInt(ServerConfigReader
					.getInstance().getPoolSize());
			threadPool = new ScheduledThreadPoolExecutor(this.core_pool_size,
					rejectedExecutionHandler);
		} catch (NumberFormatException e) {
			log.error("线程池数量配置有误", e);
		}
	}

	/**
	 * 
	 * @param task
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public void submit(Runnable task) throws Exception {
		try {
			Future f = threadPool.submit(task);
			f.get();
		} catch (RejectedExecutionException e) {
			log.error("线程池工作队列繁忙!");
			throw new Exception(e);
		} catch (Exception e) {
			log.error("线程池内部异常：", e);
			throw new Exception(e);
		}
	}

	public static ScheduledThreadPool getInstance() {
		if (instance == null) {
			instance = new ScheduledThreadPool();
		}
		return instance;
	}
}
