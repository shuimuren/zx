package com.zhixing.work.zhixin.util;


import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateFormatUtil {

    static final String TAG = DateFormatUtil.class.getSimpleName();

    public static final String HH_MM = "HH:mm";
    public static final String A_hh_MM = "a  hh:mm";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYPMMPDD = "yyyy. MM. dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String MMDD_HH_MM = "MM-dd HH:mm";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDHHMMSS = "YYYYMMDDHHMMSS";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public static final String YYYY_M = "yyyy-M";

    /**
     * 格式时期
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getTimeZ() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");


        Calendar calendar = Calendar.getInstance();
        int offset = calendar.get(Calendar.ZONE_OFFSET);
        calendar.add(Calendar.MILLISECOND, -offset);
        Date date = calendar.getTime();
        String timer = sdf.format(date);
        return timer;
    }


    /**
     * 得到UTC时间，类型为字符串，格式为"yyyy-MM-dd HH:mm"<br />
     * 如果获取失败，返回null
     *
     * @return
     */
    public static String getUTCTimeStr() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        StringBuffer UTCTimeBuffer = new StringBuffer();
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        UTCTimeBuffer.append(year).append("-").append(month).append("-").append(day);
        UTCTimeBuffer.append(" ").append(hour).append(":").append(minute);
        try {
            format.parse(UTCTimeBuffer.toString());
            return UTCTimeBuffer.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取今天的日期
     *
     * @return
     */
    public static Date today() {
        return new Date();
    }

    /*获取当前时间
    *
    * */
    public static String getTime() {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
//获取当前时间

        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取今天的时间戳
     *
     * @return
     */
    public static String toTime() {
        return System.currentTimeMillis() + "";
    }

    //	获取三天前的日期
    public static Date getThreeDaysAgo() {
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        calendar1.add(Calendar.DATE, -3);
        try {
            date = sdf1.parse(sdf1.format(calendar1.getTime()));
            return date;
        } catch (ParseException e) {
            LOG.e(TAG, e.getMessage());
            // ignore
        }
        return date;
    }

    //*
    //
    // 时间戳转换
    // */
    public static Date getDate(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            String times = format.format(time);
            date = format.parse(times);
        } catch (ParseException e) {
            LOG.e(TAG, e.getMessage());
        }
        return date;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (TextUtils.isEmpty(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date   字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 用指定的格式字符串
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String parseDate(String dateStr, String format) {
        try {

            SimpleDateFormat fmt = new SimpleDateFormat(format);


            Date date = new SimpleDateFormat(format).parse(dateStr);
            String time = fmt.format(date);
            return time;
        } catch (ParseException e) {
            LOG.e(TAG, e.getMessage());
            // ignore
        }
        return null;
    }

    public static Calendar date2Calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String formatTickTime(long timeLong, Long time) {
        if (timeLong < 60)
            return "刚刚";
        else if (timeLong < 60 * 60) {
            timeLong = timeLong / 60;
            return timeLong + "分钟前";
        } else if (timeLong < 60 * 60 * 24) {
            timeLong = timeLong / 60 / 60;
            return timeLong + "小时前";
        } else if (timeLong < 60 * 60 * 24 * 6) {
            timeLong = timeLong / 60 / 60 / 24;
            return timeLong + "天前";
//        } else if (timeLong < 60 * 60 * 24 * 1000 * 7 * 4) {
//            timeLong = timeLong / 1000 / 60 / 60 / 24 / 7;
//            return timeLong + "周前";
        } else {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(time * 1000);
            res = simpleDateFormat.format(date);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
//            return sdf.format(new Date(timeLong));
            return res;
        }
    }

    public static String TwoFormatTickTime(long timeLong, Long time) {
        if (timeLong < 60)
            return "刚刚";
        else if (timeLong < 60 * 60) {
            timeLong = timeLong / 60;
            return timeLong + "分钟前";
        } else if (timeLong < 60 * 60 * 24) {
            timeLong = timeLong / 60 / 60;
            return timeLong + "小时前";
        } else if (timeLong < 60 * 60 * 24 * 2) {
            timeLong = timeLong / 60 / 60 / 24;
            return timeLong + "天前";
//        } else if (timeLong < 60 * 60 * 24 * 1000 * 7 * 4) {
//            timeLong = timeLong / 1000 / 60 / 60 / 24 / 7;
//            return timeLong + "周前";
        } else {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(time * 1000);
            res = simpleDateFormat.format(date);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
//            return sdf.format(new Date(timeLong));
            return res;
        }
    }

    public static Boolean sizeComparison(Date dtae1, Date dtae2) {
        if (dtae1.getTime() < dtae2.getTime() || dtae1.getTime() == dtae2.getTime()) {
            return true;
        }
        return false;
    }


    private static String formatNumber(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return String.valueOf(num);
    }

    /**
     * 获取当前时间前range个月的月份时间
     *
     * @param range
     * @return
     */
    public static List<String> getMonthRange(int range) {
        List<String> data = new ArrayList<>();
        Date current = null;//定义起始日期
        //定义日期实例
        Calendar dd = Calendar.getInstance();
        current = dd.getTime();
        //设置到前range个月的月份时间
        dd.add(Calendar.MONTH, -range);
        while (dd.getTime().before(current)) {
            //判断是否到结束日期
            String str = new SimpleDateFormat(YYYY_M).format(dd.getTime());
            System.out.println("水电费月份：" + str);
            data.add(str);
            //进行当前日期月份加1
            dd.add(Calendar.MONTH, 1);
        }
        return data;
    }

    /**
     * 获取当前时间range个月的月份时间
     *
     * @param range
     * @return
     */
    public static List<String> getMonth(int range) {
        List<String> data = new ArrayList<>();
        Date current = null;//定义起始日期
        //定义日期实例
        Calendar dd = Calendar.getInstance();
        current = dd.getTime();
        //设置到前range个月的月份时间
        dd.add(Calendar.MONTH, -range);
        while (dd.getTime().before(current)) {
            //判断是否到结束日期
            String str = new SimpleDateFormat(YYYY_M).format(dd.getTime());
            System.out.println("水电费月份：" + str);
            data.add(str);
            //进行当前日期月份加1
            dd.add(Calendar.MONTH, 1);
        }
        return data;
    }


    public static List<String> get6Month() {

        List<String> list = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        for (int i = 6; i > 0; i--) {

            Calendar calendar = Calendar.getInstance();
            //获取当前时间的前6个月
            calendar.add(Calendar.MONTH, -i);
            //将calendar装换为Date类型
            Date date = calendar.getTime();

            System.out.println(sdf.format(date));
            list.add(sdf.format(date));

        }

        return list;
    }

    /**
     * 是否今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(String date) {
        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd");
        if (date.equals(fmt.format(new Date()).toString())) {//格式化为相同格式
            return true;
        } else {
            return false;
        }


    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTimeYM(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }


}
