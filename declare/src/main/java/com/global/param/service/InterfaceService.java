package com.global.param.service;

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
public interface InterfaceService {

	PageBean queryChannelInterfaceForPage(ChannelInterface info, PageBean page) throws BaseException;

	ChannelInterface saveChannelInterface(ChannelInterface info) throws BaseException;

	void deleteChannelInterface(ChannelInterface info) throws BaseException;

	List<ChannelInterface> queryChannelInterface(String channelId) throws BaseException;

	void saveChannelInterface(String channelId, List<ChannelInterface> list) throws BaseException;

	void updateChannelInterface(ChannelInterface info)throws BaseException;

}
