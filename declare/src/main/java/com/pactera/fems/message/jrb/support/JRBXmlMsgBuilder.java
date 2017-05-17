package com.pactera.fems.message.jrb.support;


import com.pactera.fems.message.jrb.domain.*;
import com.pactera.fems.message.util.JRBIntfCodeCfgUtil;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.handler.Bean2XmlHandler;

import java.util.Map;

public class JRBXmlMsgBuilder {

    public String buildXml(JRBGetTx getTx ,JRBReqHeaderMsg headerMsg, String reqXmlMapping ) throws DataCheckException {

        Map map = JRBIntfCodeCfgUtil.getCfgCache(getTx.getClass().getName());
        reqXmlMapping = (String) map.get("reqXmlMapping");

        JRBReqMsg transaction = new JRBReqMsg();
        transaction.setHeader( new JRBReqHeader(headerMsg));
        transaction.setBody(new JRBReqBody(getTx));
        String xml = new Bean2XmlHandler().toXml(transaction, reqXmlMapping, "UTF-8");
        xml = "<?xml version='1.0' encoding='GBK'?>" + xml;
        return  xml;
    }


}
