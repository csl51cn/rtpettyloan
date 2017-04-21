package com.global.param.dao;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.domain.ChannelInterface;

/**
 * 类描述：
 * 
 * @author chen.feng
 * @date 2015-7-10
 * @version v1.0
 */
public interface ChannelInterfaceDao {

	public PageBean queryChannelInterfaceForPage(ChannelInterface channelInterface, PageBean page)
			throws BaseException;

	public ChannelInterface saveChannelInterface(ChannelInterface channelInterface) throws BaseException;

	public ChannelInterface updateChannelInterface(ChannelInterface channelInterface) throws BaseException;

	public void deleteChannelInterface(ChannelInterface channelInterface) throws BaseException;

	public List<ChannelInterface> queryChannelInterface(String channelId)throws BaseException;

	public void deleteChannelInterface(String channelId)throws BaseException;

	public void saveChannelInterface(List<ChannelInterface> list)throws BaseException;

	public ChannelInterface getChannelInterface(String channelId, String faceCode) throws BaseException;
}
