package com.burnmycalories.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.burnmycalories.ui.activities.LoginActivity;

import java.util.prefs.Preferences;



public class LoginUtil {

    //--服务器登录响应CODE---------------------------------------------------------
    public static final int LOGIN_SUCESS_CODE=101;

    public static final int LOGIN_FAILD_CODE=102;

    public static final int TOKEN_UNUSEFUL_ID=-1;

    public static final int REGISTER_SUCESS_CODE=103;

    public static final int REGISTER_FAILD_CODE=104;

    public static final int UPDATE_SUCESS_CODE=105;
    //-------------------------------------------------------------


    //*****UserInfo和Type**************************************
    public static final String USER_INFO="user_info";
    public static final String TOKEN="token";
    public static final String NULL_STRING="";


    public static final String HEAD_URL_STRING="heda_url";
    public static final String USERNAME_STRING="user_name";
    //******************************************************





    public static String getUserInfo(Context context,String type){
        SharedPreferences preferences= context.getSharedPreferences(USER_INFO,Context.MODE_PRIVATE);

        String info_msg=preferences.getString(type,NULL_STRING);
        return info_msg;

    }

    public static void userInfoUpdate(Context context,String type,String msg){
        SharedPreferences preferences= context.getSharedPreferences(USER_INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
//        String token="cscsdvsdvvsvsd";
//        String userNmae="袁啸山";
//        String headaurl="http://img1.imgtn.bdimg.com/it/u=1305353222,2352820043&fm=26&gp=0.jpg";
//                editor.putString(TOKEN,token);
        editor.putString(type,msg);

        editor.apply();

    }

    public static void logOut(Context context){
        SharedPreferences preferences= context.getSharedPreferences(USER_INFO,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(TOKEN,NULL_STRING);
        editor.putString(USERNAME_STRING,NULL_STRING);
        editor.putString(HEAD_URL_STRING,NULL_STRING);

        editor.apply();
        Toast.makeText(context,"Successfully Logged out",Toast.LENGTH_SHORT).show();


    }

    public static void login(Context context){
        Toast.makeText(context,"Please Login First",Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(context, LoginActivity.class);
        context.startActivity(intent);


    }



    public static boolean isLogin(Context context){
        SharedPreferences preferences= context.getSharedPreferences(USER_INFO,Context.MODE_PRIVATE);
        if(preferences==null){
            return false;
        }else {
            String token=preferences.getString(TOKEN,NULL_STRING);

            if(token.equals(NULL_STRING)){
                return false;
            }else {
                //todo 网路处理token  有效返回true  无效置空  "" 本地token并返回false
                //暂时由于敏感操作都涉及网络请求，直接由后端控制权限

                return true;
            }
        }

    }
}
