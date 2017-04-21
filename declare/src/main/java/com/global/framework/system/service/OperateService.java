package com.global.framework.system.service;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.Operate;

/**
 * @author chen.feng
 * @date 2013-3-25
 * @version v1.0
 */
public interface OperateService {

	public List<String> getRightTree(String roleId) throws BaseException;

	public Operate saveOrUpdateOperate(Operate operate) throws BaseException;

	public void deleteOperate(String operateIds) throws BaseException;

	public PageBean queryOperateForPage(Operate operate, PageBean page)
			throws BaseException;

	public List<Operate> getOperateList() throws BaseException;

	public List<Operate> getNoCheckOperateList() throws BaseException;
}
