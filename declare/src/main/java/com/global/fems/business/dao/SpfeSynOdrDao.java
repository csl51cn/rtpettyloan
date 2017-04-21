package com.global.fems.business.dao;

import java.util.List;
import java.util.Map;

import com.global.fems.business.domain.SpfeSynOdr;
import com.global.framework.exception.BaseException;

public interface SpfeSynOdrDao {

	/**
	 * 插入
	 * @param mode
	 * @return
	 */
	public SpfeSynOdr insert(SpfeSynOdr mode) throws BaseException;
	
	/**
	 * 修改
	 * @param mode
	 * @throws BaseException
	 */
	public void update(SpfeSynOdr mode) throws BaseException;
	
	/**
	 * 删除
	 * @param mode
	 * @throws BaseException
	 */
	public void delete(SpfeSynOdr mode) throws BaseException;
	
	/**
	 * 通过主键获取对象
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public SpfeSynOdr findById(String id)  throws BaseException;
	
	/**
	 * 列表
	 * @param prm
	 * @return
	 * @throws BaseException
	 */
	List<SpfeSynOdr> findList(Map<String, Object> prm) throws BaseException;
	
}
