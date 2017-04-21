package com.global.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author chen.feng
 * @date 2013-4-11 
 * @version v1.0
 */
public class ConfigurationUtil {

	private static final Log log = LogFactory.getLog(ConfigurationUtil.class);
	private static ConfigurationUtil instance = null;

	private ConfigurationUtil() {
	}

	public synchronized static ConfigurationUtil getInstance() {
		if(instance == null){
			instance = new ConfigurationUtil();
		}
		return instance;
	}

	public String read(String properties, String key) {
		// 读取配置文件
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream(properties);
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			log.error("读取配置文件错误：", e);
		}

		// 取得配置文件中的值
		return p.getProperty(key);
	}
}
