package com.global.framework.system.service;

import java.util.List;
import java.util.Set;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.Org;
import com.global.framework.system.domain.TreeNode;
import com.global.framework.system.domain.User;

/**
 * @author cqchenf@163.com
 * @date 2011-9-7 下午11:37:50
 * @version v1.0
 */
public interface OrgService {
	
	/**
	 * 查询机构列表
	 * @return
	 */
	public List<Org> getOrgList() throws BaseException;
	
	/**
	 * 分页查询机构列表
	 * @param org 查询条件
	 * @param page
	 * @return
	 * @
	 */
	public PageBean queryOrgForPage(Org org, PageBean page) throws BaseException ;
	
	/**
	 * 保存机构信息
	 * @param org
	 */
	public Org saveOrUpdate(Org org) throws BaseException ;
	
	/**
	 * 删除机构信息
	 * @param org
	 */
	public void delete(Org org) throws BaseException ;

	/**
	 * 加载机构树
	 * @return
	 */
	public List<TreeNode> loadOrgTree() throws BaseException ;

	/**
	 * 获取机构信息
	 * @param org
	 * @return
	 */
	public Org getOrgInfo(Org org) throws BaseException;

	public Set<Org> getRightOrgList(User user) throws BaseException;
}
