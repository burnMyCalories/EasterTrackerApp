package com.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginUtils {
    private static long last_login_time = 0L;
    private static Map<String,Long> map = new HashMap<>();
    public static boolean isLogin(String username){
        long current = System.currentTimeMillis();
        long last_login_time = 0L;
        if(map.containsKey(username)){
            last_login_time=map.get(username);
        }
        return ((current-last_login_time)/(double)1000/(double)60)<=10;
//        if(username==null){
//            return false;
//        }
//        else{
//            JSONObject res = CRUDUtils.queryUser(null, username, null, null, null, null, null, null);
//            if((int)res.get("rows")!=1){
//                return false;
//            }
//            else{
//                List<JSONObject> data = (List<JSONObject>) res.get("data");
//                JSONObject temp = data.get(0);
//                return (int)temp.get("is_online")==1;
//            }
//        }

    }
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
