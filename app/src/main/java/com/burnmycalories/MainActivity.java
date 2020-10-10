package com.burnmycalories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burnmycalories.base.BaseActivity;
import com.burnmycalories.base.BaseAppCompatActivity;
import com.burnmycalories.model.Article;
import com.burnmycalories.ui.activities.ArticleActivity;
import com.burnmycalories.ui.activities.LoginActivity;
import com.burnmycalories.ui.button.BBSFragment;
import com.burnmycalories.ui.button.LocalFragment;
import com.burnmycalories.ui.button.HomeFragment;
import com.burnmycalories.util.LoginUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends BaseAppCompatActivity {

    private static final String OPEN_BBS="openBBS";

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private Fragment homeFragment= new HomeFragment();
    private Fragment localFragment= new LocalFragment();
    private Fragment bbsFragment=new BBSFragment();

    private Fragment currentFragment=homeFragment;


    private DrawerLayout mDrawerLayout;

    private TextView topToolBarText;


    //BBS发帖回到BBS
    public static void bbsActionStart(Context context){
        Intent intent=new Intent(context, MainActivity.class);
        intent.putExtra(OPEN_BBS,true);
        context.startActivity(intent);
    }




    //底边导航切换
private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
        = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
//                showFragment(homeFragment,fragmentManager,currentFragment);
                topToolBarText.setText("Home");
                showFragment(homeFragment);
                return true;
            case R.id.local:
//                showFragment(localFragment,fragmentManager,currentFragment);
                topToolBarText.setText("Local");
                showFragment(localFragment);
                return true;
            case R.id.bbs:
//                showFragment(bbsFragment,fragmentManager,currentFragment);
                topToolBarText.setText("BBS");
                showFragment(bbsFragment);
                return true;
        }
        return false;
    }
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        //网络权限申请
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this
                    ,new String[]{Manifest.permission.INTERNET},1);
        }

        //获得文件权限
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.buttom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        topToolBarText=(TextView)findViewById(R.id.top_toolbar_text);


        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.main_activity);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }



//        navigationView.setCheckedItem(R.id.draw_star);

        //侧边导航逻辑
        //todo  侧滑栏登陆等逻辑完成
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.draw_star:
                        Toast.makeText(getApplicationContext(),"Star clicked",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.draw_history:
                        Toast.makeText(getApplicationContext(),"Local clicked",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.draw_post:
                        Toast.makeText(getApplicationContext(),"Post clicked",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.draw_reply:
                        Toast.makeText(getApplicationContext(),"Histroy clicked",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        LoginUtil.logOut(MainActivity.this);

                }

                return false;
            }
        });

        //侧滑栏头本部分
        //todo  侧滑栏登陆等逻辑完成2
        View headview=navigationView.inflateHeaderView(R.layout.layout_draw_header);
        TextView userNmae=(TextView)headview.findViewById(R.id.username);
        CircleImageView circleImageViewHeadView =(CircleImageView)headview.findViewById(R.id.circle_image_view_head);


        if(LoginUtil.isLogin(MainActivity.this)){
            //登录

            String t1=LoginUtil.getUserInfo(MainActivity.this,LoginUtil.HEAD_URL_STRING);



            Glide.with(MainActivity.this)
                    .load(LoginUtil.getUserInfo(MainActivity.this,LoginUtil.HEAD_URL_STRING))
                    .apply(new RequestOptions().placeholder(R.drawable.mytest).error(R.drawable.mytest).dontAnimate().centerCrop())
                    .into(circleImageViewHeadView);

            String T=LoginUtil.getUserInfo(MainActivity.this,LoginUtil.USERNAME_STRING);

            userNmae.setText(LoginUtil.getUserInfo(MainActivity.this,LoginUtil.USERNAME_STRING));

            circleImageViewHeadView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Photo clicked",Toast.LENGTH_SHORT).show();
//                    LoginUtil.userInfoUpdate(MainActivity.this,);
                }
            });
        }else {
            //未登录提示登录
            //

            Glide.with(MainActivity.this)
                    .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589892801763&di=f3cf57800be83ba306518b955613d5f9&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Felement_pic%2F01%2F54%2F05%2F625746fd5b60878.jpg")
                    .apply(new RequestOptions().placeholder(R.drawable.mytest).error(R.drawable.mytest).dontAnimate().centerCrop())
                    .into(circleImageViewHeadView);

            userNmae.setText("Login");

            circleImageViewHeadView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   LoginUtil.login(MainActivity.this);
                }
            });
        }




        //初始化目前碎片
        Intent intent=getIntent();
        if(intent.getBooleanExtra(OPEN_BBS,false)==true){
            currentFragment=bbsFragment;
        }else {
            currentFragment=homeFragment;
        }
//        if(openBBSFragment==true){
//            currentFragment=bbsFragment;
//        }else {
//            currentFragment=homeFragment;
//        }
        //先把首页添加上
        FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction
                    .add(R.id.fragment_layout,currentFragment);
        transaction.commit();



        //去掉  toolBar 的lable
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]!=
                        PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Maybe Not working",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case 2:
                if(grantResults.length>0&&grantResults[0]!=
                        PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"The app cannot be used without permission",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



    //Fragment切换展示函数，注意放在activity外会引起重影
    private void showFragment(Fragment fg){

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if(!fg.isAdded()){
            transaction
                    .add(R.id.fragment_layout,fg)
                    .hide(currentFragment);
        }else{
            transaction
                    .hide(currentFragment)
                    .show(fg);
        }
        currentFragment = fg;
        //transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

}
