//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.global.framework.xmlbeans.handler;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.global.framework.xmlbeans.bean.XmlMsgCfg;
import org.global.framework.xmlbeans.util.PropertyUtils;
import org.global.framework.xmlbeans.util.XmlBeanUtil;
import org.global.framework.xmlbeans.util.XmlValidator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Xml2BeanHandler {
    private static final Logger log = Logger.getLogger(Xml2BeanHandler.class);

    public Xml2BeanHandler() {
    }

    public Object toBean(String message, String filename, String ccsid) throws DataCheckException {
        Map alias = XmlBeanCfgManager.getCfgByFileName(filename);
        return this.fromXML(message, alias, filename, ccsid);
    }

    private Object fromXML(String message, Map alias, String filename, String ccsid) throws DataCheckException {
        Element root = null;

        try {
            root = XmlBeanUtil.loadXml(message, ccsid);
        } catch (Exception var7) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "解析XML为实体对象出错:" + var7.getMessage());
        }

        Object obj = this.instanceXMLRootForObject(root, alias, filename);
        return obj;
    }

    private Object instanceXMLRootForObject(Element root, Map alias, String filename) throws DataCheckException {
        String key = filename + ".class";
        this.getElementNode(root);
        Object obj = null;

        try {
            obj = Class.forName(String.valueOf(alias.get(key))).newInstance();
        } catch (Exception var7) {
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实例化实体类[" + String.valueOf(alias.get(key)) + "]出错:" + var7.getMessage());
        }

        this.instanceObjectDeal(root, alias, filename, (List)null, (Class)null, obj);
        return obj;
    }

    private String getElementNode(Element e) {
        StringBuilder xmlnode = new StringBuilder();
        Element prt = e.getParent();
        if(prt != null) {
            Attribute prtatt = prt.attribute("ebills.fems.xml.xmlnode");
            xmlnode.append(prtatt.getStringValue()).append(".");
        }

        xmlnode.append(e.getName());
        e.addAttribute("ebills.fems.xml.xmlnode", xmlnode.toString());
        return xmlnode.toString();
    }

    private Object instanceObject(Element xe, Map alias, String filename, List list) throws DataCheckException {
        String key = filename + "." + this.getElementNode(xe);
        XmlMsgCfg cfg = (XmlMsgCfg)alias.get(key);
        Object result = null;
        if("Single".equals(cfg.getType()) || "Group".equals(cfg.getType())) {
            Class childClass;
            try {
                childClass = Class.forName(cfg.getClassName());
                if(list == null) {
                    result = childClass.newInstance();
                }
            } catch (Exception var10) {
                throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实例化实体类[" + cfg.getClassName() + "]出错:" + var10.getMessage());
            }

            this.instanceObjectDeal(xe, alias, filename, list, childClass, result);
        }

        return result;
    }

    private void instanceObjectDeal(Element xe, Map alias, String filename, List list, Class childClass, Object obj) throws DataCheckException {
        List xes = xe.elements();
        if(xes != null && xes.size() > 0) {
            for(int i = 0; i < xes.size(); ++i) {
                Element chilxe = (Element)xes.get(i);
                String key = filename + "." + this.getElementNode(chilxe);
                XmlMsgCfg cfg = (XmlMsgCfg)alias.get(key);
                if(cfg == null) {
                    String e = "报文节点：" + key + "在映射xml:" + filename + ".xml配置中没有配置，请重新配置该报文xml映射文件！";
                    log.error("XML格式校验失败：" + e);
                    throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "XML格式校验失败，报文节点：" + key + "不存在！");
                }

                if(list != null) {
                    try {
                        obj = Class.forName(cfg.getClassName()).newInstance();
                    } catch (Exception var13) {
                        throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实例化实体类[" + cfg.getClassName() + "]出错:" + var13.getMessage());
                    }

                    this.instanceObjectDeal(chilxe, alias, filename, (List)null, (Class)null, obj);
                    list.add(obj);
                } else {
                    this.setObjectField(chilxe, alias, cfg, filename, obj);
                }
            }

        }
    }

    private void setObjectField(Element xe, Map alias, XmlMsgCfg cfg, String filename, Object obj) throws DataCheckException {
        this.getElementNode(xe);
        XmlValidator.validateFieldOfElement(cfg, xe, filename);
        String fieldname = cfg.getFieldName();
        String setMethName = "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
        Class clasz = obj.getClass();
        Field f = PropertyUtils.getDeclaredField(clasz, fieldname);
        if(f != null) {
            Class clsFieldType = f.getType();
            Method setMth = PropertyUtils.getDeclaredMethod(clasz, setMethName, new Class[]{clsFieldType});
            this.setObjectFieldDeal(xe, obj, filename, setMth, clsFieldType, alias);
        }
    }

    private void setObjectFieldDeal(Element xe, Object obj, String filename, Method setMth, Class clsFieldType, Map alias) throws DataCheckException {
        if(!List.class.isAssignableFrom(clsFieldType) && !clsFieldType.isInstance(List.class)) {
            Object o;
            String txt1;
            if((!clsFieldType.isPrimitive() || clsFieldType.equals(Boolean.TYPE) || clsFieldType.equals(Character.TYPE)) && (clsFieldType.getSuperclass() == null || !clsFieldType.getSuperclass().equals(Number.class))) {
                txt1 = xe.getText();
                o = this.instanceObject(xe, alias, filename, (List)null);

                try {
                    if(!Object.class.isAssignableFrom(clsFieldType) || String.class.equals(clsFieldType) || txt1 != null && !"".equals(txt1.trim())) {
                        setMth.invoke(obj, new Object[]{clsFieldType.cast(txt1)});
                    } else {
                        setMth.invoke(obj, new Object[]{o});
                    }
                } catch (Exception var12) {
                    throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), var12.getMessage());
                }
            } else {
                txt1 = xe.getText();
                txt1 = txt1 != null && !"".equals(txt1)?txt1:"0";
                o = null;

                try {
                    if(clsFieldType.isPrimitive()) {
                        o = ((Class)XmlBeanUtil.getBasictypes().get(clsFieldType.getSimpleName())).getMethod("valueOf", new Class[]{String.class}).invoke((Object)null, new Object[]{txt1});
                    } else {
                        o = clsFieldType.getMethod("valueOf", new Class[]{String.class}).invoke((Object)null, new Object[]{txt1});
                    }

                    setMth.invoke(obj, new Object[]{o});
                } catch (Exception var10) {
                    throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), var10.getMessage());
                }
            }
        } else {
            ArrayList txt = new ArrayList();
            this.instanceObject(xe, alias, filename, txt);

            try {
                setMth.invoke(obj, new Object[]{txt});
            } catch (Exception var11) {
                throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), var11.getMessage());
            }
        }

    }
}
