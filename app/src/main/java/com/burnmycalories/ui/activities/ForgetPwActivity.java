package com.burnmycalories.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.burnmycalories.MainActivity;
import com.burnmycalories.R;
import com.burnmycalories.util.AuthFormatUtil;

public class ForgetPwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pw);

        EditText userName=(EditText) findViewById(R.id.fg_username);
        EditText mail=(EditText) findViewById(R.id.fg_mail);

        Button subButton=(Button)findViewById(R.id.fg_sbumit);

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringUserName=userName.getText().toString();
                String stringEmail=mail.getText().toString();

                if(AuthFormatUtil.checkUserName(ForgetPwActivity.this,stringUserName)
                        &&AuthFormatUtil.checkEmailAdress(ForgetPwActivity.this,stringEmail)
                ){
                    //提交邮箱


                    Toast.makeText(ForgetPwActivity.this,"Successfully Submitted",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ForgetPwActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
