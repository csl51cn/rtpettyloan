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

<script type="text/javascript">
$(function(){	
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})	
})	

function logout() {
	if (confirm("您确定要退出系统吗？")) {
		window.parent.location.href = 'logout.do';
	}
}

function setTitle(s, url) {
	$("#rightHeadFrame", window.parent.document)[0].contentWindow.setTitle("首页", s, url);
}
</script>


</head>

<body style="background:url(resources/images/topbg.gif) repeat-x;">

    <div class="topleft">
    <a ></a>
    </div>
    <div class="topright">    
    <ul>
    <li><span><img src="resources/images/bt_home.gif" title="我的工作台"  class="helpimg" /></span><a href="welcome.do" target="rightFrame" onclick="setTitle('我的工作台','welcome.do');">我的工作台</a></li>
    <li><span><img src="resources/images/user_edit.png" title="修改密码"  class="helpimg"/></span><a  href="sys/userController.do?method=toChangePwd&userCode=${user.userCode}" target="rightFrame" onclick="setTitle('修改密码','sys/userController.do?method=toChangePwd&userCode=${user.userCode}');">修改密码</a></li>
    <li><span><img src="resources/images/help.png" title="帮助"  class="helpimg"/></span><a  href="javascript:void(0)">帮助</a></li>
    <li><span><img src="resources/images/bt_cancel.gif" title="退出系统"  class="helpimg"/></span><a href="javascript:void(0)" onclick="logout();" target="_parent">退出</a></li>
    </ul>
     
    <div class="user">
    <span>欢迎您，${user.userCode}-${user.userName}</span>
    </div>    
    
    </div>

</body>
</html>
