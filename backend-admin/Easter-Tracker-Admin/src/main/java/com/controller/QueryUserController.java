package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.util.CRUDUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(name = "queryuser", urlPatterns = "/query/user")
public class QueryUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id=req.getParameter("id");
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String gender=req.getParameter("gender");
        String nickname=req.getParameter("nickname");
        String contact=req.getParameter("contact");
        String latitude=req.getParameter("latitude");
        String longitude=req.getParameter("longitude");
        JSONObject res = CRUDUtils.queryUser(id,username, password, gender, nickname, contact, latitude, longitude);
        PrintWriter writer = resp.getWriter();

        writer.write(res!=null?res.toString():"{}");
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
