/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: The Utility Code for accessing server timezone
 */

package com.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
//get server's timezone
public class TimeZoneUtils {
    public static String getTimeZone(){
        return DateFormatUtils.format(new Date(), "z")+DateFormatUtils.format(new Date(), "Z");
    }
}
