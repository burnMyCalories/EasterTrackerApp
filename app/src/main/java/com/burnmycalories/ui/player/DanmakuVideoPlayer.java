package com.burnmycalories.ui.player;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.burnmycalories.MainApplication;
import com.burnmycalories.R;
import com.burnmycalories.adapter.DanamakuAdapter;
import com.burnmycalories.json.Result;
import com.burnmycalories.model.Danmaku;
import com.burnmycalories.model.Media;
import com.burnmycalories.ui.activities.LoginActivity;
import com.burnmycalories.util.BiliDanmukuParser;
import com.burnmycalories.util.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.ui.widget.DanmakuView;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DanmakuVideoPlayer extends StandardGSYVideoPlayer {

    private final int SEND_TOAST=1;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case SEND_TOAST:
                    Toast.makeText(MainApplication.getCustomUtilApplicationContext(),msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    private BaseDanmakuParser mParser;//解析器对象
    private IDanmakuView mDanmakuView;//弹幕view
    private DanmakuContext mDanmakuContext;

    private TextView mSendDanmaku, mToogleDanmaku;

    private EditText danmakuContent;

    private long mDanmakuStartSeekPosition = -1;

    private boolean mDanmaKuShow = true;

//    private File mDumakuFile;

    private String jsonString;

    private int mediaId;

    public DanmakuVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public DanmakuVideoPlayer(Context context) {
        super(context);
    }

    public DanmakuVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.danmaku_layout;
    }


    @Override
    protected void init(Context context) {
        super.init(context);
        mDanmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
        mSendDanmaku = (TextView) findViewById(R.id.send_danmaku);
        mToogleDanmaku = (TextView) findViewById(R.id.toogle_danmaku);
        danmakuContent=(EditText)findViewById(R.id.danmaku_content);


        //初始化弹幕显示
        initDanmaku();

        mSendDanmaku.setOnClickListener(this);
        mToogleDanmaku.setOnClickListener(this);


    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        onPrepareDanmaku(this);
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        danmakuOnPause();
    }

    @Override
    public void onVideoResume(boolean isResume) {
        super.onVideoResume(isResume);
        danmakuOnResume();
    }

    @Override
    protected void clickStartIcon() {
        super.clickStartIcon();
        if (mCurrentState == CURRENT_STATE_PLAYING) {
            danmakuOnResume();
        } else if (mCurrentState == CURRENT_STATE_PAUSE) {
            danmakuOnPause();
        }
    }

    @Override
    public void onCompletion() {
        releaseDanmaku(this);
    }


    @Override
    public void onSeekComplete() {
        super.onSeekComplete();
        int time = mProgressBar.getProgress() * getDuration() / 100;
        //如果已经初始化过的，直接seek到对于位置
        if (mHadPlay && getDanmakuView() != null && getDanmakuView().isPrepared()) {
            resolveDanmakuSeek(this, time);
        } else if (mHadPlay && getDanmakuView() != null && !getDanmakuView().isPrepared()) {
            //如果没有初始化过的，记录位置等待
            setDanmakuStartSeekPosition(time);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.send_danmaku:
                String text= danmakuContent.getText().toString();
                danmakuContent.setText("");
                addDanmaku(true,text);
                break;
            case R.id.toogle_danmaku:
                mDanmaKuShow = !mDanmaKuShow;
                resolveDanmakuShow();
                break;
        }
    }

    @Override
    protected void cloneParams(GSYBaseVideoPlayer from, GSYBaseVideoPlayer to) {
        ((DanmakuVideoPlayer) to).jsonString = ((DanmakuVideoPlayer) from).jsonString;

        ((DanmakuVideoPlayer) to).mediaId = ((DanmakuVideoPlayer) from).mediaId;


//        ((DanmakuVideoPlayer) to).mDumakuFile = ((DanmakuVideoPlayer) from).mDumakuFile;
        super.cloneParams(from, to);
    }

    /**
     处理播放器在全屏切换时，弹幕显示的逻辑
     需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
     */
    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        if (gsyBaseVideoPlayer != null) {
            DanmakuVideoPlayer gsyVideoPlayer = (DanmakuVideoPlayer) gsyBaseVideoPlayer;
            //对弹幕设置偏移记录
            gsyVideoPlayer.setDanmakuStartSeekPosition(getCurrentPositionWhenPlaying());
            gsyVideoPlayer.setDanmaKuShow(getDanmaKuShow());
            onPrepareDanmaku(gsyVideoPlayer);
        }
        return gsyBaseVideoPlayer;
    }

    /**
     处理播放器在退出全屏时，弹幕显示的逻辑
     需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
     */
    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
        if (gsyVideoPlayer != null) {
            DanmakuVideoPlayer gsyDanmaVideoPlayer = (DanmakuVideoPlayer) gsyVideoPlayer;
            setDanmaKuShow(gsyDanmaVideoPlayer.getDanmaKuShow());
            if (gsyDanmaVideoPlayer.getDanmakuView() != null &&
                    gsyDanmaVideoPlayer.getDanmakuView().isPrepared()) {
                resolveDanmakuSeek(this, gsyDanmaVideoPlayer.getCurrentPositionWhenPlaying());
                resolveDanmakuShow();
                releaseDanmaku(gsyDanmaVideoPlayer);
            }
        }
    }

    protected void danmakuOnPause() {
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    protected void danmakuOnResume() {
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    public void setDanmakuJsonString(String danmakuJsonString){
        jsonString=danmakuJsonString;

//        mDumakuFile = is;
        if (!getDanmakuView().isPrepared()) {
            onPrepareDanmaku((DanmakuVideoPlayer) getCurrentPlayer());
        }
    }

    public void setMediaId(int mediaId1){
        mediaId=mediaId1;
    }



//        public void setDanmaKuStream(/**File is**/List<Danmaku> danmakuList) {
//        danmakuList=danmakus;
//
////        mDumakuFile = is;
//        if (!getDanmakuView().isPrepared()) {
//            onPrepareDanmaku((DanmakuVideoPlayer) getCurrentPlayer());
//        }
//    }


    private void initDanmaku() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        DanamakuAdapter danamakuAdapter = new DanamakuAdapter(mDanmakuView);
        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
                .setCacheStuffer(new SpannedCacheStuffer(), danamakuAdapter) // 图文混排使用SpannedCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);
        if (mDanmakuView != null) {
            if (jsonString != null) {

//                mParser = createParser(getIsStream(mDumakuFile));
                mParser=createParser(jsonString);


            }

            //todo 这是为了demo效果，实际上需要去掉这个，外部传输文件进来
//            mParser = createParser(this.getResources().openRawResource(R.raw.comments));

            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                }

                @Override
                public void prepared() {
                    if (getDanmakuView() != null) {
                        getDanmakuView().start();
                        if (getDanmakuStartSeekPosition() != -1) {
                            resolveDanmakuSeek(DanmakuVideoPlayer.this, getDanmakuStartSeekPosition());
                            setDanmakuStartSeekPosition(-1);
                        }
                        resolveDanmakuShow();
                    }
                }
            });
            mDanmakuView.enableDanmakuDrawingCache(true);
        }
    }

    private InputStream getIsStream(File file) {
        try {
            InputStream instream = new FileInputStream(file);
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            StringBuilder sb1 = new StringBuilder();
            sb1.append("<i>");
            //分行读取
            while ((line = buffreader.readLine()) != null) {
                sb1.append(line);
            }
            sb1.append("</i>");
            Log.e("3333333", sb1.toString());
            instream.close();
            return new ByteArrayInputStream(sb1.toString().getBytes());
        } catch (java.io.FileNotFoundException e) {
            Log.d("TestFile", "The File doesn't not exist.");
        } catch (IOException e) {
            Log.d("TestFile", e.getMessage());
        }
        return null;
    }

    /**
     弹幕的显示与关闭
     */
    private void resolveDanmakuShow() {
        post(new Runnable() {
            @Override
            public void run() {
                if (mDanmaKuShow) {
                    if (!getDanmakuView().isShown())
                        getDanmakuView().show();
                    mToogleDanmaku.setText("弹幕关");
                } else {
                    if (getDanmakuView().isShown()) {
                        getDanmakuView().hide();
                    }
                    mToogleDanmaku.setText("弹幕开");
                }
            }
        });
    }

    /**
     开始播放弹幕
     */
    private void onPrepareDanmaku(DanmakuVideoPlayer gsyVideoPlayer) {
        if (gsyVideoPlayer.getDanmakuView() != null && !gsyVideoPlayer.getDanmakuView().isPrepared() && gsyVideoPlayer.getParser() != null) {
            gsyVideoPlayer.getDanmakuView().prepare(gsyVideoPlayer.getParser(),//todo: 弹幕流最终在这里
                    gsyVideoPlayer.getDanmakuContext());
        }
    }

    /**
     弹幕偏移
     */
    private void resolveDanmakuSeek(DanmakuVideoPlayer gsyVideoPlayer, long time) {
        if (mHadPlay && gsyVideoPlayer.getDanmakuView() != null && gsyVideoPlayer.getDanmakuView().isPrepared()) {
            gsyVideoPlayer.getDanmakuView().seekTo(time);
        }
    }

    /**
     创建解析器对象，解析输入流
     @param stream
     @return
     */
    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            //todo:输入流请求json
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        //todo:改变解析方式为json
        BaseDanmakuParser parser = new BiliDanmukuParser();

        IDataSource<?> dataSource = loader.getDataSource();

        parser.load(dataSource);
        return parser;

    }

    /**
     创建解析器对象，解析输入流
     @param jsonString
     @return
     */
    private BaseDanmakuParser createParser(String jsonString) {

        if (jsonString == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

//        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);
//
//        try {
//            //todo:输入流请求json
//            loader.load(stream);
//        } catch (IllegalDataException e) {
//            e.printStackTrace();
//        }
        //todo:改变解析方式为json
        BaseDanmakuParser parser = new BiliDanmukuParser();

        IDataSource<?> dataSource = new JsonStringSource(jsonString);

        parser.load(dataSource);
        return parser;

    }

    /**
     释放弹幕控件
     */
    private void releaseDanmaku(DanmakuVideoPlayer danmakuVideoPlayer) {
        if (danmakuVideoPlayer != null && danmakuVideoPlayer.getDanmakuView() != null) {
            Debuger.printfError("release Danmaku!");
            danmakuVideoPlayer.getDanmakuView().release();
        }
    }

    public BaseDanmakuParser getParser() {
        if (mParser == null) {
            if (jsonString != null) {
//                mParser = createParser(getIsStream(mDumakuFile));
                mParser = createParser(jsonString);

            }
        }
        return mParser;
    }

    public DanmakuContext getDanmakuContext() {
        return mDanmakuContext;
    }

    public IDanmakuView getDanmakuView() {
        return mDanmakuView;
    }

    public long getDanmakuStartSeekPosition() {
        return mDanmakuStartSeekPosition;
    }

    public void setDanmakuStartSeekPosition(long danmakuStartSeekPosition) {
        this.mDanmakuStartSeekPosition = danmakuStartSeekPosition;
    }

    public void setDanmaKuShow(boolean danmaKuShow) {
        mDanmaKuShow = danmaKuShow;
    }

    public boolean getDanmaKuShow() {
        return mDanmaKuShow;
    }

    /**
     模拟添加弹幕数据
     */
    private void addDanmaku(boolean islive,String danmakuText) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
//        danmaku.text = "这是一条弹幕 " + getCurrentPositionWhenPlaying();
        danmaku.text = danmakuText;
        danmaku.padding = 5;
        danmaku.priority = 8;  // 可能会被各种过滤器过滤并隐藏显示，所以提高等级
        danmaku.isLive = islive;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 500);
        danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
        danmaku.textColor = Color.RED;
        danmaku.textShadowColor = Color.WHITE;
        danmaku.borderColor = Color.GREEN;


        /**
         * 弹幕上传发送
         */
        Danmaku danmakuEntity=new Danmaku();

        danmakuEntity.setContent(danmakuText);
        danmakuEntity.setProcess((int)mDanmakuView.getCurrentTime() + 500);
        danmakuEntity.setColor(0x00ffffff&Color.RED);
        danmakuEntity.setType(1);
        danmakuEntity.setTextSize(25);
        danmakuEntity.setPool(1);
        danmakuEntity.setMediaId(mediaId);

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder().connectTimeout(50000, TimeUnit.MILLISECONDS)
                .readTimeout(50000,TimeUnit.MILLISECONDS)
                .build();



        Gson gson=new Gson();

        String requestJson=gson.toJson(danmakuEntity,new TypeToken<Danmaku>() {
        }.getType());

        MediaType JSON=MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON,requestJson);

        //一个工具上下文
        Context context= MainApplication.getCustomUtilApplicationContext();

        HttpUrl url=new HttpUrl.Builder()
                .scheme("http")
                .host(context.getString(R.string.server_host))
                .port(Integer.parseInt(context.getString(R.string.server_port)))
                .addPathSegments("danmakus/send")
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .addHeader("User-Token", LoginUtil.getUserInfo(context,LoginUtil.TOKEN))
                .url(url)
                .post(body)
                .build();


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Response response=null;

                    response=okHttpClient.newCall(request).execute();
                    String responseBody=response.body().string();

                    Result<String> result=gson.fromJson(responseBody, new TypeToken<Result<String>>() {
                    }.getType());

                    Message message=new Message();
                    message.what=SEND_TOAST;
                    message.obj=result.getMessage();
                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();

                    Message message=new Message();
                    message.what=SEND_TOAST;
                    message.obj="发送失败";
                    handler.sendMessage(message);
                }


            }
        }).start();



//        danmakuEntity.setSendId();   todo  sendId在服务器解析token后设置



//        OkHttpClient okHttpClient;


        mDanmakuView.addDanmaku(danmaku);

    }
}
