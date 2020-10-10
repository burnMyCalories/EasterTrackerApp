package com.burnmycalories.ui.activities;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.burnmycalories.R;

import com.burnmycalories.adapter.ReplyAdapter;
import com.burnmycalories.base.BaseActivity;
import com.burnmycalories.model.Article;
import com.burnmycalories.model.Post;
import com.burnmycalories.model.Reply;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends BaseActivity {
    public static final String REPLY_TO_TYPE="reply_to_type";
    public static final String REPLY_TO_ID="reply_to_id";


    public static void actionStart(Context context,int replyToId){
        Intent intent=new Intent(context,PostActivity.class);

        intent.putExtra(REPLY_TO_ID,replyToId);

        context.startActivity(intent);

    }

    private List<Reply> replyList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        Intent intent=getIntent();


        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.send_reply);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendReplyActivity.actionStart(PostActivity.this,SendReplyActivity.REPLY_TO_TYPE_POST,1,"");

            }
        });



        //回复区
        initPostReplys();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.reply_post);
        LinearLayoutManager layoutManager =new LinearLayoutManager(PostActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(PostActivity.this,DividerItemDecoration.VERTICAL));
        ReplyAdapter adapter=new ReplyAdapter(getApplicationContext(),replyList);
        recyclerView.setAdapter(adapter);




    }

    private void initPostReplys() {
        Reply reply1=new Reply();
        reply1.setAuthor("author1");
        reply1.setContent("Test Content");
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
}
