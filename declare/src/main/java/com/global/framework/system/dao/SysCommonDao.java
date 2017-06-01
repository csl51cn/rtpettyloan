package com.global.framework.system.dao;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.CommonOrgUser;
import com.global.framework.system.domain.Property;

import java.util.List;

/**
 * 
 * @author chen.feng
 * @date 2012-6-15 下午6:33:11
 */
public interface SysCommonDao {

	public String getSeqNo(String objName) throws BaseException;

	public String getNo(String objName) throws BaseException;

	public String getBizNo(String objName) throws BaseException;
	
	public Property getProperty(String key) throws BaseException; 
	
	public void updateProperty(Property p) throws BaseException; 
	
	public List<Property> getPropertyList(String[] args) throws BaseException;
	
	public CommonOrgUser getCommonOrgUser(String operNo) throws BaseException;
	
	public CommonOrgUser insertCommonOrgUser(CommonOrgUser user) throws BaseException;
	
	public void updateCommonOrgUser(CommonOrgUser user) throws BaseException;

	public PageBean queryCommonOrgUserForPage(CommonOrgUser user, PageBean page) throws BaseException;

	public void delCommonOrgUser(String operNo) throws BaseException;

	public List<CommonOrgUser> getCommonOrgUserList() throws BaseException;
}
