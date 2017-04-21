<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统异常日志列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		initDataGrid();tt
	});

	var grid;

	function initDataGrid() {
		grid = $('#grid').datagrid(
		{methord : 'post',url : 'sysLogController.do?method=query',sortName : 'bizNo',
		sortOrder : 'asc',idField : 'bizNo',pageSize : 15,pageList : [ 10, 15, 20 ],
		/*         frozenColumns: [[{ field: 'ck', checkbox: true },]], */
	columns : [ [{field : 'bizNo',title : '业务编号',width : 80,sortable : true},
				{field : 'bizName',title : '业务名称',width : 130,sortable : true},
				{field : 'operateDate',title : '操作日期',width : 200,sortable : true},
		/* {field : 'exceptionType',title : '异常日志类型',width : 80,sortable : true}, */
				{field : 'exceptionLog',title : '异常日志',width : 100,sortable : true},
		/* {field : 'isDealed',title : '状态',width : 100,sortable : true}, */
				{field : 'opt',title : '操作',width : 120,align : 'center',rowspan : 2,
				formatter : function(value, rec) {
				var editUrl = "userController?edit&userId="+ rec.userId;
				var delUrl = "userController?delete&userId="+ rec.userId;
				return '<span style="color:blue">'+ '<a href="###" onclick="deleteLog(\''+ rec.exceptionlogId
						+ '\',\'grid\',\'\')">删除</a>&nbsp;'
						+ '<a href="###" onclick="showExceptionLogs('+ rec.exceptionlogId+ ')">查看异常信息</a>&nbsp;&nbsp;';
										}
									} ] ],
							fit : true,
							queryParams : {}, //查询条件
							pagination : true,
							rownumbers : true,
							fitColumns : true,
							striped : true, //奇偶行颜色不同 
							remoteSort : true, //服务器端排序
							singleSelect : false,//多选
							onLoadSuccess : function() {
								setTimeout(function() {
									selectRowEvent('grid');
								}, 10);
							}
						});
	}

   function showExceptionLogs(exceptionLogId) {
		$.dialog({
			title : "异常日志",
			content:'url:sysLogController.do?method=findById&exceptionLogId=' +exceptionLogId,
			ok : function() {
				this.content.save();
				return true;
			},
			cancelVal : "取消",
			cancel : true,
			width : 500,
			height : 300,
			lock : true
		});
	}  

	function deleteLog(exceptionLogId) {
		deleteHandle("sysLogController.do?method=delete&exceptionLogId=" + exceptionLogId, "grid");
	}

	function queryLog() {
		if ($('#queryForm').form('validate') == false) {
			return false;
		}
		var startDate = $("input[name=startDate]").val();
		var endDate = $("input[name=endDate]").val();

		if (!compareDate(startDate, endDate)) {
			$.messager.alert("系统提示", "结束时间必须大于或等于开始时间", "error");
			return false;
		}
		queryHandle('grid', 'queryForm');
		editIndex = undefined;
	}

	//tablelist
</script>


</head>


<body class="easyui-layout" style="overflow-y: hidden; padding: 10px;"
	scroll="no">
<!-- 	<div region="north" style="overflow: hidden;" border="false">
		<div style="padding: 1px; background: #EFEFEF;">
			<a id="delete" href="###" class="easyui-linkbutton" plain="true"
				iconCls="icon-del" onclick="deleteLog()">删除</a>
		</div>
	</div> -->
	<div region="center" border="false">
		<div id="center-div" class="easyui-layout" fit="true">
			<div region="north" title="查询条件" style="height: 92px;" id="north">
				<form id="queryForm" action="#" method="post">
					<input type="hidden" name="page" value="1" />
					<table class="editBlock" style="width: 100%;">
						<tbody>
							<tr>
								<th width="10%">业务编号</th>
								<td width="20%"><input type="text" name="bizNo"
									class="inputText" style="width: 205px;" /></td>
							<tr>
								<th width="10%">日志时间：从</th>
								<td><input name="startDate" type="text" editable="false"
									class="easyui-datebox" style="width: 205px;"
									data-options="required:true,formatter:myformatter,parser:myparser" />
								</td>
								<th width="10%">到</th>
								<td><input name="endDate" type="text" editable="false"
									class="easyui-datebox" style="width: 205px;"
									data-options="required:true,formatter:myformatter,parser:myparser" />
								</td>
								<td colspan=2><input id="save" type="button"
									class="inputButton" value="查询" onclick="queryLog();" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div region="center"
				style="width: 500px; height: 300px; padding: 1px; overflow-y: hidden"
				title="异常日志列表">
				<div id="grid"></div>
			</div>
		</div>
	</div>
</body>

</html>
