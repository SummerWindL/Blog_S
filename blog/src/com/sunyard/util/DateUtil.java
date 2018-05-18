package com.sunyard.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.sunyard.consts.Consts;
import com.sunyard.exception.BlogRuntimeException;

/**
 * 时间处理类
 * 彭敏 修改
 */
public class DateUtil {

	private static Log log = LogFactory.getLog(DateUtil.class);
	private static String defaultDatePattern = null;
	private static String timePattern = "HH:mm";
	private static final FastDateFormat yyyyMMdd = FastDateFormat.getInstance(Consts.yyyyMMdd);//"yyyyMMdd"    日期格式化类型
	private static final FastDateFormat showDate = FastDateFormat.getInstance(Consts.showDate);//"yyyy年MM月dd日" 日期格式化类型


	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static synchronized String getDatePattern() {
		Locale locale = LocaleContextHolder.getLocale();
		try {
			defaultDatePattern = ResourceBundle.getBundle(
					"ApplicationResources", locale).getString("date.format");
		} catch (MissingResourceException mse) {
			defaultDatePattern = "MM/dd/yyyy";
		}

		return defaultDatePattern;
	}
	
	
	/**
	 * 返回一个将日期偏移N天的新日期
	 * @author jinhui.z@sunyard.com
	 * @Date 2017-12-21
	 */
	public static Date getDateByOffsetDay(Date date,int n){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE, n);
		return calendar.getTime();
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 获取当前日期
	 * 
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static final String getCurrentDate(String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(new Date());
	}
	
	/**
	 * 获取系统当前时间 格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static final String getCurrentDate() {
		return getCurrentDate("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}
	
	/**
	 * 字符格式化成时间格式
	 * @param aMask 时间格式
	 * @param strDate 时间字条串
	 * @return
	 */
	public static final String formatStringToDate(String formatStr, String strDate) {
		
		if(null!=strDate && !"".equals(strDate)){
			
			SimpleDateFormat df = null;
			Date date = null;
			df = new SimpleDateFormat(formatStr);
			try {
				date = df.parse(strDate);
				
			} catch (Exception e) {
				log.error("时间格式出错。");
			}
			return df.format(date);
		} else {
			return "";
		}
	}
	
	/**
	 * 打字符串转换成需要时间字符串格式
	 * @param strFormat 字符串本身的格式
	 * @param tranFormat 要转换格式
	 * @param strDate 时间字符串
	 * @return
	 */
	public static String formatStringToDate(String strFormat,String tranFormat, String strDate){
		
		try {
			SimpleDateFormat df = new SimpleDateFormat(strFormat);
			SimpleDateFormat tran = new SimpleDateFormat(tranFormat);
			Date date = df.parse(strDate);
			
			return tran.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("字符转成时间格式出错:"+"strFormat字符串格式:"+strFormat);
			log.error("tranFormat要转换的格式:"+tranFormat);
			log.error("strDate时间字符串:"+tranFormat);
			throw new BlogRuntimeException("字符转成时间格式出错。");
		}
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}
	
	/**
	 * 分析字符串文本转化为Date
	 * 
	 * @param pattern
	 * @param dateValue
	 * @return Date
	 */
	public static final Date convertMyStringtoDate(String pattern,
			String dateValue) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date date = new Date();
		try {
			date = df.parse(dateValue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将日期格式化输出
	 * 
	 * @param pattern
	 * @param date
	 * @return String
	 */
	public static final String convertDatetoMyString(String pattern, Date date) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 将日期以一定的偏移量改变
	 * 
	 * @param date
	 * @param value
	 * @return Date
	 */
	public static final Date dateChange(Date date, int value) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DAY_OF_MONTH, value);
		return cal.getTime();
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate)
			throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + getDatePattern());
			}

			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	public static final synchronized String getyyyyMMdd(Date d) {
		return yyyyMMdd.format(d);
	}

	public static final synchronized String getShowDate(Date d) {
		return showDate.format(d);
	}

	/**
	 * 时间格式的转换
	 * 
	 * @param time
	 * @return
	 */
	public static String transformTime(String time) {
		String year = time.substring(0, 4);
		String month = time.substring(4, 6);
		String day = time.substring(6, 8);
		return year + "-" + month + "-" + day;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public static List twoDays(String str1, String str2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		List list = new ArrayList();
		try {
			Date b = df.parse(str1);
			Date e = df.parse(str2);
			Calendar c = Calendar.getInstance();
			c.setTime(b);
			while (e.compareTo(c.getTime()) >= 0) {
				String tempDateStr = "";
				tempDateStr = new SimpleDateFormat("yyyyMMdd").format(
						c.getTime()).toString();
				list.add(tempDateStr);
				c.add(c.DATE, 1);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("static-access")
	public static List<String> twoDaysNew(String str1, String str2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<String> list = new ArrayList<String>();
		try {
			Date b = df.parse(str1);
			Date e = df.parse(str2);
			Calendar c = Calendar.getInstance();
			c.setTime(b);
			while (e.compareTo(c.getTime()) >= 0) {
				String tempDateStr = "";
				tempDateStr = new SimpleDateFormat("yyyy-MM-dd").format(
						c.getTime()).toString();
				list.add(tempDateStr);
				c.add(c.DATE, 1);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return list;
	}

	/**
	 * 得到当前时间
	 * @param datePattern 时间格式
	 * @return
	 */
	public static String getCurrentTime(String datePattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		long time = System.currentTimeMillis();
		return sdf.format(new Date(time));
	}

	/**
	 * 得时间段的月的个数
	 * @param begin 开始月份
	 * @param end   结束月份
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static List<String> getTwoMonths(String begin, String end) {
		List<String> list = new ArrayList<String>();
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			Date beginDate = df.parse(begin);
			Date endDate = df.parse(end);

			int beginYear = beginDate.getYear();
			int beginMonth = beginDate.getMonth();

			int endYear = endDate.getYear();
			int endMonth = endDate.getMonth();

			int difMonth = (endYear - beginYear) * 12 + (endMonth - beginMonth)+ 1;

			String year = begin.split("-")[0];
			String month = begin.split("-")[1];
			int monthCount = 0;

			list.add(begin);
			for (int i = 1; i < difMonth; i++) {
				StringBuffer time = new StringBuffer();
				monthCount = Integer.parseInt(month);
				if (monthCount == 12) {
					int yearNum = Integer.parseInt(year);
					year = String.valueOf(yearNum + 1);

					int monthNum = 0;
					month = String.valueOf(monthNum);
				}
				time.append(year + "-");
				int monthNum = Integer.parseInt(month);
				month = String.valueOf(monthNum + 1);
				if (month.length() < 2) {
					month = "0" + month;
				}
				time.append(month);

				list.add(time.toString());

			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 得到年份
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static List<String> getTwoYears(String begin, String end) {
		List<String> list = new ArrayList<String>();
		int startYear = Integer.parseInt(begin);
		int endYear = Integer.parseInt(end);
		int num = endYear - startYear;
		list.add(begin);
		for (int i = 0; i < num; i++) {
			startYear = startYear + 1;
			list.add(String.valueOf(startYear));
		}
		return list;
	}

	public static List<String> getYearAndQuarter(String begin, String end) {
		List<String> list = new ArrayList<String>();
		int startYear = Integer.parseInt(begin);
		int endYear = Integer.parseInt(end);
		int num = endYear - startYear;
		String[] seasons = { "年第一季度", "年第二季度", "年第三季度", "年第四季度" };
		for (int i = 1; i <= 4; i++) {
			list.add(begin + seasons[i - 1]);
			if (begin.equals(getCurrentYear())) {
				if (getCurrentSeason() == i) {
					break;
				}
			}
		}

		for (int i = 0; i < num; i++) {
			startYear = startYear + 1;
			for (int j = 1; j <= 4; j++) {
				list.add(String.valueOf(startYear) + seasons[j - 1]);
				if (String.valueOf(startYear).equals(getCurrentYear())) {
					if (getCurrentSeason() == j) {
						break;
					}
				}
			}
		}
		return list;
	}

	/**
	 * 取得系统当前季度
	 * 
	 * @return
	 */
	private static int getCurrentSeason() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		long startTime = System.currentTimeMillis();
		String time = sdf.format(new Date(startTime));
		int num = Integer.parseInt(time);
		if (1 <= num && num <= 3) {
			return 1;
		} else if (4 <= num && num <= 6) {
			return 2;
		} else if (7 <= num && num <= 9) {
			return 3;
		} else {
			return 14;
		}
	}

	public static String getCurrentYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		long startTime = System.currentTimeMillis();
		return sdf.format(new Date(startTime));
	}

	/**
	 * 获取去年同期日期
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            日期格式(yyyyMMdd)
	 * @return
	 */
	public static String getPreYearDate(String date, String pattern) {

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date source = null;
		try {
			source = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(source);
		calendar.add(Calendar.YEAR, -1);
		Date convert = calendar.getTime();
		return format.format(convert);
	}

	public static String getCurrenttime(){
		Calendar cal = Calendar.getInstance();
		String currentDatetime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(cal.getTime());
		return currentDatetime;
	}
	
	/**
	 * 得到当前时间：YYYYMMDD HHMMSSmi
	 * 
	 * @return
	 */
	public static String getCurrenttimemmm() {
		Calendar cal = Calendar.getInstance();
		String currentDatetime = new SimpleDateFormat("yyyyMMdd HHmmssSSS").format(cal.getTime());

		// System.out.println(currentDatetime);
		return currentDatetime;
	}
	
	/**
	 * 日期相减得到天数
	 * 
	 * @param start
	 * @param end
	 */
	public static int dataDiff(Date start, Date end) {
		long startTime = start.getTime();
		long endTime = end.getTime();
		return (int)((endTime-startTime)/(24*60*60*1000));
	}
	
	/**
	 * 得到 开始时间和结束时间之差 秒数
	 * @param startTime
	 * @param endTime
	 * @param pattern
	 * @return
	 * 2013-5-16
	 */
	public static long getSecondsByDateTimes(String startTime, String endTime, String pattern){
		Date start = convertMyStringtoDate(pattern, startTime);
		Date end = convertMyStringtoDate(pattern, endTime);
		return (end.getTime() - start.getTime()) / 1000;
	}
	
	/**
	 * 获取传入日期的前一天
	 * @param date
	 * @return
	 */
	public static Date getPreDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
	
	/**
	 * 获取上个月初
	 * 
	 * @return
	 */
	public static String getPreMonFirstDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return convertDatetoMyString("yyyy-MM-dd", c.getTime());
	}
	
	/**
	 * 月份减1
	 * 
	 * @return
	 */
	public static String getPreMon(Date date, String formate) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		return convertDatetoMyString(formate, c.getTime());
	}
	
	/**
	 * 获取上月末
	 * @return
	 */
	public static String getPreMonLastDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return convertDatetoMyString("yyyy-MM-dd", c.getTime());
	}
	
	/**
	 * 获取当前日期（字符串） 的前一天 日期（字符串）
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String getPreDateToString(String pattern, String date){
		Date currenDate = convertMyStringtoDate(pattern, date);
		Date beforeDate = getPreDate(currenDate);
		return convertDatetoMyString(pattern, beforeDate);
	}
	
	/**
	 * 获取当前日期（字符串） 的前一月 日期（字符串）
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String getPreMonthToString(String pattern, String date){
		Date currenDate = convertMyStringtoDate(pattern, date);
		Date beforeDate = getPreMonth(currenDate);
		return convertDatetoMyString(pattern, beforeDate);
	}
	
	/**
	 * 获取传入日期的前一月
	 * @param date
	 * @return
	 */
	public static Date getPreMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		return c.getTime();
	}
	
	/**
	 * 获取传入日期的后一天
	 * @param date
	 * @return
	 */
	public static Date getAfterDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
	
	/**
	 * 获取传入日期的后一天 日期（字符串）
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String getAfterDateToString(String pattern, String date){
		Date currenDate = convertMyStringtoDate(pattern, date);
		Date afterDate = getAfterDate(currenDate);
		return convertDatetoMyString(pattern, afterDate);
	}
	
	/**
	 * 获取传入日期（字符串）的前或后 几天 日期（字符串）
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String getPreAfterDateToString(String pattern, String date, int num){
		Date currenDate = convertMyStringtoDate(pattern, date);
		Date beforeDate = getPreAfterDate(currenDate, num);
		return convertDatetoMyString(pattern, beforeDate);
	}
	
	/**
	 * 获取传入日期的前或后 几天
	 * @param date
	 * @return
	 */
	public static Date getPreAfterDate(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, num);
		return c.getTime();
	}
	
	/**
	 * 处理字符转换成时间格式，默认为没分隔字符
	 * @param formatString 格式化时间字符串，如隔字符"-"
	 * @param splitType 时间字符分隔字符
	 * @return 处理完成时间格式，如果时间格式没有不对返回原来值
	 */
	public static String getStringToDate(String formatString,String splitType){
		
		StringBuffer buf = new StringBuffer();
		if("-".equals(splitType)){//以"-"分隔的时间
			String [] temp =formatString.split("-");
			if(temp.length >=3){
				buf.append(temp[0])
				   .append("年")
				   .append(temp[1])
				   .append("月")
				   .append(temp[2])
				   .append("日");				
			}
		} else {
			if(formatString.length()==8){
				buf.append(formatString.substring(0, 4))
				   .append("年")
				   .append(formatString.substring(4, 6))
				   .append("月")
				   .append(formatString.substring(6, 8))
				   .append("日");				
			}

		}
		
		if("".equals(buf.toString())){
			return formatString;
		} else {
			return buf.toString();
		}
	}
	
	/**
	 * 得到 下个月
	 * @param pattern
	 * @param date
	 * @return
	 * 2012-10-11
	 */
	public static String getNextMonth(String pattern, String date){
		Date currenMonth = convertMyStringtoDate(pattern, date);
		Calendar c = Calendar.getInstance();
		c.setTime(currenMonth);
		c.add(Calendar.MONTH, 1);
		return convertDatetoMyString(pattern, c.getTime());
	}
	
	/**
	 * 得到 上个月
	 * @param pattern
	 * @param date
	 * @return
	 * 2012-10-11
	 */
	public static String getLastMonth(String pattern, String date){
		Date currenMonth = convertMyStringtoDate(pattern, date);
		Calendar c = Calendar.getInstance();
		c.setTime(currenMonth);
		c.add(Calendar.MONTH, -1);
		return convertDatetoMyString(pattern, c.getTime());
	}
	
	/**
	 * 得到 上年月
	 * @param pattern
	 * @param date
	 * @return
	 * 2012-10-11
	 */
	public static String getLastYearMonth(String pattern, String date){
		Date currenMonth = convertMyStringtoDate(pattern, date);
		Calendar c = Calendar.getInstance();
		c.setTime(currenMonth);
		c.add(Calendar.YEAR, -1);
		return convertDatetoMyString(pattern, c.getTime());
	}
	
	/**
	 * 前几个月
	 * 
	 * @return
	 */
	public static String getPreMonByNum(String date, String pattern, int num) {
		Date currenMonth = convertMyStringtoDate(pattern, date);
		Calendar c = Calendar.getInstance();
		c.setTime(currenMonth);
		c.add(Calendar.MONTH, num);
		return convertDatetoMyString(pattern, c.getTime());
	}
	
	/**
	 * 得到 下几年 或 下几年
	 * @param pattern
	 * @param year
	 * @return
	 * 2012-10-11
	 */
	public static String getNextYearByNum(String year, int num){
		Date currenYear = convertMyStringtoDate("yyyy", year);
		Calendar c = Calendar.getInstance();
		c.setTime(currenYear);
		c.add(Calendar.YEAR, num);
		return convertDatetoMyString("yyyy", c.getTime());
	}
	
	public static void main(String[] args) {
		
		System.out.println(getSecondsByDateTimes("2013-05-16 10:10:10", "2013-06-16 11:11:22", "yyyy-MM-dd HH:mm:ss"));
	}
	
	/**
	 * 获取传入日期的前一天
	 * @param date
	 * @return
	 */
	public static String getPreMinByToday(String dateTime, String pattern, int num) {
		Date currenYear = convertMyStringtoDate(pattern, dateTime);
		Calendar c = Calendar.getInstance();
		c.setTime(currenYear);
		c.add(Calendar.MINUTE, num);
		return convertDatetoMyString(pattern, c.getTime());
	}
	
	/***
	 * 获取2个日期间的若有日期yyyyMMdd
	 * @param startdate
	 * @param enddate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getBetweenDay(String startdate,String enddate) throws ParseException{
		List<String> days = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 		
		Date   begin=sdf.parse(startdate);      		
		Date   end=sdf.parse(enddate);      		
		double   between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒      		
		double  day=between/(24*3600);	
		for(int i = 1;i<=day;i++){
			Calendar cd = Calendar.getInstance();           
			cd.setTime(sdf.parse(startdate));           
			cd.add(Calendar.DATE, i);//增加一天           
			//cd.add(Calendar.MONTH, n);//增加一个月    
			days.add(sdf.format(cd.getTime()));
			}
		return days;
	}
	
	/**
	 * 获取当月月末
	 * 
	 * @return
	 */
	public static String getCurMonLastDay(String pattern) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return convertDatetoMyString(pattern, c.getTime());
	}
	
		/**
	 * 获取当月月初
	 * 
	 * @return
	 */
	public static String getCurMonFirstDay(String pattern) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_MONTH, 1);
		return convertDatetoMyString(pattern, c.getTime());
	}
	
	/**
	 * 根据年份和月份获取月份天数
	 * @return
	 */
	public static String yearToMonthDay(Integer year,String month){
		String day = "";
		if("01".equals(month) || "03".equals(month) || "05".equals(month) || "07".equals(month) 
				||"08".equals(month) ||"10".equals(month) || "12".equals(month) ){
			day ="31";
		}else if("04".equals(month) || "06".equals(month) || "09".equals(month) || "11".equals(month) ){
			day ="30";
		}else if("02".equals(month) && year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
			day ="29";
		}else{
			day ="28";
		}


		return day;
		
	}
	
}
