package com.haven.hckp.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by haven on 15/5/4.
 */
public class DateUtils {

    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    public static String getDateToString(String time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = null;
        if (time.equals("")) {
            return "";
        }
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long loc_time = Long.valueOf(time);
        re_StrTime = sdf.format(new Date(loc_time * 1000L));
        return re_StrTime;
    }


    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
}
