package com.burnmycalories;

import android.provider.MediaStore;

import com.burnmycalories.ui.activities.LoginActivity;

import org.junit.Test;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import static org.junit.Assert.*;

/**
 * Example ic_local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testRequestBody(){

        RequestBody body = new FormBody.Builder()
                .add("danmakuJson","aaa")
                .build();

        String string=body.toString();


        String url3="http://111.com";

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url3)
                .post(body)
                .build();

        MediaType JSON=MediaType.parse("application/json; charset=utf-8");

        String json="222";
        RequestBody body1 = RequestBody.create(JSON,json);



        System.out.println(string);
    }
}