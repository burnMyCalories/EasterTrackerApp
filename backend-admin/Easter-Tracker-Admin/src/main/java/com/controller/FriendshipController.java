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

@WebServlet(name = "friendship", urlPatterns = "/friendship")
public class FriendshipController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id=req.getParameter("id");
        String userfrom_id=req.getParameter("userfrom_id");
        String userto_id=req.getParameter("userto_id");
        String userfrom_username=req.getParameter("userfrom_username");
        String userto_username=req.getParameter("userto_username");
        String userfrom_nickname=req.getParameter("userfrom_nickname");
        String userto_nickname=req.getParameter("userto_nickname");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject res = CRUDUtils.queryFriendship(id, userfrom_id, userto_id, userfrom_username, userto_username, userfrom_nickname, userto_nickname);
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
        String userfrom_id=req.getParameter("userfrom_id");
        String userto_id=req.getParameter("userto_id");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject();
        if(userfrom_id==null||userto_id==null){
            resp.setStatus(400);
            json.put("status",1);
            json.put("result",new JSONObject());
        }
        else{
            JSONObject res = CRUDUtils.addFriendship(userfrom_id, userto_id);
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
        String userfrom_id=req.getParameter("userfrom_id");
        String userto_id=req.getParameter("userto_id");
        String is_deleted=req.getParameter("is_deleted");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject();
        if(id==null&&(userfrom_id==null||userto_id==null)){
            resp.setStatus(400);
            json.put("status",1);
            json.put("result",new JSONObject());
        }
        else{
            JSONObject res = CRUDUtils.updateFriendship(id,userfrom_id,userto_id,is_deleted);
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
        String userfrom_id=req.getParameter("userfrom_id");
        String userto_id=req.getParameter("userto_id");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject();
        if(id==null&&userfrom_id==null&&userto_id==null){
            resp.setStatus(400);
            json.put("status",1);
            json.put("result",new JSONObject());
        }
        else{
            JSONObject res = CRUDUtils.delFriendship(id,userfrom_id,userto_id);
            json.put("status",0);
            json.put("result",res);
        }
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
}
