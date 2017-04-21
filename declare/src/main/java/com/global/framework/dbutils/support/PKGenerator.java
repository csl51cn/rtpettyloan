//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.support;

import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PKGenerator {
    private static final Log log = LogFactory.getLog(PKGenerator.class);
    public static PKGenerator instance = null;
    private static long lastTime = System.currentTimeMillis();

    public static synchronized PKGenerator getInstanse() {
        if(instance == null) {
            instance = new PKGenerator();
        }

        return instance;
    }

    private PKGenerator() {
    }

    public synchronized String getGUID() {
        boolean done = false;

        while(!done) {
            long str = System.currentTimeMillis();
            if(str == lastTime) {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException var5) {
                    log.error("主键生成失败:", var5.fillInStackTrace());
                }
            } else {
                lastTime = str;
                done = true;
            }
        }

        String var6 = "";
        Random random = new Random();

        for(int result = 0; result < 3; ++result) {
            var6 = var6 + "" + random.nextInt(10);
        }

        String var7 = lastTime + var6;
        return var7;
    }

    public static void main(String[] args) {
        System.out.println(getInstanse().getGUID());
    }
}
