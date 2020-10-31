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
@WebServlet(name = "nearegg", urlPatterns = "/nearegg")
public class NearEggController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String uuname=req.getParameter("uuname");
        String id=req.getParameter("id");
        String username=req.getParameter("username");
        String nickname=req.getParameter("nickname");
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
        JSONObject json = new JSONObject(true);
        JSONObject tempjson = new JSONObject(true);
        if(uuname==null){
            resp.setStatus(400);
            tempjson.put("code",1);
            tempjson.put("msg","Invalid Parameters");
            json.put("status",tempjson);
            json.put("result",new JSONObject(true));
        }
        else if(id!=null){
            JSONObject res = CRUDUtils.queryNearEggs(id);
            if((int)res.get("rows")==0){
                resp.setStatus(410);
                tempjson.put("code",1);
                tempjson.put("msg","No such element");
            }
            else{
                resp.setStatus(200);
                tempjson.put("code",0);
                tempjson.put("msg","Success");
            }
            json.put("status",tempjson);
            json.put("result",res);
        }
        else if(username!=null||nickname!=null){
            List<JSONObject> temp_list = (List<JSONObject>) CRUDUtils.queryUser(id, username, null, null, nickname, null, null, null).get("data");
            JSONObject temp = temp_list.get(0);
            id = temp.getString("id");
            JSONObject res = CRUDUtils.queryNearEggs(id);
            if((int)res.get("rows")==0){
                resp.setStatus(410);
                tempjson.put("code",1);
                tempjson.put("msg","No such element");
            }
            else{
                resp.setStatus(200);
                tempjson.put("code",0);
                tempjson.put("msg","Success");
            }
            json.put("status",tempjson);
            json.put("result",res);
        }
        else{
            resp.setStatus(400);
            tempjson.put("code",1);
            tempjson.put("msg","Invalid Parameters");
            json.put("status",tempjson);
            json.put("result",new JSONObject(true));
        }
        LoginUtils.operate(uuname);
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
}
