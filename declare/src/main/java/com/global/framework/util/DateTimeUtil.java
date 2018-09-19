//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeUtil {
    public static String defaultDatePattern = "yyyy-MM-dd";
    public static String defaultDateTimePattern = "yyyy-MM-dd HH:mm:ss";

    public DateTimeUtil() {
    }

    public static Date getStrToDate(String value, String pattern) {
        try {
            if(StringUtils.isBlank(pattern)) {
                pattern = defaultDatePattern;
            }

            SimpleDateFormat e = new SimpleDateFormat(pattern);
            ParsePosition pos = new ParsePosition(0);
            return new Date(e.parse(value, pos).getTime());
        } catch (Exception var4) {
            throw new RuntimeException("非法的日期格式:[" + pattern + "]");
        }
    }

    public static Date getStrToDateTime(String value, String pattern) {
        try {
            if(value.length() <= 10) {
                value = value + " 00:00:00";
            }

            if(StringUtils.isBlank(pattern)) {
                pattern = defaultDateTimePattern;
            }

            SimpleDateFormat e = new SimpleDateFormat(pattern);
            ParsePosition pos = new ParsePosition(0);
            return new Date(e.parse(value, pos).getTime());
        } catch (Exception var4) {
            throw new RuntimeException("非法的日期格式:[" + pattern + "]");
        }
    }

    public static Time getStrToTime(String value, String pattern) {
        try {
            if(value.length() <= 10) {
                value = value + " 00:00:00";
            }

            if(StringUtils.isBlank(pattern)) {
                pattern = defaultDateTimePattern;
            }

            SimpleDateFormat e = new SimpleDateFormat(pattern);
            ParsePosition pos = new ParsePosition(0);
            return new Time(e.parse(value, pos).getTime());
        } catch (Exception var4) {
            throw new RuntimeException("非法的日期格式:[" + pattern + "]");
        }
    }

    public static Timestamp getStrToTimestamp(String value, String pattern) {
        try {
            if(value.length() <= 10) {
                value = value + " 01:00:00";
            }

            if(StringUtils.isBlank(pattern)) {
                pattern = defaultDateTimePattern;
            }

            SimpleDateFormat e = new SimpleDateFormat(pattern);
            ParsePosition pos = new ParsePosition(0);
            java.util.Date date = e.parse(value, pos);
            return new Timestamp(date.getTime());
        } catch (Exception var5) {
            throw new RuntimeException("非法的日期格式:[" + pattern + "]");
        }
    }

    public static String getDateToStr(java.util.Date date, String pattern) throws RuntimeException {
        if(StringUtils.isBlank(pattern)) {
            pattern = defaultDatePattern;
        }

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String getWeekByDate(Date date) {
        String[] weekDaysCode = new String[]{"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(7) - 1;
        return weekDaysCode[intWeek];
    }

    public static String getNowDateTime(String dateformat) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        String now = dateFormat.format(date);
        return now;
    }

    public static String getNowDateTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(defaultDateTimePattern);
        String now = dateFormat.format(date);
        return now;
    }

    public static String getWeek(String sdate) {
        Date date = getStrToDateTime(sdate, (String)null);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return (new SimpleDateFormat("EEEE")).format(c.getTime());
    }

    //修改日期，默认yyyy-MM-dd格式
    public static String dayAdd(String date, Integer days) {

        Date strToDate = DateTimeUtil.getStrToDate(date, defaultDatePattern);
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(strToDate);
        endTime.add(Calendar.DATE, days);
        date = DateTimeUtil.getDateToStr(endTime.getTime(), defaultDatePattern);
        return date;
    }


    /**
     * 获取某日23:59:59 时间
     *
     * @param date
     * @return
     */
    public static String toMidNight(String date) {
        Date strToDate = DateTimeUtil.getStrToDate(date, defaultDatePattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strToDate);
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH);
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, 23, 59, 59);
        return DateTimeUtil.getDateToStr(calendar.getTime(), defaultDateTimePattern);
    }


    public static void main(String[] args) {
        System.out.println(getNowDateTime("yyyy年M月dd日"));
        System.out.println(getWeek(getNowDateTime("yyyy-M-dd")));
        Date d = getStrToDate("2012-04-05 12:12:12", "yyyy-MM-dd");
        System.out.println(d);
        System.out.println(getDateToStr(d, "yyyy/MM/dd"));
    }
}
