<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>参与任务列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function () {
	initDataGrid();    
});

function initDataGrid(){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: '${pageContext.request.contextPath}/flow/tasklistController.do?method=queryPartTaskList',
        sortName: 'createDate',
        sortOrder: 'desc',
        idField: 'txnSerialNo',
        pageSize: 20,
        pageList: [10,20,50],
        columns: [[
					{ field: 'txnSerialNo', title: '业务流水号', width: 120, sortable: false },
					{ field: 'bizNo', title: '业务编号', width: 100, sortable: true },
					{ field: 'tradeName', title: '交易名称', width: 150, sortable: true },
					{ field: 'transStateName', title: '当前状态', width: 80, sortable: true},
					{ field: 'createDate', title: '创建时间', width: 120, sortable: true },
					{ field: 'createUser', title: '创建人', width: 80, sortable: true },
					{field:'opt',title:'操作',width:100,align:'center', rowspan:2,
						formatter:function(value,rec){
							return '<a href="###" onclick="showProcessHistory(\''+rec.txnSerialNo+'\',\'grid\',\'\')">查看流程历史</a>&nbsp;&nbsp;';
						}
					}
				]],
        fit:true,
        queryParams:{}, //查询条件
        pagination: true,
        rownumbers: true,
        fitColumns: true,
        striped: true, //奇偶行颜色不同 
        remoteSort: true, //服务器端排序
        singleSelect: false,//多选
        onLoadSuccess: function(){
            setTimeout(function(){
            	selectRowEvent('grid');
            }, 10);    
    	},
    	onDblClickRow: function(index, row) {
        	doProcess(row.txnSerialNo, row.transState, row.url);
        }
    });
}

function showProcessHistory(txnSerialNo){
	window.location.href = "${pageContext.request.contextPath}/flow/tasklistController.do?method=processHistoryList&txnSerialNo="+txnSerialNo;
}

function doProcess(txnSerialNo,transState, procUrl) {
	var url = procUrl + "&txnSerialNo="+txnSerialNo+"&transState=6";
	viewHandle("${pageContext.request.contextPath}/"+url);
}


function queryPartTask() {
	var startCreateTime = $("input[name=startCreateTime]").val();
	var endCreateTime = $("input[name=endCreateTime]").val();
	if (!compareDate(startCreateTime, endCreateTime)) {
		$.messager.alert("系统提示", "创建时间的结束时间必须大于或等于开始时间", "error");
		return false;
	}
	queryHandle('grid','queryForm');
}

</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 90px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">业务流水号</th>
						<td width="20%">
							<input type="text" id="txnSerialNo" name="txnSerialNo" class="inputText"/>
						</td>
						<th width="10%">业务类型</th>
						<td width="20%">
							<input class="easyui-combogrid" id="tradeNo" name="tradeNo" style="width:255px;"
								data-options="
									panelWidth: 255,
									idField: 'tradeNo',
									textField: 'tradeName',
									url: '${pageContext.request.contextPath}/flow/tradeCodeController.do?method=getTradeCode',
									columns: [[
										{field:'tradeNo',title:'交易编号',width:60},
										{field:'tradeName',title:'交易名称',width:195}
									]],
									fitColumns: true,
									nowrap:false">
						</td>
					</tr>
					<tr>
						<th width="10%">创建日期</th>
						<td colspan="3" >
							<input id="startCreateTime" name="startCreateTime" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"/>
							至<input id="endCreateTime" name="endCreateTime" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"/>
							&nbsp;&nbsp;<input id="save" type="button" class="inputButton" value="查询" onclick="queryPartTask();"/> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="参与任务列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>
</html>