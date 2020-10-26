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
import java.util.List;
@WebServlet(name = "user", urlPatterns = "/user")
public class UserController extends HttpServlet {
    //select
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
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject res = CRUDUtils.queryUser(id,username, password, gender, nickname, contact, latitude, longitude);
        PrintWriter writer = resp.getWriter();
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("result",res);
        writer.write(json.toString());
        writer.close();
    }
    //insert
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String gender=req.getParameter("gender");
        String nickname=req.getParameter("nickname");
        String contact=req.getParameter("contact");
        String latitude=req.getParameter("latitude");
        String longitude=req.getParameter("longitude");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject();
        if(username==null||password==null||gender==null||nickname==null||contact==null||latitude==null||longitude==null){
            resp.setStatus(400);
            json.put("status",1);
            json.put("result",new JSONObject());
        }
        else{
            JSONObject res = CRUDUtils.addUser(username, password, gender, nickname, contact, latitude, longitude);
            json.put("status",0);
            json.put("result",res);
        }
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
    //update
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id=req.getParameter("id");
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String gender=req.getParameter("gender");
        String nickname=req.getParameter("nickname");
        String contact=req.getParameter("contact");
        String latitude=req.getParameter("latitude");
        String longitude=req.getParameter("longitude");
        String is_online=req.getParameter("is_online");
        String is_deleted=req.getParameter("is_deleted");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject res = CRUDUtils.updateUser(id,username, password, gender, nickname, contact, latitude, longitude, is_online, is_deleted);
        PrintWriter writer = resp.getWriter();
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("result",res);
        writer.write(json.toString());
        writer.close();
    }
    //delete
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id=req.getParameter("id");
        String username=req.getParameter("username");
        String nickname=req.getParameter("nickname");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject();
        if(id==null&&username==null&&nickname==null){
            resp.setStatus(400);
            json.put("status",1);
            json.put("result",new JSONObject());
        }
        else{
            JSONObject res = CRUDUtils.delUser(id,username,nickname);
            json.put("status",0);
            json.put("result",res);
        }
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
}
