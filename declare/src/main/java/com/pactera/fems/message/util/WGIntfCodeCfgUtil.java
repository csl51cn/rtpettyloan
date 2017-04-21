package com.pactera.fems.message.util;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.global.framework.xmlbeans.handler.XmlBeanCfgManager;
import org.global.framework.xmlbeans.util.XmlBeanUtil;

import java.io.InputStream;
import java.util.*;


public class WGIntfCodeCfgUtil {
    private static final Logger log = Logger.getLogger(WGIntfCodeCfgUtil.class);

    private static Map cfgCache = null;

    static {
        cfgCache = new HashMap();
        try {
            loadIntfCodeCfg(cfgCache, "/syscfg/WGIntfCodeCfg.xml");
            Set set = cfgCache.keySet();
            HashMap elementCfgMap = new HashMap();

            String filePath = "/xmlcfg/";
            loadXmlCfg(filePath, elementCfgMap, "WGReqMsg");
            loadXmlCfg(filePath, elementCfgMap, "WGRspMsg");
            String reqPath = filePath + "request/";
            String rspPath = filePath + "response/";
            for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                if (key.startsWith("serviceMethod:")) {
                    Map m = (Map) cfgCache.get(key.substring(key.indexOf("serviceMethod:")));
                    String reqXmlMapping = (String) m.get("reqXmlMapping");
                    loadXmlCfg(reqPath, elementCfgMap, reqXmlMapping);

                    String rspXmlMapping = (String) m.get("rspXmlMapping");
                    if (!"".equals(rspXmlMapping))
                        loadXmlCfg(rspPath, elementCfgMap, rspXmlMapping);
                }
            }
        } catch (Exception e) {
            Map elementCfgMap;
            String reqPath;
            String rspPath;
            Iterator iterator;
            log.error("初始化配置文件出错：", e);
        }
    }

    private static void loadXmlCfg(String filePath, Map elementCfgMap, String xmlMapping) throws Exception {
        InputStream in = WGIntfCodeCfgUtil.class.getResourceAsStream(filePath + xmlMapping + ".xml");
        Element root = XmlBeanUtil.loadXml(in);
        XmlBeanCfgManager.getInstance().initXmlCfg(xmlMapping, root, elementCfgMap);
    }

    private static void loadIntfCodeCfg(Map map, String fileName) throws Exception {
        log.info("初始化配置文件WGIntfCodeCfg.xml开始...");
        InputStream is = WGIntfCodeCfgUtil.class.getResourceAsStream(fileName);


        parseXmlField(XmlBeanUtil.loadXml(is), map);
    }

    private static void parseXmlField(Element root, Map map) throws Exception {
        List es = root.elements();
        for (int i = 0; i < es.size(); i++) {
            Element e = (Element) es.get(i);
            Map m = new HashMap();
            m.put("serviceName", e.attribute("serviceName").getText().trim());
            m.put("reqEntity", e.attribute("reqEntity").getText().trim());
            m.put("rspEntity", e.attribute("rspEntity").getText().trim());
            m.put("reqXmlMapping", e.attribute("reqXmlMapping").getText().trim());
            m.put("rspXmlMapping", e.attribute("rspXmlMapping").getText().trim());
            m.put("serviceMethod", e.attribute("serviceMethod").getText().trim());
            map.put(e.attribute("reqEntity").getText().trim(), m);
            map.put(e.attribute("rspEntity").getText().trim(), m);
            map.put(e.attribute("serviceMethod").getText().trim(), m);
            map.put("serviceMethod:" + e.attribute("serviceMethod").getText().trim(), m);
        }
    }

    public static Map getCfgCache(String key) {
        return (Map) cfgCache.get(key);
    }

    public static void init() {
        log.info("初始化外管个人结售汇业务相关接口XML报文配置模板成功...");
    }

    public static void main(String[] args) {
    }
}