package com.example.backend_badminton.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {
    public static String getCurrentFormattedDate() {
        // 创建一个SimpleDateFormat对象，指定目标格式和时区
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        // 获取当前日期时间
        Date now = new Date();

        // 格式化当前日期时间为字符串并返回
        return dateFormat.format(now);
    }
}
