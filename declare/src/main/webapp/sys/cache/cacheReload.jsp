<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重载缓存</title>
<jsp:include page="../../common/include.jsp" />
</head>
<script type="text/javascript">
	function reload(){
		$.messager.progress({
			text:'正在重载缓存，请稍候...',
			interval : 100,
			timeout:60000
		});
		window.setTimeout("doReload()", 10000);
	}
	
	function doReload(){
		$.post("cacheReloadController?reload",function(data){
			$.messager.alert("系统提示", "缓存重载成功", "info");
			$.messager.progress('close');
		});
	}
</script>
<body>
<div class="info">机构、角色、权限、菜单、数据字典等基础数据发生变化后需重载缓存后数据才可使用。</div>
&nbsp;&nbsp;<input type="button" value="重载缓存" onclick="reload();" class="inputButton" />
</body>
</html>