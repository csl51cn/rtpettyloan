<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易流程权限列表</title>
<jsp:include page="../common/include.jsp"></jsp:include>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="交易流程权限列表">
        <table id="dg" class="easyui-datagrid" fit="true" 
			data-options="rownumbers:true,singleSelect:true,
				url:'${pageContext.request.contextPath}/flow/tradePrivilegeController.do?method=queryTradePrivilege&tradeNo=${tradeNo}',
				onDblClickRow:onClickRow">
			<thead>
				<tr>
					<th data-options="field:'tradeNo',width:100,editor:'hidden',rowspan: 2">交易编号</th>
					<th data-options="field:'privId',width:80,editor:'hidden',rowspan: 2">权限ID</th>
					<th data-options="field:'opeId',width:80,editor:'hidden',rowspan: 2">流程步骤</th>
				</tr>
			</thead>
		</table>
    </div>
  </div>
</div>
<script type="text/javascript">
var isType = [{"code":"Y","name":"是"}, {"code":"N","name":"否"}];
var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){
		return true;
	}
	if ($('#dg').datagrid('validateRow', editIndex)){
		//var menuId_value=getComboTreeValue('dg','menuId',editIndex);
		//$('#dg').datagrid('getRows')[editIndex]['menuId_text'] = menuId_value;
		$('#dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#dg').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#dg').datagrid('selectRow', editIndex);
		}
	}
}

function saveHandle() {
	if(endEditing()){
		var effectRow = new Object();
		if ($("#dg").datagrid('getChanges').length) {
			var privilegeForms = $("#dg").datagrid('getChanges');
			if (privilegeForms.length) { 
				effectRow["privilegeForms"] = JSON.stringify(privilegeForms); 
			} 
			$('#dg').datagrid('acceptChanges');
			$.post("tradePrivilegeController.do?method=update", effectRow, function(data) {
				if(data.code == "0"){
					$.messager.alert("系统提示", "保存成功!", "info");
				}else{
					$.messager.alert("系统提示", "保存失败!", "error");
				}			
			}); 
		}	
	}
}
</script>
</body>
</html>