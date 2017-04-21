<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function() {
	initDataGrid();
});

function initDataGrid(){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: 'roleController.do?method=query',
        sortName: 'sortNo',
        sortOrder: 'asc',
        idField: 'roleId',
        pageSize: 15,
        pageList: [10,15,20],
        columns: [[
					{ field: 'roleName', title: '角色名称', width: 200, sortable: true },
					{ field: 'isFix', title: '是否系统预置', width: 100, sortable: true, sortable: true, formatter:function(value){
						if(value=='Y'){
							return '是';
						}else{
							return '否';
						}
					}},
					{ field: 'sortNo', title: '排序', width: 80, sortable: true },
					{ field: 'remark', title: '角色描述', width: 300, sortable: false },
					{field:'opt',title:'操作',width:300,align:'center', rowspan:2,
						formatter:function(value,rec){
							var editUrl = "roleController.do?method=edit&roleId="+rec.roleId;
							var delUrl = "roleController.do?method=delete&roleId="+rec.roleId+"&isFix="+rec.isFix;
							var html = '<span style="color:blue"><a href="###" onclick="editHandle(\''+editUrl+'\')">编辑</a>&nbsp;&nbsp;';
							html += '<a href="###" onclick="deleteHandle(\''+delUrl+'\',\'grid\',\'\')">删除</a>&nbsp;&nbsp;';
							html += '<a href="###" onclick="doAuthOpeRight(\''+rec.roleId+'\',\''+rec.roleName+'\')">分配操作权限</a>&nbsp;&nbsp;';
							html += '<a href="###" onclick="doAuthDataRight(\''+rec.roleId+'\',\''+rec.roleName+'\')">分配机构权限</a></span>';
							return html;
						}
					}
				]],
        fit:true,
        queryParams:{}, //查询条件
        pagination: true,
        rownumbers: true,
        fitColumns: false,
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

/**授权*/
function doAuthOpeRight(roleId, roleName){
	$.dialog({ 
		title: '为角色['+roleName+']分配操作权限',
	    content: 'url:roleController.do?method=authRoleOpeRight&roleId='+roleId, 
	    ok: function(){ 
	        var jsonArray = this.content.onCheck();
	        if (jsonArray.length == 0) {
	        	alert("未勾选权限，请选择!");
	        	return false;
	        } 
	        var rightIds = JSON.stringify(jsonArray);
	        saveRoleRight(roleId, rightIds);
	        return true; 
	    }, 
	    cancelVal: '取消', 
	    cancel: true,
	    width:500,
	    height:400,
	    lock:true 
	});
}

function saveRoleRight(roleId, rightIds) {
	$.post("${pageContext.request.contextPath}/sys/roleController.do?method=saveRoleRight", {"roleId":roleId, "rightIds":rightIds}, function(data){
		$.messager.alert("系统提示", "角色权限分配成功", "info");
	});
}

function doAuthDataRight(roleId, roleName){
	$.dialog({ 
		title: '为角色['+roleName+']分配机构权限',
	    content: 'url:roleController.do?method=authRoleDataRight&roleId='+roleId+'&roleName='+encodeURI(encodeURI(roleName)), 
	    ok: function(){ 
	    	return this.content.saveRoleDataRight();
	    }, 
	    cancelVal: '取消', 
	    cancel: true,
	    width:600,
	    height:400,
	    lock:true 
	});
}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="add" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('roleController.do?method=edit')">新增</a>
    </div>
</div>
<div region="center" border="false">
  <div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 65px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">角色名称</th>
						<td><input type="text" id="roleName" name="roleName" class="inputText" value="${role.roleName}"/>&nbsp;&nbsp;
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