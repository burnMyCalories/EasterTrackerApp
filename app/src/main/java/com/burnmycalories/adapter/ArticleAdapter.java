package com.burnmycalories.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burnmycalories.MainActivity;
import com.burnmycalories.R;
import com.burnmycalories.model.Article;
import com.burnmycalories.ui.activities.ArticleActivity;
import com.burnmycalories.util.LoginUtil;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    public Context context;
    private List<Article> articleList;

    public ArticleAdapter(Context context,List<Article> articles) {
        articleList=articles;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.articleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!LoginUtil.isLogin(parent.getContext())){
                    LoginUtil.login(parent.getContext());
                    return;
                }
                int position =holder.getAdapterPosition();
                Article article=articleList.get(position);

                int articleId=1;


                Toast.makeText(v.getContext(),"article: "+article.getTitle()+" has been clicked",Toast.LENGTH_SHORT).show();

                ArticleActivity.actionStart(v.getContext(),articleId);
            }
        });

        //
        holder.articleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo  点击图片相应示例
                int position =holder.getAdapterPosition();
                Article article=articleList.get(position);
                Toast.makeText(v.getContext(),"Picture: "+article.getTitle()+" has been clicked",Toast.LENGTH_SHORT).show();
            }
        });

        //
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                Article article=articleList.get(position);

                Glide.with(context)
                        .load(article.getImageUrl())
                        .apply(new RequestOptions().placeholder(R.drawable.mytest).error(R.drawable.mytest).dontAnimate().centerCrop().override(450,300))
                        .into(holder.articleImage);

                holder.articleTitle.setText(article.getTitle());
//                holder.recommendPoint.setText(String.valueOf(article.getRecommendPoint()));

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        View articleView;
        ImageView articleImage;
        TextView articleTitle;
//        TextView recommendPoint;

        public ViewHolder(View view){
            super(view);
            articleView=view;
            articleImage=(ImageView)view.findViewById(R.id.article_image);
            articleTitle=(TextView)view.findViewById(R.id.article_title);
//            recommendPoint=(TextView)view.findViewById(R.id.article_recommend_point);
        }
    }




}
