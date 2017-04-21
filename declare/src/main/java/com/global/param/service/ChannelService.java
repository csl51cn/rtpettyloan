package com.global.param.service;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.domain.Channel;

public interface ChannelService {
	/**
	 * 分页查询渠道
	 * @param channel
	 * @param page
	 * @return
	 * @throws BaseException
	 */
	public PageBean queryChannelForPage(Channel channel, PageBean page) throws BaseException;
	
	/**
	 * 新增或更新渠道
	 * @param channel
	 * @throws BaseException
	 */
	public Channel saveOrUpdateChannel(Channel channel) throws BaseException;
	
	/**
	 * 删除渠道
	 * @param channel
	 * @throws BaseException
	 */
	public void deleteChannel(Channel channel) throws BaseException;
	
	/**
	 * 根据id查询
	 * @param channel
	 * @return
	 * @throws BaseException
	 */
	public Channel queryChannelById(String channelId) throws BaseException;
	
	/**
	 * 检查该渠道代码是否存在
	 * 
	 * @param channelCode
	 * @return
	 */
	public boolean checkChannelCodeExist(String channelCode) throws BaseException;

	public List<Channel> getChannelList()throws BaseException;

	public Channel queryChannelByReqSysCode(String reqSysCode)throws BaseException;

	public boolean checkReqSysCodeExist(String reqSysCode)throws BaseException;
}
