package com.pactera.fems.mq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.global.framework.xmlbeans.util.XmlBeanUtil;


public class MQConfigUtil {
    private static final Logger log = Logger.getLogger(MQConfigUtil.class);
    private static MQConfigUtil instance = null;
    private Map<String, Map<String, String>> map = new HashMap();

    private MQConfigUtil() {
        File f = new File(ParamsConfigUtil.getInstance().getSystemConfigHome() + "MQConfig.xml");
        InputStream is = null;
        if ((f == null) || (!f.exists())) {
            log.error(ParamsConfigUtil.getInstance().getSystemConfigHome() + "MQConfig.xml不存在");
        } else {
            try {
                log.info("加载" + ParamsConfigUtil.getInstance().getSystemConfigHome() + "MQConfig.xml开始");
                is = new FileInputStream(f);
            } catch (FileNotFoundException e) {
                log.error("加载" + ParamsConfigUtil.getInstance().getSystemConfigHome() + "MQConfig.xml失败");
            }
        }


        Element e = null;
        try {
            e = XmlBeanUtil.loadXml(is);
            parseXmlField(e);
            log.error("加载MQ服务器配置文件[MQConfig.xml]成功");
        } catch (Exception e1) {
            log.error("加载MQ服务器配置文件[MQConfig.xml]失败", e1);
        }
    }

    private void parseXmlField(Element root) throws Exception {
        List<Element> es = root.elements();
        for (int i = 0; i < es.size(); i++) {
            Element e = (Element) es.get(i);
            String bankCode = e.attribute("bankCode").getText().trim();

            Map<String, String> m = new HashMap();
            Element el = e.element("MQServerIP");
            String MQServerIP = el.getTextTrim();
            m.put("MQServerIP", MQServerIP);

            el = e.element("MQServerPort");
            String MQServerPort = el.getTextTrim();
            m.put("MQServerPort", MQServerPort);

            el = e.element("MQServerChannel");
            String MQServerChannel = el.getTextTrim();
            m.put("MQServerChannel", MQServerChannel);

            el = e.element("MqServerCCSID");
            String MqServerCCSID = el.getTextTrim();
            m.put("MqServerCCSID", MqServerCCSID);

            el = e.element("QueueManagerName");
            String QueueManagerName = el.getTextTrim();
            m.put("QueueManagerName", QueueManagerName);

            el = e.element("SendQueueName");
            String SendQueueName = el.getTextTrim();
            m.put("SendQueueName", SendQueueName);

            el = e.element("RecvQueueName");
            String RecvQueueName = el.getTextTrim();
            m.put("RecvQueueName", RecvQueueName);

            el = e.element("timeout");
            String timeout = el.getTextTrim();
            m.put("timeout", timeout);

            el = e.element("mqexpiry");
            String mqexpiry = el.getTextTrim();
            m.put("mqexpiry", mqexpiry);

            this.map.put(bankCode, m);
        }
    }

    public Map<String, Map<String, String>> getMQConfigMap() {
        return this.map;
    }

    public static MQConfigUtil getInstance() {
        if (instance == null) {
            instance = new MQConfigUtil();
        }
        return instance;
    }

    public static void main(String[] args) {
        new MQConfigUtil();
    }
}

