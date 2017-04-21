<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色操作授权</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<link rel="stylesheet" href="../resources/jquery-ztree-3.0/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="../resources/jquery-ztree-3.0/js/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="../resources/jquery-ztree-3.0/js/jquery.ztree.excheck-3.0.min.js"></script>
<script type="text/javascript">
var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},callback:{
            onCheck:onCheck
        }
	};  
		  
var treeNodes;
$(function() {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : basePath + "/sys/operateController.do?method=loadRightTree&roleId=${roleId}",//请求的action路径  
		success : function(data) { //请求成功后处理函数。    
			treeNodes = eval("[" + data + "]");; //把后台封装好的简单Json格式赋给treeNodes  
		}
	});
	
	$.fn.zTree.init($("#roleRight"), setting, treeNodes);
});

function onCheck(){
    var treeObj=$.fn.zTree.getZTreeObj("roleRight"),
    nodes=treeObj.getCheckedNodes(true);
    var jsonArray = new Array();
    for(var i=0;i<nodes.length;i++){
	    var jsonStr = {"rightId":nodes[i].id, "rightType":nodes[i].rightType};
	    jsonArray.push(jsonStr);
    }
    //alert(JSON.stringify(jsonArray));
    return jsonArray;
}
</script>
<body>
<ul id="roleRight" style="margin-top: 5px;" class="ztree"></ul>
</body>
</html>