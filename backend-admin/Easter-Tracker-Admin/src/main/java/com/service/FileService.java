/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: upload and download files
 */

package com.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "file", urlPatterns = "/file")
public class FileService extends HttpServlet {

    protected static final String filestoragepath = "/files";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        JSONObject json = new JSONObject(true);

        JSONObject status = new JSONObject(true);
        String savePath = this.getServletContext().getRealPath(filestoragepath);

        File file = new File(savePath);
        if (!file.exists() && !file.isDirectory()) {

            file.mkdir();
        }
        String message = "";
        try{
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            if(!ServletFileUpload.isMultipartContent(req)){
                throw new Exception("invalid data");
            }
            ArrayList<JSONObject> temp = new ArrayList<>();
            List<FileItem> list = upload.parseRequest(req);
            boolean flag=false;
            for(FileItem item : list){

                if(item.isFormField()){
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");

                    JSONObject jsonObject = new JSONObject(true);
                    jsonObject.put("name",name);
                    jsonObject.put("value",value);
                    jsonObject.put("type","input");
                    temp.add(jsonObject);
                }else{
                    String filename = item.getName();

                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }

                    filename = filename.substring(filename.lastIndexOf(File.separator)+1);
                    File targetFile = new File(savePath + File.separator + filename);

                    InputStream in = item.getInputStream();
                    FileOutputStream out = new FileOutputStream(savePath + File.separator + filename);
                    byte buffer[] = new byte[1024];
                    int len = 0;
                    while((len=in.read(buffer))>0){
                        out.write(buffer, 0, len);
                    }
                    in.close();
                    out.close();
                    item.delete();
                    if(!targetFile.isFile()){
                        throw new Exception(filename+" is not a file");
                    }
                    JSONObject jsonObject = new JSONObject(true);
                    jsonObject.put("name",filename);
                    jsonObject.put("value","");
                    jsonObject.put("type","file");
                    temp.add(jsonObject);
                    message = "Success";
                    status.put("code",0);
                    status.put("msg",message);
                    resp.setStatus(201);
                    flag=true;
                }
            }
            if(!flag){
                throw new Exception("no file");
            }
            json.put("result",temp);
        }catch (Exception e) {
            message= "Error: "+e.getMessage();
            resp.setStatus(400);
            status.put("code",1);
            status.put("msg",message);
            json.put("result",new JSONObject(true));
        }
        finally {
            json.put("status",status);
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
            writer.close();
        }



    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Expose-Headers", "*");
        String filepath = req.getSession().getServletContext().getRealPath(filestoragepath);
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
        try{
            resp.setContentType("APPLICATION/OCTET-STREAM");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            ServletOutputStream outputStream = resp.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(filepath + File.separator+ filename);
            byte[] buffer = new byte[5];
            int len;
            while((len = fileInputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
            }

            fileInputStream.close();
            outputStream.close();
            resp.setStatus(200);
        }
        catch (Exception e){
            resp.setStatus(500);

        }

    }
}
