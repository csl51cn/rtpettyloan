//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.annotation;

import com.global.framework.dbutils.support.DAOException;
import com.global.framework.util.DateTimeUtil;
import com.global.framework.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class BeanDataHandler {
    public BeanDataHandler() {
    }

    public static Object trans(Object value, Class<?> columnTypeClazz, String dateFormatPattern) throws DAOException {
        if(!StringUtil.isNullOrEmpty(value) && value instanceof String) {
            String v = (String)value;
            if(StringUtils.isNotBlank(v)) {
                if(columnTypeClazz.equals(Date.class)) {
                    return DateTimeUtil.getStrToDate(v, dateFormatPattern);
                }

                if(columnTypeClazz.equals(java.util.Date.class)) {
                    return DateTimeUtil.getStrToDateTime(v, dateFormatPattern);
                }

                if(columnTypeClazz.equals(Time.class)) {
                    return DateTimeUtil.getStrToTime(v, dateFormatPattern);
                }

                if(columnTypeClazz.equals(Timestamp.class)) {
                    return DateTimeUtil.getStrToTimestamp(v, dateFormatPattern);
                }

                if(columnTypeClazz.equals(Long.class)) {
                    return Long.valueOf(v);
                }

                if(columnTypeClazz.equals(Short.class)) {
                    return Short.valueOf(v);
                }

                if(columnTypeClazz.equals(Integer.class)) {
                    return Integer.valueOf(v);
                }

                if(columnTypeClazz.equals(Float.class)) {
                    return Float.valueOf(v);
                }

                if(columnTypeClazz.equals(Double.class)) {
                    return Double.valueOf(v.replaceAll(",", ""));
                }
            }
        }

        return value;
    }

    public static void main(String[] args) {
    }
}
