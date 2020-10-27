package com.service;

import com.alibaba.fastjson.JSONObject;
import com.util.VerifyUtils;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.SneakyThrows;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "confirm", urlPatterns = "/confirm")
public class ConfirmService extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String email=req.getParameter("email");
        String mobile=req.getParameter("mobile");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = VerifyUtils.generate();
        String code = json.getString("code");
        if(mobile!=null){
            ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "101531", "a60b64c8-da54-4ae7-a148-7b7c33887498");
            Map<String, Object> map = new HashMap<>();
            map.put("number", mobile);
            map.put("templateId", "2071");
            String[] templateParams = new String[1];
            templateParams[0] = code;
            map.put("templateParams", templateParams);
            String feedback = client.send(map);
            JSONObject status = JSONObject.parseObject(feedback);
            json.put("status",status);
            if((int)status.get("code")==0){
                resp.setStatus(201);
            }
            else{
                resp.setStatus(500);
            }
        }
        else if(email!=null){
//            String url = "http://api.sendcloud.net/apiv2/mail/send";
//            String apiUser = "sxn2012_test_1u6AtM";
//            String apiKey = "SOMZQu4Typ8YhXtcsjJ5023lVCAUexfe.sendcloud.org";
//            String rcpt_to = email;
//            String subject = "[EasterTracker] Verification Code";
//            String html = "Your verification code is "+code+". This will expire in 5 minutes.";
//            HttpPost httpPost = new HttpPost(url);
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            List<NameValuePair> params = new ArrayList<>();
//            params.add(new BasicNameValuePair("apiUser", apiUser));
//            params.add(new BasicNameValuePair("apiKey", apiKey));
//            params.add(new BasicNameValuePair("to", rcpt_to));
//            params.add(new BasicNameValuePair("from", "sendcloud@sendcloud.org"));
//            params.add(new BasicNameValuePair("fromName", "SendCloud"));
//            params.add(new BasicNameValuePair("subject", subject));
//            params.add(new BasicNameValuePair("html", html));
//
//            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//
//            HttpResponse response = httpClient.execute(httpPost);
//
//            JSONObject temp = new JSONObject(true);
//            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//
//                resp.setStatus(201);
//
//                temp.put("code",0);
//                temp.put("msg",response.getStatusLine().getReasonPhrase());
//
//            } else {
//
//                resp.setStatus(500);
//
//                temp.put("code",1);
//                temp.put("msg",response.getStatusLine().getReasonPhrase());
//
//            }
//            httpPost.releaseConnection();
//            json.put("status",temp);
        }
        else{
            resp.setStatus(400);
            JSONObject temp = new JSONObject(true);
            temp.put("code",1);
            temp.put("msg","Invalid Parameters");
            json.put("status",temp);
        }
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
}
