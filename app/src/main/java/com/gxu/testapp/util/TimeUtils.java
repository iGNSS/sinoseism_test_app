package com.gxu.testapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static int getCurrentTimeInSeconds(){
        return Integer.parseInt(String.valueOf(new Date().getTime() / 1000));
    }

    /**
     * 用于解析中科微震时间戳的方法
     * @param timestamp 时间戳
     * @return 格式化后的时间
     */
    public static String formatSinoseismEventTime(String timestamp){
        String time1 = timestamp.substring(0, 10);
        String time2 = timestamp.substring(11, 14);
        String postTimestamp = time1 + time2;
        long finalTimestamp = Long.parseLong(postTimestamp);
        Date date = new Date(finalTimestamp);
        String strDateFormat = "yyyy年MM月dd日 HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strDateFormat);
        return simpleDateFormat.format(date);
    }
}
