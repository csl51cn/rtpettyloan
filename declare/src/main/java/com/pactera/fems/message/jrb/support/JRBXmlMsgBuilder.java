package com.pactera.fems.message.jrb.support;


import com.pactera.fems.message.jrb.domain.*;
import com.pactera.fems.message.util.JRBIntfCodeCfgUtil;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.handler.Bean2XmlHandler;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JRBXmlMsgBuilder {

    /**
     * 实时类生成xml
     * @param getTx
     * @param headerMsg
     * @return
     * @throws DataCheckException
     */
    public String buildXml(JRBGetTx getTx, JRBReqHeaderMsg headerMsg) throws DataCheckException {

        Map map = JRBIntfCodeCfgUtil.getCfgCache(getTx.getClass().getName());
        String reqXmlMapping = (String) map.get("reqXmlMapping");


        JRBReqMsg transaction = new JRBReqMsg();
        transaction.setHeader(new JRBReqHeader(headerMsg));
        transaction.setBody(new JRBReqBody(getTx));
        Bean2XmlHandler bean2XmlHandler = new Bean2XmlHandler();

        //java对象转xml,保存xml到本地
        String xml = bean2XmlHandler.toXml(transaction, reqXmlMapping, "UTF-8");
        return formatXml(xml);
    }


    /**
     * 格式化xml,去除回车换行符和缩进符,返回的xml为一行
     * @param xml
     * @return
     */
    private String formatXml(String xml) {
        StringBuilder xmlToReturn = new StringBuilder();
        int len = xml.length();
        String length = String.format("%08d", len);
        xmlToReturn.append(length).append("<?xml version='1.0' encoding='UTF-8'?>");
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
        Matcher m = p.matcher(xml);
        String finalresult = m.replaceAll("");
        xmlToReturn.append(finalresult);
        return xmlToReturn.toString();
    }

    /**
     * 批量上传生成xml
     * @param getTxList
     * @param headerMsg
     * @return
     * @throws DataCheckException
     */
    public String buildXml(List<JRBGetTx> getTxList ,JRBReqHeaderMsg headerMsg)throws DataCheckException{
        Map map = JRBIntfCodeCfgUtil.getCfgCache(getTxList.get(0).getClass().getName());
        String reqXmlMapping = (String) map.get("reqXmlMapping");

        return null;
    }


}
