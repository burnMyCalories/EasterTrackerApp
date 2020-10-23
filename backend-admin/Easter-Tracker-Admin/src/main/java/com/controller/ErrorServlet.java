package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.dao.UserOps;
import com.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "error", urlPatterns = "/error")
public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        resp.setCharacterEncoding("utf-8");
//        PrintWriter writer = resp.getWriter();
//        writer.print("<h1>404</h1><br/>");
//
//        ServletContext context = this.getServletContext();
//        RequestDispatcher requestDispatcher = context.getRequestDispatcher("/hello");//转发的请求路径
//        requestDispatcher.forward(req,resp);//请求转发
        List<User> users = UserOps.queryUser();
        PrintWriter writer = resp.getWriter();
        JSONObject json = new JSONObject();
        for (User user : users) {
            JSONObject temp = new JSONObject();
            temp.put("name",user.getUsername());
            temp.put("pwd",user.getPassword());
            json.put(String.valueOf(user.getId()),temp);
        }
        writer.write(json.toString());
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
