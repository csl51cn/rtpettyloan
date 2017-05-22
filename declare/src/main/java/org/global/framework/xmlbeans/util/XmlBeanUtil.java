//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.global.framework.xmlbeans.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.global.framework.xmlbeans.bean.XmlMsgCfg;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlBeanUtil {
    private static Map basictypes = null;

    public XmlBeanUtil() {
    }

    public static Map getBasictypes() {
        return basictypes;
    }

    public static XmlMsgCfg getXmlMsgCfg(String key, Map map) {
        return (XmlMsgCfg)map.get(key);
    }

    public static Element loadXml(InputStream in) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(in);
        Element root = doc.getRootElement();
        return root;
    }

    public static Element loadXml(File f) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(f);
        Element root = doc.getRootElement();
        return root;
    }

    public static Element loadXml(String xml, String ccsid) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new ByteArrayInputStream(xml.getBytes(ccsid)));
        Element root = doc.getRootElement();
        return root;
    }

    public static Map elementToMap(Element root, String filename, Map elementCfgMap) throws Exception {
        HashMap alias = new HashMap();
        Attribute parent = root.attribute("extends");
        if(null != parent) {
            Element nodeName = (Element)elementCfgMap.get(parent.getText().trim());

            try {
                String e = root.attribute("extendsfield").getText().trim();
                Element extendsElement = getElementByPath(nodeName, e);
                extendsElement.add(root);
                root = nodeName;
            } catch (Exception var8) {
                var8.printStackTrace();
                throw new Exception("配置文件：" + filename + ".xml 继承属性配置“extends=\'" + parent.getText() + "\'”有误，对应的父级文件“" + parent.getText() + ".xml”不存在，请重新配置。");
            }
        }

        String nodeName1 = root.attribute("nodeName").getText().trim();
        //alias.put(filename + ".class", root.attribute("class").getText().trim());
        alias.put(filename + ".class", root.attribute("className").getText().trim());//aliasFieldOfElement()中用的是className,xml中没有class属性
        alias.put(filename + ".nodename", nodeName1);
        aliasFieldOfElement(filename, nodeName1, root.elements(), alias);
        return alias;
    }

    private static Element getElementByPath(Element e, String path) throws Exception {
        String[] paths = path.split("\\.");

        for(int i = 0; i < paths.length; ++i) {
            String pathstr = paths[i];
            e = getElementByPathDeal(e, pathstr, i);
        }

        if(e != null && e.attribute("nodeName").getText().trim().equals(paths[paths.length - 1])) {
            return e;
        } else {
            throw new Exception("xml映射文件配置节点不正确，继承的文件通过继承路径 extendsfield=\'" + path + "\'得到最终节点：" + (e != null?e.attribute("nodeName").getText():"null") + "需要的节点" + paths[paths.length - 1] + "不存在");
        }
    }

    private static Element getElementByPathDeal(Element e, String path, int pathindex) throws Exception {
        if(pathindex == 0) {
            if(!matchChildElementPath(e, path)) {
                throw new Exception("xml映射文件配置节点不正确，配置根节点：" + path + ",继承xml文件根节点：" + e.attribute("nodeName").getText());
            }
        } else {
            List es = e.elements();

            for(int i = 0; i < es.size(); ++i) {
                Element ce = (Element)es.get(i);
                if(matchChildElementPath(ce, path)) {
                    e = ce;
                    break;
                }
            }
        }

        return e;
    }

    private static boolean matchChildElementPath(Element e, String path) {
        Attribute att = e.attribute("nodeName");
        return att != null && att.getText().trim().toLowerCase().equals(path.toLowerCase());
    }

    private static void aliasFieldOfElement(String fileName, String nodeName, List es, Map alias) throws Exception {
        int i = 0;

        while(i < es.size()) {
            Element e = (Element)es.get(i);
            XmlMsgCfg xmlMsgCfg = new XmlMsgCfg();
            Attribute fieldNameAttr = e.attribute("fieldName");
            if(fieldNameAttr == null) {
                throw new Exception(fileName + ".xml文件下fieldName属性未定义!");
            }

            String fieldName = fieldNameAttr.getText();
            if(fieldName != null && !"".equals(fieldName)) {
                xmlMsgCfg.setFieldName(fieldName.trim());
                Attribute fieldTextAttr = e.attribute("text");
                if(fieldTextAttr != null) {
                    String nodeNameAttr = fieldTextAttr.getText();
                    xmlMsgCfg.setText(nodeNameAttr);
                }

                Attribute var20 = e.attribute("nodeName");
                if(var20 == null) {
                    throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的nodeName属性未定义!");
                }

                String nodename = var20.getText();
                if(nodename != null && !"".equals(nodename)) {
                    xmlMsgCfg.setNodeName(nodename.trim());
                    Attribute typeAttr = e.attribute("type");
                    if(typeAttr == null) {
                        throw new Exception(fileName + ".xml文件下Field标签有type属性未定义!");
                    }

                    String type = typeAttr.getText();
                    if(type != null && !"".equals(type)) {
                        xmlMsgCfg.setType(type.trim());
                        Attribute lengthAttr = e.attribute("length");
                        Attribute requiredAttr = e.attribute("required");
                        Attribute classNameAttr = e.attribute("className");
                        Attribute isHideAttr = e.attribute("isHide");
                        String subNodeName;
                        String ces;
                        if(!"String".equals(type) && !"Integer".equals(type) && !"Double".equals(type)) {
                            if("Single".equals(type) || "Group".equals(type)) {
                                label127: {
                                    if(classNameAttr == null) {
                                        throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的type=[Single或Group]时className属性未定义!");
                                    }

                                    subNodeName = classNameAttr.getText();
                                    if(subNodeName != null && !"".equals(subNodeName)) {
                                        xmlMsgCfg.setClassName(subNodeName.trim());
                                        if(isHideAttr == null) {
                                            throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的type=[Single或Group]时isHide属性未定义!");
                                        }

                                        ces = isHideAttr.getText();
                                        if(ces != null && !"".equals(ces)) {
                                            xmlMsgCfg.setIsHide(ces.trim());
                                            break label127;
                                        }

                                        throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的type=[Single或Group]时isHide属性不能为空!");
                                    }

                                    throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的type=[Single或Group]时className属性不能为空!");
                                }
                            }
                        } else {
                            if(lengthAttr == null) {
                                throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的length属性未定义!");
                            }

                            subNodeName = lengthAttr.getText();
                            if(subNodeName == null || "".equals(subNodeName)) {
                                throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的length属性不能为空!");
                            }

                            xmlMsgCfg.setLength(subNodeName.trim());
                            if(requiredAttr == null) {
                                throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的required属性未定义!");
                            }

                            ces = requiredAttr.getText();
                            if(ces == null || "".equals(ces)) {
                                throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的required属性不能为空!");
                            }

                            xmlMsgCfg.setRequired(ces.trim());
                        }

                        checkFieldFormat(fileName, e, xmlMsgCfg, fieldName, type);
                        Attribute var21 = e.attribute("subNodeName");
                        if(var21 != null) {
                            xmlMsgCfg.setSubNodeName(var21.getText().trim());
                        }

                        alias.put(fileName + "." + nodeName + "." + xmlMsgCfg.getFieldName(), xmlMsgCfg);
                        alias.put(fileName + "." + nodeName + "." + xmlMsgCfg.getNodeName(), xmlMsgCfg);
                        List var22 = e.elements();
                        if(var22 != null && var22.size() > 0) {
                            aliasFieldOfElement(fileName, nodeName + "." + xmlMsgCfg.getNodeName(), var22, alias);
                            aliasFieldOfElement(fileName, nodeName + "." + xmlMsgCfg.getFieldName(), var22, alias);
                        }

                        ++i;
                        continue;
                    }

                    throw new Exception(fileName + ".xml文件下Field标签有type属性为空!");
                }

                throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的nodeName属性不能为空!");
            }

            throw new Exception(fileName + ".xml文件下fieldName属性不能为空!");
        }

    }

    private static void checkFieldFormat(String fileName, Element e, XmlMsgCfg xmlMsgCfg, String fieldName, String type) throws Exception {
        if("Double".equals(type) || "Date".equals(type)) {
            Attribute formatAttr = e.attribute("format");
            if(formatAttr == null) {
                throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的type=[" + type + "]时format属性未定义!");
            }

            String format = formatAttr.getText();
            if(format == null || "".equals(format)) {
                throw new Exception(fileName + ".xml文件下fieldName=[" + fieldName + "]的type=[" + type + "]时format属性不能为空!");
            }

            xmlMsgCfg.setFormat(format.trim());
        }

    }

    static {
        basictypes = new HashMap();
        basictypes.put("byte", Byte.class);
        basictypes.put("short", Short.class);
        basictypes.put("int", Integer.class);
        basictypes.put("float", Float.class);
        basictypes.put("double", Double.class);
        basictypes.put("long", Long.class);
    }


}
