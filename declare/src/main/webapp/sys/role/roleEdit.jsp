<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑角色</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	var isFix = "${role.isFix}";
	if (!isFix){
		$("#isFix").combobox("setValue", "N");
	}
	if(isFix == "Y") {
		$("#roleName").attr("disabled", true);
	}
	$("#isFix").combobox("disable");
});

function save() {
	if ($('#fo').form('validate') == true) {
		$.post('roleController.do?method=save', $("#fo").serializeArray(), function(data) {
			$("#roleId").val(data.roleId);
			$.messager.alert('系统提示', "保存成功", 'info');
		});
	}
}
</script>
<body>
<form id="fo" action="" method="post">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="addBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('roleController.do?method=edit')">新增</a>
        <a id="saveBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="save();">保存</a>
        <a id="listBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-list"
           onclick="listHandle('roleController.do?method=list')">列表</a>
    </div>
</div>
<div region="center" border="false">
<div class="editBlock">
	<input type="hidden" id="roleId" name="roleId" value="${role.roleId}" />
		<table>
			<tr>
				<td colspan="4" class="subtitle">基本信息</td>
			</tr>
			<tbody>
				<tr>
					<th width="15%"><span class="warning">*</span>角色名称</th>
					<td colspan="3" >
						<input type="text" id="roleName" name="roleName" value="${role.roleName}" class="easyui-validatebox inputText" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th width="15%">是否系统预置</th>
					<td colspan="3">
						<input class="easyui-combobox" id="isFix" name="isFix" style="width:250px;"
							data-options="
								panelWidth: 250,
								panelHeight: 70,
								valueField: 'code',
								textField: 'name',
								data: isFix,
								fitColumns: true,
								required:true" value="${role.isFix}" />
					</td>
				</tr>
				<tr>
					<th width="15%">排序</th>
					<td colspan="3">
						<input type="text" id="sortNo" name="sortNo" value="${role.sortNo }" class="inputText" />
					</td>
				</tr>
				<tr>
					<th width="15%">角色描述</th>
					<td colspan="3">
						<textarea cols="100" rows="4" class="textarea" id="remark" name="remark" style="width:400px" >${role.remark}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</div>
</form>
</body>
</html>
