package com.global.fems.interfaces.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.global.fems.interfaces.manager.InterfaceDispatcher;
import com.global.framework.system.web.common.CacheService;
import com.global.param.domain.CommMode;

/**
 * 接收报文任务线程
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class Task implements Runnable {

	private static final Logger log = Logger.getLogger(Task.class);
	private Socket s;

	public Task(Socket s) {
		this.s = s;
	}

	public void run() {
		String faceCode = "";
		try {
			String addr = s.getRemoteSocketAddress().toString();
			String remoteHost = addr.substring(addr.indexOf("/")+1, addr.lastIndexOf(":"));
			log.debug("收到服务请求:"+remoteHost);
			// 接收报文
			String[] content = receiveMessage(s);
			if (null != content && content.length == 2){
				faceCode = content[0];// 接口代码
				String msgContent = content[1];// 报文内容
				
				String reqSysCode = faceCode.substring(0, 2);
				String tradeCode = faceCode.substring(2);
				
				CommMode cm = CacheService.getCommModeCacheByReqSysCode(reqSysCode);
				if (cm == null) {
					log.debug("系统未维护该渠道的接口通讯方式,请检查接入系统代码["+reqSysCode+"]是否输入正确?");
					return;
				}
				if (!"SOCKET".equals(cm.getCommMode())) {
					log.debug("接入系統["+reqSysCode+"]使用的通讯方式为["+cm.getCommMode()+"]，SOCKET协议方式的接口服务拒绝提供。");
					return;
				}
				
				if (!remoteHost.equals(cm.getIp())) {
					log.debug("接入系統["+reqSysCode+"]请求地址["+remoteHost+"]未在前置系统注册");
					return;
				}
				log.debug("转入服务处理程序...");
				InterfaceDispatcher im = new InterfaceDispatcher();
				try{
					String retxml = im.dispatch(reqSysCode, tradeCode, msgContent);
					// 发送回报报文
					sendMessage(faceCode, retxml, s);
					log.debug("发送反馈完成-[SUCCESS]");
				}catch (Exception e) {
					log.debug("服务处理程序错误：",e);
					//发送错误信息
				}
			}else{
				log.debug("收报解析错误。");
				//发送错误信息
			}
		} catch (Exception e) {
			log.debug("接口代码为：[" + faceCode + "],处理出现系统内部异常:", e);
			//发送错误信息
		} finally{
			try {
				//关闭链接
				log.debug("关闭链接.");
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.debug("-------NEXT------------------");
		}

	}

	/**
	 * 接收请求报文
	 * 
	 * @param sc
	 * @return
	 */
	private String[] receiveMessage_Old(Socket sc) {
		BufferedInputStream in = null;
		ByteArrayOutputStream byteout = null;
		try {
			in = new BufferedInputStream(sc.getInputStream());
			byte[] faceCodeContent = new byte[8];// 先取前8个字节，存储的是8位接口代码
			int faceCodeLen = in.read(faceCodeContent);
			byteout = new ByteArrayOutputStream();
			byteout.write(faceCodeContent, 0, faceCodeLen);
			byte[] faceCodeBytes = byteout.toByteArray();
			String faceCode = new String(faceCodeBytes, "UTF-8");

			byte[] recvMsgContent = new byte[256];// 循环获取报文内容
			int contentLen = in.read(recvMsgContent);
			byteout = new ByteArrayOutputStream();
			//czc 以下循环读取接口代码错误，当报文长度不是256时，将进入等待状态
			while (contentLen > 0) {
				byteout.write(recvMsgContent, 0, contentLen);
				contentLen = in.read(recvMsgContent);
			}

			byte[] content = byteout.toByteArray();
			String msgContent = new String(content, "UTF-8");
			return new String[] { faceCode, msgContent };
		} catch (Exception e) {
			log.error("接收报文出错：", e);
			return null;
		} finally {
			try {
				byteout.close();
			} catch (IOException e) {
			}
		}
	}

	
	/**
	 * 接收请求报文
	 * 报文格式:报文长度(6位，不含报文长度的6位长度)+接入系统代码（2位）+交易代码（6位）+交易内容（XML文件，变长）
	 * @author 94402(czc)0
	 * @param sc  SOCKET 链接
	 * @return 数组 ：[接入系统代码+交易代码][交易报文体]
	 */
	private String[] receiveMessage(Socket sc) {
		int readnum = 0;  //每次读取的报文长度
		int totallen =0 ; //报文总长度
		String tmpStr = "";
		String reqSyscode="";	//请求系统代码（2位长度）,注意此限制！！！！
		String tradeCode ="";	//交易代码(6位长度）
		String msgContent = "";	//交易报文体(XML格式)
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(sc.getInputStream());
			byte[] sockbuf = new byte[6];// 先取前6个字节，存储的是报文总长度
			readnum = in.read(sockbuf);
			if (readnum == 6) {
				tmpStr = new String(sockbuf, 0, readnum,"utf-8");
				log.debug("报文总长度："+tmpStr);
				try {
					totallen = Integer.parseInt(tmpStr);
				}catch(Exception e) {
					log.debug("报文长度字符串错误，数字转换异常");
					return null;
				}
				
				//读取:接入系统代码（2位）
				byte[]sysCodebuf = new byte[2];
				readnum = in.read(sysCodebuf);
				if (readnum == 2) {
					reqSyscode = new String(sysCodebuf, 0, readnum,"utf-8").trim(); 
					log.debug("请求系统代码："+reqSyscode);

					//读取:交易代码（6位）
					byte[] tradebuf = new byte[6];
					readnum = in.read(tradebuf);
					if (readnum == 6) {
						tradeCode = new String(tradebuf, 0, readnum,"utf-8").trim(); 
						log.debug("交易代码："+tradeCode);
						
						//读取报文体（XML)
						byte[] xmlbuf = new byte[totallen-6];
						readnum = in.read(xmlbuf);
						msgContent = new String(xmlbuf, 0, readnum,"utf-8").trim();
					}else{
						log.debug("读取交易代码错误");
						return null;
					}
				}else{
					log.debug("读取接入系统代码错误.");
					return null;
				}
			}else{
				log.debug("读取报文长度错误");
				return null;
			}
			return new String[] {reqSyscode+tradeCode, msgContent };
		} catch (Exception e) {
			log.error("接收报文出错：", e);
			return null;
		} finally {
			
		}
	}
	
	public static String StrLPad(String source, int length,String substr) {
		String ret = source ;
		int padCount=length - ret.length();
		if (source == "")
			ret = "";
		for (int i = 1; i <= padCount; i++) {
			ret = substr+ret ;
		}
		return ret;
	}	
	
	private String buildXMLHead(String Version, String enCoding){
		String tmpHead = "<?xml version=\"" + Version + "\" encoding=\"" + enCoding + "\"?>";
		return tmpHead;
	}
	
	/**
	 * 发送响应报文
	 * 报文长度(6位）+请求系统代码(2位)+服务代码(6位)+反馈报文(变长)
	 * @param content
	 * @param retxml
	 * @throws Exception
	 */
	private void sendMessage(String faceCode, String retxml, Socket sc) {
		DataOutputStream out = null;
		String Xmlhead = buildXMLHead("1.0","utf-8");
		try {
			out = new DataOutputStream(sc.getOutputStream());
//			byte[] retData = Utils.mergence(new byte[][] {
//					faceCode.getBytes("UTF-8"), retxml.getBytes("UTF-8") });
			
			String tmpStr = Xmlhead +"\n"+ retxml;//封装完整的XML报文体
			tmpStr = faceCode + tmpStr; 	 //报文长度
			int len = tmpStr.getBytes("UTF-8").length;
			String strLen = StrLPad(String.valueOf(len),6,"0");
			tmpStr = strLen+ tmpStr;
			
			log.debug("反馈报文:\n"+tmpStr);
			byte[] retData = tmpStr.getBytes("UTF-8");	
			
			out.write(retData);
			out.flush();
		} catch (Exception e) {
			log.error("发送报文出错：", e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}
}
