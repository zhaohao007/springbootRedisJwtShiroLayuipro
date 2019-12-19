package com.hinmu.lims.util.date;


import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * Created by hao on 2017/10/14.
 */
public class DateUtil {

    // 默认日期格式
    public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";
    public static final String DATE_DEFAULT_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_DEFAULT_FORMAT_YYYYMM = "yyyyMM";

    // 默认时间格式
    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_DEFAULT_FORMAT_zhengdian = "yyyy-MM-dd HH:00:00";
    public static final String DATETIME_HHmm_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATETIME_DEFAULT_FORMAT_hanzi = "yyyy年MM月dd日 HH:mm:ss";

    public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";

    // 日期格式化
    private static DateFormat dateFormat = null;
    private static DateFormat dateFormatyyyymmdd = null;
    private static DateFormat dateFormatyyyymm = null;
    private static DateFormat dateFormatyyyymmdd_hanzi = null;

    // 时间格式化
    private static DateFormat dateTimeFormat = null;
    private static DateFormat dateTimeFormat_zhengdian = null;
    private static DateFormat dateTimeHHmmFormat = null;

    private static DateFormat timeFormat = null;

    private static Calendar gregorianCalendar = null;

    static {
        dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        dateFormatyyyymmdd = new SimpleDateFormat(DATE_DEFAULT_FORMAT_YYYYMMDD);
        dateFormatyyyymm = new SimpleDateFormat(DATE_DEFAULT_FORMAT_YYYYMM);
        dateFormatyyyymmdd_hanzi = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT_hanzi);
        dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        dateTimeFormat_zhengdian = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT_zhengdian);
        dateTimeHHmmFormat = new SimpleDateFormat(DATETIME_HHmm_FORMAT);
        timeFormat = new SimpleDateFormat(TIME_DEFAULT_FORMAT);
        gregorianCalendar = new GregorianCalendar();
    }

    public static Date getDateFromMillSec(Long millSec){
        return new Date(millSec);
    }

    /**
     * 将时间 hh:mm 转化为当天日期
     * @param hhmm
     * @return
     */
    public static Date formatHHMMDate(String hhmm){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        try {
            return  df.parse(hhmm);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern){
        if (date==null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date formatDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getDateFormat(Date date) {
        return dateFormat.format(date);
    }

    /**
     * 日期格式化yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateTimeFormat(Date date) {
        return dateTimeFormat.format(date);
    }
    public static String getDateTimeFormat_zhengdian(Date date) {
        return dateTimeFormat_zhengdian.format(date);
    }

    /**
     * 日期格式化yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String getDateTimeHHmmFormat(Date date) {
        return dateTimeHHmmFormat.format(date);
    }

    public static String getDateTimeFormat_hanzi(Date date) {
        return dateFormatyyyymmdd_hanzi.format(date);
    }


    /**
     * 日期格式化yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String getDateTimeFormatyyyymmdd(Date date) {
        return dateFormatyyyymmdd.format(date);
    }
    public static String getDateTimeFormatyyyymm(Date date) {
        return dateFormatyyyymm.format(date);
    }



    /**
     * 时间格式化
     *
     * @param date
     * @return HH:mm:ss
     */
    public static String getTimeFormat(Date date) {
        return timeFormat.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param 格式类型
     * @return
     */
    public static String getDateFormat(Date date, String formatStr) {
        if (!StringUtils.isEmpty(formatStr)) {
            return new SimpleDateFormat(formatStr).format(date);
        }
        return null;
    }

    /**
     * 日期格式化
     *
     * @param date
     * @return
     */
    public static Date getDateFormat(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    public static Date getDateTimeFormat(String date) {
        try {
            return dateTimeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间格式化
     *
     * @param date yyyymmdd
     * @return
     */
    public static Date getDateTimeFormatyyyymmdd(String date) {
        try {
            return dateFormatyyyymmdd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 获取当前日期(yyyy-MM-dd)
     *
     * @return
     */
    public static Date getNowDate() {
        return DateUtil.getDateFormat(dateFormat.format(new Date()));
    }

    /**
     * 获取当前日期星期一日期
     *
     * @return date
     */
    public static Date getFirstDayOfWeek() {
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前日期星期日日期
     *
     * @return date
     */
    public static Date getLastDayOfWeek() {
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期星期一日期
     *
     * @param date 指定日期
     * @return date
     */
    public static Date getFirstDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期星期一日期
     *
     * @param date 指定日期
     * @return date
     */
    public static Date getLastDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前月的第一天
     *
     * @return date
     */
    public static Date getFirstDayOfMonth() {
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前月的最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth() {
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        gregorianCalendar.add(Calendar.MONTH, 1);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        gregorianCalendar.add(Calendar.MONTH, 1);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期前一天
     *
     * @param date
     * @return
     */
    public static Date getDayBefore(Date date,int dayNum) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day - dayNum);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期后一天
     *
     * @param date
     * @return
     */
    public static Date getDayAfter(Date date) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day + 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getNowYear() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getNowMonth() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当月天数
     *
     * @return
     */
    public static int getNowMonthDay() {
        Calendar d = Calendar.getInstance();
        return d.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取时间段的每一天
     *
     * @param startDate 开始日期
     * @param endDate 结算日期
     * @return 日期列表
     */
    public static List<Date> getEveryDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        // 格式化日期(yy-MM-dd)
        startDate = DateUtil.getDateFormat(DateUtil.getDateFormat(startDate));
        endDate = DateUtil.getDateFormat(DateUtil.getDateFormat(endDate));
        List<Date> dates = new ArrayList<Date>();
        gregorianCalendar.setTime(startDate);
        dates.add(gregorianCalendar.getTime());
        while (gregorianCalendar.getTime().compareTo(endDate) < 0) {
            // 加1天
            gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(gregorianCalendar.getTime());
        }
        return dates;
    }

    /**
     * 获取提前多少个月
     *
     * @param monty
     * @return
     */
    public static Date getFirstMonth(int monty) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -monty);
        return c.getTime();
    }

    /**
     * 获取某天日期的开始时间  YY-MM-DD 00:00:00.0
     *
     * @param someDay
     * @return
     */
    public static Date getBeginTime(Date someDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(someDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    public static Date getBeginTime(String someDay1) {
        Date someDay = getDateTimeFormat(someDay1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(someDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某天日期的开始时间  YY-MM-DD 23:59:59.999
     *
     * @param someDay
     * @return
     */
    public static Date getEndTime(Date someDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(someDay);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    public static Date getEndTime(String someDay1) {
        Date someDay = getDateTimeFormat(someDay1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(someDay);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 在beginTime之前
     * @param nowTime
     * @param beginTime
     * @return
     */
    public static boolean beforCalendar(Date nowTime, Date beginTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        if (date.before(begin)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 在beginTime之后
     * @param nowTime
     * @param endTime
     * @return
     */
    public static boolean afterCalendar(Date nowTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(endTime);
        if (date.after(begin)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 根据当前日期获得所在周的日期区间（周一和周日日期）
     *
     * @return
     * @author zhaoxuepu
     * @throws ParseException
     */
    public String getTimeInterval(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = dateTimeFormat.format(cal.getTime());
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = dateTimeFormat.format(cal.getTime());
        // System.out.println("所在周星期日的日期：" + imptimeEnd);
        return imptimeBegin + "," + imptimeEnd;
    }


    /**
     * 上月1号的日期
     * @param date
     * @return
     */
    public static String lastMonthFirstDayTime(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        String lastMonthFirstDayTime = dateTimeFormat.format(DateUtil.getFirstDayOfMonth(c.getTime()));
        return lastMonthFirstDayTime;
    }
    /**
     * 上月最后一天的日期
     * @param date
     * @return
     */
    public static String lastMonthEndtDayTime(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        String getLastDayOfMonth = dateTimeFormat.format(DateUtil.getLastDayOfMonth(c.getTime()));
        return getLastDayOfMonth;


    }



    /**
     * 上周周一的日期
     * @param date
     * @return
     */
    public static String lastWeekMondayTime(Date date){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        String lastBeginDate = dateTimeFormat.format(calendar1.getTime());
        return lastBeginDate;

    }
    /**
     * 上周周日的日期
     * @param date
     * @return
     */
    public static String lastWeekSundayTime(Date date){
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        int dayOfWeek = calendar2.get(Calendar.DAY_OF_WEEK) - 1;
        int offset2 = 7 - dayOfWeek;
        calendar2.add(Calendar.DATE, offset2 - 7);
        String lastEndDate = dateTimeFormat.format(calendar2.getTime());
        return lastEndDate;

    }

    /**
     * 昨天的日期
     * @param date
     * @return
     */
    public static String yesterdayTime(Date date){
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        calendar2.add(Calendar.DATE,-1);
        String lastEndDate = dateTimeFormat.format(calendar2.getTime());
        return lastEndDate;
    }
    /**
     * 昨天的日期
     * @param date
     * @return
     */
    public static Date yesterdayDateTime(Date date){
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        calendar2.add(Calendar.DATE,-1);
        return calendar2.getTime();
    }


    /**
     * 获取当前系统的时间戳
     *
     * @return
     */
    public static String getCurrentTimestamp() {

        long timeStamp = new Date().getTime();
        return String.valueOf(timeStamp);
    }

    public static String getCurrentTimestamp10() {

        long timeStamp = new Date().getTime() / 1000;
        String timestr = String.valueOf(timeStamp);
        return timestr;
    }

    public static String getTimeStamp() {
        int time = (int) (System.currentTimeMillis() / 1000);
        return String.valueOf(time);
    }


    //由出生日期获得年龄
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }



    public static Date getBirthdayFromCardid(String cardid){
        //身份证是从第6位开始到14位为出生日期(下标从0开始计)
        return getDateTimeFormatyyyymmdd(cardid.substring(6,14));
    }

    /**
     * 昨天的日期
     * @param date
     * @return
     */
    public static Date lastdayDateTime(Date date,int  days){
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        calendar2.add(Calendar.DATE,-days);
        return calendar2.getTime();
    }



    public static String getFriendlytime(Date d){
        long delta = (new Date().getTime()-d.getTime())/1000;
        if(delta<=0)return d.toLocaleString();
        if(delta/(60*60*24*365) > 0) return delta/(60*60*24*365) +"年前";
        if(delta/(60*60*24*30) > 0) return delta/(60*60*24*30) +"个月前";
        if(delta/(60*60*24*7) > 0)return delta/(60*60*24*7) +"周前";
        if(delta/(60*60*24) > 0) return delta/(60*60*24) +"天前";
        if(delta/(60*60) > 0)return delta/(60*60) +"小时前";
        if(delta/(60) > 0)return delta/(60) +"分钟前";
        return "刚刚";
    }


    public static   List<Map<Integer,String>> getYearMonthMapList(){
        List<Map<Integer,String>> nowMonthList = new ArrayList();
        Map map1 =new HashMap();
        map1.put("key", 1);
        map1.put("value", "1月份");
        nowMonthList.add(map1);

        Map map2 =new HashMap();
        map2.put("key",2);
        map2.put("value", "2月份");
        nowMonthList.add(map2);

        Map map3 =new HashMap();
        map3.put("key",3);
        map3.put("value", "3月份");
        nowMonthList.add(map3);


        Map map4 =new HashMap();
        map4.put("key",4);
        map4.put("value", "4月份");
        nowMonthList.add(map4);


        Map map5 =new HashMap();
        map5.put("key",5);
        map5.put("value", "5月份");
        nowMonthList.add(map5);


        Map map6 =new HashMap();
        map6.put("key",6);
        map6.put("value", "6月份");
        nowMonthList.add(map6);

        Map map7 =new HashMap();
        map7.put("key",7);
        map7.put("value", "7月份");
        nowMonthList.add(map7);

        Map map8 =new HashMap();
        map8.put("key",8);
        map8.put("value", "8月份");
        nowMonthList.add(map8);

        Map map9 =new HashMap();
        map9.put("key",9);
        map9.put("value", "9月份");
        nowMonthList.add(map9);

        Map map10 =new HashMap();
        map10.put("key",10);
        map10.put("value", "10月份");
        nowMonthList.add(map10);

        Map map11 =new HashMap();
        map11.put("key",11);
        map11.put("value", "11月份");
        nowMonthList.add(map11);

        Map map12 =new HashMap();
        map12.put("key",12);
        map12.put("value", "12月份");
        nowMonthList.add(map12);

        return  nowMonthList;
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(getFriendlytime(getDateTimeFormat("2018-3-20 11:11:11")));
        System.out.println(getFriendlytime(getDateTimeFormat("2018-3-20 12:11:11")));
        System.out.println(getFriendlytime(getDateTimeFormat("2018-3-20 18:24:11")));
        System.out.println(getDateTimeFormat(getBeginTime(date)));
        System.out.println(getDateTimeFormat(getEndTime(date)));



        //List<Date> getEveryDay(Date startDate, Date endDate)

        //  Date getFirstDayOfWeek(date);

        System.out.println("--------上个月");
        Calendar c = Calendar.getInstance();
        c.setTime(getDateTimeFormat("2017-5-31 11:11:11"));
        c.add(Calendar.MONTH, -1);
//
//        c.set(Calendar.DAY_OF_MONTH, -1);
//        System.out.println("上个月是：" + new SimpleDateFormat("yyyy年MM月dd日").format(c.getTime()));

        System.out.println(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                        DateUtil.getBeginTime(DateUtil.getFirstDayOfMonth(c.getTime()))
                )
        );
        System.out.println(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                        DateUtil.getBeginTime(DateUtil.getLastDayOfMonth(c.getTime()))
                )
        );
        System.out.println("--------上个周");
        // System.out.println(getLastTimeInterval());
        Calendar c2 = Calendar.getInstance();
        c2.setTime(getDateTimeFormat("2017-5-31 11:11:11"));
        System.out.println(lastWeekMondayTime(c2.getTime()));
        System.out.println(lastWeekSundayTime(c2.getTime()));


        System.out.println("--------昨天");
        Calendar c3 = Calendar.getInstance();
        c3.setTime(getDateTimeFormat("2017-5-1 11:11:11"));
        System.out.println(yesterdayTime(c3.getTime()));
        //gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        //gregorianCalendar.getTime();



        System.out.println("--------上月1号" + lastMonthFirstDayTime(new Date()));
        System.out.println("--------上月end" + lastMonthEndtDayTime(new Date()));
        System.out.println("-------测试当周，当月-----------------------------");
        Date today33 = DateUtil.formatDate("2015-10-31 10:00:00", DateUtil.DATETIME_DEFAULT_FORMAT);//new Date();
        System.out.println("的周一:"+getDateTimeFormat(getFirstDayOfWeek(today33)));
        System.out.println("的周日:"+getDateTimeFormat(getLastDayOfWeek(today33)));
        System.out.println("的月1号:"+getDateTimeFormat(getFirstDayOfMonth(today33)));
        System.out.println("的月末:"+getDateTimeFormat(getLastDayOfMonth(today33)));



        System.out.println("--------yyyymmdd"+getDateTimeFormatyyyymmdd(new Date()));

        System.out.println("--------yyyymmdd" + DateUtil.getDateFormat(date, "yyyy年mm月dd日 HH:mm:ss"));
        System.out.println("--------yyyymmdd" + DateUtil.getDateTimeFormat_hanzi(date));

    }
    /**
     * 计算日期相加
     */
    public static Date addDate(Date date,long day){
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day*24*60*60*1000; // 要加上的天数转换成毫秒数
        time+=day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

    public static Date addMinutes(Date source, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(source.getTime());
        c.add(12, minutes);
        return c.getTime();
    }

    public static Date subMinutes(Date source, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(source.getTime());
        c.add(Calendar.MINUTE, 0-minutes);
        return c.getTime();
    }

    public static Date subHours(Date source, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(source.getTime());
        c.add(Calendar.HOUR, 0-hours);
        return c.getTime();
    }

    public static Date subDays(Date source, int days) {
        Calendar now =Calendar.getInstance();
        now.setTime(source);
        now.set(Calendar.DATE,now.get(Calendar.DATE)+days);
        return now.getTime();

    }
    /**
     * 判断时间是不是今天
     * @param date
     * @return    是返回true，不是返回false
     */
    public static boolean isNow(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);

        return day.equals(nowDay);
    }

}

