package cn.fintecher.pangolin.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: PeiShouWen
 * @Description: 日期工具类
 * @Date 10:28 2017/3/13
 */
public class ZWDateUtil {
    public static final String STYLE_1 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前日期
     *
     * @return
     */
    public static LocalDate getNowLocalDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static LocalDateTime getNowLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取指定的日期
     *
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static LocalDate getDefinedLocalDate(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * 获取当前日期 Date 类型
     *
     * @return
     */
    public static Date getNowDate() {
        LocalDate nowLocalDate = getNowLocalDate();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = nowLocalDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当前时间 Date类型
     *
     * @return
     */
    public static Date getNowDateTime() {
        LocalDateTime nowLocalDateTime = getNowLocalDateTime();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = nowLocalDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param format
     * @return
     */
    public static String fomratterDate(Date date, String format) {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format == null ? "yyyy-MM-dd HH:mm:ss" : format);
        return sdf.format(date);
    }

    /**
     * 获取当前日期和时间
     *
     * @return
     */
    public static String getDateTime() {
        return fomratterDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前日期和时间
     *
     * @return
     */
    public static String getDateTimes() {
        return fomratterDate(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getDate() {
        return fomratterDate(new Date(), "yyyy-MM-dd");
    }

    /**
     * 转换日期 20080101 -> Date
     *
     * @param dateStr 日期字符串
     * @param format  日期的字符串格式如yyyyMMdd
     * @return Date 日期
     * @throws ParseException 日期解析异常
     */
    public static Date getUtilDate(String dateStr, String format) throws ParseException {
        if (dateStr == null || dateStr.length() == 0)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format == null ? "yyyy-MM-dd HH:mm:ss" : format);
        return sdf.parse(dateStr);
    }

    /**
     * 转换日期 20080101 -> Date
     *
     * @param dateStr 日期字符串
     * @return Date 日期
     * @throws ParseException 日期解析异常
     */
    public static Date getFormatDate(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.length() == 0)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateStr);
    }

    /**
     * 转换日期 20080101111111 -> DateTime
     *
     * @param dateStr 日期字符串
     * @return Date 日期
     * @throws ParseException 日期解析异常
     */
    public static Date getFormatDateTime(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.length() == 0)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(dateStr);
    }

    /**
     * 获取想要的当天晚上00:00:00的时间
     *
     * @param adday 参数是距离当天的天数，正数为当天之后，负数为当天之前
     * @return
     */
    public static Date getNightTime(Integer adday) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, adday + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当前日期的指定格式
     *
     * @param format
     * @return
     * @throws Exception
     */
    public static String getFormatNowDate(String format) throws Exception {
        return fomratterDate(getNowDate(), format);
    }

    /**
     * 获取两个日期相差的天数、月数(可以时负数或正数)
     *
     * @param startDate
     * @param endDate
     * @param type      :ChronoUnit.DAYS ChronoUnit.MONTHS
     * @return
     */
    public static Integer getBetween(Date startDate, Date endDate, ChronoUnit type) {
        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
        String difValue = String.valueOf(localDateTime1.until(localDateTime2, type));
        return Integer.parseInt(difValue);
    }

    public static boolean compareDate(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int year1 = cal.get(Calendar.YEAR);
        int day1 = cal.get(Calendar.DAY_OF_YEAR);
        cal.setTime(date2);
        int year2 = cal.get(Calendar.YEAR);
        int day2 = cal.get(Calendar.DAY_OF_YEAR);
        if (year1 == year2 && day1 == day2) {
            return true;
        }
        return false;
    }

    /**
     * 比较当前日期与结束日期的大小。如果当前日期在结束日期之前返回false，等于或dayu
     * @param currentDate
     * @param compareDate
     * @return
     */
    public static boolean compareDate3(Date currentDate, Date compareDate){
        long current= 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            current = sdf.parse(sdf.format(currentDate)).getTime();
        } catch (ParseException e) {
            e.getStackTrace();
        }
        long compare=compareDate.getTime();
        if((current-compare)>=0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断当前日期是不是本月的第一天，是第一天返回true，否则返回false。
     * @return
     */
    public static boolean todayIsFristDayInMonth(){
        boolean flag=false;
        int date = Calendar.getInstance().get(Calendar.DATE);
        if(date == 1){
            flag=true;
        }
        return flag;
    }

    /**
     *
     *  功能说明：求得指定日期的后N天日期  panye  2014-11-29  @param  date 日期 ，day 天数 ，style
     * 预转换的日期格式 （默认为 yyyy-MM-dd HH:mm:ss）  @return  String    @throws 
     * 该方法可能抛出的异常，异常的类型、含义。 最后修改时间： 修改人：panye 修改内容： 修改注意点：
     */
    public static Date getAfter(Date date, int day, String style) {
        if (StringUtils.isBlank(style)) {
            style = STYLE_1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(style);
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     *    获取当前时间减一天
     * @param date
     * @param day
     * @param style
     * @return
     */
    public static Date getLastDate(Date date, int day, String style) {
        if (StringUtils.isBlank(style)) {
            style = STYLE_1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(style);
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return date;
    }

}
