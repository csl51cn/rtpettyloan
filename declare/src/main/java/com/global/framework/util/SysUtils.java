package com.global.framework.util;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * @author cqchenf@qq.com
 * @date 2010-9-5
 * @version v1.0
 */
public class SysUtils {

	public static String defaultDateTimePattern = "yyyy-MM-dd HH:mm:ss";

	public static Date getStrToDate(String value, String pattern) {
		if (value.length() <= 10) {
			value = (new StringBuilder()).append(value).append(" 00:00:00")
					.toString();
		}
		if (StringUtils.isBlank(pattern)) {
			pattern = defaultDateTimePattern;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		ParsePosition pos = new ParsePosition(0);
		return new Date(format.parse(value, pos).getTime());
	}

	public static Time getStrToTime(String value, String pattern) {
		if (value.length() <= 10) {
			value = (new StringBuilder()).append(value).append(" 00:00:00")
					.toString();
		}
		if (StringUtils.isBlank(pattern)) {
			pattern = defaultDateTimePattern;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		ParsePosition pos = new ParsePosition(0);
		return new Time(format.parse(value, pos).getTime());
	}

	public static Timestamp getStrToTimestamp(String value, String pattern) {
		if (value.length() <= 10) {
			value = (new StringBuilder()).append(value).append(" 01:00:00")
					.toString();
		}
		if (StringUtils.isBlank(pattern)) {
			pattern = defaultDateTimePattern;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		ParsePosition pos = new ParsePosition(0);
		java.util.Date date = format.parse(value, pos);
		return new Timestamp(date.getTime());
	}

	public static String getTimeDiff(String maxTime, String minTime) {
		Timestamp ts1 = getStrToTimestamp(maxTime, "yyyy-MM-dd HH:mm:ss");
		Timestamp ts2 = getStrToTimestamp(minTime, "yyyy-MM-dd HH:mm:ss");
		long timeDiff = ts1.getTime() - ts2.getTime();
		long day = timeDiff / (24 * 60 * 60 * 1000);
		long hour = (timeDiff / (60 * 60 * 1000) - day * 24);
		long min = ((timeDiff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeDiff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		String timeDiffStr = "" + day + "天" + hour + "小时" + min + "分" + s + "秒";
		// System.out.println("" + day + "天" + hour + "小时" + min + "分" + s +
		// "秒");
		// System.out.println("timeDiff:"+timeDiff);
		return timeDiffStr;
	}

	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getWeekByDate(Date date) {
		String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysCode[intWeek];
	}

	/**
	 * 获取当前时间 ,自定义日期格式
	 * 
	 * @param dateformat
	 * @return
	 */
	public static String getNowDateTime(String dateformat) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String now = dateFormat.format(date);
		return now;
	}

	/**
	 * 获取当前时间，默认时间格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getNowDateTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				defaultDateTimePattern);
		String now = dateFormat.format(date);
		return now;
	}
	
	/**
	 * 获取当前日期，默认时间格式为yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getNowDate() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String now = dateFormat.format(date);
		return now;
	}
	
	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		Date date = new Date(System.currentTimeMillis());
		return date;
	}
	
	/**
	 * 获取指定日期的上一月的第一天
	 * @param currentDate
	 * @return
	 */
	public static String getFirstDayOfPreMonth(Date currentDate, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, -1);
		return getDateToStr(cal.getTime(), pattern);
	}
	
	/**
	 * 获取指定日期的上一月最后一天
	 * @param currentDate
	 * @param pattern
	 * @return
	 */
	public static String getLastDayOfPreMonth(Date currentDate, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.MONTH,-1);//减一个月
		cal.set(Calendar.DATE, 1);//把日期设置为当月第一天
		cal.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
		return getDateToStr(cal.getTime(), pattern);
	}
	
	/**
	 * 获取当前日期的前几天
	 * @param currentDate
	 * @param n
	 * @param pattern
	 * @return
	 */
	public static String getPreDayOfCurrentDate(Date currentDate, int n, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DAY_OF_MONTH, -n);
		return getDateToStr(cal.getTime(), pattern);
	}
	
	/**
	 * 获取当前日期的后几天
	 * @param currentDate
	 * @param n
	 * @param pattern
	 * @return
	 */
	public static String getNextDayOfCurrentDate(Date currentDate, int n, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DAY_OF_MONTH, n);
		return getDateToStr(cal.getTime(), pattern);
	}
	
	public static String getDateToStr(java.util.Date date, String pattern) {
		if (StringUtils.isBlank(pattern)){
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String sDate = dateFormat.format(date);
		return sDate;
	}
	
	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = getStrToDate(sdate, null);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 计算两个日期的相差天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDaysBetween(Calendar d1, Calendar d2) {
		if (d1.after(d2)) {
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
				d1.add(Calendar.YEAR, 1);

			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}
	
	public static boolean compareDateTime(String minDateTime, String maxDateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date min = sdf.parse(minDateTime);
			java.util.Date max = sdf.parse(maxDateTime);
			if (min.getTime() < max.getTime()) {
				return true;
			}
		} catch (ParseException e) {
			throw new RuntimeException("时间格式有误，无法转换成[yyyy-MM-dd HH:mm:ss]格式的时间");
		}
		return false;
	}
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	public static String formatAmt(String number) {
		if (StringUtils.isNotBlank(number)) {
			if ("null".equals(number)) {
				number = "0";
			}
			double _number = Double.valueOf(number);
			DecimalFormat df = new DecimalFormat("#,##0.00");
			return df.format(_number);
		}
		return number;
	}
	
	public static String parseAmt(String number) {
		if (StringUtils.isNotBlank(number)) {
			return number.replaceAll(",", "");
		}
		return number;
	}
	
	public static double parseDouble(String number) {
		if (StringUtils.isNotBlank(number)) {
			return Double.valueOf(number.replaceAll(",", ""));
		}
		return 0.00;
	}
	
	public static String replaceAll(String str, String regex, String replacement) {
		if (StringUtils.isNotBlank(str)) {
			return str.replaceAll(regex, replacement);
		}
		return str;
	}
	
	/**
	 * 减法运算
	 * @param max 最大数
	 * @param min 最小数
	 * @return
	 */
	public static double subtract(String max, String min) {
		BigDecimal _max = new BigDecimal(max);
		BigDecimal _min = new BigDecimal(min);
		return _max.subtract(_min).doubleValue();
	}
	
	/**
	 * 生成带日期格式的随机流水号
	 * @return
	 */
	public static String getSeqNo() {
		String seqNo = getNowDateTime("yyyyMMddHHmmss");
		Random r = new Random();
		String s = "";
		for (int i = 0; i < 5; i++) {
			s += String.valueOf(r.nextInt(10));
		}
		return seqNo + s;
	}
	
	/**
	 * 获取系统文件路径分隔符
	 * 
	 * @return String
	 * */
	public static String getFileSplit() {
		return System.getProperty("file.separator");
	}

	public static String getSystemConfigHome() {
		String os = System.getProperty("os.name");
		if (os == null || "".equals(os)) {
			os = "UNIX";
		}
		os = os.toUpperCase();
		if (os.indexOf("WINDOWS") >= 0) {
			os = "WIN";
		} else {
			os = "UNIX";
		}
		String path = System.getProperty("user.home") + getFileSplit()
				+ "ebills" + getFileSplit();
		if ("WIN".equals(os)) {
			path = "d:" + getFileSplit() + "gjyw" + getFileSplit() + "fems" + getFileSplit();
		} else {
			path = "/home" + getFileSplit() + "gjyw" + getFileSplit() + "fems" + getFileSplit();
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

	public static void main(String[] args) {
		// System.out.println(getStrToTime("2012-04-05",
		// "yyyy-MM-dd HH:mm:ss"));
		System.out.println(getNowDateTime("yyyy年M月dd日"));
		System.out.println(getWeek(getNowDateTime("yyyy-M-dd")));
		getTimeDiff("2014-06-12 09:01:30", "2014-06-11 09:01:32");
		Calendar c = Calendar.getInstance();
		c.setTime(getStrToDate("2014-07-23", "yyyy-MM-dd"));
		System.out.println(c.get(Calendar.MONTH) + 1);
		
		System.out.println(getPreDayOfCurrentDate(getStrToDate("2014-01-01", "yyyy-MM-dd"), 1, "yyyy-MM-dd"));
		System.out.println(getLastDayOfPreMonth(getStrToDate("201509", "yyyyMM"), "yyyyMM"));
	}
}
