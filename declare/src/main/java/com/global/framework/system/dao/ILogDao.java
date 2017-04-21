package com.global.framework.system.dao;

import java.util.LinkedList;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.OperateLog;

/**
 * @author cqchenf@qq.com
 * @date 2012-1-5 下午9:28:11
 * @version v1.0
 */
public interface ILogDao {

	public void insertLog(OperateLog log) throws BaseException;

	public PageBean queryLogForPage(OperateLog operateLog, PageBean page) throws BaseException;

	public void deleteLog(LinkedList<OperateLog> list) throws BaseException;

}
