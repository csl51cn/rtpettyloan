//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.util;

import java.util.Collection;
import java.util.Map;

public class StringUtil {
    public StringUtil() {
    }

    public static boolean isNullOrEmpty(Object obj) {
        if(obj == null) {
            return true;
        } else if(obj instanceof String) {
            return ((String)obj).length() == 0;
        } else if(obj instanceof CharSequence) {
            return ((CharSequence)obj).length() == 0;
        } else if(obj instanceof Collection) {
            return ((Collection)obj).isEmpty();
        } else if(obj instanceof Map) {
            return ((Map)obj).isEmpty();
        } else if(!(obj instanceof Object[])) {
            return false;
        } else {
            Object[] object = (Object[])((Object[])obj);
            if(object.length == 0) {
                return true;
            } else {
                boolean empty = true;

                for(int i = 0; i < object.length; ++i) {
                    if(!isNullOrEmpty(object[i])) {
                        empty = false;
                        break;
                    }
                }

                return empty;
            }
        }
    }
}
