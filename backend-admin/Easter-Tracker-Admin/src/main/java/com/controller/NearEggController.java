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
@WebServlet(name = "nearegg", urlPatterns = "/nearegg")
public class NearEggController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id=req.getParameter("id");
        String username=req.getParameter("username");
        String nickname=req.getParameter("nickname");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = new JSONObject(true);
        if(id!=null){
            JSONObject res = CRUDUtils.queryNearEggs(id);
            json.put("status",0);
            json.put("result",res);
        }
        else if(username!=null||nickname!=null){
            List<JSONObject> temp_list = (List<JSONObject>) CRUDUtils.queryUser(id, username, null, null, nickname, null, null, null).get("data");
            JSONObject temp = temp_list.get(0);
            id = temp.getString("id");
            JSONObject res = CRUDUtils.queryNearEggs(id);
            json.put("status",0);
            json.put("result",res);
        }
        else{
            resp.setStatus(400);
            json.put("status",1);
            json.put("result",new JSONObject(true));
        }
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
}
