package com.burnmycalories.ui.player;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.INotificationSideChannel;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.burnmycalories.R;
import com.burnmycalories.adapter.ReplyAdapter;
import com.burnmycalories.base.BaseAppCompatActivity;
import com.burnmycalories.json.Result;
import com.burnmycalories.model.Media;
import com.burnmycalories.model.Reply;
import com.burnmycalories.ui.activities.LoginActivity;
import com.burnmycalories.ui.activities.PostActivity;
import com.burnmycalories.ui.activities.SendReplyActivity;
import com.burnmycalories.util.FileUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import master.flame.danmaku.danmaku.model.Danmaku;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


public class DanmkuVideoActivity extends BaseAppCompatActivity {

    private final int SEND_TOAST=1;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case SEND_TOAST:
                    Toast.makeText(DanmkuVideoActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };


    public static final String REPLY_TO_ID="reply_to_id";
    public static final String FILE_URL="video_url";

    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");



    private String fileName;

    private int mediaId;







//    @BindView(R.id.post_detail_nested_scroll)
//    NestedScrollView postDetailNestedScroll;

    @BindView(R.id.danmaku_player)
    DanmakuVideoPlayer danmakuVideoPlayer;

    @BindView(R.id.activity_detail_player)
    LinearLayout activityDetailPlayer;


    private boolean isPlay;
    private boolean isPause;
    private boolean isDestory;

    private OrientationUtils orientationUtils;


    private List<Reply> replyList=new ArrayList<>();

    public static void actionStart(Context context,int replyToId,String url){
        Intent intent=new Intent(context,DanmkuVideoActivity.class);

        intent.putExtra(REPLY_TO_ID,replyToId);
        intent.putExtra(FILE_URL,url);

        context.startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmaku_layout);
        ButterKnife.bind(this);


        //使用自定义的全屏切换图片，!!!注意xml布局中也需要设置为一样的
        //必须在setUp之前设置
        danmakuVideoPlayer.setShrinkImageRes(R.drawable.custom_shrink);
        danmakuVideoPlayer.setEnlargeImageRes(R.drawable.custom_enlarge);


        Intent intent=getIntent();
        String url5=intent.getStringExtra(FILE_URL);

        fileName=FileUtil.getFileName(url5);

//        mediaId=

//        ((DanmakuVideoPlayer) danmakuVideoPlayer.getCurrentPlayer()).setMediaId(getMediaId(fileName));

        getMediaId(fileName);


        String url4= "file://"+ Environment.getExternalStorageDirectory().getPath()+"/[JYFanSub][Boku Dake ga Inai Machi][01][GB][720P].mp4";
        //String url = "https://res.exexm.com/cw_145225549855002";
        String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        //String url = "https://res.exexm.com/cw_145225549855002";
        danmakuVideoPlayer.setUp(url5, true, null, "测试视频");

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.xxx1);
        danmakuVideoPlayer.setThumbImageView(imageView);

        resolveNormalVideoUI();

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, danmakuVideoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        danmakuVideoPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        danmakuVideoPlayer.setRotateViewAuto(false);
        danmakuVideoPlayer.setLockLand(false);
        danmakuVideoPlayer.setShowFullAnimation(false);
        danmakuVideoPlayer.setNeedLockFull(true);

        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.send_reply);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendReplyActivity.actionStart(DanmkuVideoActivity.this,SendReplyActivity.REPLY_TO_TYPE_MEDIA,1,url5);
            }
        });

        //detailPlayer.setOpenPreView(true);
        danmakuVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                danmakuVideoPlayer.startWindowFullscreen(DanmkuVideoActivity.this, true, true);
            }
        });

        danmakuVideoPlayer.setVideoAllCallBack(new GSYSampleCallBack() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
                getDanmu();
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });

        danmakuVideoPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });




        //回复区
        initMediaReplys();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.reply_media);
        LinearLayoutManager layoutManager =new LinearLayoutManager(DanmkuVideoActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(DanmkuVideoActivity.this,DividerItemDecoration.VERTICAL));
        ReplyAdapter adapter=new ReplyAdapter(getApplicationContext(),replyList);
        recyclerView.setAdapter(adapter);

    }


    /**
     * 获取视频ID并设置到当前播放器
     * @param fileName
     * @return
     */
    private void getMediaId(String fileName) {

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder().connectTimeout(50000, TimeUnit.MILLISECONDS)
                .readTimeout(50000,TimeUnit.MILLISECONDS)
                .build();
//        RequestBody body = RequestBody.create(JSON,json);

        HttpUrl url=new HttpUrl.Builder()
                .scheme("http")
                .host(getString(R.string.server_host))
                .port(Integer.parseInt(getString(R.string.server_port)))
                .addPathSegments("/media/getMedia")
                .addPathSegment(fileName)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Response response=null;

                    response=okHttpClient.newCall(request).execute();
                    String responseBody=response.body().string();

                    Gson gson=new Gson();
                    Result<Media> result=gson.fromJson(responseBody, new TypeToken<Result<Media>>() {
                    }.getType());

                    ((DanmakuVideoPlayer) danmakuVideoPlayer.getCurrentPlayer()).setMediaId(result.getData().getId());


                }catch (IOException e){
                    e.printStackTrace();

                    Message message=new Message();
                    message.what=SEND_TOAST;
                    message.obj="获取失败";
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();

                    Message message=new Message();
                    message.what=SEND_TOAST;
                    message.obj="获取失败";
                    handler.sendMessage(message);
                }


            }
        }).start();

    }

    //初始化回复内容
    private void initMediaReplys() {
        Reply reply1=new Reply();
        reply1.setAuthor("yb");
        reply1.setContent("怎么没人撑腰，有鹰酱这个最大的恐怖主义，他们那还叫事？");
        reply1.setHeadImageUrl("http://img0.imgtn.bdimg.com/it/u=3906463260,2316822376&fm=11&gp=0.jpg");
        replyList.add(reply1);

        replyList.add(reply1);
        replyList.add(reply1);
        replyList.add(reply1);
        replyList.add(reply1);
        replyList.add(reply1);
        replyList.add(reply1);
        replyList.add(reply1);
        replyList.add(reply1);
        replyList.add(reply1);
    }

    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume();
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();

        isDestory = true;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            danmakuVideoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }


    /**
     * 网络请求获取弹幕
     */
    private void getDanmu() {
        //todo  下载demo然后设置




//        fileName="测试2";

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder().connectTimeout(50000, TimeUnit.MILLISECONDS)
                .readTimeout(50000,TimeUnit.MILLISECONDS)
                .build();
//        RequestBody body = RequestBody.create(JSON,json);

        HttpUrl url=new HttpUrl.Builder()
                .scheme("http")
                .host(getString(R.string.server_host))
                .port(Integer.parseInt(getString(R.string.server_port)))
                .addPathSegments("/danmakus/mediaName")
                .addPathSegment(fileName)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Response response=null;

                    response=okHttpClient.newCall(request).execute();
                    String responseBody=response.body().string();

                    Gson gson=new Gson();
                    Result<List<Danmaku>> result=gson.fromJson(responseBody, new TypeToken<Result<List<Danmaku>>>() {
                    }.getType());

                    ((DanmakuVideoPlayer) danmakuVideoPlayer.getCurrentPlayer()).setDanmakuJsonString(responseBody);


                    Message message=new Message();
                    message.what=SEND_TOAST;
                    message.obj=result.getMessage();
                    handler.sendMessage(message);


                }catch (IOException e){
                    e.printStackTrace();

                    Message message=new Message();
                    message.what=SEND_TOAST;
                    message.obj="获取失败";
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();

                    Message message=new Message();
                    message.what=SEND_TOAST;
                    message.obj="获取失败";
                    handler.sendMessage(message);
                }


            }
        }).start();

//        OkHttpUtils.get().url(TextUtils.concat("http://xingyuyou.com/Public/app/barragefile/","608","barrage.txt").toString())
//                .build()
//                .execute(new FileCallBack(getApplication().getCacheDir().getAbsolutePath(), "barrage.txt")//
//                {
//
//                    @Override
//                    public void onError(okhttp3.Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(File response, int id) {
//                        if (!isDestory) {
//                            ((DanmakuVideoPlayer) danmakuVideoPlayer.getCurrentPlayer()).setDanmaKuStream(response);
//                        }
//
//                    }
//
//                });
    }

    private void resolveNormalVideoUI() {
        //增加title
        danmakuVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        danmakuVideoPlayer.getBackButton().setVisibility(View.GONE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (danmakuVideoPlayer.getFullWindowPlayer() != null) {
            return  danmakuVideoPlayer.getFullWindowPlayer();
        }
        return danmakuVideoPlayer;
    }

}
