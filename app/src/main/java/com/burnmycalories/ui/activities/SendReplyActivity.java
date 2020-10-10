package com.burnmycalories.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.burnmycalories.MainActivity;
import com.burnmycalories.R;
import com.burnmycalories.base.BaseActivity;
import com.burnmycalories.base.BaseAppCompatActivity;
import com.burnmycalories.model.Reply;
import com.burnmycalories.ui.player.DanmkuVideoActivity;

import java.io.InputStream;

public class SendReplyActivity extends BaseAppCompatActivity {

    public static final String REPLY_TO_TYPE="reply_to_type";
    public static final String REPLY_TO_ID="reply_to_id";
    public static final String FILE_URL="video_url";


    public static final int REPLY_TO_TYPE_POST=1;
    public static final int REPLY_TO_TYPE_MEDIA=2;




    //从post打开发送
    public static void actionStart(Context context,int replyToType,int replyToId,String url){
        Intent intent=new Intent(context,SendReplyActivity.class);
        intent.putExtra(REPLY_TO_TYPE,replyToType);
        intent.putExtra(REPLY_TO_ID,replyToId);
        intent.putExtra(FILE_URL,url);


        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send_reply,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.send_reply){
            //发帖事件绑定
            Toast.makeText(SendReplyActivity.this,"send",Toast.LENGTH_SHORT).show();

//            Intent intent=new Intent(SendPostActivity.this, BBSFragment.class);

//            MainActivity.bbsActionStart(SendReplyActivity.this);
            Intent intent=getIntent();
            int replyToType= intent.getIntExtra(REPLY_TO_TYPE,0);
            int replyToId=intent.getIntExtra(REPLY_TO_ID,0);
            String videoUrl=intent.getStringExtra(FILE_URL);



            if(replyToType==REPLY_TO_TYPE_POST){
                PostActivity.actionStart(SendReplyActivity.this,replyToId);
                Toast.makeText(SendReplyActivity.this,"Sent to post"+replyToId,Toast.LENGTH_SHORT).show();

            }else if(replyToType==REPLY_TO_TYPE_MEDIA){

                DanmkuVideoActivity.actionStart(SendReplyActivity.this,replyToId,videoUrl);
                Toast.makeText(SendReplyActivity.this,"Sent to Video"+replyToId,Toast.LENGTH_SHORT).show();


            }else {
                Toast.makeText(SendReplyActivity.this,"Sent Failed",Toast.LENGTH_SHORT).show();
            }



        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_reply);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //去掉  toolBar 的lable
        getSupportActionBar().setDisplayShowTitleEnabled(false);




    }
}
