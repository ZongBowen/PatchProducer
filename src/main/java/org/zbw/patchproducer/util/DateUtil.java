package org.zbw.patchproducer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final String PATTERN = "yyyy-MM-dd HH:mm";
    /**
     * 将日期转为固定格式字符串
     *
     * @param dt      日期
     * @param pattern 返回的日期格式，比如 yyyy-MM-dd HH:mm:ss.SSS
     * @return 字符串
     */
    public static String toString(Date dt, String pattern) {
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        // 将传入的日期转化为固定格式字符串
        return df.format(dt);
    }

    /**
     * 将字符串转为日期
     *
     * @param dtStr   字符串
     * @param pattern 日期格式
     * @return 日期
     * @throws ParseException 转换错误
     */
    public static Date toDate(String dtStr, String pattern) throws ParseException {
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(dtStr);
    }

    /**
     * 将字符串转为日期，若失败返回null
     *
     * @param dtStr   字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date tryParse(String dtStr, String pattern) {
        try {
            return toDate(dtStr, pattern);
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * 获取当前系统时间
     *
     * @param pattern 返回的日期格式，比如 yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String getCurrentDateTime(String pattern) {
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        // new Date()为获取当前系统时间
        return df.format(new Date());
    }
}
