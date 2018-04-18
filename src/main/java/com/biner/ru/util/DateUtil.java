package com.biner.ru.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String PATTERN_YYYYMMDD = "yyyyMMdd";
	public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 
	 * @Title: increaseDate
	 * @Description: 日期增加
	 * @param @param
	 *            value
	 * @param @param
	 *            count
	 * @param @return
	 * @return Date
	 * @author xiaoxiang
	 */
	public static Date increaseDate(Date value, int count) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(value);
		cl.add(Calendar.DAY_OF_YEAR, count);
		return cl.getTime();
	}

	/**
	 * 
	 * @Title: getBetweenDays
	 * @Description: 获取两个日期相差的天数
	 * @param @param
	 *            start
	 * @param @param
	 *            end
	 * @param @return
	 * @return int
	 * @author xiaoxiang
	 */
	public static int getBetweenDays(Date start, Date end) {
		double time = (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
		return (int) (Math.round(time));
	}

	/**
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author xuwei
	 * @throws ParseException
	 * @date 2018年4月8日下午2:09:35
	 */
	public static int minutesBetween(String beginDate, String endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar instance = Calendar.getInstance();
		instance.setTime(sdf.parse(beginDate));
		long beginTime = instance.getTimeInMillis();
		instance.setTime(sdf.parse(endDate));
		long endTime = instance.getTimeInMillis();
		return Integer.parseInt(String.valueOf((endTime - beginTime) / (1000 * 60)));
	}

	/**
	 * 将日期转换为指定类型的日期字符串
	 * 
	 * @param regex
	 * @return
	 */
	public static String format(String regex) {
		SimpleDateFormat sdf = new SimpleDateFormat(regex);
		return sdf.format(new Date());
	}

	public static String format2(Date date, String regex) {
		SimpleDateFormat sdf = new SimpleDateFormat(regex);
		return sdf.format(date);
	}

	/**
	 * @author KangBinbin
	 * @date 2018年1月8日
	 * @description 获取当前时间到当天24点最后的毫秒
	 */
	public static int getLastSecondsTodayFromNow() {
		Calendar c = Calendar.getInstance();
		long from = c.getTimeInMillis();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		long end = c.getTimeInMillis();
		return (int) ((end - from) / 1000);
	}

	/**
	 * @author KangBinbin
	 * @throws ParseException
	 * @date 2018年1月11日
	 * @description 判断传入的日期是否低于当前日期
	 */
	public static boolean isExpired(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		long now = System.currentTimeMillis();
		try {
			long dateLong = sdf.parse(date).getTime();
			if ((now - dateLong) < 0) {
				return false;
			}
		} catch (ParseException e) {
			return true;
		}
		return true;
	}

	public static int isbiggerThenNow(String date, String reg) {
		boolean expired = DateUtil.isExpired(date, reg);
		if (!expired) {
			return 1;
		} else {
			// 小于等于当前时间
			if (new SimpleDateFormat(reg).format(new Date()).equals(date)) {
				return 0;
			}
			return -1;
		}
	}

	public static Date parseDate(String strDate, String regix) {
		SimpleDateFormat sdf = new SimpleDateFormat(regix);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 计算指定日期距离当前系统日期的间隔天数
	 * 
	 * @param strDate
	 * @param regix
	 * @return
	 */
	public static int getFollowDays(String strDate, String regix) {
		Date d1 = parseDate(strDate, regix);
		return getTimeDistance(new Date(), d1);
		// return getBetweenDays(new Date(),d1);
	}

	public static String formateDate(Date date, String regix) {
		SimpleDateFormat sdf = new SimpleDateFormat(regix);
		return sdf.format(date);
	}

	/**
	 * 获取当前日期的实际天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDaysBetween(Calendar c1, Calendar c2) {
		return differentDays(c1.getTime(), c2.getTime());
	}

	/**
	 * date2比date1多的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 不同年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}
			return timeDistance + (day1 - day2);
		} else // 同一年
		{
			System.out.println("判断day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}

	/**
	 * 计算间隔秒
	 * 
	 * @param strDate
	 * @param regix
	 * @return
	 */
	public static long betweenSecondas(String strDate, String regix) {
		Date date1 = parseDate(strDate, regix);
		Date date2 = new Date();
		long time = 0;
		if (date2.after(date1)) {
			time = (date2.getTime() - date1.getTime()) / 1000;
		} else {
			time = (date1.getTime() - date2.getTime()) / 1000;
		}
		return time;
	}

	public static int getTimeDistance(Date beginDate, Date endDate) {
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		long beginTime = beginCalendar.getTime().getTime();
		long endTime = endCalendar.getTime().getTime();
		int betweenDays = (int) ((endTime - beginTime) / (1000 * 60 * 60 * 24));// 先算出两时间的毫秒数之差大于一天的天数

		endCalendar.add(Calendar.DAY_OF_MONTH, -betweenDays);// 使endCalendar减去这些天数，将问题转换为两时间的毫秒数之差不足一天的情况
		endCalendar.add(Calendar.DAY_OF_MONTH, -1);// 再使endCalendar减去1天
		if (beginCalendar.get(Calendar.DAY_OF_MONTH) == endCalendar.get(Calendar.DAY_OF_MONTH))// 比较两日期的DAY_OF_MONTH是否相等
			return betweenDays + 1; // 相等说明确实跨天了
		else
			return betweenDays + 0; // 不相等说明确实未跨天
	}

	public static void main(String[] args) throws ParseException {
		String d1 = "2018-04-15";
		int days = getFollowDays(d1, "yyyy-MM-dd");
		System.out.println("days::" + days);
	}

}
