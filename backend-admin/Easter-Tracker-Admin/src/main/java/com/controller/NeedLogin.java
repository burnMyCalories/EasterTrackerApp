package com.controller;

import com.util.LoginUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@WebFilter(filterName = "needLogin", urlPatterns = "/*")
public class NeedLogin implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = ((HttpServletRequest) request).getRequestURI();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        if(path.contains("login")||LoginUtils.isLogin()){
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
