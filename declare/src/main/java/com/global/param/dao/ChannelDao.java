package com.global.param.dao;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.domain.Channel;

public interface ChannelDao {	
	/**
	 * 分页查询渠道
	 * @param channel
	 * @param page
	 * @return
	 * @throws BaseException
	 */
	public PageBean queryChannelForPage(Channel channel, PageBean page) throws BaseException;
	
	/**
	 * 新增渠道
	 * @param channel
	 * @throws BaseException
	 */
	public Channel saveChannel(Channel channel) throws BaseException;
	
	/**
	 * 更新渠道
	 * @param channel
	 * @throws BaseException
	 */
	public Channel updateChannel(Channel channel) throws BaseException;

	/**
	 * 删除渠道
	 * @param channel
	 * @throws BaseException
	 */
	public void deleteChannel(Channel channel) throws BaseException;
	
	/**
	 * 根据id查询
	 * @param channelId
	 * @throws BaseException
	 */
	public Channel queryChannelById(String channelId) throws BaseException;
	
	
	public Boolean checkChannelCodeExist(String channelCode) throws BaseException;

	public List<Channel> getChannelList() throws BaseException;

	public Channel queryChannelByReqSysCode(String reqSysCode)throws BaseException ;

	public boolean checkReqSysCodeExist(String reqSysCode)throws BaseException ;
	
	public Channel queryChannelByBizChnlCode(String bizChnlCode)throws BaseException ;
}
