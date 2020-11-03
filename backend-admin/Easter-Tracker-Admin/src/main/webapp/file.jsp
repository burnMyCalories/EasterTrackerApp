<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>File Upload</title>
</head>

<body>
<form action="/test/file" enctype="multipart/form-data" method="post">

    Upload：<input type="file" name="file"><br/>

    <input type="submit" value="Submit">
</form>
<form action="/test/file" method="get">
        FileName：<input type="text" name="filename"><br/>

    <input type="submit" value="Submit">
</form>
<form action="/test/fileres" method="get">
    FileName：<input type="text" name="filename"><br/>

    <input type="submit" value="Submit">
</form>
</body>
</html>