<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>历史任务列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function () {
	initDataGrid();    
});

var checkedId = "";
var checkedRow = null;
function initDataGrid(){
	grid = $('#dialog_grid_his').datagrid({
        methord: 'post',
        url: '${pageContext.request.contextPath}/spfeMdf.do?method=queryFinishTaskData',
        sortName: 'finishDate',
        sortOrder: 'desc',
        idField: 'txnSerialNo',
        pageSize: 20,
        pageList: [10,20,50],
        columns: [[
					{ field: 'txnSerialNo', title: '业务流水号', width: 100, sortable: true },
					{ field: 'refNo', title: '业务参号', width: 150, sortable: true },
					{ field: 'tradeName', title: '交易名称', width: 150, sortable: true }
				]],
        fit:true,
        queryParams:{}, //查询条件
        pagination: true,
        rownumbers: true,
        fitColumns: true,
        striped: true, //奇偶行颜色不同 
        remoteSort: true, //服务器端排序
        singleSelect: false,//多选
        onDblClickRow: function(index, row) {
        	doChose();
        },
        rowStyler: function(index,row){
        	$("#datagrid-row-r2-2-"+index).bind('click', function(e) {
        		if(checkedId){
        			$("#"+checkedId).css({"background-color":"white"});
        		}
        		$(this).css({"background-color":"#499"});
        		checkedId = this.id;
        		checkedRow = row;
        	});
    	}

    });
}

function doChose(){
	if(!checkedId){
		$.messager.alert("系统提示", "请点击数据行选择一条任务！", "error");
		return false;
	}
	window.parent.doDialogTask(checkedRow.txnSerialNo,checkedRow.refNo, checkedRow.tradeNo);
	window.parent.showTasklistHisDialog.close();
}

function queryFinishTask() {
	if(!$("#queryForm").form('validate')){
		return false;
	}
	var startFinishTime = $("input[name=startFinishTime]").val();
	var endFinishTime = $("input[name=endFinishTime]").val();
	if (!compareDate(startFinishTime, endFinishTime)) {
		$.messager.alert("系统提示", "完成时间的结束时间必须大于或等于开始时间", "error");
		return false;
	}
	queryHandle('dialog_grid_his','queryForm');
}


</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 121px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">业务流水号</th>
						<td width="20%">
							<input type="text" id="txnSerialNo" name="txnSerialNo"  class="inputText"/>
						</td>
						<th width="10%">业务编号</th>
						<td width="20%">
							<input type="text" id="bizNo" name="bizNo"  class="inputText"/>
						</td>
					</tr>
					<tr>
						<th width="10%"><span class="warning">*</span>任务完成开始时间</th>
						<td width="20%" >
							<input id="startFinishTime" name="startFinishTime" class="easyui-datebox" data-options="required:true,formatter:myformatter,parser:myparser"/>
						</td>
						<th width="10%"><span class="warning">*</span>任务完成结束时间</th>
						<td width="20%" >
							<input id="endFinishTime" name="endFinishTime" class="easyui-datebox" data-options="required:true,formatter:myformatter,parser:myparser"/>
						</td>
					</tr>
					<tr>
						<th width="10%"><span class="warning">*</span>所属机构</th>
						<td width="20%" >
							<select id="belongOrgNo" name="belongOrgNo" class="easyui-combogrid" style="width:200px" data-options="
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
						<th width="10%">业务参号</th>
						<td >
							<input type="text" id="refNo" name="refNo"  class="inputText" style="width:150px;"/>
							&nbsp;&nbsp;<input id="save" type="button" class="inputButton" value="查询" onclick="queryFinishTask();"/> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="业务数据列表">
        <div id="dialog_grid_his" > </div>
    </div>
  </div>
</div>
</body>
</html>