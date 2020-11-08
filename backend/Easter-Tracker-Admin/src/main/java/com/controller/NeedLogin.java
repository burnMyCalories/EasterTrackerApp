/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: filter to refuse operations conducted by users that have not logged in yet
 */

package com.controller;

import com.util.LoginUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(filterName = "needLogin", urlPatterns = "/*")
public class NeedLogin implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Methods", "*");
        ((HttpServletResponse)response).setHeader("Access-Control-Max-Age", "3600");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Headers", "*");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Credentials", "true");
        ((HttpServletResponse)response).setHeader("Access-Control-Expose-Headers", "*");

        String path = ((HttpServletRequest) request).getRequestURI();
        String uuname=((HttpServletRequest) request).getParameter("uuname");
        response.setCharacterEncoding("utf-8");
        //filter requests that have not logged in
        if(path.contains(".")||path.contains("login")||path.contains("logout")||path.contains("register")||path.contains("confirm")||path.contains("file")||path.contains("timezone")||path.contains("forget")||LoginUtils.isLogin(uuname)){
            chain.doFilter(request, response);
        }
        else {
            request.getRequestDispatcher("/nologin").forward(request, response);
        }

    }

    @Override
    public void destroy() {

    }


}
