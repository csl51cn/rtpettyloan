<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>批处理日志列表</title>
<jsp:include page="../common/include.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function() {
	initDataGrid();
});

function initDataGrid(){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: '${pageContext.request.contextPath}/batchInterfacesController.do?method=pageListBatchProcLog',
        sortName: 'endTime',
        sortOrder: 'desc',
        idField: 'logId',
        pageSize: 15,
        pageList: [10,15,20],
        frozenColumns: [[
     	                { field: 'ck', checkbox: true },
     				]],
        columns: [[
					{ field: 'taskId', title: '任务ID', width: 80, sortable: true },
					{ field: 'taskName', title: '任务名称', width: 200, sortable: true },
					{ field: 'startTime', title: '开始时间', width: 130, sortable: true },
					{ field: 'endTime', title: '结束时间', width: 130, sortable: true },
					{ field: 'state', title: '状态', width: 80, sortable: true, sortable: true, formatter:function(value){
						if(value=='1'){
							return '成功';
						}else{
							return '失败';
						}
					}},
					{ field: 'errorDesc', title: '备注', width: 650, sortable: false, formatter:function(value){
						return '<span title=\''+value+'\'>'+value+'</span>';
					} }
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

function deleteLog() {
	var rows = $('#grid').datagrid('getSelections');
	if(rows.length == 0){
		$.messager.alert("系统提示", "请选择要删除的记录!", "error");
		return;
	} else {
		$.messager.confirm("系统提示", "您确定要删除所选中的记录吗？", function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/batchInterfacesController.do?method=deleteBatchProcLog",{"rows" : JSON.stringify(rows)}, function(data){
					$.messager.alert("系统提示", "删除批处理日志成功", "info");
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
        <a id="del" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-del"
           onclick="deleteLog()">删除</a>
    </div>
</div>
<div region="center" border="false">
  <div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 91px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">任务ID</th>
						<td>
							<input type="text" id="taskId" name="taskId" class="inputText"/>
						</td>
						<th width="10%">任务名称</th>
						<td>
							<input type="text" id="taskName" name="taskName" class="inputText"/>
						</td>
					</tr>
					<tr>
						<th width="10%">批处理结束时间</th>
						<td>
							<input id="bEndTime" name="bEndTime" class="easyui-datebox" editable="false" data-options="required:false,formatter:myformatter,parser:myparser" style="width: 100px;"/>
							至<input id="eEndTime" name="eEndTime" class="easyui-datebox" editable="false" data-options="required:false,formatter:myformatter,parser:myparser" style="width: 100px;"/>
						</td>
						<th width="10%">状态</th>
						<td>
							<select id="state" name="state" class="easyui-combobox" panelHeight="70px" style="width:100px;" class="inputText">
								<option value="">全部</option>
								<option value="1">成功</option>								
								<option value="0">失败</option>
							</select>
							<input id="query" type="button" class="inputButton" value="查询" onclick="queryHandle('grid','queryForm');"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="批处理日志列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>
</html>