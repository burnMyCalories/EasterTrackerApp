package com.util;

public class LoginUtils {
    private static long last_login_time = 0L;
    public static boolean isLogin(){
        long current = System.currentTimeMillis();
        return ((current-last_login_time)/(double)1000/(double)60)<=10;
    }
    public static void operate(){
        last_login_time=System.currentTimeMillis();
    }
    public static void logout(){
        last_login_time=0L;
    }
}
