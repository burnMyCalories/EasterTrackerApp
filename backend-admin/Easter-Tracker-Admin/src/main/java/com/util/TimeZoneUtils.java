package com.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class TimeZoneUtils {
    public static String getTimeZone(){
        return DateFormatUtils.format(new Date(), "z")+DateFormatUtils.format(new Date(), "Z");
    }
}
