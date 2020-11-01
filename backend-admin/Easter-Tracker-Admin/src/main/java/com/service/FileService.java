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

    private static final String filestoragepath = "/WEB-INF/files";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
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
        JSONObject json = new JSONObject(true);

        JSONObject status = new JSONObject(true);
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = this.getServletContext().getRealPath(filestoragepath);

        File file = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {

            //创建目录
            file.mkdir();
        }
        //消息提示
        String message = "";
        try{
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //3、判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(req)){
                throw new Exception("invalid data");
            }
            ArrayList<JSONObject> temp = new ArrayList<>();
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(req);
            boolean flag=false;
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");

                    JSONObject jsonObject = new JSONObject(true);
                    jsonObject.put("name",name);
                    jsonObject.put("value",value);
                    jsonObject.put("type","input");
                    temp.add(jsonObject);
                }else{//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();

                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分

                    filename = filename.substring(filename.lastIndexOf(File.separator)+1);
                    File targetFile = new File(savePath + File.separator + filename);

//                    if(targetFile.exists()){
//                        throw new Exception(filename+" already exists");
//                    }
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + File.separator + filename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
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
