package com.burnmycalories.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.burnmycalories.R;
import com.burnmycalories.base.BaseActivity;
import com.burnmycalories.json.Result;
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

public class RegisterActivity extends BaseActivity {

    private final int SEND_TOAST=1;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case SEND_TOAST:
                    Toast.makeText(RegisterActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText userName=(EditText) findViewById(R.id.reg_username);
        EditText password=(EditText) findViewById(R.id.reg_password);
        EditText nickNmae=(EditText) findViewById(R.id.nike_name);


        Button registerButton=(Button)findViewById(R.id.register_up_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stringUserName=userName.getText().toString();
                String stringPassword=password.getText().toString();
                String stringNickNmae=nickNmae.getText().toString();


                if(AuthFormatUtil.checkUserName(RegisterActivity.this,stringUserName)
                        &&AuthFormatUtil.checkPassword(RegisterActivity.this,stringPassword)
                ){
                    //TODO  提交注册信息   处理注册逻辑  比如注册失败之类的

                    OkHttpClient okHttpClient = new OkHttpClient();
                    RequestBody body = new FormBody.Builder().build();

                    HttpUrl url=new HttpUrl.Builder()
                            .scheme("http")
                            .host(getString(R.string.server_host))
                            .port(Integer.parseInt(getString(R.string.server_port)))
                            .addPathSegments("/users/register")
                            .addQueryParameter("username",stringUserName)
                            .addQueryParameter("password",stringPassword)
                            .addQueryParameter("nickname",stringNickNmae)
                            .build();

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
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

                                if(result.getCode()== LoginUtil.REGISTER_SUCESS_CODE){

                                    Message message=new Message();
                                    message.what=SEND_TOAST;
                                    message.obj="注册成功";
                                    handler.sendMessage(message);

                                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }else {
                                    Message message=new Message();
                                    message.what=SEND_TOAST;
                                    message.obj=result.getMessage();
                                    handler.sendMessage(message);
                                }
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }).start();


                }

            }
        });
    }
}
