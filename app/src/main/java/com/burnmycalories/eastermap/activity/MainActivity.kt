/*
* Author: Xinnan Shen
* Date: 18-10-2020
* Usage: Main Activity of Application
*/

package com.burnmycalories.eastermap.activity

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
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
    private var asw_pcam_message: String? = null
    private var asw_vcam_message: kotlin.String? = null
    private var asw_file_message: ValueCallback<Uri>? = null
    private var asw_file_path: ValueCallback<Array<Uri>>? = null
    private val asw_file_req = 1

    private val loc_perm = 1
    private val file_perm = 2
    var ASWV_F_TYPE = "*/*"
    var ASWP_MULFILE = true // upload multiple files in webview


    val url = "file:////android_asset/index.html"
    private var uploadMessage: ValueCallback<Uri>? = null
    private var uploadMessageAboveL: ValueCallback<Array<Uri>>? = null
    lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        val wview: WebView
        wview = findViewById<View>(R.id.webView) as WebView
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
            override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }

            // For Android >= 5.0
//            override fun onShowFileChooser(
//                    webView: WebView,
//                    filePathCallback: ValueCallback<Array<Uri>>,
//                    fileChooserParams: WebChromeClient.FileChooserParams
//            ): Boolean {
//                uploadMessageAboveL = filePathCallback
//                openImageChooserActivity()
//                return true
//            }

            override fun onShowFileChooser(
                    webView: WebView,
                    filePathCallback: ValueCallback<Array<Uri>>,
                    fileChooserParams: WebChromeClient.FileChooserParams): Boolean {
                return if (check_permission(2) && check_permission(3)) {
                    if (ASWP_FUPLOAD) {
                        asw_file_path = filePathCallback
                        var takePictureIntent: Intent? = null
                        var takeVideoIntent: Intent? = null
                        if (ASWP_CAMUPLOAD) {
                            var includeVideo = false
                            var includePhoto = false

                            // Check the accept parameter to determine which intent(s) to include.
                            paramCheck@ for (acceptTypes in fileChooserParams.acceptTypes) {
                                // Although it's an array, it still seems to be the whole value.
                                // Split it out into chunks so that we can detect multiple values.
                                val splitTypes = acceptTypes.split(", ?+").toTypedArray()
                                for (acceptType in splitTypes) {
                                    when (acceptType) {
                                        "*/*" -> {
                                            includePhoto = true
                                            includeVideo = true
                                            break@paramCheck
                                        }
                                        "image/jpg" -> includePhoto = true
                                        "video/mp4" -> includeVideo = true
                                    }
                                }
                            }

                            // If no `accept` parameter was specified, allow both photo and video.
                            if (fileChooserParams.acceptTypes.size == 0) {
                                includePhoto = true
                                includeVideo = true
                            }

//                            includePhoto = true
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

                            if (includeVideo) {
                                takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                                if (takeVideoIntent.resolveActivity(this@MainActivity.packageManager) != null) {
                                    var videoFile: File? = null
                                    try {
                                        videoFile = create_video()
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
                        if (takePictureIntent != null && takeVideoIntent != null) {
                            intentArray = arrayOf<Intent?>(takePictureIntent, takeVideoIntent)
                        } else if (takePictureIntent != null) {
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


        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                println("Need geolocation")

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO), 1)
            } else {
                println("Success")
                startWebView(webView)
            }
        }

}

    fun startWebView(wview: WebView) {
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


//    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//        return super.onCreateView(name, context, attrs)
//    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        when (requestCode) {
            1 -> {

                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                                grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    startWebView(webView)

                } else {

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





//--------------------------------------------------------------------------------------------------
    fun check_permission(permission: Int): Boolean {
        when (permission) {

            1 -> return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            2 -> return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            3 -> return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        }
        return false
    }


    private fun openImageChooserActivity() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "image/jpg"
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE)
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

//--------------------------------------------------------------------------------------------------
/*
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
                    results = Array(clipData.itemCount) { i ->
                        clipData.getItemAt(i).uri
                    }
                }
                if (dataString != null)
                    results = arrayOf(Uri.parse(dataString))
            }
        }
        uploadMessageAboveL!!.onReceiveValue(results)
        uploadMessageAboveL = null
    }


 */


    companion object {
        private val FILE_CHOOSER_RESULT_CODE = 10000
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.statusBarColor = resources.getColor(R.color.colorPrimary)
            var results: Array<Uri>? = null
            if (resultCode == RESULT_CANCELED) {
                if (requestCode == asw_file_req) {
                    // If the file request was cancelled (i.e. user exited camera),
                    // we must still send a null value in order to ensure that future attempts
                    // to pick files will still work.
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
                    if (clipData == null && stringData == null && (asw_pcam_message != null || asw_vcam_message != null)) {
                        results = arrayOf(Uri.parse(if (asw_pcam_message != null) asw_pcam_message else asw_vcam_message))
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


}
