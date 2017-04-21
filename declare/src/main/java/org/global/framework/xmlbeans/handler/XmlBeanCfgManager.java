//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.global.framework.xmlbeans.handler;

import org.dom4j.Element;
import org.global.framework.xmlbeans.util.XmlBeanUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

public class XmlBeanCfgManager {
    private static XmlBeanCfgManager instance = new XmlBeanCfgManager();
    private static Map xmlCfgCache = new HashMap();

    private XmlBeanCfgManager() {
    }

    public static XmlBeanCfgManager getInstance() {
        return instance;
    }

    public void initXmlCfg(String fileName, Element root, Map elementCfgMap) throws Exception {
        elementCfgMap.put(fileName, root);
        xmlCfgCache.put(fileName, XmlBeanUtil.elementToMap(root, fileName, elementCfgMap));
    }

    public static Map getCfgByFileName(String name) {
        return (Map)xmlCfgCache.get(name);
    }

    public static Map getXmlCfgCache() {
        return xmlCfgCache;
    }

    public static void loadXmlcfg(String filePath, Map xmlCfgMap, Map elementCfgMap) throws Exception {
        File xmlcfg = new File(XmlBeanCfgManager.class.getClassLoader().getResource(filePath).toURI());
        File[] xmlfs = xmlcfg.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase().endsWith(".xml") && !pathname.isDirectory();
            }
        });
        File[] dirfs = xmlcfg.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return !pathname.getName().toLowerCase().endsWith(".xml") && pathname.isDirectory();
            }
        });
        int i;
        if(xmlfs != null && xmlfs.length > 0) {
            for(i = 0; i < xmlfs.length; ++i) {
                File f = xmlfs[i];
                String fileName = f.getName().replaceAll(".xml", "").replaceAll(".XML", "");
                Element root = XmlBeanUtil.loadXml(f);
                elementCfgMap.put(fileName, root);
                xmlCfgMap.put(fileName, XmlBeanUtil.elementToMap(root, fileName, elementCfgMap));
            }
        }

        if(dirfs != null && dirfs.length > 0) {
            for(i = 0; i < dirfs.length; ++i) {
                loadXmlcfg(getFilePath(dirfs[i], new StringBuilder()), xmlCfgMap, elementCfgMap);
            }
        }

    }

    private static String getFilePath(File f, StringBuilder sb) {
        String fname = f.getName();
        if(!"xmlcfg".equals(fname)) {
            getFilePath(f.getParentFile(), sb);
            sb.append(File.separatorChar);
        }

        sb.append(fname);
        return sb.toString();
    }
}
