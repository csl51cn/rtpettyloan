package com.global.param.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.fems.interfaces.BizChnlCodeEnum;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.service.SysCommonService;
import com.global.param.dao.ChannelDao;
import com.global.param.domain.Channel;
import com.global.param.service.ChannelService;

@Service("ChannelService")
public class ChannelServiceImpl implements ChannelService {
	
	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private SysCommonService sysCommonService;

	
	public PageBean queryChannelForPage(Channel channel, PageBean page) 
			throws BaseException {
		return this.channelDao.queryChannelForPage(channel, page);
	}

	
	public Channel saveOrUpdateChannel(Channel channel) throws BaseException {
		if (StringUtils.isNotBlank(channel.getBizChnlCode())) {
			channel.setBizChnlName(BizChnlCodeEnum.getValueByCode(channel.getBizChnlCode()));
		}
		if(channel != null && StringUtils.isNotBlank(channel.getChannelId())){
			Channel c = this.queryChannelById(channel.getChannelId());
			if (!c.getChannelCode().equals(channel.getChannelCode())) {
				if (!this.checkChannelCodeExist(channel.getChannelCode())){
					throw new BaseException("渠道代码["+channel.getChannelCode()+"]已存在");
				}
			}
			if (!c.getReqSysCode().equals(channel.getReqSysCode())) {
				if (!this.checkReqSysCodeExist(channel.getReqSysCode())) {
					throw new BaseException("接入系统代码["+channel.getReqSysCode()+"]已存在");
				}
			}
			return this.channelDao.updateChannel(channel);
		}else{
			channel.setChannelId(sysCommonService.getSeqNo("PA_CHANNEL"));
			channel.setIsValid("Y");
			//判断渠道号和接入系统代码是否重复
			if (!this.checkChannelCodeExist(channel.getChannelCode())){
				throw new BaseException("渠道代码["+channel.getChannelCode()+"]已存在");
			}
			if (!this.checkReqSysCodeExist(channel.getReqSysCode())) {
				throw new BaseException("接入系统代码["+channel.getReqSysCode()+"]已存在");
			}
			return this.channelDao.saveChannel(channel);
		}
	}

	
	public void deleteChannel(Channel channel) throws BaseException {
		this.channelDao.deleteChannel(channel);
	}

	
	public Channel queryChannelById(String channelId) throws BaseException {
		return this.channelDao.queryChannelById(channelId);
	}

	
	public boolean checkChannelCodeExist(String channelCode) throws BaseException {
		return this.channelDao.checkChannelCodeExist(channelCode);
	}

	
	public List<Channel> getChannelList() throws BaseException {
		return this.channelDao.getChannelList();
	}
	
	
	public Channel queryChannelByReqSysCode(String reqSysCode)
			throws BaseException {
		return this.channelDao.queryChannelByReqSysCode(reqSysCode);
	}
	
	
	public boolean checkReqSysCodeExist(String reqSysCode) throws BaseException {
		return this.channelDao.checkReqSysCodeExist(reqSysCode);
	}
}
