<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册权限</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
function save() {
	if ($('#fo').form('validate') == true) {
		$.post('operateController.do?method=save', $("#fo").serializeArray(), function(data) {
			$("#operateId").val(data.operateId);
			$.messager.alert('系统提示', "保存成功", 'info');
		});
	}
}
</script>
<body>
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="addBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('operateController.do?method=edit')">新增</a>
        <a id="saveBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="save();">保存</a>
        <a id="listBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-list"
           onclick="listHandle('operateController.do?method=list')">列表</a>
    </div>
</div>
<div region="center" border="false">
<div class="editBlock">
	<form id="fo" action="" method="post">
		<input type="hidden" id="operateId" name="operateId"  value="${operate.operateId}" />
		<table>
			<tr>
				<td colspan="4" class="subtitle">基本信息</td>
			</tr>
			<tbody>
				<tr>
					<th width="25%"><span class="warning">*</span>所属菜单</th>
					<td colspan="3" >
						<select id="menuId" name="menuId" class="easyui-combotree" url="${basePath}/sys/menuController.do?method=loadMenuTree" style="width: 250px;" data-options="required:true"></select>
					</td>
				</tr>
				<tr>
					<th width="25%"><span class="warning">*</span>权限名称</th>
					<td colspan="3" >
						<input type="text" id="operateName" name="operateName"  value="${operate.operateName}" class="easyui-validatebox inputText" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th width="25%"><span class="warning">*</span>请求URL</th>
					<td colspan="3">
						<input type="text" id="reqUrl" name="reqUrl" class="easyui-validatebox inputText" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th width="25%">是否可见</th>
					<td colspan="3">
						<label><input type="radio" id="isCheck" name="isCheck" value="Y" checked="checked"/>显示</label>
						<label><input type="radio" id="isCheck" name="isCheck" value="N"/>隐藏</label>
					</td>
				</tr>
				<tr>
					<th width="25%">排序</th>
					<td colspan="3">
						<input type="text" id="sortNo" name="sortNo" class="easyui-numberbox inputText" data-options="required:false"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
</div>
</body>
</html>
