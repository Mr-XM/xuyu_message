package com.xuyu.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 时间工具类，获取当前时间，当前周一时间，下周周几对应日期，生成时间
 */
public class TimeUtils {
	/**
	 * 获取现在的时间
	 *
	 * @return String yyyy年MM月dd日 HH:mm:ss
	 */
	public static String getNowTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Calendar m = Calendar.getInstance();
		m.setTime(new Date());
		Date y = m.getTime();
		String year = format.format(y);
		return year;
	}


	/**
	 * 获取当前周一是几号
	 *
	 * @param date 当前时间
	 * @return Date 这周一日期
	 */
	public static Date getThisMondayOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}

	/**
	 * 获取下周对应的日期
	 *
	 * @param date 当前日期
	 * @param i    设定时间距离当前周一的天数
	 * @return String
	 */
	public String getNextweekday(Date date, int i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisMondayOfDate(date));
		cal.add(Calendar.DATE, i);
		String time = sdf.format(cal.getTime());
		return time;
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param dt 当前日期
	 * @return String 星期几
	 */
	public String getWeekOfDate(Date dt) {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 设置定时器时间
	 *
	 * @param month 月
	 * @param day   年
	 * @param hour  小时
	 * @param min   分钟
	 * @return Date 时间
	 */
	public static Date setTime(int month, int day, int hour, int min) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, 0);

		Date time = calendar.getTime();
		return time;
	}
}
