package com.global.param.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.dao.ChannelDao;
import com.global.param.domain.Channel;

@Repository("ChannelDao")
public class ChannelDaoImpl extends BaseDaoSupport implements ChannelDao {

	
	public PageBean queryChannelForPage(Channel channel, PageBean page) throws BaseException {
		StringBuilder sql = new StringBuilder(256);
		sql.append("SELECT c.* FROM dc_pa_channel c WHERE 1 = 1");
		List<Object> args = new ArrayList<Object>();
		
		if(StringUtils.isNotBlank(channel.getChannelCnName())) {
			sql.append(" AND c.channelcnname LIKE ?");
			args.add("%" + channel.getChannelCnName() + "%");
		}
		if(StringUtils.isNotBlank(channel.getChannelCode())) {
			sql.append(" AND c.channelcode = ?");
			args.add(channel.getChannelCode());
		}
		if(StringUtils.isNotBlank(channel.getIsValid())) {
			sql.append(" AND c.isvalid = ?");
			args.add(channel.getIsValid());
		}
		
		return super.findForPage(sql.toString(), args.toArray(), page, Channel.class);
	}
	
	
	public void deleteChannel(Channel channel) throws BaseException {
		super.delete(channel);
	}

	
	public Channel saveChannel(Channel channel) throws BaseException {
		return super.insert(channel);
	}

	
	public Channel updateChannel(Channel channel) throws BaseException {
		super.update(channel);
		return channel;
	}

	
	public Channel queryChannelById(String channelId) throws BaseException {
		return super.findForObject(Channel.class, channelId);
	}

	
	public Boolean checkChannelCodeExist(String channelCode) throws BaseException {
		String sql = "select count(1) from dc_pa_channel c where c.channelcode=?";
		int count = super.findForIntBySql(sql, new Object[]{channelCode});
		return count > 0 ? false : true;
	}

	@SuppressWarnings("unchecked")
	
	public List<Channel> getChannelList() throws BaseException {
		return (List<Channel>) super.findForList(Channel.class);
	}

	
	public Channel queryChannelByReqSysCode(String reqSysCode)
			throws BaseException {
		String sql = "select * from dc_pa_channel t where t.reqsyscode=? ";
		return super.findForObjectBySql(sql, new Object[]{reqSysCode}, Channel.class);
	}
	
	
	public boolean checkReqSysCodeExist(String reqSysCode) throws BaseException {
		String sql = "select count(1) from dc_pa_channel c where c.reqsyscode=?";
		int count = super.findForIntBySql(sql, new Object[]{reqSysCode});
		return count > 0 ? false : true;
	}

	
	public Channel queryChannelByBizChnlCode(String bizChnlCode)
			throws BaseException {
		String sql = "select * from dc_pa_channel where BIZCHNLCODE =? ";
		return super.findForObjectBySql(sql, new Object[]{bizChnlCode}, Channel.class);
	}
}
