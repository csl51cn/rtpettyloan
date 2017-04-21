package com.global.fems.business.dao;

import java.util.List;
import java.util.Map;

import com.global.fems.business.domain.SpfeCsr;
import com.global.fems.business.domain.SpfeMdf;
import com.global.framework.exception.BaseException;

public interface SpfeCsrDao {

	/**
	 * 插入
	 * @param mode
	 * @return
	 */
	public SpfeCsr insert(SpfeCsr mode) throws BaseException;
	
	/**
	 * 修改
	 * @param mode
	 * @throws BaseException
	 */
	public void update(SpfeCsr mode) throws BaseException;
	
	/**
	 * 删除
	 * @param mode
	 * @throws BaseException
	 */
	public void delete(SpfeCsr mode) throws BaseException;
	
	/**
	 * 通过主键获取对象
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public SpfeCsr findById(String id)  throws BaseException;
	
	/**
	 * 列表
	 * @param prm
	 * @return
	 * @throws BaseException
	 */
	List<SpfeCsr> findList(Map<String, Object> prm) throws BaseException;

	public SpfeCsr findByKey(String txnSerialNo) throws BaseException;

	public SpfeMdf findSpfeInfoByCsrId(String txnSerialNo);
	
}
