package com.android.base_tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author FLB
 * 日期工具类
 */
public class DateUtils {
    private DateUtils() {
    }

    public static final String FORMAT_1 = "yyyy-MM-dd HH:mm:ss:SSS";

    public static String getCurrentTimeForGather() {
        Date date = new Date();
        return getTimeByDate(date, FORMAT_1);
    }

    public static Date getTimeByStr(String time, String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        return df.parse(time);
    }

    public static Date getDateMinus(Date date1, Date date2) {
        return new Date(date2.getTime() - date1.getTime());
    }

    private static String getTimeByDate(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        return df.format(date);
    }
}
