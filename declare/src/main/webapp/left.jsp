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
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active");
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})

function setTitle(title, subTitle, url) {
	$("#rightHeadFrame", window.parent.document)[0].contentWindow.setTitle(title, subTitle, url);
}


function addTab(title, url) {
    $("#rightFrame", window.parent.document)[0].contentWindow.addTab(title, url);
}

</script>


</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span>功能菜单</div>
    <dl class="leftmenu">
	   <c:forEach var="menuGroup" items="${menuGroupList}">
	    	<dd>
			    <div class="title">
			    	<span><img src="resources/images/leftico01.png" /></span>${menuGroup.menuName}
			    </div>
		    	<ul class="menuson">
			    	<c:forEach var="menu" items="${menuList}">
				    	<c:if test="${menu.parentMenuId eq menuGroup.menuId }">
			        		<li ><cite></cite><a href="####"  onclick='addTab("${menu.menuName}","${menu.accessUrl }","");'>${menu.menuName}</a><i></i></li>
				        </c:if>
		       		</c:forEach>
		        </ul>
			</dd>
	    </c:forEach>


    <%-- <dd>
    <div class="title">
    <span><img src="resources/images/leftico01.png" /></span>管理信息
    </div>
    	<ul class="menuson">
        <li><cite></cite><a href="index.html" target="rightFrame">首页模版</a><i></i></li>
        <li class="active"><cite></cite><a href="right.html" target="rightFrame">数据列表</a><i></i></li>
        <li><cite></cite><a href="form.html" target="rightFrame">添加编辑</a><i></i></li>
        <li><cite></cite><a href="filelist.html" target="rightFrame">信息管理</a><i></i></li>
        <li><cite></cite><a href="tab.html" target="rightFrame">Tab页</a><i></i></li>
        <li><cite></cite><a href="error.html" target="rightFrame">404页面</a><i></i></li>
        </ul>
    </dd>

    <dd>
    <div class="title">
    <span><img src="resources/images/leftico02.png" /></span>其他设置
    </div>
    <ul class="menuson">
        <li><cite></cite><a href="#">编辑内容</a><i></i></li>
        <li><cite></cite><a href="#">发布信息</a><i></i></li>
        <li><cite></cite><a href="#">档案列表显示</a><i></i></li>
        </ul>
    </dd>

    <dd><div class="title"><span><img src="resources/images/leftico03.png" /></span>编辑器</div>
    <ul class="menuson">
        <li><cite></cite><a href="#">自定义</a><i></i></li>
        <li><cite></cite><a href="#">常用资料</a><i></i></li>
        <li><cite></cite><a href="#">信息列表</a><i></i></li>
        <li><cite></cite><a href="#">其他</a><i></i></li>
    </ul>
    </dd>  --%>
    </dl>
    
</body>
</html>
