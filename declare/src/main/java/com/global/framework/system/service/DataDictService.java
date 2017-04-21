package com.global.framework.system.service;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.DataDict;
import com.global.framework.system.domain.DataDictClass;

/**
 * 
 * @author cqchenf@qq.com
 * @date 2012-8-28 下午4:58:37
 * @version v1.0
 */
public interface DataDictService {

	/**
	 * 分页查询数据字典分类
	 * 
	 * @param dictClass
	 * @param pageBean
	 * @return
	 */
	public PageBean queryDictClassForPage(DataDictClass dictClass,
                                          PageBean pageBean) throws BaseException;

	/**
	 * 新增数据字典分类
	 * 
	 * @param dictClass
	 */
	public DataDictClass saveOrUpdateDictClass(DataDictClass dictClass) throws BaseException;

	/**
	 * 删除数据字典分类
	 * 
	 * @param dictClass
	 * @return
	 */
	public int deleteDictClass(DataDictClass dictClass) throws BaseException;

	/**
	 * 获取字典分类信息
	 * 
	 * @param dictClass
	 * @return
	 */
	public DataDictClass getDictClass(DataDictClass dictClass)
			throws BaseException;

	/**
	 * 编辑字典分类
	 * 
	 * @param dictClass
	 */
	public void updateDictClass(DataDictClass dictClass) throws BaseException;

	/**
	 * 获取所有字典分类
	 * 
	 * @return
	 */
	public List<DataDictClass> getDictClassList() throws BaseException;

	// =========================================================数据字典代码
	/**
	 * 分页查询数据字典代码
	 * 
	 * @param dataDict
	 * @param pageBean
	 * @return
	 */
	public PageBean queryDataDictForPage(DataDict dataDict, PageBean pageBean)
			throws BaseException;

	/**
	 * 新增数据字典代码
	 * 
	 * @param dataDict
	 */
	public DataDict saveOrUpdateDataDict(DataDict dataDict) throws BaseException;

	/**
	 * 删除数据字典代码
	 * 
	 * @param dataDict
	 * @return
	 */
	public void deleteDataDict(DataDict dataDict) throws BaseException;

	/**
	 * 获取字典代码信息
	 * 
	 * @param dataDict
	 * @return
	 */
	public DataDict getDataDict(DataDict dataDict) throws BaseException;

	/**
	 * 编辑字典代码
	 * 
	 * @param dataDict
	 */
	public void updateDataDict(DataDict dataDict) throws BaseException;

	/**
	 * 获取所有字典代码
	 * 
	 * @return
	 */
	public List<DataDict> getDataDictList() throws BaseException;

	/**
	 * 获取所有字典代码
	 * 
	 * @param code
	 * @return
	 */
	public List<DataDict> getDataDictList(String code) throws BaseException;

	public List<String> getDictCatelog()throws BaseException;

}
