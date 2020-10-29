<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>File Upload</title>
</head>

<body>
<form action="/test/file" enctype="multipart/form-data" method="post">
<%--    上传用户：<input type="text" name="username"><br/>--%>
    Upload：<input type="file" name="file"><br/>
<%--    上传文件2：<input type="file" name="file2"><br/>--%>
    <input type="submit" value="Submit">
</form>
<form action="/test/file" method="get">
        FileName：<input type="text" name="filename"><br/>
<%--    Upload：<input type="file" name="file"><br/>--%>
    <%--    上传文件2：<input type="file" name="file2"><br/>--%>
    <input type="submit" value="Submit">
</form>
</body>
</html>