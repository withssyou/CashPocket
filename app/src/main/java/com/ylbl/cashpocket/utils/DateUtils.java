package com.ylbl.cashpocket.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    /**
     *  截取月 日
     * @param date
     * @return
     */
    public static String getMonth(String date){
        return null;
    }

    /**
     * 比较2个日期是否是同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(String date1 , String date2){
        if (date1.substring(0 ,10) .equals(date2.substring(0 ,10))){
            return true;
        }else {
            return false;
        }
    }
    public static String ms2Date(long _ms){
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

    public static String ms2DateOnlyDay(long _ms){
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(date);
    }

}
