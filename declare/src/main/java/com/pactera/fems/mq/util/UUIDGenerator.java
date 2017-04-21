package com.pactera.fems.mq.util;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class UUIDGenerator {
    public static String getId() {
        return getId("", 1);
    }


    public static String getId(int type) {
        return getId("", type);
    }

    public static String getId(String bizeno) {
        return getId(bizeno, 1);
    }

    public static String getUUID() {
        return getId("");
    }

    public static String getId(String bizeno, int type) {
        StringBuilder sb = new StringBuilder();
        try {
            Random r = new Random();

            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

            long time = System.currentTimeMillis() * 100000000L + r.nextInt(999999999);


            sb.append(bizeno).append(seriaCharNo5(r)).append(time);

            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(sb.toString().getBytes());

            return reckonId(mdTemp, hexDigits, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String reckonId(MessageDigest mdTemp, char[] hexDigits, int type) {
        byte[] md = mdTemp.digest();
        int j = md.length;
        char[] str = new char[j * 2];

        int k = 0;
        for (int i = 0; i < j; i++) {
            byte b = md[i];
            if (type != 1) {
                str[(k++)] = hexDigits[(b >> 4 & 0xF)];
            }
            str[(k++)] = hexDigits[(b & 0xF)];
        }

        String id = new String(str);
        return id;
    }


    public static String seriaCharNo5(Random r) {
        String chars = "abcdefjhijklmnopqrstuvwxyz";

        char[] c = new char[5];
        for (int i = 0; i < 5; i++) {
            c[i] = chars.charAt(r.nextInt(25));
        }

        String seriano = new String(c);
        return seriano;
    }

    public static void main(String[] args) {
        Map ids = new HashMap();
        for (; ; ) {
            String id = getId("12345");
            System.out.println(id);
        }
    }
}


