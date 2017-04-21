package com.global.fems.message.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.global.framework.util.SysUtils;

/**
 * 类描述：日期时间工具类
 * 
 * @author chen.feng
 * @date 2015-6-18
 * @version v1.0
 */
public class DateTimeUtil {

	private static String defaultDateTimePattern = "yyyy-MM-dd HH:mm:ss";
	private static String defaultDatePattern = "yyyy-MM-dd";
	public final static String NothingStr = "";
	public final static String NullStr = "null#NULL#Null";
	/**
	 * 获取当前时间，默认时间格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				defaultDateTimePattern);
		String now = dateFormat.format(date);
		return now;
	}
	public static String getCurrentDate() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				defaultDatePattern);
		String now = dateFormat.format(date);
		return now;
	}
	
	public static String getCurrentDate(String pattern) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				pattern);
		String now = dateFormat.format(date);
		return now;
	}
	
	public static boolean isNull(String s){
		if (s == null || (NullStr.indexOf(s) > -1 || s.trim().equals(NothingStr))
				|| "undefined".equals(String.valueOf(s))) {
			return true;
		}
		return false;
	}
	    
	public static boolean isNotNull(String str){
	    return ! isNull(str);
	}  
	
	/**
	 * 计算两个时间之间的天数
	 * @param fromDate	减数
	 * @param toDate	被减数
	 * @return	两个日期之间的天数差
	 * @throws EbillsException
	 */
	public static int calcTwoDateDays(Date fromDate, Date toDate){
		long to = fromDate.getTime();
		long from = toDate.getTime();
		return (int) ((to - from) / (1000 * 60 * 60 * 24));
	}

	
	/**String转换成java.sql.Date*/
	public static Date toSQLDate(String date, String formater) throws Exception{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formater);
		    return new Date( (sdf.parse(date)).getTime());
		}catch(Exception e){
			throw new Exception("String转换java.sqlDate错误:"+e.toString());
		}
	}
	/**String转换成java.util.Date*/
	public static java.util.Date toUtilDate(String date) throws Exception{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			java.util.Date d = sdf.parse(date);
		    return d;
		}catch(Exception e){
			throw new Exception("String转换java.sqlDate错误:"+e.toString());
		}
	}
	
	/**
	 * 二个数字的减法运算
	 * @param maxNumber
	 * @param minNumber
	 * @return
	 */
	public static double subtract(String maxNumber, String minNumber) {
		BigDecimal max = new BigDecimal(SysUtils.parseAmt(maxNumber));
		BigDecimal min = new BigDecimal(SysUtils.parseAmt(minNumber));
		return max.subtract(min).doubleValue();
	}
}
