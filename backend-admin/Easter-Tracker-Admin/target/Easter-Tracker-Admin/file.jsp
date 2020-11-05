<%@ page language="java" pageEncoding="UTF-8"%>
<%--
  ~ Copyright (c) 2020.  EasterTracker App
  ~ Group: Burn My Calories
  ~ Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
  ~ Project 2 for COMP90018, 2020 S2
  ~ Time: 2020/10/22 23:24.
  ~ Usage: page of uploading/downloading/showing resource files
  --%>

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