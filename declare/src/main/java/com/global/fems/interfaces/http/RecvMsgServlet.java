package com.global.fems.interfaces.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.global.fems.interfaces.manager.InterfaceDispatcher;
import com.global.framework.system.web.common.CacheService;
import com.global.param.domain.CommMode;

/**
 * http报文接收器
 * 
 * @author:feng.chen
 * @date:2014-2-17
 * @version:V1.0
 */
public class RecvMsgServlet extends BaseServlet {

	private static final long serialVersionUID = 3686249352944526001L;
	private static final Logger log = Logger.getLogger(RecvMsgServlet.class);

	public RecvMsgServlet() {

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 1、获得Http请求的消息串
		try {
			String url = request.getRequestURI();
			int length = Integer.parseInt(request.getHeader("Content-Length"));
			String reqSysCode = request.getHeader("ReqSysCode");//接入系统代码，由前置系统统一分配
			reqSysCode = "01";//测试用
			String faceCode = url.substring(url.length()-6);//接口交易码
			String remoteHost = request.getRemoteHost();
			log.info("http服务监听到接入系统["+reqSysCode+"]的请求地址:"+remoteHost);
			
			CommMode cm = CacheService.getCommModeCacheByReqSysCode(reqSysCode);
			if (cm == null) {
				log.error("系统未维护该渠道的接口通讯方式,请检查接入系统代码["+reqSysCode+"]是否输入正确?");
				return;
			}
			if (!"HTTP".equals(cm.getCommMode())) {
				log.error("接入系統["+reqSysCode+"]使用的通讯方式为["+cm.getCommMode()+"]，HTTP协议方式的接口服务拒绝提供。");
				return;
			}
			
			if (!remoteHost.equals(cm.getIp())) {
				log.error("接入系統["+reqSysCode+"]请求地址["+remoteHost+"]未在前置系统注册");
				return;
			}
			
			String message = getMessage(request, length);
			message = URLDecoder.decode(message, "UTF-8");
			InterfaceDispatcher dispatch = new InterfaceDispatcher();
			String rspXml = dispatch.dispatch(reqSysCode, faceCode, message);
			super.response(response, rspXml);
		} catch (Exception e) {
			log.error("系统内部异常:", e);
		}
	}

	private String getMessage(HttpServletRequest request, int length) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		StringBuffer sb = new StringBuffer();
		char buffer[] = new char[length];
		int len = reader.read(buffer);
		while (len != -1) {
			sb.append(new String(buffer, 0, len));
			len = reader.read(buffer);
		}
		return sb.toString();
	}
}
