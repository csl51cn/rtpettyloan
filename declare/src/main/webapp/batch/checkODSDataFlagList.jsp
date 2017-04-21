<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>批处理标志位</title>
<jsp:include page="../common/include.jsp"></jsp:include>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="back" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-save"
           onclick="saveHandle();">保存</a>
    </div>
</div>
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="批处理标志位信息">
        <table id="dg" class="easyui-datagrid" fit="true" 
			data-options="rownumbers:true,singleSelect:true,toolbar:'#tb',
				url:'${pageContext.request.contextPath}/batchInterfacesController.do?method=pageListCheckODSDataFlag',
				onDblClickRow:onClickRow">
			<thead>
				<tr>
					<th data-options="field:'tmpFlag',width:120,editor:'hidden',hidden:'true',rowspan: 2">临时标志判断是否保存了数据库</th>
					<th data-options="field:'taskId',width:120,editor:{type:'validatebox',options:{required:true}},rowspan: 2">批处理任务ID</th>
					<th data-options="field:'rate',width:80,editor:{type:'combobox',
								options:{
									panelWidth:80,
									panelHeight:'auto',
									valueField:'code',
									textField:'name',
									data:rateType,
									editable:false,
									required:true
								}
							}, formatter:function(value) {
								if (value == 'M') {
									return '按月';
								} else if (value == 'D'){
									return '按日';
								}
							},rowspan: 2">频度</th>
					<th data-options="field:'preProcDate',width:120,rowspan: 2,editor:{type:'datebox',
								options:{
									required: true,
									editable:false,
									formatter:myformatter,
									parser:myparser
								}
							},formatter:function(value) {
								if (value != undefined) {
									return value.substring(0,11);
								}
							} ">预处理日期</th>
					<th data-options="field:'flag',width:130,editor:{type:'combobox',
								options:{
									panelWidth:130,
									panelHeight:'auto',
									valueField:'code',
									textField:'name',
									data:checkFlag,
									editable:false,
									required:true
								}
							}, formatter:function(value) {
								if (value == 'Y') {
									return '已处理';
								} else {
									return '未处理';
								}
							},rowspan: 2">状态</th>
					<th data-options="field:'startTime',width:130,editor:'hidden',rowspan: 2">开始时间</th>
					<th data-options="field:'endTime',width:130,editor:'hidden',rowspan: 2">完成时间</th>
					<th data-options="field:'procCount',width:80,editor:'hidden',rowspan: 2">处理笔数</th>
				</tr>
			</thead>
		</table>
    </div>
  </div>
</div>
<div id="tb" style="height:auto">
	<a id="addRow" href="javascript:append();" class="easyui-linkbutton" plain="true" iconCls="icon-add">增加</a>
	<a id="removeRow" href="javascript:remove();" class="easyui-linkbutton" plain="true" iconCls="icon-remove">删除</a>
</div>
<script type="text/javascript">
var checkFlag = [{"code":"Y","name":"已处理"},{"code":"N","name":"未处理"}];
var rateType = [{"code":"D","name":"按日"},{"code":"M","name":"按月"}];

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){
		return true;
	}
	if ($('#dg').datagrid('validateRow', editIndex)){
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
			var flag = $("#dg").datagrid('getEditor', {index: editIndex, field:'flag'}).target;
			if (flag.combobox('getValue') == 'Y') {
				$('#dg').datagrid('acceptChanges');
				return;
			}
			var taskId = $("#dg").datagrid('getEditor', {index: editIndex, field:'taskId'}).target;
			taskId.attr('disabled', true);
			var rate = $("#dg").datagrid('getEditor', {index: editIndex, field:'rate'}).target;
			rate.combobox('disable');
		} else {
			$('#dg').datagrid('selectRow', editIndex);
		}
	}
}

function append(){
	if (endEditing()){
		$('#dg').datagrid('appendRow',{tmpFlag:'Y', flag:'N'});//tmpFlag=Y时表示该笔数据处于页面新建状态，未保存入库，以便删除操作时使用
		editIndex = $('#dg').datagrid('getRows').length-1;
		$('#dg').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
		//增加时不允许更改FLAG标识
		var flag = $("#dg").datagrid('getEditor', {index: editIndex, field:'flag'}).target;
		flag.combobox('disable');
	} 
}

function remove(){
	var row = $("#dg").datagrid("getSelected");
	if (row == null || row.length == 0) {
		$.messager.alert("系统提示","请选择要删除的行数据","error");
		return;
	}
	editIndex = $('#dg').datagrid('getRowIndex', row);//获取选中行的索引
	
	$.messager.confirm("系统提示","确定要删除该行数据?删除后该数据不可恢复",function(r){
		if(r){
			//调用后台删除操作
			if(row.tmpFlag != 'Y' && !isEmpty(row.taskId) && !isEmpty(row.preProcDate) && !row.flag != 'Y'){//判断是否需要删除后台数据
				$.post("${pageContext.request.contextPath}/batchInterfacesController.do?method=deleteODSDataFlag",
						{"taskId":row.taskId,"preProcDate":row.preProcDate}, function(data){
					$.messager.alert("系统提示", "删除操作成功", "info");
				}); 
			}
			$('#dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
			editIndex = undefined;
		}
	});
}


function saveHandle() {
	if(endEditing()){
		var effectRow = new Object();
		if ($("#dg").datagrid('getChanges').length) {
			var changeDatas = $("#dg").datagrid('getChanges');
			if (changeDatas.length) { 
				effectRow["changeDatas"] = JSON.stringify(changeDatas); 
			} 
			$('#dg').datagrid('acceptChanges');
			$.post("${pageContext.request.contextPath}/batchInterfacesController.do?method=saveODSDataFlag", effectRow, function(data) {
				$.messager.alert("系统提示", "保存成功", "info");
			}); 
		} else {
			$.messager.alert("系统提示", "未修改任何信息", "warning");
		}
	}
}
</script>
</body>
</html>