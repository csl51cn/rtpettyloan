package com.global.param.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.dao.CommModeDao;
import com.global.param.domain.CommMode;

/**
 * 类描述： 
 *
 * @author chen.feng
 * @date 2015-7-10
 * @version v1.0
 */
@Repository("commModeDao")
public class CommModeDaoImpl extends BaseDaoSupport implements CommModeDao {

	
	public PageBean queryForPage(CommMode info, PageBean page)
			throws BaseException {
		StringBuilder sql = new StringBuilder(256);
		sql.append("SELECT c.*,(select d.channelcode from dc_pa_channel d where d.channelid=c.channelid) channelcode," +
				"(select d.channelcnname from dc_pa_channel d where d.channelid=c.channelid) channelname from dc_pa_channel_commmode c WHERE 1=1 ");
		List<Object> args = new ArrayList<Object>();
		
		if(StringUtils.isNotBlank(info.getChannelId())) {
			sql.append(" AND c.ChannelId = ?");
			args.add(info.getChannelId());
		}
		
		return super.findForPage(sql.toString(), args.toArray(), page, CommMode.class);
	}

	
	public CommMode saveOrUpdate(CommMode info) throws BaseException {
		return super.saveOrUpdate(info);
	}

	
	public void delete(CommMode info) throws BaseException {
		super.delete(info);
	}

	
	public CommMode getCommMode(String channelId) throws BaseException {
		return super.findForObject(CommMode.class, channelId);
	}
	
	@SuppressWarnings("unchecked")
	public List<CommMode> getCommModeList() throws BaseException {
		String sql = "select t.*, (select c.reqsyscode from dc_pa_channel c where c.channelid=t.channelid) reqsyscode from dc_pa_channel_commmode t";
		return (List<CommMode>) super.findForListBySql(sql, null, CommMode.class);
	}
}
