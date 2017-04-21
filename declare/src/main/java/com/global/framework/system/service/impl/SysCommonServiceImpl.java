package com.global.framework.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.SysCommonDao;
import com.global.framework.system.domain.CommonOrgUser;
import com.global.framework.system.domain.Property;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.util.MD5Utils;

/**
 * 
 * @author chen.feng
 * @date 2012-6-15 下午6:35:46
 */
@Service("sysCommonService")
public class SysCommonServiceImpl implements SysCommonService {

	@Autowired
	private SysCommonDao sysCommonDao;
	
	public String getSeqNo(String objName) throws BaseException {
		// 流水号
		String seqNo = this.sysCommonDao.getSeqNo(objName);
		return seqNo;
	}
	
	public String getSeqNoByOperNo(String objName, String operNo) throws BaseException {
		// 流水号
		String seqNo = this.sysCommonDao.getSeqNo(objName);
		//通过柜员号找金融机构代码
		Property p = this.sysCommonDao.getProperty("SRC");
		try {
			seqNo = p.getValue() + seqNo;
		} catch (Exception e) {
			throw new BaseException("获取流水号失败", e);
		}
		return seqNo;
	}

	public String getBizNo(String objName) throws BaseException {
		return this.sysCommonDao.getBizNo(objName);
	}
	
	public PageBean queryCommonOrgUserForPage(CommonOrgUser user, PageBean page)
			throws BaseException {
		return this.sysCommonDao.queryCommonOrgUserForPage(user, page);
	}
	
	public CommonOrgUser getCommonOrgUser(String operNo)
			throws BaseException {
		return this.sysCommonDao.getCommonOrgUser(operNo);
	}
	
	public CommonOrgUser insertCommonOrgUser(CommonOrgUser user)
			throws BaseException {
		return this.sysCommonDao.insertCommonOrgUser(user);
	}
	
	public void updateCommonOrgUser(CommonOrgUser user) throws BaseException {
		this.sysCommonDao.updateCommonOrgUser(user);
	}

	public void delCommonOrgUser(String operNo)
			throws BaseException {
		this.sysCommonDao.delCommonOrgUser(operNo);
	}
	
	public CommonOrgUser saveOrUpdateCommonUser(CommonOrgUser user)
			throws BaseException {
		user.setPASSWORD(MD5Utils.md5Encode(user.getPASSWORD()));
		CommonOrgUser u = this.sysCommonDao.getCommonOrgUser(user.getOPERNO());
		if (u != null) {
			this.sysCommonDao.updateCommonOrgUser(user);
			return user;
		} else {
			return this.sysCommonDao.insertCommonOrgUser(user);
		}
	}
	
	public List<Property> getPropertyList(String[] args) throws BaseException {
		return this.sysCommonDao.getPropertyList(args);
	}
	
	public List<CommonOrgUser> getCommonOrgUserList() throws BaseException {
		return this.sysCommonDao.getCommonOrgUserList();
	}
}
