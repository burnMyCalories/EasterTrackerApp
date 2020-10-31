package com.service;

import com.alibaba.fastjson.JSONObject;
import com.sun.mail.util.MailSSLSocketFactory;
import com.util.VerifyUtils;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.SneakyThrows;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
        /* 允许跨域的主机地址 */
        resp.setHeader("Access-Control-Allow-Origin", "*");
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        resp.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        resp.setHeader("Access-Control-Max-Age", "3600");
        /* 允许跨域的请求头 */
        resp.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        resp.setHeader("Access-Control-Allow-Credentials", "true");
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
            // 收件人电子邮箱
            String to = email;

            // 发件人电子邮箱
            String from = "543777820@qq.com";

            // 指定发送邮件的主机为 smtp.qq.com
            String host = "smtp.qq.com";  //QQ 邮件服务器

            // 获取系统属性
            Properties properties = System.getProperties();

            // 设置邮件服务器
            properties.setProperty("mail.smtp.host", host);

            properties.put("mail.smtp.auth", "true");
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            // 获取默认session对象
            Session session = Session.getDefaultInstance(properties,new Authenticator(){
                public PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication("543777820@qq.com", "yujzducclsjwbdaj"); //发件人邮件用户名、密码
                }
            });

            try{
                // 创建默认的 MimeMessage 对象
                MimeMessage message = new MimeMessage(session);

                // Set From: 头部头字段
                message.setFrom(new InternetAddress(from));

                // Set To: 头部头字段
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: 头部头字段
                message.setSubject("Verification Email");

                // 设置消息体
                message.setText("Your verification code is "+code+". This will expire in 5 minutes.");

                // 发送消息
                Transport.send(message);
                JSONObject temp = new JSONObject(true);
                resp.setStatus(201);

                temp.put("code",0);
                temp.put("msg","Success");
                json.put("status",temp);
//                System.out.println("Sent message successfully....from runoob.com");
            }catch (MessagingException mex) {
                JSONObject temp = new JSONObject(true);
                resp.setStatus(500);

                temp.put("code",1);
                temp.put("msg",mex.getMessage());
                json.put("status",temp);
            }

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
