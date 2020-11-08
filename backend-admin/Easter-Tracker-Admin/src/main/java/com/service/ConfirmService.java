/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: Send verification code to verify whether contact details are correct
 */

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {//verify mobile or email
        req.setCharacterEncoding("utf-8");
        String email=req.getParameter("email");
        String mobile=req.getParameter("mobile");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Expose-Headers", "*");
        JSONObject json = VerifyUtils.generate();
        String code = json.getString("code");
        if(mobile!=null){//send sms
            ZhenziSmsClient client = new ZhenziSmsClient("*******", "********", "************");
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
        else if(email!=null){//send email
            String to = email;
            String from = "************";
            String host = "***********";
            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            properties.put("mail.smtp.auth", "true");
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            Session session = Session.getDefaultInstance(properties,new Authenticator(){
                public PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication("***************", "*************");
                }
            });

            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Verification Email");
                message.setText("Your verification code is "+code+". This will expire in 5 minutes.");
                Transport.send(message);
                JSONObject temp = new JSONObject(true);
                resp.setStatus(201);
                temp.put("code",0);
                temp.put("msg","Success");
                json.put("status",temp);

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
