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

@WebServlet(name = "action", urlPatterns = "/action")
public class UserEggActionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id=req.getParameter("id");
        String user_id=req.getParameter("user_id");
        String egg_id=req.getParameter("egg_id");
        String action=req.getParameter("action");
        String user_username=req.getParameter("user_username");
        String user_nickname=req.getParameter("user_nickname");
        String egg_name=req.getParameter("egg_name");
        resp.setCharacterEncoding("utf-8");
        List<JSONObject> res = CRUDUtils.queryAction(id, user_id, egg_id, action, user_username, user_nickname, egg_name);
        PrintWriter writer = resp.getWriter();
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("result",res);
        writer.write(json.toString());
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String user_id=req.getParameter("user_id");
        String egg_id=req.getParameter("egg_id");
        String action=req.getParameter("action");
        resp.setCharacterEncoding("utf-8");
        JSONObject json = new JSONObject();
        if(user_id==null||egg_id==null||action==null){
            resp.setStatus(400);
            json.put("status",1);
        }
        else{
            int res = CRUDUtils.addAction(user_id, egg_id, action);
            json.put("status",0);
            json.put("result",res);
        }
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id=req.getParameter("id");
        String user_id=req.getParameter("user_id");
        String egg_id=req.getParameter("egg_id");
        String action=req.getParameter("action");
        String is_deleted=req.getParameter("is_deleted");
        resp.setCharacterEncoding("utf-8");
        JSONObject json = new JSONObject();
        if(id==null&&(user_id==null||egg_id==null||action==null)){
            resp.setStatus(400);
            json.put("status",1);
        }
        else{
            int res = CRUDUtils.updateAction(id, user_id, egg_id, action, is_deleted);
            json.put("status",0);
            json.put("result",res);
        }
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id=req.getParameter("id");
        String user_id=req.getParameter("user_id");
        String egg_id=req.getParameter("egg_id");
        String action=req.getParameter("action");
        resp.setCharacterEncoding("utf-8");
        JSONObject json = new JSONObject();
        if(id==null&&(user_id==null||egg_id==null||action==null)){
            resp.setStatus(400);
            json.put("status",1);
        }
        else{
            int res = CRUDUtils.delAction(id, user_id, egg_id, action);
            json.put("status",0);
            json.put("result",res);
        }
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
}
