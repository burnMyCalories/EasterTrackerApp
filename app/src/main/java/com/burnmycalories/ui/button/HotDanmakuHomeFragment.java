package com.burnmycalories.ui.button;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.burnmycalories.R;
import com.burnmycalories.adapter.MediaAdapter;
import com.burnmycalories.adapter.PostAdapter;
import com.burnmycalories.base.BaseFragment;
import com.burnmycalories.json.Result;
import com.burnmycalories.model.Media;
import com.burnmycalories.model.StreamMedia;
import com.burnmycalories.ui.player.DanmakuVideoPlayer;
import com.burnmycalories.ui.player.DanmkuVideoActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotDanmakuHomeFragment extends BaseFragment {

    private final int SEND_TOAST=1;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case SEND_TOAST:
                    Toast.makeText(getActivity(),msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };


    private List<StreamMedia> streamMediaList =new ArrayList<>();


    public HotDanmakuHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_hot_media, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();

        //影视作品UI处理
        initStreamMedias();
        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.hot_medias);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        MediaAdapter adapter=new MediaAdapter(getActivity().getApplicationContext(),streamMediaList);
        recyclerView.setAdapter(adapter);

    }

    private void initStreamMedias(){


        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder().connectTimeout(50000, TimeUnit.MILLISECONDS)
                .readTimeout(50000,TimeUnit.MILLISECONDS)
                .build();
//        RequestBody body = RequestBody.create(JSON,json);

        HttpUrl url=new HttpUrl.Builder()
                .scheme("http")
                .host(getString(R.string.server_host))
                .port(Integer.parseInt(getString(R.string.server_port)))
                .addPathSegments("danmakus/getHotDanmakus")
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
                    Result<List<Media>> result=gson.fromJson(responseBody, new TypeToken<Result<List<Media>>>() {
                    }.getType());


                    List<Media> mediaList1=result.getData();


                    streamMediaList=new ArrayList<>();

                    int baseHotPoint=2314;
                    for(Media media:mediaList1){
                        StreamMedia streamMedia1=new StreamMedia();

                        streamMedia1.setMediaName(media.getName());
                        streamMedia1.setMediaHotPoint(media.getHotPoint()*baseHotPoint);
                        streamMediaList.add(streamMedia1);
                    }

                    Message message=new Message();
                    message.what=SEND_TOAST;
                    message.obj=result.getMessage();
                    handler.sendMessage(message);

                }catch (IOException e){
                    e.printStackTrace();

                    Message message=new Message();
                    message.what=SEND_TOAST;
                    message.obj="Access Failed";
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();

                    Message message=new Message();
                    message.what=SEND_TOAST;
                    message.obj="Access Failed";
                    handler.sendMessage(message);
                }


            }
        }).start();



    }
}
