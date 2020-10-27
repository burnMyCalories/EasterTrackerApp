package com.controller;

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
import java.util.List;

@WebServlet(name = "egg", urlPatterns = "/egg")
public class EggController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id=req.getParameter("id");
        String name=req.getParameter("name");
        String color=req.getParameter("color");
        String type=req.getParameter("type");
        String latitude=req.getParameter("latitude");
        String longitude=req.getParameter("longitude");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject res = CRUDUtils.queryEgg(id, name, color, type, latitude, longitude);
        PrintWriter writer = resp.getWriter();
        JSONObject json = new JSONObject(true);
        JSONObject temp = new JSONObject(true);
        if((int)res.get("rows")==0){
            resp.setStatus(410);
            temp.put("code",1);
            temp.put("msg","No such element");
        }
        else{
            resp.setStatus(200);
            temp.put("code",0);
            temp.put("msg","Success");
        }
        LoginUtils.operate();
        json.put("status",temp);
        json.put("result",res);
        writer.write(json.toString());
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String name=req.getParameter("name");
        String color=req.getParameter("color");
        String type=req.getParameter("type");
        String latitude=req.getParameter("latitude");
        String longitude=req.getParameter("longitude");
        String content=req.getParameter("content");
        String expire_time=req.getParameter("expire_time");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject(true);
        JSONObject temp = new JSONObject(true);
        if(name==null||color==null||type==null||latitude==null||longitude==null||content==null||expire_time==null){
            resp.setStatus(400);
            temp.put("code",1);
            temp.put("msg","Invalid Parameters");
            json.put("status",temp);
            json.put("result",new JSONObject(true));
        }
        else{
            JSONObject res = CRUDUtils.addEgg(name, color, type, latitude, longitude, content, expire_time);
            if((int)res.get("rows")==0){
                resp.setStatus(410);
                temp.put("code",1);
                temp.put("msg","Not successful");
            }
            else{
                resp.setStatus(201);
                temp.put("code",0);
                temp.put("msg","Success");
            }
            json.put("status",temp);
            json.put("result",res);
        }
        LoginUtils.operate();
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id=req.getParameter("id");
        String name=req.getParameter("name");
        String color=req.getParameter("color");
        String type=req.getParameter("type");
        String latitude=req.getParameter("latitude");
        String longitude=req.getParameter("longitude");
        String content=req.getParameter("content");
        String expire_time=req.getParameter("expire_time");
        String is_deleted=req.getParameter("is_deleted");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject(true);
        JSONObject temp = new JSONObject(true);
        if(id==null&&name==null){
            resp.setStatus(400);
            temp.put("code",1);
            temp.put("msg","Invalid Parameters");
            json.put("status",temp);
            json.put("result",new JSONObject(true));
        }
        else{
            JSONObject res = CRUDUtils.updateEgg(id, name, color, type, latitude, longitude, content, expire_time, is_deleted);
            if((int)res.get("rows")==0){
                resp.setStatus(410);
                temp.put("code",1);
                temp.put("msg","No such element");
            }
            else{
                resp.setStatus(201);
                temp.put("code",0);
                temp.put("msg","Success");
            }
            json.put("status",temp);
            json.put("result",res);
        }
        LoginUtils.operate();
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id=req.getParameter("id");
        String name=req.getParameter("name");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject(true);
        JSONObject temp = new JSONObject(true);
        if(id==null&&name==null){
            resp.setStatus(400);
            temp.put("code",1);
            temp.put("msg","Invalid Parameters");
            json.put("status",temp);
            json.put("result",new JSONObject(true));
        }
        else{
            JSONObject res = CRUDUtils.delEgg(id, name);
            if((int)res.get("rows")==0){
                resp.setStatus(410);
                temp.put("code",1);
                temp.put("msg","No such element");
            }
            else{
                resp.setStatus(201);
                temp.put("code",0);
                temp.put("msg","Success");
            }
            json.put("status",temp);
            json.put("result",res);
        }
        LoginUtils.operate();
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
}
