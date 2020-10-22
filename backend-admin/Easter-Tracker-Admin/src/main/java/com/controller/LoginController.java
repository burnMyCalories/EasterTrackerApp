package com.controller;

import com.util.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Arrays;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String username=req.getParameter("userName");
        String password=req.getParameter("passWord");
        if(username.trim().equals("admin")&&password.trim().equals("admin")){
            resp.setCharacterEncoding("utf-8");
            PrintWriter out = resp.getWriter();
            out.append("{success}");
//            resp.sendRedirect("/test/success.jsp");
//            req.setAttribute("userName",username);
//            req.getRequestDispatcher("/success.jsp").forward(req,resp);
        }
        else{
            req.getRequestDispatcher("/error.jsp").forward(req,resp);
        }
//        System.out.println(username);
//        System.out.println(password);
//        String[] hobbies = req.getParameterValues("hobbies");
//        System.out.println(Arrays.toString(hobbies));
////        req.getRequestDispatcher("/success.jsp").forward(req,resp);
//        Connection connection = DBUtils.getConnection();

    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
