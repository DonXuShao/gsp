package com.gsp.springcloud.utils;

import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Don
 * @Description  时间日期工具类
 * @Date 2020/7/10 17:00
 **/
public class DateUtils {


    private DateUtils() {
    }


    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    private static final Collection DEFAULT_PATTERNS = Arrays.asList("EEE MMM d HH:mm:ss yyyy", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE, dd MMM yyyy HH:mm:ss zzz");
    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
    private static final TimeZone GMT;
    /**
     * 时间格式
     */
    public static final String TIME_TYPE = "yyyy-MM-dd HH:mm:ss";

/**
 * @Author Don
 * @Description :  按照TIME_TYPE格式进行 转换
 * @Date 2020/7/17 11:30
 * @Parameter : [date]
 * @Return  java.lang.String
 **/
    public static String formatDate(Object date){
        if (null == date){
            return null;
        }else{
            return formatDate(date,TIME_TYPE);
        }
    }

    public static Date parseDate(String dateValue) throws DateParseException {
        return parseDate(dateValue, (Collection)null, (Date)null);
    }

    public static Date parseDate(String dateValue, Collection dateFormats) throws DateParseException {
        return parseDate(dateValue, dateFormats, (Date)null);
    }

    public static Date parseDate(String dateValue, Collection dateFormats, Date startDate) throws DateParseException {
        if (dateValue == null) {
            throw new IllegalArgumentException("dateValue is null");
        } else {
            if (dateFormats == null) {
                dateFormats = DEFAULT_PATTERNS;
            }

            if (startDate == null) {
                startDate = DEFAULT_TWO_DIGIT_YEAR_START;
            }

            if (dateValue.length() > 1 && dateValue.startsWith("'") && dateValue.endsWith("'")) {
                dateValue = dateValue.substring(1, dateValue.length() - 1);
            }

            SimpleDateFormat dateParser = null;
            Iterator formatIter = dateFormats.iterator();

            while(formatIter.hasNext()) {
                String format = (String)formatIter.next();
                if (dateParser == null) {
                    dateParser = new SimpleDateFormat(format, Locale.US);
                    dateParser.setTimeZone(TimeZone.getTimeZone("GMT"));
                    dateParser.set2DigitYearStart(startDate);
                } else {
                    dateParser.applyPattern(format);
                }

                try {
                    return dateParser.parse(dateValue);
                } catch (ParseException var7) {
                }
            }

            throw new DateParseException("Unable to parse the date " + dateValue);
        }
    }

    /**
     * @Author Don
     * @Description :  按照指定格式日期来进行转换
     * @Date 2020/7/17 11:18 
     * @Parameter : [date, formatType]
     * @Return  java.lang.String
     **/
    public static final String formatDate(Object date,String formatType){
        if (null == date){
            return null;
        }else{
            if (StringUtils.isNotEmpty(formatType)){
                //说明需要根据客户所定义的格式来转换
                SimpleDateFormat format = new SimpleDateFormat(formatType);
                return format.format(date);
            }else{
                //说明没有指定格式
                return formatDate(date);
            }
        }
    }


    /**
     * @Author Don
     * @Description :  将时间转换为字符串
     * @Date 2020/7/17 11:19 
     * @Parameter : [millisecond]
     * @Return  java.lang.String
     **/
    public static String formatDateAgo(long millisecond){
        StringBuilder stringBuilder = new StringBuilder();
        //判断传进来的时间大小
        if (1000 > millisecond){
            stringBuilder.append(millisecond).append("秒");
        }else{
            //说明传进来的秒数大于1000
            Integer ss = 1000;
            Integer mi = ss * 60;
            Integer hh = mi * 60;
            Integer dd = hh * 24;

            Long day = millisecond / dd;
            Long hour = (millisecond - day * dd) / hh;
            Long minute = (millisecond - day * dd - hour * hh) / mi;
            Long second = (millisecond - day * dd - hour * hh - minute * mi) / ss;

            if (day > 365){
                return formatDate(new Date(millisecond),"yyyy年MM月dd日 HH时mm分ss秒");
            }
            if (day > 0){
                stringBuilder.append(day).append("日");
            }
            if (hour > 0){
                stringBuilder.append(hour).append("时");
            }
            if (minute > 0){
                stringBuilder.append(minute).append("分");
            }
            if (second > 0){
                stringBuilder.append(second).append("秒");
            }
        }
        return stringBuilder.toString();
    }


    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        } else if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.US);
            formatter.setTimeZone(GMT);
            return formatter.format(date);
        }
    }


    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, 0, 1, 0, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
        GMT = TimeZone.getTimeZone("GMT");
    }



}
