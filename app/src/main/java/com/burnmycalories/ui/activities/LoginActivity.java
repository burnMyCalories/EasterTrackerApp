package com.burnmycalories.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.burnmycalories.MainActivity;
import com.burnmycalories.R;
import com.burnmycalories.base.BaseActivity;
import com.burnmycalories.json.Result;
import com.burnmycalories.model.User;
import com.burnmycalories.util.AuthFormatUtil;
import com.burnmycalories.util.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private final int SEND_TOAST=1;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case SEND_TOAST:
                    Toast.makeText(LoginActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText userName=(EditText) findViewById(R.id.username);
        EditText password=(EditText) findViewById(R.id.password);
        TextView forgetPassword=(TextView)findViewById(R.id.forget_password);

        Button loginButton=(Button)findViewById(R.id.login_button);
        Button registerButton=(Button)findViewById(R.id.register_button);




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringUserName= userName.getText().toString();
                String stringPassword= password.getText().toString();
//                System.out.println(stringUserName);
//                System.out.println(stringPassword);
                //todo  登陆逻辑处理  用户头像，用户名，token都存到本地

//                StringBuilder url=new StringBuilder();
//                String urlPart1=getString(R.string.server_url);
                //登录名密码·等格式检查

                if(AuthFormatUtil.checkUserName(LoginActivity.this,stringUserName)
                &&AuthFormatUtil.checkPassword(LoginActivity.this,stringPassword)){
                    OkHttpClient okHttpClient = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .build();

//                String url=urlPart1+"/users/login?"+"username="+stringUserName+"&password="+stringPassword;

                    HttpUrl url2=new HttpUrl.Builder()
                            .scheme("http")
                            .host(getString(R.string.server_host))
                            .port(Integer.parseInt(getString(R.string.server_port)))
                            .addPathSegments("users/login")
                            .addQueryParameter("username",stringUserName)
                            .addQueryParameter("password",stringPassword)
                            .build();
                    String url3=url2.toString();

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url3)
                            .post(body)
                            .build();



                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Response response=null;

                                response=okHttpClient.newCall(request).execute();
                                String responseBody=response.body().string();

                                Gson gson=new Gson();
                                Result<String> result=gson.fromJson(responseBody, new TypeToken<Result<String>>() {
                                }.getType());

//                                Log.d(TAG, "token: "+result.getData());

                                if(result.getCode()== LoginUtil.LOGIN_SUCESS_CODE){
                                    //存储token  token请求userInfo存储
                                    LoginUtil.userInfoUpdate(LoginActivity.this,LoginUtil.TOKEN,result.getData());

                                    HttpUrl urlToken=new HttpUrl.Builder()
                                            .scheme("http")
                                            .host(getString(R.string.server_host))
                                            .port(Integer.parseInt(getString(R.string.server_port)))
                                            .addPathSegments("users")
                                            .addPathSegment(LoginUtil.getUserInfo(LoginActivity.this,LoginUtil.TOKEN))
                                            .build();

                                    okhttp3.Request requestT = new okhttp3.Request.Builder()
                                            .url(urlToken.toString())
                                            .build();

                                    Response responseT=null;

                                    responseT=okHttpClient.newCall(requestT).execute();
                                    String responseBodyT=responseT.body().string();

                                    //new TypeToken<Result<User>>() {
                                    //        }.getType()

                                    Gson gsonT=new Gson();
                                    Result<User> resultT=gson.fromJson(responseBodyT, new TypeToken<Result<User>>() {
                                    }.getType());

                                    if(resultT.getCode()==LoginUtil.LOGIN_SUCESS_CODE){

                                        String nickname=resultT.getData().getNickName();
                                        String uuu=resultT.getData().getAvatar();

                                        LoginUtil.userInfoUpdate(LoginActivity.this,LoginUtil.USERNAME_STRING,resultT.getData().getNickName());
                                        LoginUtil.userInfoUpdate(LoginActivity.this,LoginUtil.HEAD_URL_STRING,resultT.getData().getAvatar());

//                                        Looper.prepare();
////                                        Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT);
////                                        Looper.loop();
                                        Message message=new Message();
                                        message.what=SEND_TOAST;
                                        message.obj="Login Success";
                                        handler.sendMessage(message);

                                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);

                                    }else {
                                        //多线程不可直接使用Toast

                                        Message message=new Message();
                                        message.what=SEND_TOAST;
                                        message.obj=resultT.getMessage();
                                        handler.sendMessage(message);

//                                        Looper.prepare();
//                                        Toast.makeText(LoginActivity.this,resultT.getMessage(),Toast.LENGTH_SHORT).show();
//                                        Looper.prepare();
                                    }


                                }else {
                                    //弹出错误信息
                                }

                                Message message=new Message();
                                message.what=SEND_TOAST;
                                message.obj=result.getMessage();
                                handler.sendMessage(message);

//                                Looper.prepare();
//                                Toast.makeText(LoginActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
//                                Looper.prepare();

                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }





//                LoginUtil.login(LoginActivity.this);
//                if(stringUserName.equals("yxs")&&stringPassword.equals("997518")){
//                    String token="yxs";
//                    LoginUtil.userInfoUpdate(LoginActivity.this,LoginUtil.TOKEN,"token");
//
//                    Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT);
//
//                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//
//                }

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, ForgetPwActivity.class);
                startActivity(intent);
            }
        });

    }
}
