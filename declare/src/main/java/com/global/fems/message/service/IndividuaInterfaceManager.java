package com.global.fems.message.service;

import org.global.framework.xmlbeans.bean.DataCheckException;

import com.global.fems.message.domain.MsgHead;
import com.global.fems.message.domain.business.receive.RecvCommonUser;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYCancel;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYMakeUp;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYModify;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYQuery;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYRegCheck;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYRegQuery;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYRegister;
import com.global.fems.message.domain.business.receive.RecvMakeUpSignStatus;
import com.global.fems.message.domain.business.receive.RecvQueryIndividualFXSEAInfo;
import com.global.fems.message.domain.business.receive.RecvSafeExRateQuery;
import com.global.param.domain.Channel;

/**
 * 类描述：行内接口门面服务接口类
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public interface IndividuaInterfaceManager {

	/**
	 * 美元折算率查询
	 * 
	 * @param ds
	 *            报文BODY实体
	 * @param head
	 *            请求报文头
	 * @param channel
	 *            渠道信息
	 * @return
	 * @throws Exception
	 */
	public Object[] doSafeExRateQuery(RecvSafeExRateQuery ds, MsgHead head, Channel channel) throws DataCheckException;

	/**
	 * 个人结售汇额度查询
	 * 
	 * @param ds
	 *            报文BODY实体
	 * @param head
	 *            请求报文头
	 * @param channel
	 *            渠道信息
	 * @return
	 * @throws Exception
	 */
	public Object[] doIndividualLCYQuery(RecvIndividualLCYQuery ds, MsgHead head, Channel channel) throws DataCheckException;

	/**
	 * 个人结售汇额度登记
	 * 
	 * @param ds
	 *            报文BODY实体
	 * @param head
	 *            请求报文头
	 * @param channel
	 *            渠道信息
	 * @return
	 * @throws Exception
	 */
	public Object[] doIndividualLCYRegister(RecvIndividualLCYRegister ds, MsgHead head, Channel channel) throws DataCheckException;

	/**
	 * 个人结售汇信息修改
	 * 
	 * @param ds
	 *            报文BODY实体
	 * @param head
	 *            请求报文头
	 * @param channel
	 *            渠道信息
	 * @return
	 * @throws Exception
	 */
	public Object[] doIndividualLCYModify(RecvIndividualLCYModify ds, MsgHead head, Channel channel) throws DataCheckException;

	/**
	 * 个人结售汇信息撤消
	 * 
	 * @param ds
	 *            报文BODY实体
	 * @param head
	 *            请求报文头
	 * @param channel 
	 *            渠道信息
	 * @return
	 * @throws DataCheckException
	 */
	public Object[] doIndividualLCYCancel(RecvIndividualLCYCancel ds, MsgHead head, Channel channel) throws DataCheckException;

	/**
	 * 结售汇额度登记指令同步核对接口
	 * 
	 * @param ds
	 *            报文BODY实体
	 * @param head
	 *            请求报文头
	 * @param channel
	 *            渠道信息
	 * @return
	 * @throws Exception
	 */
	public Object[] doIndividualLCYRegCheck(RecvIndividualLCYRegCheck ds, MsgHead head, Channel channel) throws DataCheckException;

	/**
	 * 个人结售汇额度登记指令查询
	 * 
	 * @param ds
	 *            报文BODY实体
	 * @param head
	 *            请求报文头
	 * @param channel
	 *            渠道信息
	 * @return
	 * @throws Exception
	 */
	public Object[] doIndividualLCYRegQuery(RecvIndividualLCYRegQuery ds, MsgHead head, Channel channel) throws DataCheckException;
	
	/**
	 * 个人结售汇明细信息查询
	 * 
	 * @param ds
	 *            报文BODY实体
	 * @param head
	 *            请求报文头
	 * @param channel
	 *            渠道信息
	 * @return
	 * @throws DataCheckException
	 */
	public Object[] doQueryIndividualFXSEAInfo(RecvQueryIndividualFXSEAInfo ds, MsgHead head, Channel channel) throws DataCheckException;
	
	/**
	 * 个人结售汇补录
	 * 
	 * @param ds
	 * @param head
	 * @param channel
	 * @return
	 * @throws DataCheckException
	 */
	public Object[] doIndividualLCYMakeUp(RecvIndividualLCYMakeUp ds, MsgHead head, Channel channel) throws DataCheckException;
	
	/**
	 * 预关注风险提示/关注名单告知
	 * 
	 * @param ds
	 * @param head
	 * @param channel
	 * @throws DataCheckException
	 */
	public void doMakeUpSignStatus(RecvMakeUpSignStatus ds, MsgHead head, Channel channel) throws DataCheckException;
	
	/**
	 * 外管登录用户信息更新
	 * 
	 * @param ds
	 * @param head
	 * @throws DataCheckException
	 */
	public void doUpdateCommonUser(RecvCommonUser ds, MsgHead head, Channel channel) throws DataCheckException;
	
	/**
	 * 删除错误的外围系统登记数据
	 * @param reqSeqNo
	 * @throws DataCheckException
	 */
	public void dodeleteFalse(String reqSeqNo) throws Exception;
}
