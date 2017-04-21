package com.global.fems.message.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.global.framework.xmlbeans.handler.XmlBeanCfgManager;
import org.global.framework.xmlbeans.util.XmlBeanUtil;

/**
 * 类描述：接口交易码工具类
 * 
 * @author chen.feng
 * @date 2015-6-13
 * @version v1.0
 */
@SuppressWarnings("rawtypes")
public class IntfCodeCfgUtil {
	private final static Logger log = Logger.getLogger(IntfCodeCfgUtil.class);

	private static Map cfgCache = null;

	static {
		cfgCache = new HashMap();
		try {
			//先加载接口配置文件
			loadIntfCodeCfg(cfgCache, "/syscfg/IntfCodeCfg.xml");
			Set set = cfgCache.keySet();
			Map elementCfgMap = new HashMap();
			//先加载WGReqMsg.xml和WGRspMsg.xml
			String filePath = "/xmlcfg/";
			loadXmlCfg(filePath, elementCfgMap, "Msg");
			loadXmlCfg(filePath, elementCfgMap, "MsgHead");
			String recvPath = filePath + "receive/";
			String rspPath = filePath + "response/";
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (key.startsWith("txCode:")) {
					IntfCodeCfg cfg = (IntfCodeCfg) cfgCache.get(key.substring(key.indexOf("txCode:")));
					loadXmlCfg(recvPath, elementCfgMap, cfg.getRecvXmlMapping());
					
					if (!"".equals(cfg.getRspXmlMapping())) {
						loadXmlCfg(rspPath, elementCfgMap, cfg.getRspXmlMapping());
					}
				}
			}
		} catch (Exception e) {
			log.error("初始化配置文件出错：", e);
		}
	}

	public static void loadIntfCodeCfg(Map cfgMap, String fileName) throws Exception {
		log.info("初始化配置文件IntfCodeCfg.xml开始...");
		// cfgPath = Utils.getSystemConfigHome() + "IntfCodeCfg.xml";
//		cfgPath = IntfCodeCfgUtil.class.getClassLoader().getResource("syscfg")
//				.getFile()
//				+ File.separator + "IntfCodeCfg.xml";
//		File f = new File(cfgPath);
//		if (f == null || !f.exists()) {
//			log.info("未找到IntfCodeCfg.xml文件");
//			return;
//		}
		InputStream is = IntfCodeCfgUtil.class.getResourceAsStream(fileName);
		parseXmlField(XmlBeanUtil.loadXml(is), cfgMap);
	}

	@SuppressWarnings("unchecked")
	private static void parseXmlField(Element root, Map cfgMap) throws Exception {
		List es = root.elements();
		for (int i = 0; i < es.size(); i++) {
			Element e = (Element) es.get(i);
			IntfCodeCfg cfg = new IntfCodeCfg();
			cfg.setTxCode(e.attribute("txCode").getText().trim());
			cfg.setTxName(e.attribute("txName").getText().trim());
			cfg.setRecvEntity(e.attribute("recvEntity").getText().trim());
			cfg.setRspEntity(StringUtils.isNotBlank(e.attribute("rspEntity").getText()) ? e.attribute("rspEntity").getText().trim() : "");
			cfg.setRecvXmlMapping(e.attribute("recvXmlMapping").getText().trim());
			cfg.setRspXmlMapping(StringUtils.isNotBlank(e.attribute("rspXmlMapping").getText()) ? e.attribute("rspXmlMapping").getText().trim() : "");
			
			cfg.setServiceMethod(e.attribute("serviceMethod").getText().trim());
			cfg.setIsValid(e.attribute("isValid").getText().trim());
			cfg.setVersion(e.attribute("version").getText().trim());
			cfgMap.put(cfg.getTxCode(), cfg);
			cfgMap.put("txCode:" + cfg.getTxCode(), cfg);
		}
	}
	
	private static void loadXmlCfg(String filePath, Map elementCfgMap,
			String xmlMapping) throws Exception {
		InputStream in = IntfCodeCfgUtil.class.getResourceAsStream(filePath + xmlMapping + ".xml");
		Element root = XmlBeanUtil.loadXml(in);
		XmlBeanCfgManager.getInstance().initXmlCfg(xmlMapping, root, elementCfgMap);
	}

	/**
	 * 获取接口交易配置信息
	 * 
	 * @param tradeCode
	 *            接口交易码
	 * @return
	 */
	public static IntfCodeCfg getIntfCodeCfg(String tradeCode) {
		IntfCodeCfg cfg = (IntfCodeCfg) cfgCache.get(tradeCode);
		if (cfg == null) {
			return null;
		}
		return cfg;
	}
	
	public static void init() {
		log.info("初始化前置接口XML报文配置模板成功...");
	}

	public static void main(String[] args) {
		init();
	}
}
