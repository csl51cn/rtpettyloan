<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function() {
	initDataGrid();
});

function initDataGrid(){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: 'operateController.do?method=query',
        sortName: 'menuId',
        sortOrder: 'asc',
        idField: 'operateId',
        pageSize: 20,
        pageList: [10,20,50],
        frozenColumns: [[
     	                { field: 'ck', checkbox: true }
     				]],
        columns: [[
					{ field: 'operateName', title: '权限名称', width: 100, sortable: true },
					{ field: 'menuName', title: '功能模块', width: 100, sortable: true },
					{ field: 'reqUrl', title: '请求URL', width: 300, sortable: false },
					{ field: 'isCheck', title: '是否可见', width: 80, sortable: false },
					{ field: 'sortNo', title: '排序', width: 30, sortable: true }
				]],
        fit:true,
        queryParams:{}, //查询条件
        pagination: true,
        rownumbers: true,
        fitColumns: true,
        striped: true, //奇偶行颜色不同 
        remoteSort: true, //服务器端排序
        singleSelect: false,//多选
        onLoadSuccess: function(){
            setTimeout(function(){
            	selectRowEvent('grid');//单击行不选中
            }, 10);    
    	}
    });
}

function batchDelRight() {
	var ids = new Array();
	var rows = $('#grid').datagrid('getSelections');
	for(var i=0; i<rows.length; i++){
		var idJson = {"operateId" : rows[i].operateId};
	    ids.push(idJson);
	}
	if(ids == undefined || ids == ''){
		$.messager.alert('系统提示','请选择需要删除的记录!','warning');
	}else{
		$.messager.confirm("系统提示","您确定要删除所选记录吗？此操作不可逆！",function(r){
			if(r){
				$.post("operateController.do?method=delete", {"operateIds":JSON.stringify(ids)}, function (data) {
					$.messager.alert("系统提示", "删除权限成功", "info");
					$('#grid').datagrid('reload');
				});
			}
		});
	}
}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="add" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('operateController.do?method=edit')">新增</a>
        <a id="delete" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-del"
           onclick="batchDelRight();">删除</a>
    </div>
</div>
<div region="center" border="false">
  <div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 67px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">所属菜单</th>
						<td width="20%">
							<select id="menuId" name="menuId" class="easyui-combotree" url="${basePath}/sys/menuController?loadMenuTree" style="width: 205px;" >
							</select>
						</td>
						<th width="10%">权限名称</th>
						<td><input type="text" id="operateName" name="operateName" class="inputText"/>&nbsp;&nbsp;
							<input id="query" type="button" class="inputButton" value="查询" onclick="queryHandle('grid','queryForm');"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="角色列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>
</html>