package com.controller;

import com.util.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;

@WebServlet(name = "web", urlPatterns = "/web")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("utf-8");
//        String username=req.getParameter("username");
//        String password=req.getParameter("password");
//        System.out.println(username);
//        System.out.println(password);
//        String[] hobbies = req.getParameterValues("hobbies");
//        System.out.println(Arrays.toString(hobbies));
////        req.getRequestDispatcher("/success.jsp").forward(req,resp);
        Connection connection = DBUtils.getConnection();
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
