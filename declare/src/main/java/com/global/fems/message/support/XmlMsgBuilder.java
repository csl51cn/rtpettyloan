package com.global.fems.message.support;


import org.global.framework.xmlbeans.handler.Bean2XmlHandler;

import com.global.fems.message.domain.DataStores;
import com.global.fems.message.domain.Msg;
import com.global.fems.message.domain.MsgBody;
import com.global.fems.message.domain.MsgFault;
import com.global.fems.message.domain.MsgHead;
import com.global.fems.message.util.DateTimeUtil;
import com.global.fems.message.util.IntfCodeCfg;
import com.global.fems.message.util.IntfCodeCfgUtil;

/**
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class XmlMsgBuilder {

	/**
	 * 构建XML报文
	 * 
	 * @param ds
	 *            XML响应数据实体
	 * @param svrSeqNo
	 *            服务端业务流水号
	 * @return
	 * @throws Exception
	 */
	public String buildRespXml(MsgHead recvHead, DataStores ds, MsgFault fault, String svrSeqNo)
			throws Exception {
		// 获取接口映射配置信息
		String txCode = recvHead.getTxCode().substring(
				recvHead.getTxCode().length() - 6);
		IntfCodeCfg cfg = IntfCodeCfgUtil.getIntfCodeCfg(txCode);
		
		Msg msg = new Msg();
		msg.setHead(buildRespMsgHead(recvHead, svrSeqNo));
		msg.setBody(buildMsgBody(ds));
		msg.setFault(fault);
		String xml = new Bean2XmlHandler().toXml(msg, cfg.getRspXmlMapping(), "UTF-8");
		return xml;
	}

	/**
	 * 构建响应报文头
	 * 
	 * @param recvHead
	 *            接收的请求报文头
	 * @param svrSeqNo
	 *            服务端流水号
	 * @return
	 */
	private MsgHead buildRespMsgHead(MsgHead recvHead, String svrSeqNo) {
		recvHead.setSvrDateTime(DateTimeUtil.getCurrentDateTime());
		recvHead.setSvrSeqNo(svrSeqNo);
		return recvHead;
	}

	/**
	 * 构建报文体
	 * 
	 * @param data
	 * @return
	 */
	private MsgBody buildMsgBody(DataStores data) {
		MsgBody body = new MsgBody();
		body.setDataStores(data);
		return body;
	}

	/**
	 * 构建响应报文错误结构体
	 * 
	 * @return
	 */
	public MsgFault buildRespMsgFault(String code, String remark) {
		MsgFault fault = new MsgFault();
		fault.setFaultCode(code);
		fault.setFaultDesc(remark);
		return fault;
	}
}
