package com.service;

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

@WebServlet(name = "logout", urlPatterns = "/logout")
public class LogoutService extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String username=req.getParameter("username");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        if(username==null){
            JSONObject json = new JSONObject(true);
            JSONObject temp = new JSONObject(true);
            resp.setStatus(400);
            temp.put("code",1);
            temp.put("msg","Invalid Parameters");
            json.put("status",temp);
            json.put("result",new JSONObject(true));
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
            writer.close();
        }
        else if(!LoginUtils.isLogin()){
            JSONObject json = new JSONObject(true);
            JSONObject temp = new JSONObject(true);
            resp.setStatus(403);
            temp.put("code",1);
            temp.put("msg","Already Logged out");
            json.put("status",temp);
            json.put("result",new JSONObject(true));
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
            writer.close();
        }
        else{
            req.getRequestDispatcher("/user").include(req,resp);
            JSONObject res = CRUDUtils.updateUser(null, username, null, null, null, null, null, null, "0", null);
            System.out.println(res);
            if((int)res.get("rows")==0) {
                resp.setStatus(401);
            }
            else{
                resp.setStatus(200);
                LoginUtils.logout();

            }

        }
    }
}
