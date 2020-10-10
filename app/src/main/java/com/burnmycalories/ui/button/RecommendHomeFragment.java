package com.burnmycalories.ui.button;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burnmycalories.MainActivity;
import com.burnmycalories.R;
import com.burnmycalories.adapter.ArticleAdapter;
import com.burnmycalories.base.BaseFragment;
import com.burnmycalories.model.Article;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendHomeFragment extends BaseFragment {

    private List<Article> articleList=new ArrayList<>();


    public RecommendHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_recommend, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();

        //轮播图
        BGABanner banner=(BGABanner)appCompatActivity.findViewById(R.id.banner_guide_content);
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(getActivity())
                        .load(model)
                        .apply(new RequestOptions().placeholder(R.drawable.mytest).error(R.drawable.mytest).dontAnimate().centerCrop())
                        .into(itemView);
            }
        });




        //todo   轮播页面url与标题传入
        List<String> urls=new ArrayList<String>();
        urls.add("http://www.hinews.cn/pic/003/010/038/00301003864_672af1eb.jpg");
        urls.add("http://photocdn.sohu.com/20130927/Img387363334.jpg");
        urls.add("http://cms-bucket.nosdn.127.net/2018/07/05/ad362b083a3c446895361eb28dee9495.jpeg");



        banner.setData(urls
                ,Arrays.asList("test1","test2","test3"));

        banner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                Toast.makeText(banner.getContext(), position + "clicked", Toast.LENGTH_SHORT).show();
            }
        });



        //文章区
        initArticles();
        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recommend_articles);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        ArticleAdapter adapter=new ArticleAdapter(getActivity().getApplicationContext(),articleList);
        recyclerView.setAdapter(adapter);



    }

    private void initArticles(){
        Article article1 =new Article();
        article1.setImageUrl("http://www.hinews.cn/pic/003/010/038/00301003864_672af1eb.jpg");
        article1.setTitle("test1");
        article1.setRecommendPoint(5);
        articleList.add(article1);

        Article article2 =new Article();
        article2.setImageUrl("http://photocdn.sohu.com/20130927/Img387363334.jpg");
        article2.setTitle("test2");
        article2.setRecommendPoint(5);
        articleList.add(article2);

        Article article3 =new Article();
        article3.setImageUrl("http://cms-bucket.nosdn.127.net/2018/07/05/ad362b083a3c446895361eb28dee9495.jpeg");
        article3.setTitle("test3");
        article3.setRecommendPoint(5);
        articleList.add(article3);


    }
}
