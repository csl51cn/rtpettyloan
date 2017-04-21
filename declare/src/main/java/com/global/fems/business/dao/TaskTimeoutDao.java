package com.global.fems.business.dao;

import java.util.List;

import com.global.fems.business.domain.TaskTimeout;
import com.global.framework.exception.BaseException;


/**
 * 类描述： 
 *
 * @author chen.feng
 * @date 2015-7-9
 * @version v1.0
 */
public interface TaskTimeoutDao {

	public void insert(TaskTimeout info) throws BaseException;
	
	/**
	 * 查询超时任务列表
	 * 
	 * @param dealState 处理状态 0:未处理 1：处理成功 2：处理失败
	 * @throws BaseException
	 */
	public List<TaskTimeout> findListByState(String dealState) throws BaseException;
	
	/**
	 * 更新处理状态
	 * 
	 * @param dealState 处理状态 0:未处理 1：处理成功 2：处理失败
	 * @param retCode 返回码
	 * @param retMsg 返回消息
	 * @throws BaseException
	 */
	public int updateDealState(TaskTimeout timeout) throws BaseException;
	
	
}
