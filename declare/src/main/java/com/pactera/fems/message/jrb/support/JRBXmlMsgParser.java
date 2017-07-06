package com.pactera.fems.message.jrb.support;


import com.pactera.fems.message.util.JRBIntfCodeCfgUtil;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.handler.Xml2BeanHandler;

import java.util.Map;

public class JRBXmlMsgParser {
    public static Object parseXml(String serviceMethod, String xml)
            throws DataCheckException {
        Map map = JRBIntfCodeCfgUtil.getCfgCache(serviceMethod);
        Object rspMsg =  new Xml2BeanHandler().toBean(xml, (String) map.get("rspXmlMapping"), "UTF-8");
        return rspMsg;
    }


}
