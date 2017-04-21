package com.pactera.fems.message.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DateTimeUtil {
    public static String defaultDateTimePattern = "yyyy-MM-dd HH:mm:ss";

    public static String defaultDatePattern = "yyyy-MM-dd";

    public static String defaultYearPattern = "yyyy";


    public static String getCurrentDateTime() {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(defaultDateTimePattern);

        String now = dateFormat.format(date);
        return now;
    }


    public static java.util.Date formatToDate(String date)
            throws ParseException {
        return formatToDate(date, defaultDatePattern);
    }


    public static java.util.Date formatToDate(String date, String format)
            throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }


    public static String formatToString(java.util.Date date)
            throws ParseException {
        return formatToString(date, defaultDatePattern);
    }


    public static String formatToString(java.util.Date date, String format)
            throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


    public static java.util.Date getNow() {
        return new java.util.Date();
    }


    public static java.util.Date getNowFormat()
            throws ParseException {
        return formatToDate(formatToString(getNow()));
    }


    public static java.util.Date getNowFormat(String fromat)
            throws ParseException {
        return formatToDate(formatToString(getNow(), fromat), fromat);
    }

    public static java.sql.Date toSQLDate(String date, String formater) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formater);
            return new java.sql.Date(sdf.parse(date).getTime());
        } catch (Exception e) {
            throw new Exception("String转换java.sqlDate错误:" + e.toString());
        }
    }


    public static int calcTwoDateDays(java.util.Date fromDate, java.util.Date toDate) {
        long to = fromDate.getTime();
        long from = toDate.getTime();
        return (int) ((to - from) / 86400000L);
    }

    public static String getCurrentDate() {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(defaultDatePattern);

        String now = dateFormat.format(date);
        return now;
    }

    public static String getCurrentDate(String pattern) {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        String now = dateFormat.format(date);
        return now;
    }
}