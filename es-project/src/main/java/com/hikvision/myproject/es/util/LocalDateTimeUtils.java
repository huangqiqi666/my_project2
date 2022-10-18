package com.hikvision.myproject.es.util;


import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * LocalDateTime时间工具类
 *
 * @author huangqiqi
 * @return
 * @date 2020/10/16 11:16
 */

@Slf4j
public class LocalDateTimeUtils {
    public static DateFormat normalformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    public static DateFormat yyyyMMddFormat = new SimpleDateFormat("yyyyMMdd");

    public static final String STANDARD_TIME_PATTERN="yyyy-MM-dd HH:mm:ss";
    public static final String ISO8601_TIME_PATTERN="yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String YYYYMMDD_TIME_PATTERN="yyyyMMdd";

    //Date转换为LocalDateTime
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    //LocalDateTime转换为Date
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    //LocalDateTime转换为iso8601
    public static String covertLDTToIso8601(LocalDateTime localDateTime) {
        Date date = LocalDateTimeUtils.convertLDTToDate(localDateTime);
        return iso8601Format.format(date);
    }

    //时间戳->LocalDateTime
    public static LocalDateTime covertTimeStamp2Ldt(long timestamp ){
//        long timestamp = System.currentTimeMillis();
        LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        return localDateTime;
    }

    //LocalDateTime->时间戳
    public static long covertLdt2TimeStamp(LocalDateTime localDateTime ){
        LocalDate localDate = LocalDate.now();
        long timestamp = localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        return timestamp;
    }


    //获取指定日期的毫秒
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    //获取指定日期的秒
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * @Description 将pattern格式的时间字符串->LocalDateTime
     * @param datetime 时间 2022-07-20 12:00:00
     * @param pattern  时间的格式 如yyyy-MM-dd HH:mm:ss
     * @return java.time.LocalDateTime
     * @date   2022/7/20 17:36
     * @Author huangqiqi
     */
    public static LocalDateTime parseDateTimeString(String datetime,String pattern) {
        return LocalDateTime.parse(datetime,DateTimeFormatter.ofPattern(pattern));
    }

    //获取指定时间的指定格式
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * @Description 从一个格式的时间——>转为另一个时间格式的时间
     * @param time  时间字符串
     * @param sourcePattern 原来的格式
     * @param desPattern  目标格式
     * @return java.lang.String
     * @date   2022/7/20 17:52
     * @Author huangqiqi
     */
    public static String covertDataPattern2OtherPattern(String time, String sourcePattern,String desPattern) {
        String result=time;
        try {
            LocalDateTime localDateTime = LocalDateTimeUtils.parseDateTimeString(time,sourcePattern);
            result = LocalDateTimeUtils.formatTime(localDateTime, desPattern);
        } catch (Exception e) {
            log.error("时间格式转换失败！time：{}，sourcePattern：{}，desPattern：{}.错误信息：{}",time,sourcePattern,desPattern,e.getMessage());
        }
        return result;
    }

    //获取当前时间的指定格式
    public static String formatNow(String pattern) {
        return formatTime(LocalDateTime.now(), pattern);
    }

    //日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    //日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*
     *
     * @param startTime
     * @param endTime
     * @param field     单位(年月日时分秒)
     * @return
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12 + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    //获取一天的开始时间，2017,7,22 00:00
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    //获取一天的结束时间，2017,7,22 23:59:59.999999999
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }


    public static void main(String[] args) {
        LocalDateTime todayZero = LocalDateTimeUtils.getDayStart(LocalDateTimeUtils.convertDateToLDT(new Date()));
        String todayZero4Iso8601 =covertLDTToIso8601(todayZero);
        System.out.println("转换后的todayZero4Iso8601:" + todayZero4Iso8601);
        String isoDate = iso8601Format.format(new Date());
        System.out.println("转换后的isoDate：" + isoDate);

        //获取前一天的时间
        LocalDateTime todayZero2 = LocalDateTimeUtils.getDayStart(LocalDateTimeUtils.convertDateToLDT(new Date()));
        LocalDateTime threeDaysAgo = todayZero2.minusDays(3);
        System.out.println("三天前的时间:" + threeDaysAgo);

        //format
        String pattern="yyyyMMdd";
        String s = formatNow(pattern);
        System.out.println(s);

    }
}

