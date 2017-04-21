package com.global.fems.interfaces.server;

import com.global.fems.message.util.IntfCodeCfgUtil;
import com.global.framework.system.dao.SysCommonDao;
import com.global.framework.util.SpringContextUtil;
import com.pactera.fems.message.util.WGIntfCodeCfgUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author chen.feng
 * @date 2015-6-24
 * @version v1.0
 */
public class Receiver extends Thread {

	private static final Logger log = Logger.getLogger(Receiver.class);
	private static Receiver instance = null;
	private ServerSocket serverSocket = null;
	private SysCommonDao dao = (SysCommonDao) SpringContextUtil.getBean("sysCommonDao");

	public Receiver() {
		IntfCodeCfgUtil.init();
		WGIntfCodeCfgUtil.init();
//		Map m = XmlBeanCfgManager.getXmlCfgCache();
//		System.out.println(m);
		init();
	}

	public static Receiver getInstance() {
		if (instance == null) {
			return new Receiver();
		}
		return instance;
	}

	private void init() {
		try {
			int port = Integer.parseInt(ServerConfigReader.getInstance()
					.getServerPort());
			log.info("socket接口服务端口：" + port);
			serverSocket = new ServerSocket(port);
//			log.info("Socket接口服务启动成功，监听端口号为：" + port);
		} catch (NumberFormatException e) {
			log.error("加载服务器端口出错", e);
		} catch (IOException e) {
			log.error("创建SOCKET服务异常", e);
		}
	}

	/**
	 * 启动SOCKET服务
	 */
	public void startServer() {
		try {
			while (true) {
				String serverMode = ServerConfigReader.getInstance().getServerMode();
				if ("SOCKET".equals(serverMode)) {
					Socket s = serverSocket.accept();
					s.setSoTimeout(Integer.parseInt(ServerConfigReader
							.getInstance().getServerTimeout()) * 1000);
					//log.info("监听到客户端连接:" + s.getRemoteSocketAddress());
					ScheduledThreadPool.getInstance().submit(new Task(s));
				} else if ("MQ".equals(serverMode)) {
				}
			}
		} catch (Exception e) {
			log.error("接口服务开启异常：", e);
		}
	}

	public void run() {
		startServer();
	}
	
	public static void main(String[] args) {
		new Receiver().start();
	}
}
