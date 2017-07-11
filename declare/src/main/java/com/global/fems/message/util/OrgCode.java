package com.global.fems.message.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by senlin.deng on 2017-06-08.
 */
public class OrgCode {
    private static String orgCode = null;
    static{
        Properties properties = new Properties();
        try {
            properties.load(OrgCode.class.getResourceAsStream("/resource.properties"));
            orgCode = (String) properties.get("ORG_CODE");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static  String getOrgCode(){
        return orgCode;
    }
}
