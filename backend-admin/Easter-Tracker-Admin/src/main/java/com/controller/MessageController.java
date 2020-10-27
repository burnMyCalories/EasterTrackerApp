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

@WebServlet(name = "message", urlPatterns = "/message")
public class MessageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String uuname=req.getParameter("uuname");
        String id=req.getParameter("id");
        String friend_id=req.getParameter("friend_id");
        String userfrom_id=req.getParameter("userfrom_id");
        String userto_id=req.getParameter("userto_id");
        String userfrom_username=req.getParameter("userfrom_username");
        String userto_username=req.getParameter("userto_username");
        String userfrom_nickname=req.getParameter("userfrom_nickname");
        String userto_nickname=req.getParameter("userto_nickname");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject res = CRUDUtils.queryMessage(id, friend_id, userfrom_id, userto_id, userfrom_username, userto_username, userfrom_nickname, userto_nickname);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String uuname=req.getParameter("uuname");
        String friend_id=req.getParameter("friend_id");
        String type=req.getParameter("type");
        String content=req.getParameter("content");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject(true);
        JSONObject temp = new JSONObject(true);
        if(friend_id==null||type==null||content==null||uuname==null){
            resp.setStatus(400);
            temp.put("code",1);
            temp.put("msg","Invalid Parameters");
            json.put("status",temp);
            json.put("result",new JSONObject(true));
        }
        else{
            JSONObject res = CRUDUtils.addMessage(friend_id, type, content);
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
        LoginUtils.operate(uuname);
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String uuname=req.getParameter("uuname");
        String id=req.getParameter("id");
        String friend_id=req.getParameter("friend_id");
        String is_deleted=req.getParameter("is_deleted");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject(true);
        JSONObject temp = new JSONObject(true);
        if((id==null&&friend_id==null)||uuname==null){
            resp.setStatus(400);
            temp.put("code",1);
            temp.put("msg","Invalid Parameters");
            json.put("status",temp);
            json.put("result",new JSONObject(true));
        }
        else{
            JSONObject res = CRUDUtils.updateMessage(id, friend_id, is_deleted);
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
        LoginUtils.operate(uuname);
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String uuname=req.getParameter("uuname");
        String id=req.getParameter("id");
        String friend_id=req.getParameter("friend_id");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject(true);
        JSONObject temp = new JSONObject(true);
        if((id==null&&friend_id==null)||uuname==null){
            resp.setStatus(400);
            temp.put("code",1);
            temp.put("msg","Invalid Parameters");
            json.put("status",temp);
            json.put("result",new JSONObject(true));
        }
        else{
            JSONObject res = CRUDUtils.delMessage(id, friend_id);
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
        LoginUtils.operate(uuname);
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
}
