<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="e" uri="/WEB-INF/tld/dicitem.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户编辑</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
var flag="${flag}";
$(function(){
	//loadOrgTree('orgId');
	//用户ID不为空时，表示修改操作，不允许更改登录帐号
	var userid = "${user.userId}";
	if (userid != "") {
		$("#userCode").attr("disabled", true);
		$("#orgId").combobox({disabled:true}).combobox("setValue", "${user.orgId }");
		$("#orgId").combobox({disabled:true}).combobox("setText", "${user.orgName }");
	}else{
		$("#userCode").attr("disabled", false);
	}
});

function save() {
	if ($('#fo').form('validate') == true) {
		$.post('userController.do?method=save', $("#fo").serializeArray(), function(data) {
			$("#userId").val(data.userId);
			$("#userCode").attr("disabled", true);
			$.messager.alert('系统提示', "保存成功", 'info');
		});
	}
}
</script>
<body>
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="addBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('userController.do?method=edit')">新增</a>
        <a id="saveBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="save();">保存</a>
        <a id="listBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-list"
           onclick="listHandle('userController.do?method=list')">列表</a>
    </div>
</div>
<div region="center" border="false">
<div class="editBlock">
	<form id="fo" name="form1" method="post" action="#">
		<input type="hidden" id="userId" name="userId" value="${user.userId }" />
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="4" class="subtitle">基本信息</td>
			</tr>
			<tbody>
				<tr>
					<th width="15%" ><span class="warning">*</span>登录帐号</th>
					<td width="30%">
						<input type="text" id="userCode" name="userCode" value="${user.userCode }" class="easyui-validatebox inputText" data-options="required:true" />
					</td>
					<th><span class="warning">*</span>姓名</th>
					<td>
						<input type="text" id="userName" name="userName" value="${user.userName }" class="easyui-validatebox inputText" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th width="15%" ><span class="warning">*</span>所属机构</th>
					<td width="30%">
						<%-- <input id="orgId" name="orgId" class="easyui-combotree" style="width:250px;" data-options="required:true" value="${user.orgId }"/> --%>
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
					<th width="15%" >性别</th>
					<td width="30%">
						<e:select id="sex" name="sex" diccode="SEX" clazz="easyui-combobox" style="width:250px;" value="${user.sex }"></e:select>
					</td>
				</tr>
				<tr>
					<th width="15%" >办公电话</th>
					<td width="30%">
						<input type="text" id="officeTel" name="officeTel" value="${user.officeTel }" class="inputText" />
					</td>
					<th>手机号码</th>
					<td>
						<input type="text" id="mobileNo" name="mobileNo" value="${user.mobileNo }" class="inputText" />
					</td>
				</tr>
				<tr>
					<th width="15%" >Email</th>
					<td width="30%">
						<input type="text" id="email" name="email" value="${user.email }" class="inputText" />
					</td>
					<th>MSN</th>
					<td>
						<input type="text" id="msn" name="msn" value="${user.msn }" class="inputText"/>
					</td>
				</tr>
				<tr>
					<th width="15%" >QQ</th>
					<td colspan="3">
						<input type="text" id="qq" name="qq" class="inputText" value="${user.qq}" />
					</td>
				</tr>
				<tr>
					<th width="15%" >描述</th>
					<td colspan="3">
						<textarea cols="100" rows="4" class="textarea" id="remark" name="remark" style="width:400px" >${user.remark}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
</div>
</body>
</html>
