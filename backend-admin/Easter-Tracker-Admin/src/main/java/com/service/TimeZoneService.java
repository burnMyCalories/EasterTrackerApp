/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: Provide user with server's timezone
 */

package com.service;

import com.alibaba.fastjson.JSONObject;
import com.util.CRUDUtils;
import com.util.TimeZoneUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
@WebServlet(name = "timezone", urlPatterns = "/timezone")
public class TimeZoneService extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
//        String uuname=req.getParameter("uuname");
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
        resp.setHeader("Access-Control-Expose-Headers", "*");
        JSONObject json = new JSONObject(true);
        JSONObject temp = new JSONObject(true);

        JSONObject res = new JSONObject(true);
//        System.out.println(DateFormatUtils.format(new Date(), "z"));//‘z’小写CST；'Z'大写 +0800
//        System.out.println(DateFormatUtils.format(new Date(), "Z"));//‘z’小写CST；'Z'大写 +0800
//        System.out.println(DateFormatUtils.format(new Date(), "zz"));//'zz'小写一样 "ZZ"大写+08:00
//        System.out.println(DateFormatUtils.format(new Date(), "ZZ"));//'zz'小写一样 "ZZ"大写+08:00
//        System.out.println(TimeZoneUtils.getTimeZone());
        res.put("timezone", TimeZoneUtils.getTimeZone());
        resp.setStatus(200);
        temp.put("code", 0);
        temp.put("msg", "Success");
        json.put("status", temp);
        json.put("result", res);

//        LoginUtils.operate(uuname);
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
}
