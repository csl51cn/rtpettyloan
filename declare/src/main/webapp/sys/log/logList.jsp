<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function () {
	initDataGrid();    
});

function initDataGrid(){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: 'logController.do?method=query',
        sortName: 'operateDate',
        sortOrder: 'desc',
        idField: 'logId',
        pageSize: 20,
        pageList: [10,20,50],
        frozenColumns: [[
					{ field: 'ck', checkbox: true }
				]],
        columns: [[
					{ field: 'userId', title: '操作人ID', width: 100, sortable: true },
					{ field: 'userName', title: '操作人姓名', width: 100, sortable: true },
					{ field: 'operateType', title: '日志类型', width: 60, sortable: true, formatter:function(rowIndex){
						if(rowIndex=='1'){
							return '新增';
						}else if (rowIndex=='2'){
							return '编辑';
						}else if (rowIndex=='3'){
							return '删除';
						}else if (rowIndex=='4'){
							return '登录';
						}else if (rowIndex=='5'){
							return '退出';
						}
					}  },
					{ field: 'content', title: '日志内容', width: 350, sortable: false, formatter:function(value){
						return '<span style="color:blue"><a href="javascript:void(0);" onclick="showContent(\''+value+'\')">'+value.substring(0,50)+'...</a></span>';
					} },
					{ field: 'operateIp', title: '访问IP', width: 100, sortable: false },
					{ field: 'operateDate', title: '操作时间', width: 100, sortable: true }
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
    	}
    });
}

function showContent(content) {
	$.dialog({ 
		title: '日志内容',
	    content: "<span style='color:red;font-size:16px;'>"+content+"</span>", 
	    ok: function(){ 
	    	return true;
	    }, 
	    width:400,
	    height:150,
	    maxBtn:false,
	    minBtn:false,
	    lock:true 
	});
}

function deleteLog(){
	var ids = [];
	var rows = $('#grid').datagrid('getSelections');
	for(var i=0; i<rows.length; i++){
	    ids.push(rows[i].logId);
	}
	if(ids==undefined || ids==''){
		alertMsg('请选择需要删除的记录!','error');
	}else{
		deleteHandle('logController.do?method=delete&logId='+ids,'grid');
	}
}

function queryLog(){
	if ($('#queryForm').form('validate') == false) {
		return false;
	}
	var startDate = $("input[name=startDate]").val();
	var endDate = $("input[name=endDate]").val();
	
	if (!compareDate(startDate, endDate)) {
		$.messager.alert("系统提示", "结束时间必须大于或等于开始时间", "error");
		return false;
	}
	queryHandle('grid','queryForm');
	editIndex = undefined;
}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="delete" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-del"
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
						<th width="10%">日志类型</th>
						<td widht="20%">
							<select id="operateType" name="operateType" class="easyui-combobox" style="width:205px;">
								<option value="">全部</option>
								<c:forEach var="operateType" items="${operateType}">
									<option value="${operateType.code}">${operateType.value}</option>
								</c:forEach>
							</select>
						</td>
						<th width="10%">操作人ID</th>
						<td widht="20%">
							<input type="text" id="userId" name="userId" class="inputText"/>
						</td>
					</tr>
					<tr>
						<th width="10%">操作时间</th>
						<td colspan=3>
						    <input id="startDate" name="startDate" class="easyui-datebox" editable="false" data-options="required:true,formatter:myformatter,parser:myparser" style="width: 150px;"/>
							至<input id="endDate" name="endDate" class="easyui-datebox" editable="false" data-options="required:true,formatter:myformatter,parser:myparser" style="width: 150px;"/>
							&nbsp;&nbsp;<input id="save" type="button" class="inputButton" value="查询" onclick="queryLog();"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="日志列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>
</html>