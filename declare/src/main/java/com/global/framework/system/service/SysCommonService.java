package com.global.framework.system.service;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.CommonOrgUser;
import com.global.framework.system.domain.Property;

/**
 * 
 * @author chen.feng
 * @date 2012-6-15 下午6:34:55
 */
public interface SysCommonService {

	/**
	 * 获取业务流水号
	 * @param objName
	 * @return
	 */
	public String getSeqNo(String objName) throws BaseException;
	
	/**
	 * 根据柜员号生成流水号,格式为:金融机构代码（4位）+年月日+8位唯一代码
	 * @param objName
	 * @param operNo
	 * @return
	 * @throws BaseException
	 */
	public String getSeqNoByOperNo(String objName, String operNo) throws BaseException;
	
	/**
	 * 获取业务编号
	 * @param objName
	 * @return
	 */
	public String getBizNo(String objName) throws BaseException;

	public PageBean queryCommonOrgUserForPage(CommonOrgUser user, PageBean page) throws BaseException;
	
	public CommonOrgUser getCommonOrgUser(String operNo) throws BaseException;
	
	public CommonOrgUser insertCommonOrgUser(CommonOrgUser user) throws BaseException;
	
	public void updateCommonOrgUser(CommonOrgUser user) throws BaseException;

	public void delCommonOrgUser(String operNo) throws BaseException;

	public CommonOrgUser saveOrUpdateCommonUser(CommonOrgUser user)throws BaseException;

	public List<Property> getPropertyList(String[] args)throws BaseException;

	public List<CommonOrgUser> getCommonOrgUserList()throws BaseException;
}
