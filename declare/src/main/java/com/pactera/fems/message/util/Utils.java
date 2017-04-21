package com.pactera.fems.message.util;

import java.io.File;
import java.math.BigDecimal;


public class Utils {
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
            path = "d:" + getFileSplit() + "gjyw" + getFileSplit() + "ebills" + getFileSplit() + "config" + getFileSplit();
        } else {
            path = "/home" + getFileSplit() + "gjyw" + getFileSplit() + "ebills" + getFileSplit() + "config" + getFileSplit();
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


    public static boolean compareNumber(String minNum, String maxNum) {
        BigDecimal min = new BigDecimal(minNum);
        BigDecimal max = new BigDecimal(maxNum);
        if (max.subtract(min).doubleValue() > 0.0D) {
            return true;
        }
        return false;
    }


    public static double subtract(String max, String min) {
        BigDecimal _max = new BigDecimal(max);
        BigDecimal _min = new BigDecimal(min);
        return _max.subtract(_min).doubleValue();
    }
}


