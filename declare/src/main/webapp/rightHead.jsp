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

<div id="place" class="place">
	<span>当前位置：</span>
	<ul class="placeul">
	<li><a href="#" id="node">首页</a></li>
	<li><a href="welcome.do" id="subNode" target="rightFrame">我的工作台</a></li>
	</ul>
</div>
     

	
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	function setTitle(title, subTitle, url) {
		$("#node").text(title);
		$("#subNode").text(subTitle);
		$("#subNode").attr('href', url);
	}
	</script>

</body>

</html>
