//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.global.framework.xmlbeans.handler;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.global.framework.xmlbeans.bean.SerialBean;
import org.global.framework.xmlbeans.bean.XmlMsgCfg;
import org.global.framework.xmlbeans.util.PropertyUtils;
import org.global.framework.xmlbeans.util.XmlBeanUtil;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bean2XmlHandler {
    public Bean2XmlHandler() {
    }

    public String toXml(Object obj, String fileName, String ccsid) throws DataCheckException {
        XStream xs = new XStream(new DomDriver(ccsid, new NoNameCoder()));
        System.out.println("fileName=" + fileName);
        Map alias = XmlBeanCfgManager.getCfgByFileName(fileName);
        String nodeName = (String)alias.get(fileName + ".nodename");
        xs.alias(nodeName, obj.getClass());
        Field[] fields = PropertyUtils.getDeclaredFields(obj.getClass(), new ArrayList());

        for(int xml = 0; xml < fields.length; ++xml) {
            Field field = fields[xml];
            String key = fileName + "." + nodeName + ".";
            buildXml(xs, alias, key + field.getName(), field, obj);
        }

        String var11 = xs.toXML(obj);
        return var11;
    }

    private static void buildXml(XStream xs, Map alias, String key, Field field, Object obj) throws DataCheckException {
        XmlMsgCfg xmlMsgCfg = XmlBeanUtil.getXmlMsgCfg(key, alias);
        insertFieldValue(field, obj, xmlMsgCfg);
        xs.aliasField(xmlMsgCfg.getNodeName(), obj.getClass(), field.getName());
        if("Single".equals(xmlMsgCfg.getType()) || "Group".equals(xmlMsgCfg.getType())) {
            Class subCls = null;

            try {
                subCls = Class.forName(xmlMsgCfg.getClassName());
            } catch (ClassNotFoundException var11) {
                throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "系统未找到实体类[" + xmlMsgCfg.getClassName() + "]:" + var11.getMessage());
            }

            Object subObj = getFieldValue(field, obj);
            if(subObj == null) {
                return;
            }

            xs.aliasSystemAttribute((String)null, "class");
            xs.alias(xmlMsgCfg.getNodeName(), subCls);
            if("Group".equals(xmlMsgCfg.getType()) && List.class.isAssignableFrom(subCls)) {
                if("H".equals(xmlMsgCfg.getIsHide())) {
                    xs.addImplicitCollection(obj.getClass(), xmlMsgCfg.getFieldName());
                }

                buildAliasForGroup(xs, alias, key + "." + xmlMsgCfg.getSubNodeName(), subObj);
            } else {
                Field[] subFields = PropertyUtils.getDeclaredFields(subCls, new ArrayList());

                for(int j = 0; j < subFields.length; ++j) {
                    Field subField = subFields[j];
                    buildAliasForField(xs, alias, key, subCls, subField, subObj);
                }
            }
        }

    }

    private static void insertFieldValue(Field field, Object obj, XmlMsgCfg subXmlMsgCfg) throws DataCheckException {
        String setMethName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        Method setMth = PropertyUtils.getDeclaredMethod(obj.getClass(), setMethName, new Class[]{field.getType()});

        try {
            if("Y".equals(subXmlMsgCfg.getIsHide())) {
                setMth.invoke(obj, new Object[]{null});
            } else {
                Object e = getFieldValue(field, obj);
                System.out.println("value=" + e);
                if(String.class.equals(field.getType()) && e == null) {
                    setMth.invoke(obj, new Object[]{""});
                } else if(SerialBean.class.isAssignableFrom(field.getType()) && e == null) {
                    setMth.invoke(obj, new Object[]{field.getType().newInstance()});
                } else if(Double.class.equals(field.getType()) || "Double".equals(subXmlMsgCfg.getType())) {
                    if(StringUtils.isBlank(subXmlMsgCfg.getFormat())) {
                        return;
                    }

                    DecimalFormat df = new DecimalFormat(subXmlMsgCfg.getFormat().replaceAll("#", "0"));
                    String val = (String)e;
                    if(StringUtils.isBlank(val)) {
                        val = "0.00";
                    }

                    String tmpValue = df.format(new Double(val.replaceAll(",", "")));
                    setMth.invoke(obj, new Object[]{tmpValue});
                }
            }

        } catch (Exception var9) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "处理实体类[" + obj.getClass() + "]的属性[" + field.getName() + "]空值出错:" + var9.getMessage());
        }
    }

    private static Object getFieldValue(Field field, Object obj) throws DataCheckException {
        String getMethName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        Method getMth = PropertyUtils.getDeclaredMethod(obj.getClass(), getMethName, new Class[0]);
        Object value = null;

        try {
            value = getMth.invoke(obj, new Object[0]);
            return value;
        } catch (Exception var6) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "获取实体类[" + obj.getClass() + "]的属性[" + field.getName() + "]值出错:" + var6.getMessage());
        }
    }

    private static void buildAliasForGroup(XStream xs, Map alias, String key, Object obj) throws DataCheckException {
        XmlMsgCfg cfg = XmlBeanUtil.getXmlMsgCfg(key, alias);
        Class subCls = null;

        try {
            subCls = Class.forName(cfg.getClassName());
        } catch (ClassNotFoundException var12) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "系统未找到实体类[" + cfg.getClassName() + "]:" + var12.getMessage());
        }

        xs.alias(cfg.getNodeName(), subCls);
        Field[] subFields = PropertyUtils.getDeclaredFields(subCls, new ArrayList());
        List list = (List)obj;

        for(int i = 0; i < list.size(); ++i) {
            for(int j = 0; j < subFields.length; ++j) {
                Field subField = subFields[j];
                String subKey = key + "." + subField.getName();
                buildXml(xs, alias, subKey, subField, list.get(i));
            }
        }

    }

    private static void buildAliasForField(XStream xs, Map alias, String key, Class subCls, Field subField, Object obj) throws DataCheckException {
        XmlMsgCfg subXmlMsgCfg = XmlBeanUtil.getXmlMsgCfg(key + "." + subField.getName(), alias);
        if(subXmlMsgCfg == null) {
            System.out.println("忽略此字段：" + key + "." + subField.getName());
            xs.omitField(subCls, subField.getName());
        } else {
            xs.aliasField(subXmlMsgCfg.getNodeName(), subCls, subField.getName());
            insertFieldValue(subField, obj, subXmlMsgCfg);
            if("Single".equals(subXmlMsgCfg.getType()) || "Group".equals(subXmlMsgCfg.getType())) {
                buildXml(xs, alias, key + "." + subField.getName(), subField, obj);
            }

        }
    }

    private String formatXml(String xml, String ccsid) throws DataCheckException {
        OutputFormat of = OutputFormat.createPrettyPrint();
        of.setTrimText(false);
        of.setNewlines(false);
        of.setEncoding(ccsid);
        ByteArrayOutputStream bo = new ByteArrayOutputStream();

        try {
            XMLWriter e = new XMLWriter(bo, of);
            Element e1 = XmlBeanUtil.loadXml(xml, ccsid);
            e.write(e1);
            e.close();
            bo.close();
            xml = new String(bo.toByteArray(), ccsid);
            return xml;
        } catch (Exception var7) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "格式化XML报文出错:" + var7.getMessage());
        }
    }
}
