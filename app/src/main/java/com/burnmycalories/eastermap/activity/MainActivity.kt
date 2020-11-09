/*
* Copyright (c) 2020.  EasterTracker App
* Group: Burn My Calories
* Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
* Project 2 for COMP90018, 2020 S2
* Time: 2020/10/22 23:24.
* Usage: Main Activity
*/

package com.burnmycalories.eastermap.activity

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.burnmycalories.eastermap.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    var ASWP_FUPLOAD = true // upload file from webview
    var ASWP_CAMUPLOAD = true // enable upload from camera for photos
    var ASWP_ONLYCAM = false // incase you want only camera files to upload
    private var asw_acam_message: String? = null
    private var asw_pcam_message: String? = null
    private var asw_vcam_message: String? = null
    private var asw_file_message: ValueCallback<Uri>? = null
    private var asw_file_path: ValueCallback<Array<Uri>>? = null
    private val asw_file_req = 1
    private val file_perm = 2
    var ASWV_F_TYPE = "*/*"
    var ASWP_MULFILE = true // upload multiple files in webview


    val url = "file:////android_asset/index.html" //webview index page
    lateinit var webView: WebView // global webview


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //set view format of main activity
        //define webview
        val wview: WebView = findViewById<View>(R.id.webView) as WebView
        webView = wview
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
        webView.settings.setGeolocationDatabasePath(applicationContext.filesDir.toString())
        webView.webChromeClient = object : WebChromeClient() {
            //make sure to use location of mobile phone
            override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }


            //override filechooser function
            //when an image is needed, ask for file or camera
            //when an audio is needed, ask for file or recorder
            //when a video is needed, ask for file or vidicon
            override fun onShowFileChooser(
                    webView: WebView,
                    filePathCallback: ValueCallback<Array<Uri>>,
                    fileChooserParams: WebChromeClient.FileChooserParams): Boolean {
                return if (check_permission(2) && check_permission(3)) {
                    if (ASWP_FUPLOAD) {
                        asw_file_path = filePathCallback
                        var takeAudioIntent: Intent? = null
                        var takePictureIntent: Intent? = null
                        var takeVideoIntent: Intent? = null
                        if (ASWP_CAMUPLOAD) {
                            var includeAudio = false
                            var includeVideo = false
                            var includePhoto = false

                            // Check the accept parameter to determine which intent(s) to include.
                            paramCheck@ for (acceptTypes in fileChooserParams.acceptTypes) {
                                // Although it's an array, it still seems to be the whole value.
                                // Split it out into chunks so that we can detect multiple values.
                                val splitTypes = acceptTypes.split(", ?+").toTypedArray()
                                for (acceptType in splitTypes) {
                                    when (acceptType) {
                                        //every file is accepted
                                        "*/*" -> {
                                            includeAudio = true
                                            includePhoto = true
                                            includeVideo = true
                                            break@paramCheck
                                        }
                                        //audio only
                                        "audio/mp3" -> includeAudio = true
                                        //image only
                                        "image/jpg" -> includePhoto = true
                                        //video only
                                        "video/mp4" -> includeVideo = true
                                    }
                                }
                            }

                            // If no `accept` parameter was specified, allow both photo and video.
                            if (fileChooserParams.acceptTypes.size == 0) {
                                includeAudio = true
                                includePhoto = true
                                includeVideo = true
                            }

                            //audio is accepted
                            if (includeAudio){
                                takeAudioIntent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
                                if (takeAudioIntent.resolveActivity(this@MainActivity.packageManager) != null){
                                    var audioFile :File? = null
                                    try{
                                        audioFile = create_audio()
                                        takeAudioIntent.putExtra("AudioPath", asw_acam_message)
                                    } catch (ex: IOException) {
                                        Log.e(TAG, "Audio file creation failed", ex)
                                    }
                                    if (audioFile != null){
                                        asw_acam_message = "file:" + audioFile.absolutePath
                                        takeAudioIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(audioFile))
                                    } else {
                                        takeAudioIntent = null
                                    }
                                }
                            }


                            //image is accepted
                            if (includePhoto) {
                                takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                if (takePictureIntent.resolveActivity(this@MainActivity.packageManager) != null) {
                                    var photoFile: File? = null
                                    try {
                                        photoFile = create_image()
                                        takePictureIntent.putExtra("PhotoPath", asw_pcam_message)
                                    } catch (ex: IOException) {
                                        Log.e(TAG, "Image file creation failed", ex)
                                    }
                                    if (photoFile != null) {
                                        asw_pcam_message = "file:" + photoFile.absolutePath
                                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
                                    } else {
                                        takePictureIntent = null
                                    }
                                }
                            }

                            //video is included
                            if (includeVideo) {
                                takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                                if (takeVideoIntent.resolveActivity(this@MainActivity.packageManager) != null) {
                                    var videoFile: File? = null
                                    try {
                                        videoFile = create_video()
                                        takeVideoIntent.putExtra("VideoPath", asw_vcam_message)
                                    } catch (ex: IOException) {
                                        Log.e(TAG, "Video file creation failed", ex)
                                    }
                                    if (videoFile != null) {
                                        asw_vcam_message = "file:" + videoFile.absolutePath
                                        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile))
                                    } else {
                                        takeVideoIntent = null
                                    }
                                }
                            }
                        }
                        val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                        if (!ASWP_ONLYCAM) {
                            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                            contentSelectionIntent.type = ASWV_F_TYPE
                            if (ASWP_MULFILE) {
                                contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                            }
                        }
                        val intentArray: Array<Intent?>
                        if (takeAudioIntent != null && takePictureIntent != null && takeVideoIntent != null) {
                            intentArray = arrayOf<Intent?>(takeAudioIntent, takePictureIntent, takeVideoIntent)
                        }else if (takeAudioIntent != null) {
                            intentArray = arrayOf<Intent?>(takeAudioIntent)
                        }else if (takePictureIntent != null) {
                            intentArray = arrayOf<Intent?>(takePictureIntent)
                        } else if (takeVideoIntent != null) {
                            intentArray = arrayOf<Intent?>(takeVideoIntent)
                        } else {
                            intentArray = arrayOfNulls(0)
                        }

                        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                        chooserIntent.putExtra(Intent.EXTRA_TITLE, "File Chooser")
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
                        startActivityForResult(chooserIntent, asw_file_req)
                    }
                    true
                } else {
                    get_file()
                    false
                }
            }
        }

        //ask user for permission
        if (Build.VERSION.SDK_INT >= 21) {
            //if user has not grant permissions
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)!= PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ) {
                //requires permission
                Log.i(TAG,"Need permission")

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.BLUETOOTH, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {//permission already granted
                Log.i(TAG,"Permission Success")
                startWebView(webView)//start webview
            }
        }
        else{//no need ask for permission
            startWebView(webView)
        }

    }

    //start the main page of webview
    fun startWebView(wview: WebView) {
        wview.loadUrl(url)
        wview.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                Log.i(TAG,"MainPage Loaded")
            }
        })


    }




    //dealing with the result of requesting permission from user
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        when (requestCode) {
            //filter by the code with which the app requests permission from user
            1 -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()) {
                    for (result in grantResults) {
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            // Permission Denied
                            permission_denied()
                        }
                    }
                    // Permission is granted.
                    // Start Webview
                    startWebView(webView)

                } else {
                    // Permission Denied
                    permission_denied()
                }
                return
            }

            // no other requests
            else -> {

            }
        }
    }

    fun permission_denied(){
        System.exit(0)
    }

    //check for required permissions when browsing files
    fun check_permission(permission: Int): Boolean {
        when (permission) {

            1 -> return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            2 -> return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            3 -> return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    //create required audio for upload
    @Throws(IOException::class)
    private fun create_audio(): File? {
        @SuppressLint("SimpleDateFormat") val file_name = SimpleDateFormat("yyyy_mm_ss").format(Date())
        val new_name = "file_" + file_name + "_"
        val sd_directory = getExternalFilesDir(Environment.DIRECTORY_ALARMS)
        return File.createTempFile(new_name, ".mp3", sd_directory)
    }

    //Creating image file for upload
    @Throws(IOException::class)
    private fun create_image(): File? {
        @SuppressLint("SimpleDateFormat") val file_name = SimpleDateFormat("yyyy_mm_ss").format(Date())
        val new_name = "file_" + file_name + "_"
        val sd_directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(new_name, ".jpg", sd_directory)
    }

    //Creating video file for upload
    @Throws(IOException::class)
    private fun create_video(): File? {
        @SuppressLint("SimpleDateFormat") val file_name = SimpleDateFormat("yyyy_mm_ss").format(Date())
        val new_name = "file_" + file_name + "_"
        val sd_directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(new_name, ".mp4", sd_directory)
    }

    //get files
    fun get_file() {
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)

        //Checking for storage permission to write images for upload
        if (ASWP_FUPLOAD && ASWP_CAMUPLOAD && !check_permission(2) && !check_permission(3)) {
            ActivityCompat.requestPermissions(this@MainActivity, perms, file_perm)

            //Checking for WRITE_EXTERNAL_STORAGE permission
        } else if (ASWP_FUPLOAD && !check_permission(2)) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), file_perm)

            //Checking for CAMERA permissions
        } else if (ASWP_CAMUPLOAD && !check_permission(3)) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.CAMERA), file_perm)
        }
    }




    //get the result of activities (mainly for browsing files)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            var results: Array<Uri>? = null
            if (resultCode == RESULT_CANCELED) {
                if (requestCode == asw_file_req) {
                    //the action is cancelled
                    asw_file_path!!.onReceiveValue(null)
                    return
                }
            }
            if (resultCode == RESULT_OK) {
                if (requestCode == asw_file_req) {
                    if (null == asw_file_path) {
                        return
                    }
                    var clipData: ClipData?
                    var stringData: String?
                    try {
                        clipData = intent!!.clipData
                        stringData = intent.dataString
                    } catch (e: Exception) {
                        clipData = null
                        stringData = null
                    }
                    if (clipData == null && stringData == null && (asw_acam_message != null ||asw_pcam_message != null || asw_vcam_message != null)) {
                        results = arrayOf(Uri.parse(if (asw_pcam_message != null) asw_pcam_message else if (asw_vcam_message !=null) asw_vcam_message else asw_acam_message))
                    } else {
                        if (null != clipData) { // checking if multiple files selected or not
                            results = Array(clipData.itemCount) { i ->
                                clipData.getItemAt(i).uri
                            }
                            // checking if multiple files selected or not
                            for (i in 0 until clipData.itemCount) {
                                results[i] = clipData.getItemAt(i).uri
                            }
                        } else {
                            results = arrayOf(Uri.parse(stringData))
                        }
                    }
                }
            }
            asw_file_path!!.onReceiveValue(results)
            asw_file_path = null
        } else {
            if (requestCode == asw_file_req) {
                if (null == asw_file_message) return
                val result = if (intent == null || resultCode != RESULT_OK) null else intent.data
                asw_file_message!!.onReceiveValue(result)
                asw_file_message = null
            }
        }
    }


    //if the system key of default system is pressed, override actions
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        //mainly override back key, to avoid quitting the app
        return if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            true
        }
        else{
            //if not the key, perform normal actions
            super.onKeyDown(keyCode, event)
        }

    }


}

