package com.global.param.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.dao.ChannelInterfaceDao;
import com.global.param.domain.ChannelInterface;
import com.global.param.service.InterfaceService;

/**
 * 类描述： 
 *
 * @author chen.feng
 * @date 2015-7-10
 * @version v1.0
 */
@Service("interfaceService")
public class InterfaceServiceImpl implements InterfaceService {

	@Autowired
	private ChannelInterfaceDao channelInterfaceDao;
	
	
	public PageBean queryChannelInterfaceForPage(ChannelInterface info,
			PageBean page) throws BaseException {
		return channelInterfaceDao.queryChannelInterfaceForPage(info, page);
	}

	
	public ChannelInterface saveChannelInterface(ChannelInterface info)
			throws BaseException {
		return channelInterfaceDao.saveChannelInterface(info);
	}
	
	
	public void saveChannelInterface(String channelId, List<ChannelInterface> list)
			throws BaseException {
		//先删除原来的
		channelInterfaceDao.deleteChannelInterface(channelId);
		this.channelInterfaceDao.saveChannelInterface(list);
	}

	
	public void deleteChannelInterface(ChannelInterface info)
			throws BaseException {
		channelInterfaceDao.deleteChannelInterface(info);
	}
	
	
	public List<ChannelInterface> queryChannelInterface(String channelId)
			throws BaseException {
		return this.channelInterfaceDao.queryChannelInterface(channelId);
	}

	
	public void updateChannelInterface(ChannelInterface info)
			throws BaseException {
		this.channelInterfaceDao.updateChannelInterface(info);
	}
}
