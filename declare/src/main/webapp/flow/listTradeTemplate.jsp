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
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="back" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-save"
           onclick="saveHandle();">保存</a>
        <!-- 
        <a id="back" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-back"
           onclick="viewHandle('processManageController?listDefProcess')">返回</a>
        -->
    </div>
</div>
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="交易流程信息">
        <table id="dg" class="easyui-datagrid" fit="true" 
			data-options="rownumbers:true,singleSelect:true,
				url:'${pageContext.request.contextPath}/flow/tradeTemplateController.do?method=queryTradeTemplate',
				onDblClickRow:onClickRow">
			<thead>
				<tr>
					<th data-options="field:'tradeNo',width:100,editor:'hidden',rowspan: 2">交易编号</th>
					<th data-options="field:'tradeName',width:200,editor:'hidden',rowspan: 2">交易名称</th>
					<th data-options="field:'isUsed',
						formatter:function(value,row){
								return getIsType(value);
							},editor:{
						    type:'combobox',
						     	options:{
									valueField:'code',
									textField:'name',
									data:isType,
									editable:false,
									required:true,
									onChange:isUsedCtrl
								}
						},width:120,rowspan: 2">是否启用工作流</th>
					<th data-options="field:'isHandle',
						formatter:function(value,row){
								return getIsType(value);
							},editor:{
						    type:'combobox',
						     	options:{
									valueField:'code',
									textField:'name',
									data:isType,
									editable:false,
									required:true,
									onChange:isHandleCtrl
								}
						},width:60,rowspan: 2">经办</th>
					<th data-options="field:'isCheck',
						formatter:function(value,row){
								return getIsType(value);
							},editor:{
						    type:'combobox',
						     	options:{
									valueField:'code',
									textField:'name',
									data:isType,
									editable:false,
									required:true,
									onChange:isCheckCtrl
								}
						},width:60,rowspan: 2">复核</th>
					<th data-options="field:'isAuth',
						formatter:function(value,row){
								return getIsType(value);
							},editor:{
						    type:'combobox',
						     	options:{
									valueField:'code',
									textField:'name',
									data:isType,
									editable:false,
									required:true
								}
						},width:60,rowspan: 2">授权</th>
					<th data-options="field:'opt',width:200,editor:'hidden',rowspan: 2,formatter:function(value,row){
						//var html = '<span style=\'color:blue\'><a href=\'###\' onclick=\'setPrivlege(\'+value+\')\'>查看流程权限</a>&nbsp;&nbsp;';
						//html = html + '<span style=\'color:blue\'><a href=\'###\' onclick=\'setPrivlege()\'>设置流程权限</a>';
						return getHrefHtml(row.tradeNo);
					}" >操作</th>
				</tr>
			</thead>
		</table>
    </div>
  </div>
</div>
<script type="text/javascript">
var isType = [{"code":"Y","name":"是"}, {"code":"N","name":"否"}];
function getIsType(code) {
	for(var json in isType) {
		if (isType[json].code == code) {
			return isType[json].name;
		}
	}
}

function getHrefHtml(tradeNo) {
	var html = '<span style=color:blue><a href=### onclick=showPrivlege(\''+tradeNo+'\')>查看流程权限</a>&nbsp;&nbsp;';
	return html;
}

function showPrivlege(tradeNo){
	$.dialog({ 
		title: '查看交易流程权限',
	    content: 'url:tradePrivilegeController.do?method=listTradePrivilege&tradeNo='+tradeNo, 
	    ok: function(){ 
	    	return true;
	    }, 
	    cancelVal: '取消', 
	    cancel: true,
	    width:500,
	    height:300,
	    lock:true
	});
}

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
			var tradeTempForms = $("#dg").datagrid('getChanges');
			if (tradeTempForms.length) { 
				effectRow["tradeTempForms"] = JSON.stringify(tradeTempForms); 
			} 		
			$('#dg').datagrid('acceptChanges');
			$.post("tradeTemplateController.do?method=update", effectRow, function(data) {
				if(data.code == "0"){
					$.messager.alert("系统提示", "保存成功!", "info");
				}else{
					$.messager.alert("系统提示", "保存失败!", "error");
				}		
			}); 
		}
	}
}

/***************************选项控制******************************/ 
 
function  isUsedCtrl(n,o){
	toggleEdit(n,'isHandle');
}

function  isHandleCtrl(n,o){
	toggleEdit(n,'isCheck');
}
 
function  isCheckCtrl(n,o){
	toggleEdit(n,'isAuth');
}


function toggleEdit(n,fieldName){
	var isDisabled = false;
	var val = "N";
	if("N"==n){
		isDisabled = true;
		val = "N";
	}else{
		isDisabled = false;
		val = "Y";
	}
	if( "isHandle" == fieldName){
		isDisabled = true;
	}
	$(".combobox-f.combo-f","[field="+fieldName+"]").combo({disabled:isDisabled});
	$(".combobox-f.combo-f","[field="+fieldName+"]").combobox("setValue",val);
}
</script>
</body>
</html>