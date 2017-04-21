<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增菜单</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	var isFun = "${menu.isFunction}";
	if (isFun == 'Y') {
		$("#isFunction").attr("checked", true);
		$("#url").show();
	} else {
		$("#url").hide();
	}
});
function saveMenu(){
	if ($('#fo').form('validate') == true) {
		$.post('menuController.do?method=save', $("#fo").serializeArray(), function(data) {
			$("#menuId").val(data.menuId);
			$.messager.alert('系统提示', "保存成功", 'info');
		});
	}
	return true;
}
</script>
<body>
<div class="editBlock">
	<form id="fo" action="" method="post">
		<table>
			<tbody>
				<tr>
					<th width="15%" ><span class="warning">*</span>上级菜单</th>
					<td colspan="3" >
						<input type="hidden" id="menuId" name="menuId" value="${menu.menuId}" /> 
						<input type="hidden" id="parentMenuId" name="parentMenuId" value="${menu.parentMenuId}" /> 
						<input type="text" id="parentMenuName" name="parentMenuName" class="inputText" value="${menu.parentMenuName}" disabled="disabled" />(增加一级菜单时上级菜单为空) 
					</td>
				</tr>
				<tr>
					<th width="15%" ><span class="warning">*</span>菜单名称</th>
					<td colspan="3" >
						<input type="text" id="menuName" name="menuName" class="easyui-validatebox inputText" data-options="required:true" value="${menu.menuName}" />
					</td>
				</tr>
				<tr>
					<th width="15%" ><span class="warning">*</span>是否功能</th>
					<td colspan="3" >
						<label>
							<input type="radio" id="isFunction" name="isFunction" value="Y" onclick="$('#url').show();" />
							是
						</label>
						<label>
							<input type="radio" id="isFunction" name="isFunction" value="N" checked="checked" onclick="$('#url').hide();" />
							否
						</label>
					</td>
				</tr>
				<tr id="url" style="display: none;">
					<th width="15%" >请求URL</th>
					<td colspan="3" >
						<input type="text" id="accessUrl" name="accessUrl" class="easyui-validatebox inputText" data-options="required:false" value="${menu.accessUrl}" />
					</td>
				</tr>
				<tr>
					<th width="15%" ><span class="warning">*</span>排序</th>
					<td colspan="3" >
						<input type="text" id="sortNo" name="sortNo" class="easyui-numberbox inputText" data-options="required:true" value="${menu.sortNo}" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
</body>
</html>
