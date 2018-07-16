package com.global.fems.message.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by senlin.deng on 2017-06-08.
 */
public class OrgCode {
    private static String orgCode = null;
    static{
        Properties properties = new Properties();
        InputStream resourceAsStream = OrgCode.class.getResourceAsStream("/resource.properties");
        try {
            properties.load(resourceAsStream);
            orgCode = (String) properties.get("ORG_CODE");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(resourceAsStream);
        }
    }

    public  static  String getOrgCode(){
        return orgCode;
    }
}
