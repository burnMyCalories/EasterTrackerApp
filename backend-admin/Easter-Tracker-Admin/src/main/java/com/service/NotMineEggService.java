/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: The Utility Code for performing CRUD operations
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
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "othersegg", urlPatterns = "/othersegg")
public class NotMineEggService extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String uuname=req.getParameter("uuname");
//        String id=req.getParameter("id");
//        String user_id=req.getParameter("user_id");
//        String egg_id=req.getParameter("egg_id");
//        String action=req.getParameter("action");
//        String user_username=req.getParameter("user_username");
//        String user_nickname=req.getParameter("user_nickname");
//        String egg_name=req.getParameter("egg_name");
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
        JSONObject res = CRUDUtils.querySpecialAction(uuname);
        PrintWriter writer = resp.getWriter();
        JSONObject json = new JSONObject(true);
        JSONObject temp = new JSONObject(true);
        if((int)res.get("rows")==0){
            resp.setStatus(410);
            temp.put("code",1);
            temp.put("msg","No such element");
        }
        else if(uuname==null){
            resp.setStatus(400);
            temp.put("code",1);
            temp.put("msg","Invalid Parameters");
            res = new JSONObject(true);
        }
        else{
            resp.setStatus(200);
            temp.put("code",0);
            temp.put("msg","Success");
        }
        LoginUtils.operate(uuname);
        json.put("status",temp);
        json.put("result",res);
        writer.write(json.toString());
        writer.close();
    }
}
