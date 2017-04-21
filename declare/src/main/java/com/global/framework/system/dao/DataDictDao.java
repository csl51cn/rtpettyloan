package com.global.framework.system.dao;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.DataDict;
import com.global.framework.system.domain.DataDictClass;
import com.global.framework.system.domain.ZTreeNode;

/**
 * 数据字典DAO
 * @author cqchenf@qq.com
 * @date 2013-2-28 下午11:03:35
 * @version v1.0
 */
public interface DataDictDao {

	/**
	 * 分页查询数据字典分类
	 * 
	 * @param dictClass
	 * @param pageBean
	 * @return
	 * @throws BaseException
	 */
	public PageBean queryDictClassForPage(DataDictClass dictClass,
                                          PageBean pageBean) throws BaseException;

	/**
	 * 新增数据字典分类
	 * 
	 * @param dictClass
	 * @throws BaseException
	 */
	public DataDictClass insertDictClass(DataDictClass dictClass) throws BaseException;

	/**
	 * 删除数据字典分类
	 * 
	 * @param dictClass
	 * @return
	 * @throws BaseException
	 */
	public int deleteDictClass(DataDictClass dictClass) throws BaseException;

	/**
	 * 获取字典分类信息
	 * 
	 * @param dictClass
	 * @return
	 * @throws BaseException
	 */
	public DataDictClass getDictClass(DataDictClass dictClass)
			throws BaseException;

	/**
	 * 编辑字典分类
	 * 
	 * @param dictClass
	 * @throws BaseException
	 */
	public DataDictClass updateDictClass(DataDictClass dictClass) throws BaseException;

	/**
	 * 获取所有字典分类
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<DataDictClass> getDictClassList() throws BaseException;

	// =========================================================数据字典代码
	/**
	 * 分页查询数据字典代码
	 * 
	 * @param dataDict
	 * @param pageBean
	 * @return
	 * @throws BaseException
	 */
	public PageBean queryDataDictForPage(DataDict dataDict, PageBean pageBean)
			throws BaseException;

	/**
	 * 新增数据字典代码
	 * 
	 * @param dataDict
	 * @throws BaseException
	 */
	public DataDict insertDataDict(DataDict dataDict) throws BaseException;

	/**
	 * 删除数据字典代码
	 * 
	 * @param dataDict
	 * @return
	 * @throws BaseException
	 */
	public void deleteDataDict(DataDict dataDict) throws BaseException;

	/**
	 * 获取字典代码信息
	 * 
	 * @param dataDict
	 * @return
	 * @throws BaseException
	 */
	public DataDict getDataDict(DataDict dataDict) throws BaseException;

	/**
	 * 编辑字典代码
	 * 
	 * @param dataDict
	 * @throws BaseException
	 */
	public DataDict updateDataDict(DataDict dataDict) throws BaseException;

	/**
	 * 获取所有字典代码
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<DataDict> getDataDictList() throws BaseException;

	/**
	 * 获取字典代码
	 * 
	 * @param code
	 * @return
	 * @throws BaseException
	 */
	public List<DataDict> getDataDictList(String code) throws BaseException;

	public List<ZTreeNode> getDataDictListForZTreeNode()throws BaseException;
}
