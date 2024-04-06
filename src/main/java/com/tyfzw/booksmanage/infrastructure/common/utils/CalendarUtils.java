package com.tyfzw.booksmanage.infrastructure.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2022-07-20
 * Time: 22:31
 */
@Slf4j
public class CalendarUtils {

    private final static long YEAR = 1000 * 60 * 60 * 24 * 365L;
    private final static long MONTH = 1000 * 60 * 60 * 24 * 30L;
    private final static long DAY = 1000 * 60 * 60 * 24L;
    private final static long HOUR = 1000 * 60 * 60L;
    private final static long MINUTE = 1000 * 60L;
    private final static String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String STANDARD_DATE_FORMAT_DAY = "yyyy-MM-dd";


    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        return sdf.format(date);
    }

    public static String dataToStringDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT_DAY);
        return sdf.format(date);
    }

    /**
     * 字符串转日期
     *
     * @param date
     * @return
     */
    public static Date stringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            log.error("日期转换发生错误", e);
        }
        return null;
    }


    /**
     * 字符串转日期天
     * @param date
     * @return
     */
    public static Date stringToDateDay(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT_DAY);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            log.error("日期转换发生错误", e);
        }
        return null;
    }

    /**
     * 获取距今的时间间隔
     *
     * @param date
     * @return
     */
    public static String TimeSpan(Date date) {
        Date now = new Date();
        long between = now.getTime() - date.getTime();
        if (between > YEAR) {
            return ((between - YEAR) / YEAR + 1) + "年前";
        }
        if (between > MONTH) {
            return ((between - MONTH) / MONTH + 1) + "月前";
        }
        if (between > DAY) {
            return ((between - DAY) / DAY + 1) + "天前";
        }
        if (between > HOUR) {
            return ((between - HOUR) / HOUR + 1) + "小时前";
        }
        if (between > MINUTE) {
            return ((between - MINUTE) / MINUTE + 1) + "分钟前";

        }
        return "刚刚";
    }

    public static Date TimeAddDay(int num, Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        return ca.getTime();
    }






}
