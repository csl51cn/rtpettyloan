<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>美元折算率列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
$(function () {
	initDataGrid();
	loadYearAndMonthDate("queryForm","yearMonth");
	$("#usdQuery").click(function(){
		if($("#currencyCode").combobox("getValue") == "" && $("#yearMonth").val() == ""){
			$.messager.alert('系统提示', "币种代码和年月必须输入一项！", 'info');
		}else{
			queryHandle('grid','queryForm');
		}		
	});
	
});

function initDataGrid(){
	$('#grid').datagrid({
        methord: 'post',
        url: 'spfeQuery.do?method=usdCvsRateQuery',
        //sortName: 'sortNo',
        //sortOrder: 'asc',
        //idField: 'currencyCode',
        pageSize: 15,
        pageList: [10,15,20],
        columns: [[
					{ field: 'yearMonth', title: '年月', width: 60, sortable: true },
					{ field: 'currencyCode', title: '币种', width: 60, sortable: true },
					{ field: 'exchange', title: '折算率', width: 150, sortable: true }
				]],
        fit:true,
        queryParams:{"yearMonth":"${yearMonth}"},   //查询条件
        pagination: true,
        rownumbers: true,
        fitColumns: true,
        striped: true 	  //奇偶行颜色不同 
        //remoteSort: true, //服务器端排序
        //singleSelect: false//多选
	});
}

/**
 * 导入折算率
 */
function importSafeExRate() {
	var yearMonth = $("#yearMonth").val();
	createHandle('spfeQuery.do?method=importSafeExRate&yearMonth=' + yearMonth);
}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="add" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="importSafeExRate();">导入折算率</a>
        <a id="activate" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-unlock"
           onclick="createHandle('spfeQuery.do?method=copyPreMonthExRate')">沿用折算率</a>
    </div>
</div>
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 71px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">币种代码</th>
						<td width="20%">
						<input class="easyui-combobox" id="currencyCode" name="currencyCode" style="width:250px;"
							data-options="
								panelWidth: 250,
								panelHeight: 160,
								valueField: 'curSign',
								textField: 'curSign',
								url: '${pageContext.request.contextPath}/param/paramCommonController.do?method=getCurrencys',
								fitColumns: true" />
						</td>
						<th width="10%">年月</th>
						<td width="20%">
							<input type="text" id="yearMonth" name="yearMonth" readOnly="true" style="width: 120px;" value="${yearMonth}" class="easyui-validatebox inputText"/>
							<input id="usdQuery" type="button" class="inputButton" value="查询"/>
						</td>						
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="美元折算率列表">
        <div id="grid"></div>
    </div>
  </div>
</div>
</body>
</html>