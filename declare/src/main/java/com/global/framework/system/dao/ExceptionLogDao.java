package com.global.framework.system.dao;

import java.util.List;
import java.util.Map;

import com.global.framework.dbutils.support.Entity;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.ExceptionLog;

/**
 * 异常日志
 * @author longjun
 *
 */
public interface ExceptionLogDao {
	

	/**
	 * 插入
	 * @param log
	 * @return
	 */
	public ExceptionLog insert(ExceptionLog log) throws BaseException;
	
	/**
	 * 修改
	 * @param log
	 * @throws BaseException
	 */
	public void update(ExceptionLog log) throws BaseException;
	
	/**
	 * 删除
	 * @param log
	 * @throws BaseException
	 */
	public void delete(ExceptionLog log) throws BaseException;
	
	/**
	 * 列表
	 * @param prm
	 * @return
	 * @throws BaseException
	 */
	List<ExceptionLog> findList(Map<String, Object> prm) throws BaseException;
	

	<T  extends Entity> List<T> findList(Class<T> entity, Map<String, Object> prm) throws BaseException;
}
