/*
* Copyright (c) 2020.  EasterTracker App
* Group: Burn My Calories
* Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
* Project 2 for COMP90018, 2020 S2
* Time: 2020/10/22 23:24.
* Usage: implementations of operations on egg
*/

package com.burnmycalories.eastermap.activity

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.burnmycalories.eastermap.R



class MainActivity : AppCompatActivity()
{



    val url = "file:////android_asset/index.html"
    private var uploadMessage: ValueCallback<Uri>? = null
    private var uploadMessageAboveL: ValueCallback<Array<Uri>>? = null
    lateinit var webView:WebView
    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        val wview: WebView = findViewById<View>(R.id.webView) as WebView
        webView=wview
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setGeolocationEnabled(true)
        webView.settings.databaseEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.allowUniversalAccessFromFileURLs = true
        webView.settings.domStorageEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.builtInZoomControls = true
        webView.settings.setGeolocationDatabasePath(applicationContext.filesDir.toString() )
        webView.settings.setPluginState(WebSettings.PluginState.ON);
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }
            // For Android < 3.0
            fun openFileChooser(valueCallback: ValueCallback<Uri>) {
                uploadMessage = valueCallback
                openImageChooserActivity()
            }

            // For Android  >= 3.0
            fun openFileChooser(valueCallback: ValueCallback<Uri>, acceptType: String) {
                uploadMessage = valueCallback
                openImageChooserActivity()
            }

            //For Android  >= 4.1
            fun openFileChooser(
                    valueCallback: ValueCallback<Uri>,
                    acceptType: String,
                    capture: String
            ) {
                uploadMessage = valueCallback
                openImageChooserActivity()
            }

            // For Android >= 5.0
            override fun onShowFileChooser(
                    webView: WebView,
                    filePathCallback: ValueCallback<Array<Uri>>,
                    fileChooserParams: WebChromeClient.FileChooserParams
            ): Boolean {
                uploadMessageAboveL = filePathCallback
                openImageChooserActivity()
                return true
            }

        }


        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                println("Need permission")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.BLUETOOTH), 1)
            } else {
                println("Success")
                startWebView(webView)
            }
        }
        else{
            startWebView(webView)
        }

    }

    fun startWebView(wview: WebView){
        wview.loadUrl(url)
        wview.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                println("Webview Loaded")

            }
        })


    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {

                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                                grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    startWebView(webView)

                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    System.exit(0)
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }




    private fun openImageChooserActivity() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "image/*"
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return
            val result = if (data == null || resultCode != Activity.RESULT_OK) null else data.data
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data)
            } else if (uploadMessage != null) {
                uploadMessage!!.onReceiveValue(result)
                uploadMessage = null
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onActivityResultAboveL(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return
        var results: Array<Uri>? = null
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                val dataString = intent.dataString
                val clipData = intent.clipData
                if (clipData != null) {
                    results = Array(clipData.itemCount){
                        i -> clipData.getItemAt(i).uri
                    }
                }
                if (dataString != null)
                    results = arrayOf(Uri.parse(dataString))
            }
        }
        uploadMessageAboveL!!.onReceiveValue(results)
        uploadMessageAboveL = null
    }

    companion object {
        private val FILE_CHOOSER_RESULT_CODE = 10000
    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        return if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            true
        }
        else{
            super.onKeyDown(keyCode, event)
        }

    }




}
