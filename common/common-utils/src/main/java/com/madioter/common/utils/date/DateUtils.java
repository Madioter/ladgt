package com.madioter.common.utils.date;

import com.madioter.common.utils.string.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author chenjie
 */
public class DateUtils {

    /**
     * 年月 <code>yyyy-MM</code>
     */
    public static final String MONTH_PATTERN = "yyyy-MM";

    /**
     * 年月日 <code>yyyyMMdd</code>
     */
    public static final String DEFAULT_PATTERN = "yyyyMMdd";

    /**
     * 年月日时分秒 <code>yyyyMMddHHmmss</code>
     */
    public static final String FULL_PATTERN = "yyyyMMddHHmmss";

    /**
     * 标准格式的年月日时分秒 <code>yyyyMMdd HH:mm:ss</code>
     */
    public static final String FULL_STANDARD_PATTERN = "yyyyMMdd HH:mm:ss";

    /**
     * 中文格式的年月日格式 <code>yyyy-MM-dd</code>
     */
    public static final String CHINESE_PATTERN = "yyyy-MM-dd";

    /**
     * 中文格式的年月日时分秒格式 <code>yyyy-MM-dd HH:mm:ss</code>
     */
    public static final String FULL_CHINESE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param interval
     *          值类型
     * @param number
     *          值，可以为负数
     * @param date
     *          原始值
     * @return 最后值
     */
    public static int dateAdd(String interval, int number, Date date) {
        long d = DateUtils.date2MysqlDate(date);
        long myTime = 0;
        if (interval.equals("d")) { // 天
            myTime = d + (60 * 60 * 24 * number);
        } else if (interval.equals("w")) { // 星期
            myTime = d + (60 * 60 * 24 * (number * 7));
        } else if (interval.equals("m")) { // 月
            myTime = d + (60 * 60 * 24 * (number * 30));
        } else if (interval.equals("y")) { // 年
            myTime = d + (60 * 60 * 24 * (number * 365));
        } else if (interval.equals("h")) { // 小时
            myTime = d + (60 * 60 * number);
        } else if (interval.equals("n")) { // 分钟
            myTime = d + (60 * number);
        } else if (interval.equals("s")) { // 秒
            myTime = d + number;
        }
        return (int) myTime;
    }

    public static String formatDate(Date date) {
        return formatDate(date, FULL_CHINESE_PATTERN);
    }

    public static String formatDate(Date date, String format) {
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date getDateByAdd(String interval, int number, Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        if (interval.equals("d")) { // 天
            now.set(Calendar.DATE, now.get(Calendar.DATE) + number);
        } else if (interval.equals("m")) { // 月
            now.set(Calendar.MONTH, now.get(Calendar.MONTH) + number);
        } else if (interval.equals("y")) { // 年
            now.set(Calendar.YEAR, now.get(Calendar.YEAR) + number);
        } else if (interval.equals("h")) { // 小时
            now.set(Calendar.HOUR, now.get(Calendar.HOUR) + number);
        } else if (interval.equals("n")) { // 分钟
            now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + number);
        } else if (interval.equals("s")) { // 秒
            now.set(Calendar.SECOND, now.get(Calendar.SECOND) + number);
        }
        return now.getTime();
    }

    /**
     * @param interval
     *          值类型
     * @param number
     *          值，可以为负数
     * @param datenum
     *          原始值
     * @return 最后值
     */
    public static int dateAdd(String interval, int number, int datenum) {
        Long l = Long.valueOf("10000000000");
        if (datenum >= l.longValue())
            datenum = datenum / 1000;
        long d = datenum;
        long myTime = 0;
        if (interval.equals("d")) { // 天
            myTime = d + (60 * 60 * 24 * number);
        } else if (interval.equals("w")) { // 星期
            myTime = d + (60 * 60 * 24 * (number * 7));
        } else if (interval.equals("m")) { // 月
            myTime = d + (60 * 60 * 24 * (number * 30));
        } else if (interval.equals("y")) { // 年
            myTime = d + (60 * 60 * 24 * (number * 365));
        } else if (interval.equals("h")) { // 小时
            myTime = d + (60 * 60 * number);
        } else if (interval.equals("n")) { // 分钟
            myTime = d + (60 * number);
        } else if (interval.equals("s")) { // 秒
            myTime = d + number;
        }
        return (int) myTime;
    }

    public static int dateDiff(String interval, long date1, long date2) {
        Long l = Long.valueOf("10000000000");
        if (date1 >= l.longValue())
            date1 = date1 / 1000;
        if (date2 >= l.longValue())
            date2 = date2 / 1000;

        long d = 0;
        if (interval.equals("d")) { // 天
            d = (date1 - date2) / (24 * 60 * 60);
        } else if (interval.equals("w")) { // 星期
            d = (date1 - date2) / (24 * 60 * 60 * 7);
        } else if (interval.equals("m")) { // 月
            d = (date1 - date2) / (24 * 60 * 60 * 30);
        } else if (interval.equals("y")) { // 年
            d = (date1 - date2) / (24 * 60 * 60 * 365);
        } else if (interval.equals("h")) { // 小时
            d = (date1 - date2) / (60 * 60);
        } else if (interval.equals("n")) { // 分钟
            d = (date1 - date2) / (60);
        } else if (interval.equals("s")) { // 秒
            d = date1 - date2;
        }
        return (int) Math.abs(d);
    }

    public static Date getDate(String name, String df, Date defaultValue) {
        if (name == null) {
            return defaultValue;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(df);

        try {
            return formatter.parse(name);
        } catch (ParseException e) {
        }

        return defaultValue;
    }

    public static Date getDateDf(String name, String df) {
        return getDate(name, df, null);
    }

    /**
     * 把字符串转化成日期型。 缺省为当前系统时间。
     *
     * @param name
     * @return
     */
    public static Date getDate(String name) {
        return getDate(name, null);
    }

    /**
     * @param name
     * @return
     */
    public static Date getDateTime(String name) {
        return getDateTime(name, null);
    }

    /**
     * 把字符串转化成日期型。
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static Date getDate(String name, Date defaultValue) {
        return getDate(name, "yyyy-MM-dd", defaultValue);
    }

    /**
     * 把字符串转化成日期型。
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static Date getDateTime(String name, Date defaultValue) {
        return getDate(name, "yyyy-MM-dd HH:mm:ss", defaultValue);
    }

    /**
     * @param str
     *          要创建的标准时间格式
     * @return UNIX时间
     */
    public static int parseStr(String str) {
        return parseStr(str, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @param str
     *          要创建的标准时间格式
     * @return 时间
     */
    public static Date parseStrDate(String str, String format) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        SimpleDateFormat sd = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        return sd.parse(str, pos);
    }

    /**
     * @param str
     *          要创建的标准时间格式
     * @return UNIX时间
     */
    public static int parseStr(String str, String format) {
        Date d1 = parseStrDate(str, format);
        if (d1 == null)
            return 0;
        return (int) (d1.getTime() / 1000);
    }

    /**
     * @return 得到日期
     */
    public static int getDatebyUnix(long l) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return (int) DateUtils.parseStr(formatter.format(l) + " 00:00:00");
    }

    public static Date mysqlDate2Date(int seconds) {
        long l = (long) seconds * 1000;
        Date date = new Date(l);
        return date;
    }

    public static long date2MysqlDate(Date date) {
        return date.getTime() / 1000;
    }

    public static int date2MysqlDateInt(Date date) {
        long l = date2MysqlDate(date);
        return Integer.parseInt(l + "");
    }

    /**
     * 返回两个日期的时间差，返回的时间差格式可以是: Calendar.YEAR, Calendar.MONTH, Calendar.DATE
     * 注意：该功能采用递归的方法，效率还有待解决，如果两个时间之差较大，而要求返回的时间格式又很小，这时效率就很差
     * 但此方法在要求精度较高的情况下比较有效，如求月份差的时候就比较准确，考虑到了各种情况．如闰月，闰年
     *
     * @param earlyDate
     * @param lateDate
     * @param returnTimeFormat
     * @return time
     */
    public static int getBetweenTime(Calendar earlyDate, Calendar lateDate, int returnTimeFormat) {
        earlyDate = (Calendar) earlyDate.clone();
        lateDate = (Calendar) lateDate.clone();
        int time = 0;
        while (earlyDate.before(lateDate)) {
            earlyDate.add(returnTimeFormat, 1);
            time++;
        }
        return time;
    }

    /**
     * 返回两个日期的时间差，返回的时间差格式可以是: Calendar.YEAR, Calendar.MONTH, Calendar.DATE
     * 注意：该功能采用递归的方法，效率还有待解决，如果两个时间之差较大，而要求返回的时间格式又很小，这时效率就很差
     * 但此方法在要求精度较高的情况下比较有效，如求月份差的时候就比较准确，考虑到了各种情况．如闰月，闰年
     *
     * @param earlyDate
     * @param lateDate
     * @param returnTimeFormat
     * @return time
     */
    public static int getBetweenTime(Date earlyDate, Date lateDate, int returnTimeFormat) {
        Calendar cnow = Calendar.getInstance();
        cnow.setTime(earlyDate);
        Calendar clast = Calendar.getInstance();
        clast.setTime(lateDate);

        return getBetweenTime(cnow, clast, returnTimeFormat);
    }

    /**
     * 得到给定的时间距离现在的天数
     *
     * @param last
     * @return
     */
    public static int getBetweenDays(Date begin, Date last) {
        return getBetweenTime(begin, last, Calendar.DATE);
    }

    /**
     * 得到给定的时间距离现在的天数
     *
     * @param last
     * @return
     */
    public static int getBetweenHours(Date begin, Date last) {
        return getBetweenTime(begin, last, Calendar.HOUR_OF_DAY);
    }

    /**
     * 得到给定的时间距离现在的分钟数
     *
     * @param last
     * @return
     */
    public static int getBetweenMins(Date begin, Date last) {
        return getBetweenTime(begin, last, Calendar.MINUTE);
    }

    /**
     * 得到给定的时间距离现在的月数
     *
     * @param last
     * @return
     */
    public static int getBetweenMonths(Date begin, Date last) {
        return getBetweenTime(begin, last, Calendar.MONTH);
    }

    /**
     * 得到给定的时间距离现在的年数
     *
     * @param last
     * @return
     */
    public static int getBetweenYears(Date begin, Date last) {
        return getBetweenTime(begin, last, Calendar.YEAR);
    }

    /**
     * 得到给定的时间距离现在的天数
     *
     * @param last
     * @return
     */
    public static int getBetweenDays(int last) {
        Calendar cnow = Calendar.getInstance();
        Calendar clast = Calendar.getInstance();
        clast.setTime(DateUtils.mysqlDate2Date(last));
        int between = getBetweenTime(clast, cnow, Calendar.DATE);
        return between;
    }

    public static String dateFormate(Date date, String formate, boolean useUSA) {
        if (date != null) {
            SimpleDateFormat sdf = null;
            if (useUSA) {
                sdf = new SimpleDateFormat(formate, Locale.US);
            } else {
                sdf = new SimpleDateFormat(formate);
            }
            return sdf.format(date);
        } else {
            return null;
        }
    }

    public static String dateFormate(Date date, String formate) {
        return dateFormate(date, formate, false);
    }

    public static String dateFormateHuman(int seconds) {
        Date date = DateUtils.mysqlDate2Date(seconds);

        return dateFormateHuman(date);
    }

    public static String dateFormateHuman(Date date) {
        Calendar cnow = Calendar.getInstance();
        Calendar clast = Calendar.getInstance();
        cnow.setTime(new Date());
        clast.setTime(date);

        String format = "";
        if (cnow.get(Calendar.YEAR) == clast.get(Calendar.YEAR) && cnow.get(Calendar.MONTH) == clast.get(Calendar.MONTH) && cnow.get(Calendar.DATE) == clast.get(Calendar.DATE)) {
            format = "H:mm";
        } else if (cnow.get(Calendar.YEAR) == clast.get(Calendar.YEAR)) {
            format = "M月d日";
        } else {
            format = "yyyy年M月d日";
        }

        return dateFormate(date, format);
    }

    public static String getTimeBetweenHuman(int seconds) {
        return getTimeBetweenHuman(mysqlDate2Date(seconds));
    }

    public static String getTimeBetweenHuman(Date date) {
        Calendar cnow = Calendar.getInstance();
        Calendar clast = Calendar.getInstance();
        cnow.setTime(new Date());
        clast.setTime(date);
        String re = "";
        int b = 0;

        if (cnow.get(Calendar.YEAR) == clast.get(Calendar.YEAR) && cnow.get(Calendar.MONTH) == clast.get(Calendar.MONTH) && cnow.get(Calendar.DATE) == clast.get(Calendar.DATE)
                && cnow.get(Calendar.HOUR_OF_DAY) == clast.get(Calendar.HOUR_OF_DAY)) {
            b = getBetweenMins(clast.getTime(), cnow.getTime());
            re = "分钟";
        } else if (cnow.get(Calendar.YEAR) == clast.get(Calendar.YEAR) && cnow.get(Calendar.MONTH) == clast.get(Calendar.MONTH) && cnow.get(Calendar.DATE) == clast.get(Calendar.DATE)) {
            b = getBetweenHours(clast.getTime(), cnow.getTime());
            re = "小时";
        } else {
            b = getBetweenDays(clast.getTime(), cnow.getTime());
            re = "天";
        }

        return b + re;
    }

    public static String dateFormate(int seconds, String formate) {
        Date date = DateUtils.mysqlDate2Date(seconds);
        return dateFormate(date, formate);
    }

    /**
     * 清除日期中的小时，分钟，秒
     *
     * @param date
     * @return
     */
    public static Date clearHMS(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTime();
    }

    /**
     * 清除日期中的指定的数据
     *
     * @param date
     *          日期
     * @return 按format类型清除时分秒数据
     */
    public static Date format(Date date, String format) {
        if (format.equals(DateUtils.CHINESE_PATTERN)) {
            return clearHMS(date);
        } else if (format.equals(DateUtils.FULL_CHINESE_PATTERN)) {
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.set(Calendar.MILLISECOND, 0);
            return now.getTime();
        } else {
            return date;
        }
    }

    public static Date getPrevDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
        return now.getTime();
    }

    public static Date getNextDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 1);
        return now.getTime();
    }

    public static Date getNextMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
        return now.getTime();
    }

    public static String getStrDateBefor(int createdDate) {
        if (createdDate <= 0) {
            return "9999";
        }
        Calendar createDate = Calendar.getInstance();
        Calendar nowDate = Calendar.getInstance();

        createDate.setTime(DateUtils.mysqlDate2Date(createdDate));
        int datesBefor = DateUtils.getBetweenTime(createDate, nowDate, Calendar.DATE);
        String sDatesBefor = StringUtils.getIndexString(datesBefor, 4);
        return sDatesBefor;
    }

    /**
     * 解析字符串为时间
     *
     * @param source
     *          字符串 格式:yyyy-MM-dd
     * @return 时间
     */
    public static Date parseDate(String source) {
        return parseDate(source, "yyyy-MM-dd");
    }

    /**
     * 解析字符串为时间
     *
     * @param source
     *          字符串
     * @param pattern
     *          字符串格式
     * @return 时间
     */
    public static Date parseDate(String source, String pattern) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);

            return sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

}