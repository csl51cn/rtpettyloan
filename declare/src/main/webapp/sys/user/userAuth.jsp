<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户授权</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
</head>
<script type="text/javascript">
var userId = "${userId}";
var grid;
$(function(){
	initDataGrid();
});

function initDataGrid() {
	grid = $('#grid').datagrid(
		{
			methord : 'post',
			url : basePath+'/sys/roleController.do?method=query',
			sortName : 'sortNo',
			sortOrder : 'asc',
			idField : 'roleId',
			frozenColumns : [ [
					{
						field : 'ck',
						checkbox : true
					} ] ],
			columns : [ [ {
				field : 'roleName',
				title : '角色名称',
				width : 300,
				sortable : true
			}, {
				field : 'remark',
				title : '角色描述',
				width : 300,
				sortable : false
			} ] ],
			fit : true,
			queryParams : {}, //查询条件
			pagination : false,
			rownumbers : true,
			fitColumns : true,
			striped : true, //奇偶行颜色不同 
			remoteSort : true, //服务器端排序
			singleSelect : false,
			onLoadSuccess: function(){
				$.post("userController.do?method=getRolesByUserId", {"userId":userId}, function(data){
					$.each(data,function(index,value){
						$("#grid").datagrid("selectRecord",value.roleId); 
					});
				});
			}
		});
}

function saveUserRole(){
	var rows = $('#grid').datagrid('getSelections');
	if (rows.length) {
		$.each(rows, function(index, row){
			insertJson(row, "userId", userId);
		});
		$.post("${pageContext.request.contextPath}/sys/userController.do?method=saveUserRole", {userRoles:JSON.stringify(rows)}, function(data) {
			alert("用户角色分配成功");
		});
		return true;
	} else {
		$.messager.alert("系统提示", "请选择需要分配的角色", "error");
		return false;
	}
}
</script>
<body class="easyui-layout" >
<div region="center" style="width: 500px; height: 300px; padding: 1px;" title="角色分配">
    <div id="grid" > </div>
</div>
</body>
</html>