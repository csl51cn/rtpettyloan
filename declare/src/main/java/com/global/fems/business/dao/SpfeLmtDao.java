package com.global.fems.business.dao;

import java.util.List;
import java.util.Map;

import com.global.fems.business.domain.SpfeLmt;
import com.global.framework.exception.BaseException;

public interface SpfeLmtDao {

	/**
	 * 插入
	 * @param mode
	 * @return
	 */
	public SpfeLmt insert(SpfeLmt mode) throws BaseException;
	
	/**
	 * 修改
	 * @param mode
	 * @throws BaseException
	 */
	public void update(SpfeLmt mode) throws BaseException;
	
	/**
	 * 删除
	 * @param mode
	 * @throws BaseException
	 */
	public void delete(SpfeLmt mode) throws BaseException;
	
	/**
	 * 通过主键获取对象
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public SpfeLmt findById(String id)  throws BaseException;
	
	/**
	 * 通过业务编号获取对象
	 * @param bizNo
	 * @return
	 * @throws BaseException
	 */
	public SpfeLmt findByBizNo(String bizNo)  throws BaseException;
	/**
	 * 列表
	 * @param prm
	 * @return
	 * @throws BaseException
	 */
	List<SpfeLmt> findList(Map<String, Object> prm) throws BaseException;

	public void updateBySql(SpfeLmt mode) throws BaseException;
	
}
