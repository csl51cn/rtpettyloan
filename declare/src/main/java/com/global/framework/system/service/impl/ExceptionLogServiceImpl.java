package com.global.framework.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.dbutils.support.Entity;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.ExceptionLogDao;
import com.global.framework.system.domain.ExceptionLog;
import com.global.framework.system.service.ExceptionLogService;

@Service("exceptionLogService")
public class ExceptionLogServiceImpl implements ExceptionLogService {
	
	@Autowired
	private ExceptionLogDao exceptionLogDao;
	
	
	
	public ExceptionLog insert(ExceptionLog log) throws BaseException {
		return exceptionLogDao.insert(log);
	}

	
	public void update(ExceptionLog log) throws BaseException {
		exceptionLogDao.update(log);
	}

	
	public void delete(ExceptionLog log) throws BaseException {
		exceptionLogDao.delete(log);
	}

	
	public <T extends Entity> List<T> findList(Class<T> entity,Map<String, Object> prm) throws BaseException {
		return exceptionLogDao.findList(entity, prm);
	}
	
	
}
