<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="resources/js/jquery-1.8.0.min.js"></script>

</head>


<body>

    <div class="mainindex">
    <div class="welinfo">
    <span>欢迎您，来自&nbsp;<b>${user.orgName }，${user.userName }</b></span>
    </div>
    <div class="xline"></div>
    <div class="box"></div>

    <div class="welinfo">
    <span><img src="resources/images/dp.png" alt="提醒" /></span>
    <b>任务提醒</b>
    </div>

    <ul class="infolist">
    <li><span>您的待办任务笔数：<b><font color="red">${waitCount }</font></b></span><a href="flow/tasklistController.do?method=waitTaskList">查看</a></li>
    <li><span>您的参与任务笔数：<b><font color="red">${partCount }</span></font></b><a href="flow/tasklistController.do?method=partTaskList">查看</a></li>
    <li><span>您的历史任务笔数：<b><font color="red">${finishCount }</span></font></b><a href="flow/tasklistController.do?method=finishTaskList">查看</a></li>
    </ul>

    <div class="xline"></div>
    </div>


</body>

</html>
