package com.burnmycalories.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;


import com.burnmycalories.MainActivity;
import com.burnmycalories.R;
import com.burnmycalories.base.BaseAppCompatActivity;
import com.burnmycalories.ui.button.BBSFragment;

public class SendPostActivity extends BaseAppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send_post,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.send_post){
            //发帖事件绑定
            Toast.makeText(SendPostActivity.this,"blog sent",Toast.LENGTH_SHORT).show();

//            Intent intent=new Intent(SendPostActivity.this, BBSFragment.class);

            MainActivity.bbsActionStart(SendPostActivity.this);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_post);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //去掉  toolBar 的lable
        getSupportActionBar().setDisplayShowTitleEnabled(false);




    }
}
