package com.global.fems.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.global.fems.business.dao.TaskTimeoutDao;
import com.global.fems.business.domain.TaskTimeout;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;

/**
 * 类描述： 
 *
 * @author chen.feng
 * @date 2015-7-9
 * @version v1.0
 */
@Repository("taskTimeoutDao")
public class TaskTimeoutDaoImpl extends BaseDaoSupport implements
		TaskTimeoutDao {

	public void insert(TaskTimeout info) throws BaseException {
		super.insert(info);
	}

	@SuppressWarnings("unchecked")
	public List<TaskTimeout> findListByState(String dealState) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealState", dealState);
		return (List<TaskTimeout>) super.findForList(TaskTimeout.class, map);
	}

	public int updateDealState(TaskTimeout timeout) throws BaseException {		
		return super.update(timeout);
	}

}
