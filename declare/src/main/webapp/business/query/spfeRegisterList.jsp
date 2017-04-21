<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人结售汇额度登记指令列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
$(function () {
	initDataGrid();
	$("#regQuery").click(function(){
		if($("#seqNo").val() == "" && $("#refNo").val() == ""){
			$.messager.alert('系统提示', "业务流水号和业务参号必须输入一项！", 'info');
		}else{
			queryHandle('grid','queryForm');
		}		
	});
	
});

function initDataGrid(){
	$('#grid').datagrid({
        methord: 'post',
        url: 'spfeQuery.do?method=spfeRegisterQuery',
        //sortName: 'sortNo',
        //sortOrder: 'asc',
        //idField: 'currencyCode',
        //pageSize: 15,
        //pageList: [10,15,20],
        columns: [[
					{ field: 'txnSerialNo', title: '业务流水号', width: 60, sortable: true },
					{ field: 'bizNo', title: '业务编号', width: 60, sortable: true },
					{ field: 'refNo', title: '业务参号', width: 150, sortable: true },
					{ field: 'finishDate', title: '处理时间', width: 150, sortable: true },
					{ field: 'transStateName', title: '处理状态', width: 150, sortable: true }
				]],
        fit:true,
        queryParams:{},   //查询条件
        //pagination: true,
        rownumbers: true,
        fitColumns: true,
        striped: true 	  //奇偶行颜色不同 
        //remoteSort: true, //服务器端排序
        //singleSelect: false//多选
	});
}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 61px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">业务流水号</th>
						<td width="20%">
							<input type="text" id="seqNo" name="seqNo" class="inputText"/>
						</td>
						<th width="10%">业务参号</th>
						<td width="20%">
							<input type="text" id="refNo" name="refNo" class="inputText"/>
							<input id="regQuery" type="button" class="inputButton" value="查询"/>
						</td>						
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="个人结售汇额度登记指令列表">
        <div id="grid"></div>
    </div>
  </div>
</div>
</body>
</html>