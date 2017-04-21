package com.global.framework.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.SysLogDao;
import com.global.framework.system.domain.ExceptionLog;
/**
 * 异常操日志DAO
 * 
 * @author bai.shulun
 * @date 2015-7-28 
 * 
 */

@Repository("SysLogDao")
public class SysLogDaoImpl extends BaseDaoSupport implements SysLogDao{

	public PageBean queryLogForPage( ExceptionLog log, PageBean page)
			throws BaseException {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from sys_exceptionlog t where 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isNotBlank(log.getBizNo())) {
			sql.append(" and t.bizNo = ? ");
			list.add(log.getBizNo());
		}
		if (StringUtils.isNotBlank(log.getStartDate()) && StringUtils.isNotBlank(log.getEndDate())) {			
			sql.append(" and t.operateDate >= to_date(?,'yyyy-MM-dd') and t.operateDate <= to_date(?,'yyyy-MM-dd')");
			list.add(log.getStartDate());
			list.add(log.getEndDate());
		}
		
		return super.findForPage(sql.toString(), list.toArray(), page,
				ExceptionLog.class);
	}
    
	public void delete(String exceptionLogId) throws BaseException {
		String sql = "delete from sys_exceptionlog t where t.exceptionLogId = ?　";
		super.delete(sql, new Object[] { exceptionLogId });
	}

	public ExceptionLog findById(String exceptionLogId) throws BaseException {
		String sql="select * from sys_exceptionlog t where t.exceptionlogid=?";
		return super.findForObjectBySql(sql, new Object[]{exceptionLogId}, ExceptionLog.class);
	}
	

}
