<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>申报系统</title>
</head>
<frameset rows="88,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="top.do" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="207,*" frameborder="no" border="0" framespacing="0">
    <frame src="left.do" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frameset rows="38,*" cols="*" frameborder="no" border="0" framespacing="0" id="mainFrame" name="mainFrame">
    	<frame src="rightHead.jsp" name="rightHeadFrame" scrolling="No" noresize="noresize" id="rightHeadFrame" title="rightHeadFrame" />
	    <frame src="welcome.do" name="rightFrame" id="rightFrame" title="rightFrame" />
	  </frameset>
  </frameset>
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>
