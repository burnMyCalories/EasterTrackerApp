package com.service;

import com.alibaba.fastjson.JSONObject;
import com.util.VerifyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "confirm", urlPatterns = "/confirm")
public class ConfirmService extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String email=req.getParameter("email");
        String mobile=req.getParameter("mobile");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        JSONObject json = VerifyUtils.generate();
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
        writer.close();
    }
}
