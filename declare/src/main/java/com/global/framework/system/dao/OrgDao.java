package com.global.framework.system.dao;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.Org;

/**
 * @author cqchenf@qq.com
 * @date 2011-9-7 下午10:57:10
 * @version v1.0
 */
public interface OrgDao {

	/**
	 * 查询机构列表
	 * 
	 * @return
	 */
	public List<Org> getOrgList() throws BaseException;

	/**
	 * 分页查询机构列表
	 * 
	 * @param org
	 * @param page
	 * @return @
	 */
	public PageBean queryOrgForPage(Org org, PageBean page)
			throws BaseException;

	/**
	 * 新增机构信息
	 * 
	 * @param org
	 * @return
	 */
	public Org insertOrg(Org org) throws BaseException;

	/**
	 * 修改机构信息
	 * 
	 * @param org
	 * @return
	 */
	public Org updateOrg(Org org) throws BaseException;

	/**
	 * 删除机构信息
	 * 
	 * @param org
	 */
	public void deleteOrg(Org org) throws BaseException;

	/**
	 * 获取机构信息
	 * 
	 * @param org
	 * @return
	 */
	public Org getOrgInfo(Org org) throws BaseException;

	/**
	 * 根据机构层级获取当前最大机构号
	 * 
	 * @param orgLevel
	 * @return
	 */
	public String getMaxOrgNoByLevel(String orgLevel) throws BaseException;
	
	
	/**
	 * 获取机构信息
	 * 
	 * @param org
	 * @return
	 */
	public Org getOrgInfo(String bankCode) throws BaseException;
}
