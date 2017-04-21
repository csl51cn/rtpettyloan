<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function () {
	initDataGrid();    
    //loadOrgTree('orgId');
});

function initDataGrid(){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: 'userController.do?method=query',
        sortName: 'userId',
        sortOrder: 'asc',
        idField: 'userId',
        pageSize: 15,
        pageList: [10,15,20],
        frozenColumns: [[
	                { field: 'ck', checkbox: true },
				]],
        columns: [[
					{ field: 'userCode', title: '登录帐号', width: 80, sortable: true },
					{ field: 'userName', title: '姓名', width: 50, sortable: true },
					{ field: 'sex', title: '性别', width: 30, sortable: true, formatter:function(rowIndex){
						if(rowIndex==1){
							return '男';
						}else if(rowIndex==2){
							return '女';
						}
					} },
					{ field: 'orgName', title: '机构', width: 100, sortable: true },
					{ field: 'mobileNo', title: '手机号码', width: 80, sortable: true },
					{ field: 'status', title: '状态', width: 30, sortable: true, sortable: true, formatter:function(rowIndex){
						if(rowIndex=='Y'){
							return '<span><img src="${basePath}/resources/images/onSuccess.gif" title="激活"/></span>';
						}else{
							return '<span><img src="${basePath}/resources/images/onError.gif" title="停用"/></span>';
						}
					} },
					{field:'opt',title:'操作',width:120,align:'center', rowspan:2,
						formatter:function(value,rec){
							var editUrl = "userController.do?method=edit&userId="+rec.userId;
							var delUrl = "userController.do?method=delete&userId="+rec.userId;
							return '<span style="color:blue"><a href="###" onclick="editHandle(\''+editUrl+'\')">编辑</a>&nbsp;&nbsp;'+
								   '<a href="###" onclick="deleteHandle(\''+delUrl+'\',\'grid\',\'\')">删除</a>&nbsp;&nbsp;'+
								   '<a href="###" onclick="doUserAuth(\''+rec.userId+'\',\''+rec.userName+'\')">分配角色</a></span>';
						}
					}
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
            	selectRowEvent('grid');
            }, 10);    
    	}
    });
}


function doUserAuth(userId, userName){
	//window.parent.frames['mainFrame'].
	$.dialog({ 
		title: '为用户['+userName+']分配角色',
	    content: 'url:userController.do?method=userAuth&userId='+userId, 
	    ok: function(){ 
	    	return this.content.saveUserRole();
	    }, 
	    cancelVal: '取消', 
	    cancel: true,
	    width:500,
	    height:400,
	    lock:true 
	});
}

function getSelectUserIds(status,flag){
	var ids = [];
	var rows = $('#grid').datagrid('getSelections');
	for(var i=0; i<rows.length; i++){
		if(!flag){
			if(status=='Y' && rows[i].status=='Y'){
				$.messager.alert("系统提示", "["+rows[i].userCode+"]用户已为激活状态！", "info");
				return false;
			}else if(status=='N' && rows[i].status=='N'){
				$.messager.alert("系统提示", "["+rows[i].userCode+"]用户已为停用状态！", "info");
				return false;
			}
		}
	    ids.push(rows[i].userId);
	}
	if(ids==''){
		$.messager.alert("系统提示", "请选择用户信息后操作", "error");
		return false;
	}
	return ids;
}

function batchActivate(){
	var ids = getSelectUserIds('Y');
	if(ids != false){
		$.messager.confirm("系统提示", "您确定要激活所选用户吗？", function(r) {
			if (r) {
				$.post("userController.do?method=update&userIds="+ids+"&status=Y", function(data){
					$.messager.alert("系统提示", "激活用户成功", "info");
					$('#grid').datagrid('reload'); 
				});
			}
		});
	}
}

function batchStop(){
	var ids = getSelectUserIds('N');
	if(ids != false){
		$.messager.confirm("系统提示", "您确定要停用所选用户吗？", function(r) {
			if (r) {
				$.post("userController.do?method=update&userIds="+ids+"&status=N", function(data){
					$.messager.alert("系统提示", "停用用户成功", "info");
					$('#grid').datagrid('reload'); 
				});
			}
		});
	}
}

function resetPwd(){
	var ids = getSelectUserIds('',true);
	if(ids != false){
		$.messager.confirm("系统提示", "您确定要重置所选用户密码吗？重置后初始密码变为a123456", function(r) {
			if (r) {
				$.post("userController.do?method=batchChangePwd&userIds="+ids, function(data){
					$.messager.alert("系统提示", "重置密码成功", "info");
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
           onclick="createHandle('userController.do?method=edit')">新增</a>
        <a id="activate" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-unlock"
           onclick="batchActivate()">激活</a>
        <a id="stop" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-lock"
           onclick="batchStop()">停用</a>
        <a id="resetPwd" title="重置密码后，密码为123456" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-undo"
           onclick="resetPwd()">重置密码</a>
    </div>
</div>
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 92px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">机构</th>
						<td width="20%">
							<!-- <input id="orgId" name="orgId" class="easyui-combotree" style="width:205px;" /> -->
							<select id="orgId" name="orgId" class="easyui-combogrid" style="width:250px" data-options="
								panelWidth: 450,
								sortName : 'sortNo',
								sortOrder : 'asc',
								idField: 'orgId',
								textField: 'orgName',
								url: '${pageContext.request.contextPath}/sys/orgController.do?method=loadRightOrgList',
								columns: [[
									{field:'orgCode',title:'机构编码',width:70},
									{field:'orgName',title:'机构名称',width:200}
								]],
								fit:true,
						        rownumbers: true,
						        fitColumns: true,
						        striped: true, 
						        remoteSort: true, 
						        required: true,
						        singleSelect: false
							" ></select>
						</td>
						<th width="10%">登录帐号</th>
						<td><input type="text" id="userCode" name="userCode" class="inputText"/></td>
					</tr>
					<tr>
						<th width="10%">姓名</th>
						<td width="20%"><input type="text" id="userName" name="userName" class="inputText"/></td>
						<th width="10%">状态</th>
						<td>
							<select id="status" name="status" class="easyui-combobox" editable="false" style="width:205px;">
								<option value="" selected="selected">全部</option>
								<c:forEach items="${status}" var="status">
									<option value="${status.code}">${status.value}</option>
								</c:forEach>
							</select>&nbsp;&nbsp;
							<input id="save" type="button" class="inputButton" value="查询" onclick="queryHandle('grid','queryForm');"/> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="用户列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>
</html>