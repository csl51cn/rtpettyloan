package com.global.fems.business.dao;

import java.util.List;
import java.util.Map;

import com.global.fems.business.domain.SpfeMkUp;
import com.global.framework.exception.BaseException;

public interface SpfeMkUpDao {

	/**
	 * 插入
	 * @param mode
	 * @return
	 */
	public SpfeMkUp insert(SpfeMkUp mode) throws BaseException;
	
	/**
	 * 修改
	 * @param mode
	 * @throws BaseException
	 */
	public void update(SpfeMkUp mode) throws BaseException;
	
	/**
	 * 删除
	 * @param mode
	 * @throws BaseException
	 */
	public void delete(SpfeMkUp mode) throws BaseException;
	
	/**
	 * 通过主键获取对象
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public SpfeMkUp findById(String id)  throws BaseException;
	
	/**
	 * 列表
	 * @param prm
	 * @return
	 * @throws BaseException
	 */
	List<SpfeMkUp> findList(Map<String, Object> prm) throws BaseException;
	
}
