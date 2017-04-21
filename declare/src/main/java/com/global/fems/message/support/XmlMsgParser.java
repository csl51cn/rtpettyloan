package com.global.fems.message.support;

import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.handler.Xml2BeanHandler;

import com.global.fems.message.domain.ExceptionMsg;
import com.global.fems.message.domain.Msg;
import com.global.fems.message.util.IntfCodeCfg;
import com.global.fems.message.util.IntfCodeCfgUtil;

/**
 * 类描述：XML报文解析器
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class XmlMsgParser {

	/**
	 * 解析XML报文
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Object parseXml(String txCode, String xml) throws DataCheckException {
		// 获取接口映射配置信息
		IntfCodeCfg cfg = IntfCodeCfgUtil.getIntfCodeCfg(txCode);
		Msg recvMsg = (Msg) new Xml2BeanHandler().toBean(xml,
				cfg.getRecvXmlMapping(), "UTF-8");
		return recvMsg;
	}
	
	public static Object parseHeadXml(String xml) throws DataCheckException {
		ExceptionMsg recvMsg = (ExceptionMsg) new Xml2BeanHandler().toBean(xml,"MsgHead", "UTF-8");
		return recvMsg;
	}
}
