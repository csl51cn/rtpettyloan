package com.global.fems.interfaces.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.global.framework.util.SysUtils;

/**
 * 
 * @author chen.feng
 * @date 2015-7-1
 * @version v1.0
 */
public class ServerConfigReader {
	private static final Logger log = Logger.getLogger(ServerConfigReader.class);
	private static ServerConfigReader instance = new ServerConfigReader();
	
	private String serverMode;//服务方式MQ,SOCKET
	private String serverPort;
	private String poolSize;
	private String serverTimeout;
	
	private String mqServerIP;
	private int mqServerPort;
	private int mqServerCCSID;//字符集
	private String mqServerChannel;
	private String queueManagerName;// #队列管理器名称
	private String sendQueueName;// #发送队列名称
	private String recvQueueName;// #接收队列名称
	private int timeout;//设置等待的时间限制
	private int mqexpiry;//消息过期
	
	private String exeDay;//美元折算率执行周期，日期
	private String exeTime;//美元折算率执行周期，时间

	private ServerConfigReader() {
		Properties prop = new Properties();
		File f = new File(SysUtils.getSystemConfigHome() + "ServerConfig.properties");
		InputStream is = null;
		if (f == null || !f.exists()) {
			log.error(SysUtils.getSystemConfigHome() + "ServerConfig.properties不存在，读取程序包中的ServerConfig.properties");
			is = ServerConfigReader.class.getClassLoader()
					.getResourceAsStream("ServerConfig.properties");
		} else {
			try {
				log.info("加载" + SysUtils.getSystemConfigHome() + "ServerConfig.properties");
				is = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				log.error(SysUtils.getSystemConfigHome() + "ServerConfig.properties不存在，读取程序包中的ServerConfig.properties");
				is = ServerConfigReader.class.getClassLoader()
						.getResourceAsStream("ServerConfig.properties");
			}
		}
		try {
			prop.load(is);
			this.serverMode = prop.getProperty("serverMode");
			this.serverPort = prop.getProperty("serverPort");
			this.poolSize = prop.getProperty("poolSize");
			this.serverTimeout = prop.getProperty("serverTimeout");
			this.mqServerIP = prop.getProperty("MQServerIP");
			this.mqServerPort = Integer.parseInt(prop.getProperty("MQServerPort"));
			this.mqServerChannel = prop.getProperty("MQServerChannel");
			this.mqServerCCSID = Integer.parseInt(prop.getProperty("MqServerCCSID"));
			this.queueManagerName = prop.getProperty("QueueManagerName");
			this.sendQueueName = prop.getProperty("SendQueueName");
			this.recvQueueName = prop.getProperty("RecvQueueName");
			this.timeout = Integer.parseInt(prop.getProperty("timeout"));
			this.mqexpiry = Integer.parseInt(prop.getProperty("mqexpiry"));
			this.exeDay = prop.getProperty("exeDay");
			this.exeTime = prop.getProperty("exeTime");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("初始化ServerConfig.properties配置信息失败");
		}
	}

	public static ServerConfigReader getInstance() {
		return instance;
	}

	public String getServerPort() {
		return serverPort;
	}

	public String getPoolSize() {
		return poolSize;
	}

	public String getServerTimeout() {
		return serverTimeout;
	}

	public String getMqServerIP() {
		return mqServerIP;
	}
	public void setMqServerIP(String mqServerIP) {
		this.mqServerIP = mqServerIP;
	}
	public int getMqServerPort() {
		return mqServerPort;
	}
	public void setMqServerPort(int mqServerPort) {
		this.mqServerPort = mqServerPort;
	}
	public int getMqServerCCSID() {
		return mqServerCCSID;
	}
	public void setMqServerCCSID(int mqServerCCSID) {
		this.mqServerCCSID = mqServerCCSID;
	}
	public String getMqServerChannel() {
		return mqServerChannel;
	}
	public void setMqServerChannel(String mqServerChannel) {
		this.mqServerChannel = mqServerChannel;
	}
	public String getQueueManagerName() {
		return queueManagerName;
	}
	public void setQueueManagerName(String queueManagerName) {
		this.queueManagerName = queueManagerName;
	}
	public String getSendQueueName() {
		return sendQueueName;
	}
	public void setSendQueueName(String sendQueueName) {
		this.sendQueueName = sendQueueName;
	}
	public String getRecvQueueName() {
		return recvQueueName;
	}
	public void setRecvQueueName(String recvQueueName) {
		this.recvQueueName = recvQueueName;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getMqexpiry() {
		return mqexpiry;
	}
	public void setMqexpiry(int mqexpiry) {
		this.mqexpiry = mqexpiry;
	}

	public String getServerMode() {
		return serverMode;
	}

	public String getExeDay() {
		return exeDay;
	}

	public String getExeTime() {
		return exeTime;
	}
}
