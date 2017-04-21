package com.global.fems.interfaces.manager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;

import com.global.fems.message.domain.DataStores;
import com.global.fems.message.domain.ExceptionMsg;
import com.global.fems.message.domain.Msg;
import com.global.fems.message.domain.MsgFault;
import com.global.fems.message.service.IndividuaInterfaceManager;
import com.global.fems.message.support.XmlMsgBuilder;
import com.global.fems.message.support.XmlMsgParser;
import com.global.fems.message.util.IntfCodeCfg;
import com.global.fems.message.util.IntfCodeCfgUtil;
import com.global.framework.exception.BaseException;
import com.global.framework.util.SpringContextUtil;
import com.global.param.dao.ChannelInterfaceDao;
import com.global.param.domain.Channel;
import com.global.param.domain.ChannelInterface;
import com.global.param.service.ChannelService;

/**
 * 类描述：前置接口服务管理器
 * 
 * @author chen.feng
 * @date 2015-7-2
 * @version v1.0
 */
public class InterfaceDispatcher {

	private static final Logger log = Logger.getLogger(InterfaceDispatcher.class);
	private ChannelService channelService = null;
	private ChannelInterfaceDao channelInterfaceDao = null;
	private IndividuaInterfaceManager individuaInterfaceManager = null;
	
	public InterfaceDispatcher() {
		channelService = (ChannelService) SpringContextUtil.getBean("ChannelService");
		channelInterfaceDao = (ChannelInterfaceDao) SpringContextUtil.getBean("channelInterfaceDao");
		individuaInterfaceManager = (IndividuaInterfaceManager) SpringContextUtil.getBean("individuaInterfaceManager");
	}
	/**
	 * 接口分发方法
	 * 
	 * @param reqSysCode
	 *            接入系统代码
	 * @param faceCode
	 *            13位接口代码
	 * @param xmlMsg
	 *            接收的XML报文
	 * @return 响应XML报文
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String dispatch(String reqSysCode, String faceCode, String xmlMsg) {
		try {
			ChannelInterface channelInterface = channelInterfaceDao.getChannelInterface(reqSysCode, faceCode);
			if ("N".equals(channelInterface.getIsValid())) {
				log.error("该渠道["+reqSysCode+"]未开通["+faceCode+"]该接口，请联系系统管理员!");
				return null;
			}
		} catch (BaseException e2) {
			log.error("该渠道["+reqSysCode+"]未开通["+faceCode+"]该接口，请联系系统管理员!", e2);
			return null;
		}
		MsgFault fault = new MsgFault();
		fault.setFaultCode(MsgErrorCodeEnum.ERRCODE_00000.getCode());
		fault.setFaultDesc(MsgErrorCodeEnum.ERRCODE_00000.getValue());
		try {
			log.info("接入系统代码：["+reqSysCode+"],接口代码：["+faceCode+"]接收到的报文内容：["+faceCode+"]\r\n" + xmlMsg);
			
			XmlMsgBuilder xmb = new XmlMsgBuilder();
			IntfCodeCfg cfg = IntfCodeCfgUtil.getIntfCodeCfg(faceCode);
			Class cls = Class.forName(cfg.getRspEntity());
			Object o = cls.newInstance();
			
			//判断该渠道是否启用接口服务
			Channel channel = channelService.queryChannelByReqSysCode(reqSysCode);
			
			// 解析报文得到接收数据实体
			Object data = null;
			try {
				
				
				
				data = XmlMsgParser.parseXml(faceCode, xmlMsg);
			} catch (DataCheckException e) {
				log.error("解析接收到的XML报文出错:", e);
				fault.setFaultCode(e.getCode());
				fault.setFaultDesc(e.getReason());
				try {
					String s = xmlMsg.replaceAll(xmlMsg.substring(xmlMsg.indexOf("</Head>")+7, xmlMsg.lastIndexOf("</Msg>")),"");
					data = XmlMsgParser.parseHeadXml(s);
				} catch (Exception e1) {
					log.error("系统异常：", e);
					return null;
				}
				ExceptionMsg msgData = (ExceptionMsg) data;
				return xmb.buildRespXml(msgData.getHead(), (DataStores) o, fault, null);
			}
			log.info("接入系统代码：["+reqSysCode+"],接口代码：["+faceCode+"]解析报文得到接收数据实体:" + data);
			Msg msgData = (Msg) data;
			
			if (channel == null) {
				log.error("未找到该接入系统["+reqSysCode+"]的渠道维护信息，请检查PA_CHANNEL");
				fault.setFaultCode(MsgErrorCodeEnum.ERRCODE_400000.getCode());
				fault.setFaultDesc(MsgErrorCodeEnum.ERRCODE_400000.getValue()+":接入系统["+reqSysCode+"]未开通访问权限!");
				return xmb.buildRespXml(msgData.getHead(), (DataStores) o, fault, null);
			}
			
			if (!"Y".equals(channel.getIsValid())) {
				log.error("接入系统["+reqSysCode+"]未开通访问权限!");
				fault.setFaultCode(MsgErrorCodeEnum.ERRCODE_400000.getCode());
				fault.setFaultDesc(MsgErrorCodeEnum.ERRCODE_400000.getValue()+":未找到该接入系统["+reqSysCode+"]的渠道维护信息!");
				return xmb.buildRespXml(msgData.getHead(), (DataStores) o, fault, null);
			}
			
			//判断接入系统代码和交易码是否与报文头传入的一致
			if (!reqSysCode.equals(msgData.getHead().getReqSysCode())) {
				fault.setFaultCode(MsgErrorCodeEnum.ERRCODE_400007.getCode());
				fault.setFaultDesc(MsgErrorCodeEnum.ERRCODE_400007.getValue()+":请求报文头参数接入系统代码[ReqSysCode]不合法!");
				log.error("接入系统代码：["+reqSysCode+"],接口代码：["+faceCode+"]请求报文头参数接入系统代码[ReqSysCode]不合法!");
				return xmb.buildRespXml(msgData.getHead(), (DataStores) o, fault, null);
			}
			
			if (!faceCode.equals(msgData.getHead().getTxCode())) {
				fault.setFaultCode(MsgErrorCodeEnum.ERRCODE_400007.getCode());
				fault.setFaultDesc(MsgErrorCodeEnum.ERRCODE_400007.getValue()+":请求报文头参数接口代码[TxCode]不合法!");
				log.error("接入系统代码：["+reqSysCode+"],接口代码：["+faceCode+"]请求报文头参数接口代码[TxCode]不合法!");
				return xmb.buildRespXml(msgData.getHead(), (DataStores) o, fault, null);
			}
			
			Object retObj = null;
			// 调用业务逻辑总控
			log.info("接入系统代码：["+reqSysCode+"],接口代码：["+faceCode+"]调用业务逻辑开始...");

			Method ms[] = individuaInterfaceManager.getClass().getDeclaredMethods();
			for (int i = 0; i < ms.length; i++) {
				Method m = ms[i];
				String name = ms[i].getName();
				if (cfg.getServiceMethod().equals(name)) {
					try {
						List params = new ArrayList();
						params.addAll(Arrays.asList(new Object[]{msgData.getBody().getDataStores(), 
								msgData.getHead(), 
								channel}));
						if(m.getParameterTypes().length>4){
							for(int j=0;j<m.getParameterTypes().length-4;j++){
								params.add(null);
							}
						}
						retObj = m.invoke(individuaInterfaceManager, params.toArray());
					} catch (Exception e) {
						if (e.getCause() instanceof DataCheckException) {
							DataCheckException dce = (DataCheckException) e.getCause();
							fault.setFaultCode(dce.getCode());
							fault.setFaultDesc(MsgErrorCodeEnum.getValueByCode(dce.getCode()) + ":" + dce.getReason());
						} else {
							fault.setFaultCode(MsgErrorCodeEnum.ERRCODE_999999.getCode());
							fault.setFaultDesc(MsgErrorCodeEnum.ERRCODE_999999.getValue());
						}
						if (!MsgErrorCodeEnum.ERRCODE_00000.getCode().equals(fault.getFaultCode())) {
							log.error("接入系统代码：["+reqSysCode+"],接口代码：["+faceCode+"]业务逻辑处理出错：", e);
						}
					}
					log.info("接入系统代码：["+reqSysCode+"],接口代码：["+faceCode+"]调用业务逻辑结束...");
					break;
				}
			}

			// 组装响应报文
			String retxml = "";
			if (retObj == null) {
				retxml = xmb.buildRespXml(msgData.getHead(), (DataStores) o,
						fault, null);
			} else {
				Object[] result = (Object[]) retObj;
				// 数组的第一位为响应实体对象，第二位为业务流水号
				retxml = xmb.buildRespXml(msgData.getHead(),
						(DataStores) result[0], fault, (String) result[1]);
			}
			log.info("接入系统代码：["+reqSysCode+"],接口代码：["+faceCode+"]响应的报文内容：\r\n" + retxml);
			return retxml;
		} catch (Exception e) {
			log.error("系统内部异常:", e);
			return null;
		}
	}
	
	public static void main(String[] args) {
		InterfaceDispatcher im = new InterfaceDispatcher();
	}
}
