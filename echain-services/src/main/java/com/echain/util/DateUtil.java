package com.echain.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

public class DateUtil {

    public static final String FORMAT_PATTERN_DAY_1 = "yyyyMMdd";

    public static final String FORMAT_PATTERN_DAY = "yyyy-MM-dd";

    public static final String FORMAT_PATTERN_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_PATTERN_MIN = "yyyy-MM-dd HH:mm";

    public static String secondTotime(Integer second) {
        if (second == null || second == 0) {
            return "00:00:00";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date(second * 1000 - 3600 * 8 * 1000));
    }

    /**
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 格式化string为Date
     * 
     * @param datestr
     * @return date
     */
    public static Date parseDate(String datestr) {
        if (null == datestr || "".equals(datestr)) {
            return null;
        }
        try {
            String fmtstr = null;
            if (datestr.indexOf(':') > 0) {
                fmtstr = "yyyy-MM-dd HH:mm:ss";
            } else {
                fmtstr = "yyyy-MM-dd";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr, Locale.UK);
            return sdf.parse(datestr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(String date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);// 小写的mm表示的是分钟
        Date dates = null;
        try {
            dates = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdf.format(dates);
    }

    public static String formatDateSeconds(Date date) {
        if (null == date) {
            date = new Date();
        }
        return formatDate(date, FORMAT_PATTERN_SECOND);
    }

    /**
     * 简介：yyyy-MM-dd
     * 
     * @author jemond
     * @date 2017年8月10日 下午6:05:22
     * @return
     */
    public static String getTradeDateFormat() {
        return DateUtil.formatDate(new Date(), "yyyy-MM-dd");
    }

    /**
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static Date parseDate(String date, String format) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param date
     * @param format
     * @param pos
     * @return yyyy-MM-dd//yyyy-MM-dd HH:mm
     */
    public static Date parseDate(String date, String format, ParsePosition pos) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date, pos);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到今天的最后的一秒
     * 
     * @return
     */
    public static Date getEndTime(Date date) {
        if (date == null || "".equals(date)) {
            return null;
        }
        String endTime = DateUtil.formatDate(date, "yyyy-MM-dd");
        // endTime+=" 23:59:59";
        return DateUtil.parseDate(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到今天的第一秒
     * 
     * @return
     */
    public static Date getStartTime(Date date) {
        if (date == null || "".equals(date)) {
            return null;
        }
        String endTime = DateUtil.formatDate(date, "yyyy-MM-dd");
        return DateUtil.parseDate(endTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 判断一个时间 是否在今天
     * 
     * @param datetime
     * @return
     */
    public static boolean isToday(Date datetime) {
        Long timeMil = datetime.getTime();
        if (getStartTime(new Date()).getTime() <= timeMil && timeMil <= getEndTime(new Date()).getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 得到今天开始计算 前后某些天的 日期
     * 
     * @param offset
     * @param type
     * @return
     */
    public static String getOffsetDayString(int offset, int type) {
        Calendar workDateCl = Calendar.getInstance();
        workDateCl.add(type, offset);
        return DateUtil.formatDate(workDateCl.getTime(), "yyyy-MM-dd");
    }

    // 获得当前日期与本周一相差的天数
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     * 
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     * 
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static Date string2Date(String str) {
        try {
            if (StringUtils.isBlank(str)) {
                return null;
            }
            return DateUtils.parseDate(str, new String[] {"yyyy-MM-dd", "yyyyMMdd", "yyyy年MM月dd日", "MM/dd/yyyy",
                "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm"});
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * 取得向前或向后移动N个月时间
     * 
     * @param date
     * @param amount
     * @return
     */
    public static Date getAddMonthOfDate(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        return calendar.getTime();
    }

    /**
     * 取得向前或向后移动N天
     * 
     * @param date
     * @param amount
     * @return
     */
    public static Date getAddDayOfDate(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }

    /**
     * 取得向前或向后移动N小时
     * 
     * @param date
     * @param amount
     * @return
     */
    public static Date getAddDayOfHour(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, amount);
        return calendar.getTime();
    }

    /**
     * 取得向前或向后移动N分钟
     * 
     * @param date
     * @param amount
     * @return
     */
    public static Date getAddDayOfMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 取得向前或向后移动N年
     * 
     * @param date
     * @param amount
     * @return
     */
    public static Date getAddYearOfDate(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, amount);
        return calendar.getTime();
    }

    public static String getTwoDay(String sj1, String sj2) {
        Long day = 0l;
        try {
            java.util.Date date = string2Date(sj1);
            java.util.Date mydate = string2Date(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    // 获得本周星期一的日期
    public static String getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        String monday = formatDate(currentDate.getTime(), "yyyy-MM-dd");
        return monday;
    }

    // 获得本月的第一天
    public static String getCurrentMonth() {
        String month = formatDate(new Date(), "yyyy-MM");
        return month + "-01";
    }

    public static String getShortTime(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String shortstring = null;

        long now = Calendar.getInstance().getTimeInMillis();

        long deltime = (now - date.getTime()) / 1000;

        if (deltime > 24 * 60 * 60) {
            long time = deltime / (24 * 60 * 60);
            if (time < 365 || DateUtil.formatDate(date, "yyyy").equals(DateUtil.formatDate(new Date(now), "yyyy"))) {
                if (time < 30) {
                    shortstring = time + "天";
                } else if (time % 30 == 0) {
                    shortstring = time / 30 + "月";
                } else {
                    shortstring = time / 30 + "月" + time % 30 + "天";
                }
            } else if (time % 365 == 0) {
                shortstring = time / 365 + "年";
            } else if (time % 30 == 0) {
                shortstring = time / 365 + "年" + (time % 365) / 30 + "月";
            } else {
                shortstring = time / 365 + "年" + (time % 365) / 30 + "月" + ((time % 365) % 30) % 30 + "天";
            }
            // if(deltime < 365*24*60*60 ||
            // DateUtil.formatDate(date,"yyyy").equals(DateUtil.formatDate(new
            // Date(now),"yyyy"))){
            // shortstring = deltime/(24*60*60)+"天";
            // }else{
            // shortstring = deltime/(24*60*60)+"天";
            // }
        } else if (deltime > 60 * 60) {
            shortstring = (int)(deltime / (60 * 60)) + "小时";
        } else if (deltime > 60) {
            shortstring = ((int)(deltime / (60))) + "分钟";
        } else if (deltime > 1) {
            shortstring = deltime + "秒钟";
        } else {
            shortstring = "1秒";
        }
        return shortstring;
    }

    public static String getShortTimes(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String shortstring = null;

        long now = Calendar.getInstance().getTimeInMillis();

        long deltime = (now - date.getTime()) / 1000;
        if (deltime > 24 * 60 * 60) {
            if (deltime < 360 * 24 * 60 * 60
                || DateUtil.formatDate(date, "yyyy").equals(DateUtil.formatDate(new Date(now), "yyyy"))) {
                shortstring = DateUtil.formatDate(date, "MM月dd天");
            } else {
                shortstring = DateUtil.formatDate(date, "yyyy-MM-dd");
            }
        } else if (deltime > 60 * 60) {
            shortstring = (int)(deltime / (60 * 60)) + "小时前";
        } else if (deltime > 60) {
            shortstring = ((int)(deltime / (60))) + "分前";
        } else if (deltime > 1) {
            shortstring = deltime + "秒前";
        } else {
            shortstring = "1秒前";
        }
        return shortstring;
    }

    /**
     * @param now 当前系统时间
     * @param lastInsertTime RedisUtil中获取的最后插入时间
     * @param warningTime job 中获取的 warningTime
     * @return
     */
    public static boolean compareTime(long now, long lastInsertTime, int warningTime) {
        long difTime = now - lastInsertTime;
        if (difTime > warningTime * 60 * 60 * 1000) {
            return true;
        } else {
            return false;
        }
    }

    public static Long getDiffTime(long now, long lastInsertTime, int warningTime) {
        long difTime = now - lastInsertTime;
        if (difTime <= warningTime * 60 * 60 * 1000) {
            return 0L;
        }
        difTime = difTime - warningTime * 60 * 60 * 1000;

        return difTime;
    }

    public static String getDiff(long now, long lastInsertTime, int warningTime) {
        long difTime = now - lastInsertTime;
        if (difTime <= warningTime * 60 * 60 * 1000) {
            return "";
        }
        difTime = difTime - warningTime * 60 * 60 * 1000;
        long day = 0;
        long hour = 0;
        long second = 0;
        String shortStr = "";
        day = difTime / (1000 * 60 * 60 * 24);
        hour = (difTime - day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60);
        second = (difTime - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60) / (1000 * 60);
        if (day == 0) {
            if (hour == 0) {
                shortStr = second + "分钟";
            } else if (second == 0) {
                shortStr = hour + "小时";
            } else {
                shortStr = hour + "小时" + second + "分钟";
            }
        } else if (hour == 0 && second == 0) {
            shortStr = day + "天";
        } else if (second == 0) {
            shortStr = day + "天" + hour + "小时";
        } else {
            shortStr = day + "天" + hour + "小时" + second + "分钟";
        }
        return shortStr;
    }

    public static String longToString(long currentTime, String formatType) throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    public static Date longToDate(long currentTime, String formatType) throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static Integer intervalsView(Integer time) {
        if (time == null) {
            return null;
        } else {
            return time / 1000;
        }
    }

    /**
     * 时间戳转成时间
     * 
     * @param time
     * @return
     * @throws ParseException
     */
    public static String getTimeToDate(Long time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        // Date date=format.parse(d);
        // System.out.println("Format To String(Date):"+d);
        // System.out.println("Format To Date:"+date);
        return d;
    }

    /**
     * 时间戳转成时间
     * 
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date getTimeToDates(Long time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        Date date = format.parse(d);
        // System.out.println("Format To String(Date):"+d);
        // System.out.println("Format To Date:"+date);
        return date;
    }

    public static String subStringDate(String date) {
        String newDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, date.length());
        return newDate;
    }

    public static Date stringToDate(String strTime, String formatType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * @param begin 起始时间
     * @param end 截止时间
     * @return begin和end的时间戳差值
     * @throws ParseException
     */
    public static long timeStampDiff(Object begin, Object end) throws ParseException {
        Date beginDate = stringToDate(begin.toString(), "yyyy-MM-dd HH:mm");
        Date endDate = stringToDate(end.toString(), "yyyy-MM-dd HH:mm");
        Date now = new Date();
        if (now.getTime() - beginDate.getTime() < 0) {
            return -2;// 起始时间在当前时间之后，异常
        }
        if (endDate.getTime() - beginDate.getTime() < 0) {
            return -1;// 起始时间在结束时间之后，异常
        }
        long day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * @param begin 起始时间
     * @param end 截止时间
     * @return begin和end的时间戳差值
     * @throws ParseException
     */
    public static long timeDiff(Object begin, Object end) throws ParseException {
        Date beginDate = string2Date(begin.toString());
        Date endDate = string2Date(end.toString());
        // string2Date
        long day = endDate.getTime() - beginDate.getTime();
        return day;
    }

    public static boolean isDate(String str) {

        boolean convertSuccess = true;
        try {
            Date dtStartTime = DateUtil.parseDate(str);
        } catch (Exception e) {
            convertSuccess = false;
        }

        return convertSuccess;
    }

    /**
     * 简介：连个时间比较 如果start>=end 返回true 否则返回false 或者 start end 有一个为null也返回 false
     * 
     * @author jemond
     * @date 2017年8月8日 下午4:05:46
     * @param start
     * @param end
     * @return
     */
    public static boolean compareDate(Date start, Date end) {
        if (start == null || end == null) {
            return false;
        }
        return start.getTime() >= end.getTime();
    }

    /**
     * 转换成格式化的时长显示
     * 
     * @param duration 时长，单位秒
     * @return 返回格式化后的时长显示，如：3661——>1小时1分钟1秒
     */
    public static String formatDuration(Integer duration) {
        if (duration == null)
            return "0秒";

        String s = "";
        // if(duration/60>0){
        // if(duration%60>0){
        // s = duration/60+"分钟"+duration%60+"秒";
        // }else{
        // s = duration/60+"分钟";
        // }
        // }else{
        // s = duration%60+"秒";
        // }

        if (duration / 60 > 0) {
            if (duration / 3600 > 0) {
                if (duration % 3600 > 0) {
                    if (duration % 3600 % 60 > 0) {
                        if (duration % 3600 / 60 > 0) {
                            s = duration / 3600 + "小时" + duration % 3600 / 60 + "分钟" + duration % 3600 % 60 + "秒";
                        } else {
                            s = duration / 3600 + "小时" + duration % 3600 % 60 + "秒";
                        }
                    } else {
                        s = duration / 3600 + "小时" + duration % 3600 / 60 + "分钟";
                    }
                } else {
                    s = duration / 3600 + "小时";
                }
            } else {
                if (duration % 60 > 0) {
                    s = duration / 60 + "分钟" + duration % 60 + "秒";
                } else {
                    s = duration / 60 + "分钟";
                }
            }
        } else {
            s = duration % 60 + "秒";
        }

        return s;
    }

    /**
     * 简介：传入 yyyy-MM-dd 得到此时间 向前一天的时间
     * 
     * @author jemond
     * @date 2018年1月22日 下午2:34:32
     * @param date
     * @return
     */
    public static String getNextStatisticsDate(String date) {
        try {
            Date parseDate = parseDate(date);
            Date addDayOfDate = getAddDayOfDate(parseDate, 1);
            String formatDate = formatDate(addDayOfDate, FORMAT_PATTERN_DAY);
            return formatDate;
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 简介：得到两个时间之间相差几天
     * 
     * @author jemond
     * @date 2018年1月22日 下午2:53:45
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int getDaysBetween(String sdate, String bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(sdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            return 1;
        }
    }
    
    /***
     * 简介：得到两个时间之间相差几天 时间格式为yy-MM-dd
     * 
     * @author jemond
     * @date 2018年1月22日 下午2:53:45
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int getDaysBetweenFormat(String sdate, String bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(sdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            return 1;
        }
    }
    

    /**
     * @param args
     * @throws ParseException
     * @throws NumberFormatException
     */
    public static void main(String[] args) throws Exception {
        Date date = getAddDayOfMinute(new Date(), 20);
        System.out.println(formatDateSeconds(date));
        String parseDate = getNextStatisticsDate("2017-12-14");
        System.out.println(getDaysBetween("2017-12-13", "2017-12-20"));

        // System.out.println(formatDuration(3660));

    }

}
