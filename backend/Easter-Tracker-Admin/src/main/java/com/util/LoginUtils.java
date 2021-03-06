/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: The Utility Code for login and logout
 */

package com.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginUtils {
    private static long last_login_time = 0L;
    private static Map<String,Long> map = new HashMap<>();
    //check whether user has logged in
    public static boolean isLogin(String username){
        long current = System.currentTimeMillis();
        long last_login_time = 0L;
        if(map.containsKey(username)){
            last_login_time=map.get(username);
        }
        return ((current-last_login_time)/(double)1000/(double)60)<=10;


    }
    //update user operations
    //if no operations during 10 minutes, log out automatically
    public static void operate(String username){
        if(username!=null){
            map.put(username,System.currentTimeMillis());
        }

    }
    public static void logout(String username){
        if(username!=null){
            map.put(username,0L);
        }

    }
}
