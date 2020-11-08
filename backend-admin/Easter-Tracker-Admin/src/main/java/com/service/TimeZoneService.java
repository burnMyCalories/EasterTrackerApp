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
public class TimeZoneService extends HttpServlet {//get server's timezone
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Expose-Headers", "*");
        JSONObject json = new JSONObject(true);
        JSONObject temp = new JSONObject(true);

        JSONObject res = new JSONObject(true);
        res.put("timezone", TimeZoneUtils.getTimeZone());
        resp.setStatus(200);
        temp.put("code", 0);
        temp.put("msg", "Success");
        json.put("status", temp);
        json.put("result", res);

        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
}
