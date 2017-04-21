package com.pactera.fems.mq.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;


public class MQConfigReader {
    private static final Logger log = Logger.getLogger(MQConfigReader.class);
    private static MQConfigReader instance = null;
    private MQConfig mqConfig = null;

    private MQConfigReader() {
        Properties prop = new Properties();
        File f = new File(getSystemConfigHome() + "MQConfig.properties");
        InputStream is = null;
        if ((f == null) || (!f.exists())) {
            log.error(getSystemConfigHome() + "MQConfig.properties不存在，读取程序包中的MQConfig.properties");
            is = MQConfigReader.class.getResourceAsStream("/syscfg/MQConfig.properties");
        } else {
            try {
                log.info("加载" + getSystemConfigHome() + "MQConfig.properties");
                is = new FileInputStream(f);
            } catch (FileNotFoundException e) {
                log.error(getSystemConfigHome() + "MQConfig.properties不存在，读取程序包中的MQConfig.properties");
                is = MQConfigReader.class.getResourceAsStream("/syscfg/MQConfig.properties");
            }
        }

        try {
            prop.load(is);
            this.mqConfig = new MQConfig();
            this.mqConfig.setMqServerIP(prop.getProperty("MQServerIP"));
            this.mqConfig.setMqServerPort(Integer.parseInt(prop.getProperty("MQServerPort")));
            this.mqConfig.setMqServerChannel(prop.getProperty("MQServerChannel"));
            this.mqConfig.setMqServerCCSID(Integer.parseInt(prop.getProperty("MqServerCCSID")));
            this.mqConfig.setQueueManagerName(prop.getProperty("QueueManagerName"));
            this.mqConfig.setSendQueueName(prop.getProperty("SendQueueName"));
            this.mqConfig.setRecvQueueName(prop.getProperty("RecvQueueName"));
            this.mqConfig.setTimeout(Integer.parseInt(prop.getProperty("timeout")));
            this.mqConfig.setMqexpiry(Integer.parseInt(prop.getProperty("mqexpiry")));
        } catch (IOException e) {
            log.error("初始化MQ服务器配置信息失败", e);
        }
    }

    public static MQConfigReader getInstance() {
        if (instance == null) {
            instance = new MQConfigReader();
        }
        return instance;
    }

    public MQConfig getMqConfig() {
        return this.mqConfig;
    }

    public static String getSystemConfigHome() {
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
        String path = System.getProperty("user.home") + getFileSplit() + "ebills" + getFileSplit();

        if ("WIN".equals(os)) {
            path = "d:" + getFileSplit() + "gjyw" + getFileSplit() + "fems" + getFileSplit();
        } else {
            path = "/home" + getFileSplit() + "gjyw" + getFileSplit() + "fems" + getFileSplit();
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
}

