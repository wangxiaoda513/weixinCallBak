package com.weixin.util;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Package      com.keegoo.uService.common.utils
 * @Title:       DateUtils
 * @Author       Wangjl
 * @Date         2016/6/27  15:03
 * @Desc         有关时间相关的基本操作公用类
 */
public class DateUtils {

    private static Logger logger = Logger.getLogger(DateUtils.class);

    private static final String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";

    private static final String FORMAT_yyyyMMdd = "yyyy/MM/dd";

    private static final String FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    private static final String FORMAT_yyMMddmmss = "yyMMddmmss";

    private static final String FORMAT_HHmm_yyyy_MM_dd = "HH:mm yyyy-MM-dd";

    /**
     * yyyy/MM/dd
     */
    public static SimpleDateFormat SDF2 = new SimpleDateFormat(FORMAT_yyyyMMdd);

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static SimpleDateFormat SDF3 = new SimpleDateFormat(FORMAT_yyyy_MM_dd_HH_mm_ss);

    /**
     * yyMMddHHmmss
     */
    public static SimpleDateFormat SDF4 = new SimpleDateFormat(FORMAT_yyMMddmmss);

    /**
     * HH:mm yyyy-MM-dd
     */
    public static SimpleDateFormat SDF5 = new SimpleDateFormat(FORMAT_HHmm_yyyy_MM_dd);

    /**
     * @method       getAge
     * @author       Wangjl
     * @date         2016-06-27  14:49:57
     * @params       babyBirthday       出生年月
     * @return       age                当前年龄
     * @desc         根据传入的出生日期生日获取其年龄
     */
    public static String getAge(Date birthday){

        Calendar cal    = Calendar.getInstance();

        //  获取当前年份
        int yearNow     = cal.get(Calendar.YEAR);
        int monthNow    = cal.get(Calendar.MONTH) + 1;
        int dayNow      = cal.get(Calendar.DAY_OF_MONTH);

        //  获取出生日期对象
        cal.setTime(birthday);

        int yearBirthday    = cal.get(Calendar.YEAR);
        int monthBirthday   = cal.get(Calendar.MONTH) + 1;
        int dayBirthday     = cal.get(Calendar.DAY_OF_MONTH);

        //  计算岁数
        int ageYear     = yearNow   - yearBirthday;
        int ageMonth    = monthNow  - monthBirthday;
        int ageDay      = dayNow    - dayBirthday;

        if (ageYear < 0)
            ageYear = 0;

        if (monthNow <= monthBirthday){
            if (monthNow == monthBirthday){
                if (dayNow < dayBirthday){
                    ageMonth = 11;
                    ageYear --;
                }
            }else{
                ageMonth = monthNow + 12 - monthBirthday;
                ageYear --;
            }
        }

        if (ageYear == 0){
            if (ageMonth == 0){
                return ageDay + "天";
            }else{
                return ageMonth + "个月";
            }
        }else{
            if (ageMonth == 0){
                return ageYear + "岁";
            }else{
                return ageYear + "岁" + ageMonth + "个月";
            }
        }
    }

    /**
     * @method       getAge
     * @author       Wangjl
     * @date         2016-06-27  14:49:57
     * @params       babyBirthday       出生年月
     * @return       age                当前年龄
     * @desc         根据传入的出生日期生日获取其年龄
     */
    public static int getOrderAge(Date birthday){

        Calendar cal    = Calendar.getInstance();

        //  获取当前年份
        int yearNow     = cal.get(Calendar.YEAR);
        int monthNow    = cal.get(Calendar.MONTH) + 1;
        int dayNow      = cal.get(Calendar.DAY_OF_MONTH);

        //  获取出生日期对象
        cal.setTime(birthday);

        int yearBirthday    = cal.get(Calendar.YEAR);
        int monthBirthday   = cal.get(Calendar.MONTH) + 1;
        int dayBirthday     = cal.get(Calendar.DAY_OF_MONTH);

        //  计算岁数
        int ageYear     = yearNow   - yearBirthday;
        int ageMonth    = monthNow  - monthBirthday;
        int ageDay      = dayNow    - dayBirthday;


        if (ageYear < 0)
            ageYear = 0;

        if (monthNow <= monthBirthday){
            if (monthNow == monthBirthday){
                if (dayNow < dayBirthday){
                    ageMonth = 11;
                    ageYear --;
                }
            }else{
                ageMonth = monthNow + 12 - monthBirthday;
                ageYear --;
            }
        }


        if (ageMonth == 0){
            return ageYear;
        }else{
            return ageYear+1;
        }

    }

    /**
     * 计算当前年份
     * @param date
     * @return
     */
    public static int getDiffYear(Date date){

        int year = 0 ;
        try{

            Calendar cal = Calendar.getInstance();
            int year1  = cal.get(Calendar.YEAR);
            cal.setTime(date);
            int year2  = cal.get(Calendar.YEAR);
            year = (year2 - year1) + 1;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return year;
    }

    /**
     * @method       getDeclareFormatDateString
     * @author       Wangjl
     * @date         2016-06-27  17:20:49
     * @params       formater           所需要的时间格式，eg: 2016-06-15 为  ：yyyy-MM-dd
     *                datePar            传入的指定的时间
     * @return       String             格式化后的字符串化时间参数值
     * @desc         获取指定传入格式的时间字符串
     */
    public static String getDeclareFormatDateString(String formater,Date datePar){
        return new SimpleDateFormat(formater).format(datePar).toString();
    }

    /**
     * @method       getDateString
     * @author       Wangjl
     * @date         2016-06-27  17:20:49
     * @params       formater           所需要的时间格式，eg: 2016-06-15 为  ：yyyy-MM-dd
     *                datePar            传入的指定的时间
     * @return       String             格式化后的字符串化时间参数值
     * @desc         获取指定传入格式的时间字符串
     */
    public static String getDateString(String formater,Date datePar){
        return new SimpleDateFormat(formater).format(datePar).toString();
    }

    /**
     * @method       getPriceDoubleValue
     * @author       Wangjl
     * @date         2016-06-28  14:55:44
     * @params       price              要格式化的值
     * @return       String             格式化后的字符串化金额
     * @desc         获取double数值转换为保留两位小数的字符串
     */
    public static String getPriceDoubleValue(double price){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(price);
    }

    /**
     * 仅比较年月日 相等
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            logger.debug("isSameDate : 参数有空值，直接返回false");
            return false;
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c1.setTime(d2);

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DATE) == c2.get(Calendar.DATE);

    }

    /**
     *
     * getDateAddMinutes:(获取指定时间之后多少分钟的时间).
     *
     * @author sid
     * @param date
     * @param minutes
     * @return
     */
    public static Date getDateAddMinutes(Date date,int minutes){

        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MINUTE, minutes);
        return cl.getTime();
    }

    /**
     * 保留日期 ，把时间设置为 0 <br>
     * HOUR_OF_DAY<br>
     * MINUTE<br>
     * SECOND<br>
     * MILLISECOND<br>
     *
     * @param d
     * @return
     */
    public static Date clearTime(Date d) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * 增加天数(负值为减)
     * @param d
     * @param dayToAdd
     * @return
     */
    public static Date addDay(Date d, int dayToAdd) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        ca.add(Calendar.DAY_OF_MONTH, dayToAdd);
        return ca.getTime();
    }

    /**
     * 是否为"今天"
     *
     * @param d
     * @return
     */
    public static boolean isToday(Date d) {
        return isSameDate(d, new Date());
    }

    /**
     *
     *  将字符串格式的日期转换为Date型的日期<p>
     *  modify by wjz 0326<p>
     *  考虑到日期格式比较复杂，在转换之前先做如下假定：<p>
     *  都是按照年－月－日的格式排列<p>
     *  年都是4位<p>
     * strToDate:
     *
     * @author sid
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        if (strDate == null || strDate.length() < 6) {
            throw new IllegalArgumentException("illeage date format");
        }
        String fmt = "yyyy-MM-dd HH:mm:ss";
        if (strDate.length() == 19) {
            if (strDate.indexOf("-") > 0) {
                fmt = "yyyy-MM-dd HH:mm:ss";
            } else if (strDate.indexOf("/") > 0) {
                fmt = "yyyy/MM/dd HH:mm:ss";
            }
        } else if (strDate.length() == 18) {
            if (strDate.indexOf("-") > 0) {
                fmt = "yyyy-MM-ddHH:mm:ss";
            } else if (strDate.indexOf("/") > 0) {
                fmt = "yyyy/MM/ddHH:mm:ss";
            }
        } else if (strDate.length() == 16) {
            if (strDate.indexOf("-") > 0) {
                fmt = "yyyy-MM-dd HH:mm";
            } else if (strDate.indexOf("/") > 0) {
                fmt = "yyyy/MM/dd HH:mm";
            }
        } else if (strDate.length() == 14) {

            fmt = "yyyyMMddHHmmss";
        } else if (strDate.length() == 10) {
            if (strDate.indexOf("-") > 0) {
                fmt = "yyyy-MM-dd";
            } else if (strDate.indexOf("/") > 0) {
                fmt = "yyyy/MM/dd";
            } else if (strDate.indexOf(".") > 0) {
                fmt = "yyyy.MM.dd";
            }
        } else if (strDate.length() == 8) {
            if (strDate.indexOf("-") > 0) {
                fmt = "yy-MM-dd";
            } else if (strDate.indexOf("/") > 0) {
                fmt = "yy/MM/dd";
            } else if (strDate.indexOf(".") > 0) {
                fmt = "yy.MM.dd";
            } else {
                fmt = "yyyyMMdd";
            }

        }

        SimpleDateFormat formatter = new SimpleDateFormat(fmt);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Date strYYYY_MM_HHToDate(String strDate) {
        if (strDate == null || strDate.length() < 6) {
            throw new IllegalArgumentException("illeage date format");
        }
        String fmt = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(fmt);
        try {
            Date strtodate = formatter.parse(strDate);
            return strtodate;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    /**
     * @method       getDateString
     * @author       Wangjl
     * @date         2016-06-27  17:20:49
     * @params       formater           所需要的时间格式，eg: 2016-06-15 为  ：yyyy-MM-dd
     *                datePar            传入的指定的时间
     * @return       String             格式化后的字符串化时间参数值
     * @desc         获取指定传入格式的时间字符串
     */
    public static String dateToString(Date datePar){

        if(null == datePar){
            return "";
        }
        return dateToString(datePar, FORMAT_yyyy_MM_dd);
    }

    /**
     * 日期转字符串
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        if (date == null) {
            return "";
        }
        if (format == null) {
            format = "yyyy-MM-dd hh:mm:ss";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 日期转字符串
     *  12小时制
     * @param date
     * @param date
     * @return
     */
    public static String date12ToString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return df.format(date);
    }

    /**
     *  格式化为yyyyMMddHHmmss的形式
     * @param
     * @return
     */
    public static String dateToString(){
        return SDF4.format(new Date());
    }

    /**
     * 日期转字符串
     *  24小时制
     * @param date
     * @param
     * @return
     */
    public static String date24ToString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    /**
     * 日期转时间戳
     *
     * @param date
     * @return
     */
    public static long dateToTimeMillis(Date date) {
        if (date == null) {
            return 0;
        }
        return date.getTime() / 1000;
    }

    /**
     * add by Bill
     * 2011-07-07
     * @param datestr
     * @return
     */
    public static Date StringToDate(String datestr){
        Date dt=null;
        if(datestr==null || "".equals(datestr)){
            dt = new Date();
        }
        try {
            dt = SDF3.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dt;
    }

    /**
     * 格式化时间
     * @param timeToTrunk
     * @return
     */
    public static String getStandardTime(String timeToTrunk){
        String times = null;
        try{
            times = date24ToString(SDF3.parse(timeToTrunk));
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * @method       getStandardTimeShort
     * @author       Wangjl
     * @date         2016-07-21  18:34:20
     * @params
     * @return
     * @desc         获取格式化的年月日时间字符串
     */
    public static String getStandardTimeShort(String timeToTrunk){
        String times = null;
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            times = df.format(df.parse(timeToTrunk));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
}
