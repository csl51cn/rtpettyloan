package com.pactera.fems.message.jrb.support;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pactera.fems.message.util.JRBIntfCodeCfgUtil;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.handler.Xml2BeanHandler;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JRBXmlMsgParser {
    public static Object parseXml(String serviceMethod, String xml)
            throws DataCheckException {
        Map map = JRBIntfCodeCfgUtil.getCfgCache(serviceMethod);
        Object rspMsg =  new Xml2BeanHandler().toBean(xml, (String) map.get("rspXmlMapping"), "UTF-8");
        return rspMsg;
    }
    /**
     * 一个迭代方法
     * @param element
     *  org.jdom.Element
     * @return java.util.Map 实例
     */
    @SuppressWarnings("unchecked")
    private static Map  iterateElement(Element element) {
        List jiedian = element.getChildren();
        Element et = null;
        Map obj = new HashMap();
        List list = null;
        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList();
            et = (Element) jiedian.get(i);
            //  System.out.println(et);
            if (et.getTextTrim().equals("")) {
    /*            if (et.getChildren().size() == 0)
                    continue;  */
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                obj.put(et.getName(), list);
            } else {
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                obj.put(et.getName(), list);
            }
        }
        return obj;
    }

    /**
     * 转换一个xml格式的字符串到json格式
     * @param xml    xml格式的字符串
     * @return 成功返回json 格式的字符串;失败反回null
     */
    public static  JSONObject xml2JSON(String xml) {
        JSONObject obj = new JSONObject();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            obj.put(root.getName(), iterateElement(root));
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        JSONObject jsonObject = xml2JSON(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><transaction><header><msg></msg></header><body><GetTx><SYS_ERRCODE>1708</SYS_ERRCODE><BATCH_NO>2018090400000005</BATCH_NO><DATA_TYPE>CONTRACT_INFO</DATA_TYPE></GetTx></body></transaction>   ");
        JSONArray jsonObject1 = jsonObject.getJSONObject("transaction").getJSONArray("body").getJSONObject(0).getJSONArray("GetTx").getJSONObject(0).getJSONArray("SYS_ERRCODE");
        System.out.println(jsonObject1);
//        JSONObject body = (JSONObject) jsonObject.get("body");
//        JSONObject getTx = (JSONObject) body.get("GetTx");
//        String systemErrorCode = (String) getTx.get("SYS_ERRCODE");
//        System.out.println(systemErrorCode);
        System.out.println(jsonObject);
    }

}
