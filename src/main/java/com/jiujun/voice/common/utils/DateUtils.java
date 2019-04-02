package com.jiujun.voice.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public class DateUtils {

	public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATETIME_PATTERN_MSEC = "yyyy-MM-dd HH:mm:ss:SSS";
	
	public static final String YYYYMMDD="yyyyMMdd";
	
	public static final String DATA_PATTERN="yyyy-MM-dd";

	public static String getWeek(Date date) {
		final String [] dayNames = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0) {
			dayOfWeek = 0;
		}
		return dayNames[dayOfWeek];
	}
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getDateString(){
		return getDateString(DATA_PATTERN);
	}
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getDateString(String format){
		return toString(new Date(),format);
	}
	/**
	 * 时间增减
	 * @param date
	 * @param changeDay
	 * @return
	 */
	public static Date changeDay(Date date, Integer changeDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, changeDay);
		return c.getTime();
	}
	/**
	 * 获取某日开始时间
	 * @return
	 */
	public static Date getDayFirstTime(Date date) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}
	/**
	 * 获取某日结束时间
	 * @param date
	 * @return
	 */
	public static Date getDayLastTime(Date date) {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}
	
	/**
	 * 时间转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String toString(Date date,String format){
		if(date==null){
			return null;
		}
		SimpleDateFormat sfDate = new SimpleDateFormat(format);
		return sfDate.format(date);
	}

	public static String toString(Date date){
		return toString(date, DATETIME_PATTERN);
	}

	/**
	 * 转换为时间
	 */
	public static Date toDate(Object value){
		if(value==null){
			return null;
		}
		try {
			Class<?> clazz=value.getClass();
			if(Date.class.isAssignableFrom(value.getClass())){
				return (Date) value;
			}
			if(clazz.isPrimitive()){
				if(value.toString().length()==13){
					return new Date(Long.valueOf(value.toString()));
				}
			}
			if (StringUtil.isMatcher(value.toString(),
					"\\d{13}")) {
				value =new Date(Long.valueOf(value
						.toString()));
				return (Date) value;
			}
			if (StringUtil.isMatcher(value.toString(),
					"\\d{8}")) {
				value = new SimpleDateFormat(YYYYMMDD).parse(value
						.toString());
				return (Date) value;
			}
			if (StringUtil.isMatcher(value.toString(),
					"\\d{14}")) {
				value = new SimpleDateFormat("yyyyMMddHHmmss").parse(value
						.toString());
				return (Date) value;
			}
			if (StringUtil.isMatcher(value.toString(),
					"\\d{17}")) {
				value = new SimpleDateFormat("yyyyMMddHHmmssSSS").parse(value
						.toString());
				return (Date) value;
			}
			if (StringUtil.isMatcher(value.toString(),
					"[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")) {
				value = new SimpleDateFormat(DATA_PATTERN).parse(value
						.toString());
				return (Date) value;
			}
			if (StringUtil
					.isMatcher(value.toString(),
							"^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*")) {
				value = new SimpleDateFormat(DATETIME_PATTERN)
						.parse(value.toString());
				return (Date) value;
			}
			if (StringUtil
					.isMatcher(value.toString(),
							"^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,3}\\D*")) {
				value = new SimpleDateFormat(DATETIME_PATTERN)
						.parse(value.toString());
				return (Date) value;
			}
			return (Date) value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Date getWeekFirstTime() {
		return getWeekFirstTime(new Date());
	}

	public static Date getWeekFirstTime(Date date) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(date);
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return (Date) currentDate.getTime();
	}

	

	/**
	 * 日编码
	 * @param week (0:本周 -1:前一周 -2:前两周)
	 * @return
	 */
	public static String getDayCode(){
		return getDayCode(new Date());
	}
	/**
	 * 日编码
	 * @param week (0:本周 -1:前一周 -2:前两周)
	 * @return
	 */
	public static String getDayCode(Date time){
		return toString(time,YYYYMMDD);
	}
	/**
	 * 周编码
	 * @param week (0:本周 -1:前一周 -2:前两周)
	 * @return
	 */
	public static String getWeekCode(int week){
		String weekCode = "";
		if(week == 0){
			weekCode = toString(DateUtils.getWeekFirstTime(),YYYYMMDD);
		}else{
			Date lastWeek = addDate(new Date(), week*7);
			weekCode = toString(getWeekFirstTime(lastWeek),YYYYMMDD);
		}
		return weekCode;
	}
	/**
	 * 月编码
	 * @param month (0:本月 -1:前一月)
	 * @return
	 */
	public static String getMonthCode(int month){
		 Calendar   c=Calendar.getInstance();//获取当前日期 
		 c.add(Calendar.MONTH, month);
		 c.set(Calendar.DAY_OF_MONTH,1);
		 return toString(c.getTime(),YYYYMMDD);
	}
	
	
	public static Date addDate(Date date, Integer dateNum) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.DATE, dateNum);
		return cl.getTime();
	}
	
	/**
	 * 获取截取时间戳，月日时分秒
	 * @author Shao.x
	 * @date 2018年12月10日
	 * @return
	 */
	public static long getSubTime() {
		Date now = new Date();
		return Long.valueOf(String.valueOf(now.getTime()).substring(2, 10));
	}
	
	public static long getSubTime(Date date) {
		return Long.valueOf(String.valueOf(date.getTime()).substring(2, 10));
	}
	
	/**
	 * 增减时间
	 * @author Shao.x
	 * @date 2018年12月23日
	 * @param date 日期
	 * @param type 类型：1-秒，2-分，3-小时，4-天，5-月
	 * @param num 正数为加，负数为减
	 * @return
	 */
	public static Date addDate(Date date, int type, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int field = 0;
		switch(type) {
		case 1:
			field = Calendar.SECOND;
			break;
		case 2:
			field = Calendar.MINUTE;
			break;
		case 3:
			field = Calendar.HOUR;
			break;
		case 4:
			field = Calendar.DAY_OF_YEAR;
			break;
		case 5:
			field = Calendar.MONTH;
			break;
		default :
			field = Calendar.DAY_OF_YEAR;
		}
		cal.add(field, num);
		return cal.getTime();
	}
	
	/**
	 * 获取日期的日
	 * @author Shao.x
	 * @date 2019年1月22日
	 * @param d
	 * @return
	 */
	public static int getDay(Date d) {
		int day = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	
	/**
	 * 获取时间的月
	 * @param d
	 * @return
	 */
	public static int getMounth(Date d) {
		int m = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		m = calendar.get(Calendar.MONTH) + 1;
		return m;
	}
	
	public static void main(String[] args) {
		System.out.println(getMounth(new Date()));
	}
	
}
