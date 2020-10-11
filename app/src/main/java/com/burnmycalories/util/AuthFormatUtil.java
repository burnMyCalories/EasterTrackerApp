package com.burnmycalories.util;

import android.content.Context;
import android.widget.Toast;

public class AuthFormatUtil {

    public static final String USER_NAME_REGEX="^[^0-9][\\w_]{5,9}$";
    public static final String PASSWORD_REGEX="^[\\w_]{6,20}$";
    public static final String EMAIL_ADRESS_REGEX="[a-zA-Z0-9_]+@[0-9a-z]+(\\.[a-z]+)+";



    public static boolean checkUserName(Context context,String userName){

        if(userName.matches(USER_NAME_REGEX)){
            return true;
        }else {
            Toast.makeText(context,"Username not valid",Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public static boolean checkPassword(Context context,String password){

        if(password.matches(PASSWORD_REGEX)){
            return true;
        }else {
            Toast.makeText(context,"Password not valid",Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public static boolean checkEmailAdress(Context context,String emailAdress){

        if(emailAdress.matches(EMAIL_ADRESS_REGEX)){
            return true;
        }else {
            Toast.makeText(context,"Email not valid",Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}
