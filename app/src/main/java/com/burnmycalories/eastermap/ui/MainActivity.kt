/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.burnmycalories.eastermap.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.burnmycalories.eastermap.R

class MainActivity : AppCompatActivity()
{




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        val url = "file:////android_asset/homeMap.html"
        val wview = findViewById<View>(R.id.webView) as WebView
        wview.settings.javaScriptEnabled = true
        wview.settings.javaScriptCanOpenWindowsAutomatically = true
        wview.settings.setGeolocationEnabled(true)
        wview.settings.databaseEnabled = true
        wview.settings.allowFileAccess = true
        wview.settings.allowFileAccessFromFileURLs = true
        wview.settings.allowUniversalAccessFromFileURLs = true
        wview.settings.domStorageEnabled = true
        wview.settings.useWideViewPort = true
        wview.settings.builtInZoomControls = true
        wview.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }
        }

        if (Build.VERSION.SDK_INT >= 23) {
            val checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                println("Need geolocation")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                println("Success")
            }
        }
        wview.loadUrl(url)
        wview.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                println("Test")
//                wview.post {
//                    wview.loadUrl("javascript:initialize()")
//                }
            }
        })

//        wview.post {
//            wview.loadUrl("javascript:clickJS2()")
//        }

    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }














}
