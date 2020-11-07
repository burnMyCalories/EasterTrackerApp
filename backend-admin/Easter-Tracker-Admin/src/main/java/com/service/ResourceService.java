/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: Get Access to Resource
 */

package com.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
@WebServlet(name = "resource", urlPatterns = "/fileres")
public class ResourceService extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String filepath = req.getSession().getServletContext().getRealPath(FileService.filestoragepath);
        String filename = req.getParameter("filename");
        if(filename==null){
            resp.setStatus(400);
            return;
        }
        File targetFile = new File(filepath + File.separator+ filename);
        if(!targetFile.exists()){
            resp.setStatus(404);
            return;
        }
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Expose-Headers", "*");
        FileInputStream fis = new FileInputStream(filepath + File.separator+ filename) ;

        int size = fis.available();
        byte data[] = new byte[size] ;
        fis.read(data) ;
        fis.close();

        if(filename.endsWith("jpg")||filename.endsWith("jpeg")){
            resp.setContentType("image/jpeg");
        }
        else if(filename.endsWith("gif")){
            resp.setContentType("image/gif");
        }
        else if(filename.endsWith("png")){
            resp.setContentType("image/png");
        }
        else if(filename.endsWith("ico")){
            resp.setContentType("image/x-icon");
        }

        else{
            resp.setStatus(410);
            return;
        }
        OutputStream os = resp.getOutputStream() ;
        os.write(data);
        os.flush();
        os.close();
    }
}
