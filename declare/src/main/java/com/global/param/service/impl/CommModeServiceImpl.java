package com.global.param.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.web.common.CacheService;
import com.global.param.dao.CommModeDao;
import com.global.param.domain.CommMode;
import com.global.param.service.CommModeService;

/**
 * 类描述： 
 *
 * @author chen.feng
 * @date 2015-7-10
 * @version v1.0
 */
@Service("commModeService")
public class CommModeServiceImpl implements CommModeService {

	@Autowired
	private CommModeDao commModeDao;

	
	public PageBean queryForPage(CommMode info, PageBean page)
			throws BaseException {
		return this.commModeDao.queryForPage(info, page);
	}

	
	public CommMode saveOrUpdate(CommMode info) throws BaseException {
		info = this.commModeDao.saveOrUpdate(info);
		CacheService.getInstance().loadCommMode();
		return info;
	}

	
	public void delete(CommMode info) throws BaseException {
		this.commModeDao.delete(info);
		CacheService.getInstance().loadCommMode();
	}
	
	
	public CommMode getCommMode(String channelId) throws BaseException {
		return this.commModeDao.getCommMode(channelId);
	}
	
	
	public List<CommMode> getCommModeList() throws BaseException {
		return this.commModeDao.getCommModeList();
	}
}
