<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
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

function initDataGrid(){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: '${pageContext.request.contextPath}/flow/tasklistController.do?method=queryFinishTaskList',
        sortName: 'finishDate',
        sortOrder: 'desc',
        idField: 'txnSerialNo',
        pageSize: 20,
        pageList: [10,20,50],
        columns: [[
					{ field: 'txnSerialNo', title: '业务流水号', width: 120, sortable: false },
					{ field: 'reqSeqNo', title: '渠道流水号', width: 100, sortable: true },
					{ field: 'refNo', title: '业务参号', width: 100, sortable: true },
					{ field: 'tradeName', title: '交易名称', width: 150, sortable: true },
					{ field: 'transStateName', title: '当前状态', width: 80, sortable: true},
					{ field: 'createDate', title: '创建时间', width: 120, sle: true },
					{ field: 'finishDate', title: '完成时间', width: 120, sortable: true },
					{ field: 'createUser', title: '创建人', width: 80, sortable: true },
					{field:'opt',title:'操作',width:100,align:'center', rowspan:2,
						formatter:function(value,rec){
							return '<a href="###" onclick="showProcessHistory(\''+rec.txnSerialNo+'\',\'grid\',\'\')">查看流程历史</a>&nbsp;&nbsp;';
						}
					}
				]],
        fit:true,
        queryParams:{startFinishTime:$("#startFinishTime").val(), endFinishTime:$("#endFinishTime").val()}, //查询条件
        pagination: true,
        rownumbers: true,
        fitColumns: true,
        striped: true, //奇偶行颜色不同 
        remoteSort: true, //服务器端排序
        singleSelect: false,//多选
        onDblClickRow: function(index, row) {
        	doProcess(row.txnSerialNo, row.transState, row.url);
        },
        onLoadSuccess: function(){
            setTimeout(function(){
            	selectRowEvent('grid');
            }, 10);    
    	}
    });
}

function showProcessHistory(txnSerialNo){
	window.location.href = "${pageContext.request.contextPath}/flow/tasklistController.do?method=processHistoryList&txnSerialNo="+txnSerialNo;
}

function doProcess(txnSerialNo,transState, procUrl) {
	var url = procUrl + "&txnSerialNo="+txnSerialNo+"&transState="+transState;
	viewHandle("${pageContext.request.contextPath}/"+url);
}

function queryFinishTask() {
	var startCreateTime = $("input[name=startCreateTime]").val();
	var endCreateTime = $("input[name=endCreateTime]").val();
	if (!compareDate(startCreateTime, endCreateTime)) {
		$.messager.alert("系统提示", "创建时间的结束时间必须大于或等于开始时间", "error");
		return false;
	}
	var startFinishTime = $("input[name=startFinishTime]").val();
	var endFinishTime = $("input[name=endFinishTime]").val();
	if (!compareDate(startFinishTime, endFinishTime)) {
		$.messager.alert("系统提示", "完成时间的结束时间必须大于或等于开始时间", "error");
		return false;
	}
	queryHandle('grid','queryForm');
}

</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 150px;" id="north">
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
						<th width="10%">业务参号</th>
						<td width="20%">
							<input type="text" id="refNo" name="refNo" class="inputText"/>
						</td>
						<th width="10%">业务状态</th>
						<td width="20%">
							<select id="sfzx" name="sfzx" class="easyui-combobox" style="width:205px;">
								<option value="ALL">全部</option>
								<option value="Y" selected="selected">有效</option>
								<option value="N">无效</option>
							</select>
						</td>
					</tr>
					<tr>
						<th width="10%">渠道流水号</th>
						<td width="20%">
							<input type="text" id="reqSeqNo" name="reqSeqNo" class="inputText"/>
						</td>
						<th width="10%">渠道</th>
						<td width="20%">
							<select id="channelId" name="channelId" class="easyui-combobox" style="width:205px;">
								<option value="">全部</option>
								<c:forEach items="${channels }" var="channel">
									<option value="${channel.channelId }">${channel.channelCnName }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th width="10%">创建时间</th>
						<td width="20%" >
							<input id="startCreateTime" name="startCreateTime" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"/>
							至<input id="endCreateTime" name="endCreateTime" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"/>
						</td>
						<th width="10%">完成时间</th>
						<td width="20%" >
							<input id="startFinishTime" name="startFinishTime" value="${model.startFinishTime }" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"/>
							至<input id="endFinishTime" name="endFinishTime" value="${model.endFinishTime }" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"/>
							&nbsp;&nbsp;<input id="save" type="button" class="inputButton" value="查询" onclick="queryFinishTask();"/> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="历史任务列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>
</html>