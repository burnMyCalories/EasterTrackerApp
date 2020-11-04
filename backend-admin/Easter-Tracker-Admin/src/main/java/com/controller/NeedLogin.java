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
        /* 允许跨域的主机地址 */
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        ((HttpServletResponse)response).setHeader("Access-Control-Max-Age", "3600");
        /* 允许跨域的请求头 */
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Credentials", "true");
        ((HttpServletResponse)response).setHeader("Access-Control-Expose-Headers", "*");

        String path = ((HttpServletRequest) request).getRequestURI();
        String uuname=((HttpServletRequest) request).getParameter("uuname");
        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/json");

//        /* 允许跨域的主机地址 */
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        /* 允许跨域的请求方法GET, POST, HEAD 等 */
//        response.setHeader("Access-Control-Allow-Methods", "*");
//        /* 重新预检验跨域的缓存时间 (s) */
//        response.setHeader("Access-Control-Max-Age", "3600");
//        /* 允许跨域的请求头 */
//        response.setHeader("Access-Control-Allow-Headers", "*");
//        /* 是否携带cookie */
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        System.out.println(path);
//        System.out.println(path);
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
