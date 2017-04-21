package com.global.framework.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.Entity;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.ExceptionLogDao;
import com.global.framework.system.domain.ExceptionLog;

@SuppressWarnings("unchecked")
@Repository("exceptionLogDao")
public class ExceptionLogDaoImpl  extends BaseDaoSupport  implements ExceptionLogDao{

	public ExceptionLog insert(ExceptionLog log) throws BaseException {
		return super.insert(log);
	}

	public void update(ExceptionLog log) throws BaseException {
		super.update(log);
	}

	public void delete(ExceptionLog log) throws BaseException {
		super.delete(log);
	}

	public List<ExceptionLog> findList(Map<String, Object> prm) throws BaseException {
		return (List<ExceptionLog>) super.findForList(ExceptionLog.class, prm);
	}

	public <T extends Entity> List<T> findList(Class<T> entity,
			Map<String, Object> prm) throws BaseException {
		return (List<T>) super.findForList(entity, prm);
	}
}
