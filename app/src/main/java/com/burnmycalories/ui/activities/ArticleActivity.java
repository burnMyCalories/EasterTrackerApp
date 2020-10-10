package com.burnmycalories.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burnmycalories.R;
import com.burnmycalories.base.BaseAppCompatActivity;
import com.burnmycalories.model.Article;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArticleActivity extends BaseAppCompatActivity {

    public static final String ARTICLE_ID_STRING = "article_id";

    public static void actionStart(Context context,int articleId){
        Intent intent=new Intent(context,ArticleActivity.class);
        intent.putExtra(ARTICLE_ID_STRING,articleId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);



        TextView articleContent=(TextView)findViewById(R.id.article_content);
        articleContent.setMovementMethod(ScrollingMovementMethod.getInstance());

        TextView articleTitle=(TextView)findViewById(R.id.article_title);
        TextView author=(TextView)findViewById(R.id.author);
        CircleImageView head=(CircleImageView)findViewById(R.id.circle_image_view_head);


        /**
         * Glide.with(getActivity())
         *                         .load(model)
         *                         .apply(new RequestOptions().placeholder(R.drawable.mytest).error(R.drawable.mytest).dontAnimate().centerCrop())
         *                         .into(itemView);
         */

        //todo  网络处理得到文章具体信息
        Intent intent=getIntent();
        int articleId=intent.getIntExtra(ARTICLE_ID_STRING,-1);
        if(articleId==-1){
            Toast.makeText(ArticleActivity.this,"Article not accessible",Toast.LENGTH_SHORT).show();

        }else{
            //

            articleTitle.setText("ArticleTitle");
            articleContent.setText("Article Content" );

            author.setText("aticleAuthor");


        }

        Glide.with(ArticleActivity.this)
                .load("http://img0.imgtn.bdimg.com/it/u=3906463260,2316822376&fm=11&gp=0.jpg")
                .apply(new RequestOptions().placeholder(R.drawable.mytest).error(R.drawable.mytest).dontAnimate().centerCrop())
                .into(head);



    }

    private void articleInit(){

    }
}
