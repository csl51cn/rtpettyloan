package com.global.framework.system.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.Operate;
import com.global.framework.system.domain.RoleRight;
import com.global.framework.system.domain.ZTreeNode;

/**
 * @author chen.feng
 * @date 2013-3-25
 * @version v1.0
 */
public interface OperateDao {

	public List<ZTreeNode> getRightTree() throws BaseException;

	public List<RoleRight> getRightsByRoleId(String roleId)
			throws BaseException;

	public Operate saveOperate(Operate operate) throws BaseException;

	public void deleteOperate(LinkedList<Operate> operateList) throws BaseException;

	public PageBean queryOperateForPage(Operate right, PageBean page)
			throws BaseException;

	public List<Operate> getOperateList() throws BaseException;

	/**
	 * 查询不需要校验权限的按钮列表
	 * @return
	 * @throws BaseException
	 */
	public List<Operate> getNoCheckOperateList() throws BaseException;

	public Operate updateOperte(Operate operate) throws BaseException;

	public Map<String, Object> queryTradeTemplateByPrivID(String privId) throws BaseException;
}
