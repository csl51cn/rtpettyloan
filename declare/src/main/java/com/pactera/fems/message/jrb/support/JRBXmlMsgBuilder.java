package com.pactera.fems.message.jrb.support;


import com.pactera.fems.message.jrb.domain.*;
import com.pactera.fems.message.util.JRBIntfCodeCfgUtil;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.handler.Bean2XmlHandler;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JRBXmlMsgBuilder {

    public String buildXml(JRBGetTx getTx, JRBReqHeaderMsg headerMsg) throws DataCheckException {

        Map map = JRBIntfCodeCfgUtil.getCfgCache(getTx.getClass().getName());
        String reqXmlMapping = (String) map.get("reqXmlMapping");

        JRBReqMsg transaction = new JRBReqMsg();
        transaction.setHeader(new JRBReqHeader(headerMsg));
        transaction.setBody(new JRBReqBody(getTx));
        String xml = new Bean2XmlHandler().toXml(transaction, reqXmlMapping, "UTF-8");
        StringBuilder xmlToReturn = new StringBuilder();
        int len = xml.length();
        String length = String.format("%08d", len);
        xmlToReturn.append(length).append("<?xml version='1.0' encoding='UTF-8'?>");
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
        Matcher m = p.matcher(xml);
        String  finalresult = m.replaceAll("");
        xmlToReturn.append(finalresult);
        return xmlToReturn.toString();
    }



}
