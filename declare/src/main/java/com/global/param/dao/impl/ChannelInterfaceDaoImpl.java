package com.global.param.dao.impl;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.dao.ChannelInterfaceDao;
import com.global.param.domain.ChannelInterface;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 
 * @author chen.feng
 * @date 2015-7-10
 * @version v1.0
 */
@Repository("channelInterfaceDao")
public class ChannelInterfaceDaoImpl extends BaseDaoSupport implements
		ChannelInterfaceDao {

	
	public PageBean queryChannelInterfaceForPage(
			ChannelInterface channelInterface, PageBean page)
			throws BaseException {
		StringBuilder sql = new StringBuilder(256);
		sql.append("SELECT c.*,(select d.channelcode from dc_pa_channel d where d.channelid=c.channelid) channelcode," +
				"(select d.channelcnname from dc_pa_channel d where d.channelid=c.channelid) channelname from dc_pa_channel_interface c WHERE 1=1 ");
		List<Object> args = new ArrayList<Object>();
		
		if(StringUtils.isNotBlank(channelInterface.getChannelId())) {
			sql.append(" AND c.ChannelId = ?");
			args.add(channelInterface.getChannelId());
		}
		if(StringUtils.isNotBlank(channelInterface.getFaceCode())) {
			sql.append(" AND c.facecode = ?");
			args.add(channelInterface.getFaceCode());
		}
		if(StringUtils.isNotBlank(channelInterface.getFaceName())) {
			sql.append(" AND c.facename LIKE ?");
			args.add("%" + channelInterface.getFaceName() + "%");
		}
		if(StringUtils.isNotBlank(channelInterface.getIsValid())) {
			sql.append(" AND c.isvalid = ?");
			args.add(channelInterface.getIsValid());
		}
		page.setSort("channelid");
		return super.findForPage(sql.toString(), args.toArray(), page, ChannelInterface.class);
	}

	
	public ChannelInterface saveChannelInterface(
			ChannelInterface channelInterface) throws BaseException {
		return super.insert(channelInterface);
	}

	
	public ChannelInterface updateChannelInterface(
			ChannelInterface channelInterface) throws BaseException {
		super.update(channelInterface);
		return channelInterface;
	}

	
	public void deleteChannelInterface(ChannelInterface channelInterface)
			throws BaseException {
		super.delete(channelInterface);
	}

	@SuppressWarnings("unchecked")
	
	public List<ChannelInterface> queryChannelInterface(String channelId)
			throws BaseException {
		String sql = "select * from dc_pa_channel_interface where channelid=?";
		return (List<ChannelInterface>) super.findForListBySql(sql, new Object[]{channelId}, ChannelInterface.class);
	}
	
	
	public void deleteChannelInterface(String channelId) throws BaseException {
		String sql = "delete from dc_pa_channel_interface t where t.channelid = ? ";
		super.delete(sql, new Object[]{channelId});
	}
	
	
	public void saveChannelInterface(List<ChannelInterface> list)
			throws BaseException {
		super.batchInsert(list);
	}
	
	public ChannelInterface getChannelInterface(String channelId,
			String faceCode) throws BaseException {
		String sql = "select * from dc_pa_channel_interface a, pa_channel b where a.channelid=b.channelid and b.reqsyscode=? and facecode=? ";
		return super.findForObjectBySql(sql, new Object[]{channelId, faceCode}, ChannelInterface.class);
	}
}
