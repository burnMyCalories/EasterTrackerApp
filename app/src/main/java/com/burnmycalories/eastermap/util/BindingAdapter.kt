/*
* Copyright (c) 2020.  EasterTracker App
* Group: Burn My Calories
* Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
* Project 2 for COMP90018, 2020 S2
* Time: 2020/10/22 23:24.
* Usage: Binding
*/

package com.burnmycalories.eastermap.util


import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("layoutFullscreen")
fun View.bindLayoutFullscreen(previousFullscreen: Boolean, fullscreen: Boolean) {
    if (previousFullscreen != fullscreen && fullscreen) {
        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}

