<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色数据授权</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
</head>
<script type="text/javascript">
$(function(){
	checkedActionType();
	checkedRightType();
});

function checkedActionType() {
	$("input[name=actionType]:eq(${dataRight.actionType})").attr("checked",'checked'); 
}

function checkedRightType() {
	var rightType = "${dataRight.rightType}";
	$("input[name=rightType]:eq("+rightType+")").attr("checked",'checked'); 
	changeRightType(rightType);
}

function changeRightType(rightType) {
	if (rightType == "3") {
		var rightOrgNoList = "${dataRight.orgNoList}";
		var rightOrgNos = rightOrgNoList.split(",");
		$("#orgNoList").combogrid("setValues", rightOrgNos);
		$("#orgNoListDiv").show();
	} else {
		$("#orgNoListDiv").hide();
	}
}

function saveRoleDataRight() {
	var roleId = $("#roleId").val();
	var actionType = $("input[name='actionType']:checked").val();
	var rightType = $("input[name='rightType']:checked").val();
	var orgNoList = $("#orgNoList").combogrid("getValues");
	/* if (actionType == undefined || actionType == "") {
		$.messager.alert("系统提示", "请选择操作类型", "error");
		return false;
	} */
	if (rightType == undefined || rightType == "") {
		$.messager.alert("系统提示", "请选择权限类型", "error");
		return false;
	}
	if (rightType == "3" && (orgNoList == undefined || orgNoList == "")) {
		$.messager.alert("系统提示", "权限类型为指定机构时必须选择需要指定的机构", "error");
		return false;
	}
	var roleDataRights = {"roleId":roleId,"actionType":actionType,"rightType":rightType};
	if (rightType == "3") {
		roleDataRights["orgNoList"] = orgNoList.join(",");
	}
	$.post("${pageContext.request.contextPath}/sys/roleController.do?method=saveRoleDataRight", {"roleDataRights":JSON.stringify(roleDataRights)}, function(data){
		alert("角色数据权限设置成功");
	});
	return true;
}
</script>
<body class="easyui-layout" >
<div region="center" style="width: 500px; height: 105px; padding: 1px;" title="数据权限设置">
	<form id="roleDataRightForm" action="#" method="post">
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">角色名称</th>
						<td width="20%">
							<input type="hidden" id="roleId" name="roleId" class="inputText" value="${roleId}"/>
							<input type="text" id="roleName" name="roleName" class="inputText" value="${roleName}" disabled="disabled"/>
						</td>
					</tr>
					<tr style="display: none;">
						<th width="10%">操作类型</th>
						<td width="20%">
							<label><input type="radio" id="actionType" name="actionType" value="0" checked="checked"/>全部</label>&nbsp;&nbsp;
							<label><input type="radio" id="actionType" name="actionType" value="1" />查看</label>&nbsp;&nbsp;
							<label><input type="radio" id="actionType" name="actionType" value="2" />操作</label>&nbsp;&nbsp;
						</td>
					</tr>
					<tr>
						<th width="10%">权限类型</th>
						<td width="20%">
							<label><input type="radio" id="rightType" name="rightType" value="0" onclick="changeRightType(0)"/>本人</label>&nbsp;&nbsp;
							<label><input type="radio" id="rightType" name="rightType" value="1" onclick="changeRightType(1)"/>本机构</label>&nbsp;&nbsp;
							<label><input type="radio" id="rightType" name="rightType" value="2" onclick="changeRightType(2)"/>本机构及下辖机构</label>&nbsp;&nbsp;
							<label><input type="radio" id="rightType" name="rightType" value="3" onclick="changeRightType(3)"/>指定机构</label>&nbsp;&nbsp;
						</td>
					</tr>
					<tr id="orgNoListDiv" style="display: none;">
						<th width="10%">选择机构</th>
						<td >
							<select id="orgNoList" name="orgNoList" class="easyui-combogrid" style="width:250px" data-options="
								panelWidth: 450,
								multiple: true,
								sortName : 'sortNo',
								sortOrder : 'asc',
								idField: 'orgId',
								textField: 'orgName',
								url: basePath + '/sys/orgController.do?method=loadRightOrgList',
								columns: [[
									{field:'ck',checkbox:true},
									{field:'orgCode',title:'机构编码',width:70},
									{field:'orgName',title:'机构名称',width:200}
								]],
								fitColumns: true,
								editable: false,
								pageSize: 15,
								pagination: true
							">
						</select>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
</div>
</body>
</html>