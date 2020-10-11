package com.burnmycalories.ui.button;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.burnmycalories.R;
//import com.burnmycalories.adapter.MediaFileAdapter;
import com.burnmycalories.base.BaseFragment;
import com.burnmycalories.model.MediaFile;
//import com.burnmycalories.ui.player.DanmkuVideoActivity;
import com.burnmycalories.util.LoginUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends BaseFragment {

    List<MediaFile> mediaFileList= new ArrayList<>();


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_local, container, false);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if(LoginUtil.isLogin(getActivity())){
////                    startActivityForResult(intent, 1);
//        }else {
//            LoginUtil.login(getActivity());
//        }
//        ImageView seclectFile=(ImageView)getActivity().findViewById(R.id.elect_media_file);
//        seclectFile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                //intent.setType(“image/*”);//选择图片
//                //intent.setType(“audio/*”); //选择音频
//                intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
//                //intent.setType(“video/*;image/*”);//同时选择视频和图片
//                //intent.setType("*/*");//无类型限制
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//                if(LoginUtil.isLogin(getActivity())){
//                    startActivityForResult(intent, 1);
//                }else {
//                    LoginUtil.login(getActivity());
//                }
//            }
//        });
//
//       //最近播放区
//        initMediaFiles();
//        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recent_play);
//        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
//        MediaFileAdapter adapter=new MediaFileAdapter(getActivity().getApplicationContext(),mediaFileList);
//        recyclerView.setAdapter(adapter);
    }

    private void initMediaFiles(){
//        MediaFile mediaFile1=new MediaFile();
//        mediaFile1.setMediaFileName("Test media");
//
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);
//        mediaFileList.add(mediaFile1);




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        String path;
//
//        if (resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getData();
//            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
//                path = uri.getPath();
//                startVideoByElect(path);
//
//                return;
//            }
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
//                path = getPath(getActivity(), uri);
//                startVideoByElect(path);
//
//            } else {//4.4以下下系统调用方法
//                path = getRealPathFromURI(uri);
//                startVideoByElect(path);
//
//            }
//        }
    }

//    /**
//     * 将选择路径传入到视频
//     * @param path
//     */
//    private void startVideoByElect(String path)  {
//        String urlPath="file://"+path;
//        Intent intent=new Intent(getActivity(), DanmkuVideoActivity.class);
//        intent.putExtra("video_url",urlPath);
//        startActivity(intent);
//    }
//
//    public String getRealPathFromURI(Uri contentUri) {
//        String res = null;
//        String[] proj = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
//        if(null!=cursor&&cursor.moveToFirst()){;
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            res = cursor.getString(column_index);
//            cursor.close();
//        }
//        return res;
//    }
//
//    /**
//     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
//     */
//    @SuppressLint("NewApi")
//    public String getPath(final Context context, final Uri uri) {
//
//        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//
//        // DocumentProvider
//        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
//            // ExternalStorageProvider
//            if (isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                if ("primary".equalsIgnoreCase(type)) {
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }
//            }
//            // DownloadsProvider
//            else if (isDownloadsDocument(uri)) {
//
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//
//                return getDataColumn(context, contentUri, null, null);
//            }
//            // MediaProvider
//            else if (isMediaDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                Uri contentUri = null;
//                if ("image".equals(type)) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//
//                final String selection = "_id=?";
//                final String[] selectionArgs = new String[]{split[1]};
//
//                return getDataColumn(context, contentUri, selection, selectionArgs);
//            }
//        }
//        // MediaStore (and general)
//        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            return getDataColumn(context, uri, null, null);
//        }
//        // File
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;
//    }
//
//    /**
//     * Get the value of the data column for this Uri. This is useful for
//     * MediaStore Uris, and other file-based ContentProviders.
//     *
//     * @param context       The context.
//     * @param uri           The Uri to query.
//     * @param selection     (Optional) Filter used in the query.
//     * @param selectionArgs (Optional) Selection arguments used in the query.
//     * @return The value of the _data column, which is typically a file path.
//     */
//    public String getDataColumn(Context context, Uri uri, String selection,
//                                String[] selectionArgs) {
//
//        Cursor cursor = null;
//        final String column = "_data";
//        final String[] projection = {column};
//
//        try {
//            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
//                    null);
//            if (cursor != null && cursor.moveToFirst()) {
//                final int column_index = cursor.getColumnIndexOrThrow(column);
//                return cursor.getString(column_index);
//            }
//        } finally {
//            if (cursor != null)
//                cursor.close();
//        }
//        return null;
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is ExternalStorageProvider.
//     */
//    public boolean isExternalStorageDocument(Uri uri) {
//        return "com.android.externalstorage.documents".equals(uri.getAuthority());
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is DownloadsProvider.
//     */
//    public boolean isDownloadsDocument(Uri uri) {
//        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is MediaProvider.
//     */
//    public boolean isMediaDocument(Uri uri) {
//        return "com.android.providers.media.documents".equals(uri.getAuthority());
//    }
}
