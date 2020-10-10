package com.burnmycalories.controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.burnmycalories.R;



public class FragmentController {


    //Controller失败,会引起重影，原因待探究
//    public static void showFragment(Fragment fg, FragmentManager fragmentManager,Fragment currentFragment){
//
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        //如果之前没有添加过
//        if(!fg.isAdded()){
//            transaction
//                    .add(R.id.fragment_layout,fg)
//                    .hide(currentFragment);
//        }else{
//            transaction
//                    .hide(currentFragment)
//                    .show(fg);
//        }
//        currentFragment = fg;
//        //transaction.addToBackStack(null);
//        transaction.commit();
//
//    }

}
