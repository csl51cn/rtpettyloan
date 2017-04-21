package com.pactera.fems.mq.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ParamsConfigUtil {
    private static final Logger log = Logger.getLogger(ParamsConfigUtil.class);
    private static ParamsConfigUtil instance = null;
    private String winPath;
    private String linuxPath;

    private ParamsConfigUtil() {
        Properties prop = new Properties();
        InputStream is = ParamsConfigUtil.class.getResourceAsStream("/syscfg/params.properties");
        try {
            prop.load(is);
            this.winPath = prop.getProperty("win.path");
            this.linuxPath = prop.getProperty("linux.path");
        } catch (IOException e) {
            log.error("初始化params.properties失败", e);
        }
    }

    public static ParamsConfigUtil getInstance() {
        if (instance == null) {
            instance = new ParamsConfigUtil();
        }
        return instance;
    }

    public String getSystemConfigHome() {
        String os = System.getProperty("os.name");
        if ((os == null) || ("".equals(os))) {
            os = "UNIX";
        }
        os = os.toUpperCase();
        if (os.indexOf("WINDOWS") >= 0) {
            os = "WIN";
        } else {
            os = "UNIX";
        }
        String path = null;
        if ("WIN".equals(os)) {
            path = this.winPath + getFileSplit();
        } else {
            path = this.linuxPath + getFileSplit();
        }
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        if (!f.isDirectory()) {
            f.deleteOnExit();
            f.mkdirs();
        }
        return path;
    }


    public static String getFileSplit() {
        return System.getProperty("file.separator");
    }

    public static void main(String[] args) {
        getInstance();
    }
}


