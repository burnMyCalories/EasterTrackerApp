/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: User Login
 */

package com.service;

import com.alibaba.fastjson.JSONObject;
import com.util.CRUDUtils;
import com.util.LoginUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginService extends HttpServlet {//user login
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Expose-Headers", "*");
        if(username==null||password==null){
            JSONObject json = new JSONObject(true);
            JSONObject temp = new JSONObject(true);
            resp.setStatus(400);
            temp.put("code",1);
            temp.put("msg","Invalid Parameters");
            json.put("status",temp);
            json.put("result",new JSONObject(true));
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
            writer.close();
        }
        else if(LoginUtils.isLogin(username)){
            JSONObject json = new JSONObject(true);
            JSONObject temp = new JSONObject(true);
            resp.setStatus(403);
            temp.put("code",1);
            temp.put("msg","Already Logged in");
            json.put("status",temp);
            json.put("result",new JSONObject(true));
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
            writer.close();
        }
        else{
            JSONObject res = CRUDUtils.queryUser(null,username, password, null, null, null, null, null);
            JSONObject temp = new JSONObject(true);
            if((int)res.get("rows")==0){
                resp.setStatus(401);
                temp.put("code",1);
                temp.put("msg","Incorrect username or password");
            }
            else{
                res = CRUDUtils.updateUser(null, username, null, null, null, null, null, null, "1", null);
                if((int)res.get("rows")==0) {
                    resp.setStatus(401);
                    temp.put("code",1);
                    temp.put("msg","Login Failed");
                }
                else{
                    resp.setStatus(200);
                    temp.put("code",0);
                    temp.put("msg","Success");
                    LoginUtils.operate(username);
                }


            }
            PrintWriter writer = resp.getWriter();
            JSONObject json = new JSONObject(true);
            json.put("status",temp);
            json.put("result",res);
            writer.write(json.toString());
            writer.close();

        }
    }
}
