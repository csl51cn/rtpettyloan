package com.global.framework.system.dao.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.ILogDao;
import com.global.framework.system.domain.OperateLog;

/**
 * 操作日志DAO
 * 
 * @author cqchenf@qq.com
 * @date 2013-2-28 下午7:58:26
 * @version v1.0
 */
@Repository("logDao")
public class LogDao extends BaseDaoSupport implements ILogDao {

	public void insertLog(OperateLog log) throws BaseException {
		super.insert(log);
	}

	public PageBean queryLogForPage(OperateLog log, PageBean page)
			throws BaseException {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dc_sys_operatelog t where 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isNotBlank(log.getUserId())) {
			sql.append(" and t.userid = ? ");
			list.add(log.getUserId());
		}
		if (StringUtils.isNotBlank(log.getOperateType())) {
			sql.append(" and t.operatetype = ? ");
			list.add(log.getOperateType());
		}
		if (StringUtils.isNotBlank(log.getStartDate())) {
			sql.append(" and to_char(t.operatedate,'yyyy-MM-dd') >= ? ");
			list.add(log.getStartDate());
		}
		if (StringUtils.isNotBlank(log.getEndDate())) {
			sql.append(" and to_char(t.operatedate,'yyyy-MM-dd') <= ? ");
			list.add(log.getEndDate());
		}
		return super.findForPage(sql.toString(), list.toArray(), page,
				OperateLog.class);
	}

	public void deleteLog(LinkedList<OperateLog> list) throws BaseException {
		super.batchDelete(list);
	}
}
