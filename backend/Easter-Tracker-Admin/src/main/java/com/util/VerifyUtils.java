/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: The Utility Code for generating verify code
 */

package com.util;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
//generate verify code and expire date
public class VerifyUtils {
    private static Date expire_time;
    private static String verification_code;
    public static JSONObject generate(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            Random random = new Random();
            int choice = random.nextInt(2);
            if (choice==0){
                int num = random.nextInt(10);
                sb.append(num);

            }
            else{
                int lower = random.nextInt(26);
                sb.append((char)('a'+lower));
            }
        }
        verification_code=sb.toString();
        expire_time=new Date(new Date().getTime()+1000*60*5);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        JSONObject json = new JSONObject();
        json.put("code",verification_code);
        json.put("expire",sdf.format(expire_time));
        return json;
    }
}
