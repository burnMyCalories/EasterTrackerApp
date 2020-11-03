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
        /* 允许跨域的主机地址 */
        resp.setHeader("Access-Control-Allow-Origin", "*");
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        resp.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        resp.setHeader("Access-Control-Max-Age", "3600");
        /* 允许跨域的请求头 */
        resp.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
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
//        else if(filename.endsWith("tif")||filename.endsWith("tiff")){
//            resp.setContentType("image/tiff");
//        }
//        else if(filename.endsWith("wbmp")){
//            resp.setContentType("image/vnd.wap.wbmp");
//        }
//        else if(filename.endsWith("avi")){
//            resp.setContentType("video/avi");
//        }
//        else if(filename.endsWith("mp4")){
//            resp.setContentType("video/mpeg4");
//        }
//        else if(filename.endsWith("wmv")){
//            resp.setContentType("video/x-ms-wmv");
//        }
//        else if(filename.endsWith("wav")){
//            resp.setContentType("audio/wav");
//        }
//        else if(filename.endsWith("wma")){
//            resp.setContentType("audio/x-ms-wma");
//        }
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
